package zw.co.techtrendz.school.management.api.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import zw.co.techtrendz.school.management.api.entity.*;
import zw.co.techtrendz.school.management.api.querydsl.ActivityPointQueryService;
import zw.co.techtrendz.school.management.api.querydsl.ExtraCurricularYearQueryService;
import zw.co.techtrendz.school.management.api.querydsl.StudentQueryService;
import zw.co.techtrendz.school.management.api.querydsl.TeacherQueryService;
import zw.co.techtrendz.school.management.api.repository.ActivityPointRepository;
import zw.co.techtrendz.school.management.api.service.ActivityPointService;
import zw.co.techtrendz.school.management.api.service.RatingService;
import zw.co.techtrendz.school.management.api.utility.AppUtils;
import zw.co.techtrendz.school.management.api.view.StudentDto;
import zw.co.techtrendz.school.management.api.view.TeacherDto;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ActivityPointServiceImpl implements ActivityPointService {

    private final ActivityPointRepository activityPointRepository;
    private final ActivityPointQueryService activityPointQueryService;
    private final RatingService ratingService;
    private final ExtraCurricularYearQueryService extraCurricularYearQueryService;
    private final TeacherQueryService teacherQueryService;
    private final StudentQueryService studentQueryService;

    public ActivityPointServiceImpl(ActivityPointRepository activityPointRepository,
                                    ActivityPointQueryService activityPointQueryService,
                                    RatingService ratingService,
                                    ExtraCurricularYearQueryService extraCurricularYearQueryService,
                                    TeacherQueryService teacherQueryService,
                                    StudentQueryService studentQueryService) {
        this.activityPointRepository = activityPointRepository;
        this.activityPointQueryService = activityPointQueryService;
        this.ratingService = ratingService;
        this.extraCurricularYearQueryService = extraCurricularYearQueryService;
        this.teacherQueryService = teacherQueryService;
        this.studentQueryService = studentQueryService;
    }

    @Override
    public ActivityPoint save(ActivityPoint activityPoint) {
        return this.activityPointRepository.save(activityPoint);
    }

    @Override
    public List<ActivityPoint> saveAll(List<ActivityPoint> activityPoints) {
        return this.activityPointRepository.saveAll(activityPoints);
    }

    @Override
    public ActivityPoint getById(long id) {
        ActivityPoint activityPoint = this.activityPointQueryService.getById(id);
        if (activityPoint == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Activity point with id '%d' was not found", id));
        }
        activityPoint.setRatings(this.ratingService.getAllByActivityId(id));
        return activityPoint;
    }

    @Override
    public List<ActivityPoint> getAll() {
        List<ActivityPoint> activityPoints = this.activityPointQueryService.getAll();
        return this.getRatingsForActivityPoints(activityPoints);
    }

    @Override
    public List<ActivityPoint> getAllByActivityId(long activityId) {
        List<ActivityPoint> activityPoints = this.activityPointQueryService.getAllByActivityId(activityId);
        return this.getRatingsForActivityPoints(activityPoints);
    }

    @Override
    public List<ActivityPoint> getAllByActivityIds(List<Long> activityIds) {
        List<ActivityPoint> activityPoints = this.activityPointQueryService.getAllByActivityIds(activityIds);
        return this.getRatingsForActivityPoints(activityPoints);
    }

    @Override
    public List<ActivityPoint> getAllByStudentId(long studentId) {
        List<ActivityPoint> activityPoints = this.activityPointQueryService.getAllByStudentId(studentId);
        return this.getRatingsForActivityPoints(activityPoints);
    }

    @Override
    public ActivityPoint getByStudentIdByActivityId(long studentId, long activityId) {
        ActivityPoint activityPoint = this.activityPointQueryService.getByStudentIdByActivityId(studentId, activityId);
        activityPoint.setRatings(this.ratingService.getAllByActivityId(activityId));
        return activityPoint;
    }

    private List<ActivityPoint> getRatingsForActivityPoints(List<ActivityPoint> activityPoints) {
        List<Long> ids = activityPoints
                .parallelStream()
                .map(ActivityPoint::getId)
                .collect(Collectors.toCollection(
                        CopyOnWriteArrayList::new
                ));

        if (ids.isEmpty()) {
            return activityPoints;
        }

        List<Rating> ratings = this.ratingService.getAllByActivityIds(ids);
        Map<Long, List<Rating>> mappedRatings = ratings
                .parallelStream()
                .collect(Collectors.groupingBy(r -> r.getActivityPoint().getId()));

        return activityPoints
                .parallelStream()
                .map(activityPoint -> {
                    activityPoint.setRatings(mappedRatings.get(activityPoint.getId()));
                    return activityPoint;
                })
                .collect(Collectors.toCollection(
                                CopyOnWriteArrayList::new
                        )
                );
    }

    public List<ActivityPoint> getRatingsForReports(List<ActivityPoint> activityPoints) {
        List<Long> ids = activityPoints
                .parallelStream()
                .map(ActivityPoint::getId)
                .collect(Collectors.toCollection(
                        CopyOnWriteArrayList::new
                ));

        if (ids.isEmpty()) {
            return activityPoints;
        }

        // Fetch and populate ratings
        List<Rating> ratings = this.ratingService.getAllByActivityIds(ids);
        Map<Long, List<Rating>> mappedRatings = ratings
                .parallelStream()
                .collect(Collectors.groupingBy(r -> r.getActivityPoint().getId()));

        // Fetch and populate ECA data
        List<Long> ecaIds = activityPoints
                .parallelStream()
                .filter(ap -> ap.getExtraCurricularYear() != null)
                .map(ap -> ap.getExtraCurricularYear().getId())
                .distinct()
                .collect(Collectors.toCollection(CopyOnWriteArrayList::new));

        // Group facilitators and students by ECA ID
        Map<Long, List<Teacher>> mappedFacilitators = null;
        Map<Long, List<Student>> mappedStudents = null;

        if (!ecaIds.isEmpty()) {
            // Fetch facilitators and students for these ECA IDs
            List<TeacherDto> facilitators = this.teacherQueryService.getFacilitatorsByExtraCurricular(ecaIds);
            List<StudentDto> students = this.studentQueryService.getStudentsByExtraCurricular(ecaIds);

                        facilitators
                    .stream()
                    .forEach(facilitator -> {
                        Long ecaId = facilitator.getExtraCurricularYearId();
                        Teacher teacher = AppUtils.dtoToTeacher(facilitator);
                        if (!mappedFacilitators.get(ecaId).isEmpty()) {
                            List<Teacher> temp = mappedFacilitators.get(ecaId);
                            temp.add(teacher);
                            mappedFacilitators.put(ecaId, temp);
                        } else {
                            List<Teacher> temp = new ArrayList<>();
                            temp.add(teacher);
                            mappedFacilitators.put(ecaId, temp);
                        }
                    });

            students
                    .stream()
                    .forEach(studentDto -> {
                        Long ecaId = studentDto.getExtraCurricularYearId();
                        Student student = AppUtils.dtoToStudent(studentDto);
                        if (!mappedStudents.get(ecaId).isEmpty()) {
                            List<Student> temp = mappedStudents.get(ecaId);
                            temp.add(student);
                            mappedStudents.put(ecaId, temp);
                        } else {
                            List<Student> temp = new ArrayList<>();
                            temp.add(student);
                            mappedStudents.put(ecaId, temp);
                        }
                    });
        }

        return activityPoints
                .parallelStream()
                .map(activityPoint -> {
                    // Set ratings
                    activityPoint.setRatings(mappedRatings.get(activityPoint.getId()));

                    // Set ECA data if available
                    if (activityPoint.getExtraCurricularYear() != null && !ecaIds.isEmpty()) {
                        Long ecaId = activityPoint.getExtraCurricularYear().getId();
                        ExtraCurricularYear extraCurricularYear = activityPoint.getExtraCurricularYear();
                        if (mappedFacilitators != null && !mappedFacilitators.isEmpty()) {
                            extraCurricularYear.setFacilitators(mappedFacilitators.get(ecaId));
                        }
                        if (mappedStudents != null && !mappedStudents.isEmpty()) {
                            extraCurricularYear.setStudents(mappedStudents.get(ecaId));
                        }
                    }

                    return activityPoint;
                })
                .collect(Collectors.toCollection(
                                CopyOnWriteArrayList::new
                        )
                );
    }

    @Override
    public List<ActivityPoint> getAllByTeacherId(long teacherId) {
        List<ActivityPoint> activityPoints = this.activityPointQueryService.getAllByTeacherId(teacherId);
        return this.getRatingsForReports(activityPoints);
    }

    @Override
    public List<ActivityPoint> getAllByClassroomId(long classroomId) {
        List<ActivityPoint> activityPoints = this.activityPointQueryService.getAllByClassroomId(classroomId);
        return this.getRatingsForReports(activityPoints);
    }

    @Override
    public List<ActivityPoint> getAllByFacilitatorId(long facilitatorId) {
        List<ActivityPoint> activityPoints = this.activityPointQueryService.getAllByFacilitatorId(facilitatorId);
        return this.getRatingsForReports(activityPoints);
    }

    @Override
    public List<ActivityPoint> getAllByStudentIdByActivityIdByYear(long studentId, long activityId, Integer year) {
        List<ActivityPoint> activityPoints = this.activityPointQueryService.getAllByStudentIdByActivityIdByYear(studentId, activityId, year);
        return this.getRatingsForReports(activityPoints);
    }

    @Override
    public List<ActivityPoint> getAllByStudentIdByYear(long studentId, Integer year) {
        List<ActivityPoint> activityPoints = this.activityPointQueryService.getAllByStudentIdByYear(studentId, year);
        return this.getRatingsForReports(activityPoints);
    }

    @Override
    public List<ActivityPoint> getAllByActivityIdByYear(long activityId, Integer year) {
        List<ActivityPoint> activityPoints = this.activityPointQueryService.getAllByActivityIdByYear(activityId, year);
        return this.getRatingsForReports(activityPoints);
    }

    @Override
    public List<ActivityPoint> getAllByTeacherIdByYear(long teacherId, Integer year) {
        List<ActivityPoint> activityPoints = this.activityPointQueryService.getAllByTeacherIdByYear(teacherId, year);
        return this.getRatingsForReports(activityPoints);
    }
}
