package zw.co.techtrendz.school.management.api.service.impl;

import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.dto.CourseworkDropdown;
import zw.co.techtrendz.school.management.api.dto.ReportsFilters;
import zw.co.techtrendz.school.management.api.dto.YearDropdown;
import zw.co.techtrendz.school.management.api.entity.ExtraCurricular;
import zw.co.techtrendz.school.management.api.entity.Subject;
import zw.co.techtrendz.school.management.api.querydsl.CourseworkQueryService;
import zw.co.techtrendz.school.management.api.querydsl.SubjectYearQueryService;
import zw.co.techtrendz.school.management.api.service.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportsDropdownsServiceImpl implements ReportsDropdownsService {

    private final StudentService studentService;
    private final TeacherService teacherService;
    private final CourseworkQueryService courseworkQueryService;
    private final ClassroomService classroomService;
    private final SubjectService subjectService;
    private final ExtraCurricularService extraCurricularService;
    private final SubjectYearQueryService subjectYearQueryService;

    public ReportsDropdownsServiceImpl(
            StudentService studentService,
            TeacherService teacherService,
            CourseworkQueryService courseworkQueryService,
            ClassroomService classroomService,
            SubjectService subjectService,
            ExtraCurricularService extraCurricularService,
            SubjectYearQueryService subjectYearQueryService
    ) {
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.courseworkQueryService = courseworkQueryService;
        this.classroomService = classroomService;
        this.subjectService = subjectService;
        this.extraCurricularService = extraCurricularService;
        this.subjectYearQueryService = subjectYearQueryService;
    }

    @Override
    public ReportsFilters getAllDropdowns() {
        return new ReportsFilters(
                this.getAllStudentsForDropdown(),
                this.getAllTeachersForDropdown(),
                this.getAllCourseworksForDropdown(),
                this.getAllClassroomsForDropdown(),
                this.getAllSubjectsForDropdown(),
                this.getAllExtraCurricularsForDropdown(),
                this.getAllCalendarYearsForDropdown()
        );
    }

    @Override
    public List<YearDropdown> getAllStudentsForDropdown() {
        return this.studentService.getAllStudentsForDropdown();
    }

    @Override
    public List<YearDropdown> getAllTeachersForDropdown() {
        return this.teacherService.getAllTeachersForDropdown();
    }

    @Override
    public List<CourseworkDropdown> getAllCourseworksForDropdown() {
        return this.courseworkQueryService.getAllCourseworkForDropdown();
    }

    @Override
    public List<YearDropdown> getAllClassroomsForDropdown() {
        List<zw.co.techtrendz.school.management.api.entity.Classroom> classrooms = this.classroomService.getAllList(List.of("name"), org.springframework.data.domain.Sort.Direction.ASC);
        return classrooms.stream()
                .map(c -> new YearDropdown(c.getId(), c.getName(), null))
                .collect(Collectors.toList());
    }

    @Override
    public List<YearDropdown> getAllSubjectsForDropdown() {
        List<Subject> subjects = this.subjectService.getAllList(List.of("name"), org.springframework.data.domain.Sort.Direction.ASC);
        return subjects.stream()
                .map(s -> new YearDropdown(s.getId(), s.getName(), null))
                .collect(Collectors.toList());
    }

    @Override
    public List<YearDropdown> getAllExtraCurricularsForDropdown() {
        List<ExtraCurricular> extraCurriculars = this.extraCurricularService.getAllList(List.of("name"), org.springframework.data.domain.Sort.Direction.ASC);
        return extraCurriculars.stream()
                .map(e -> new YearDropdown(e.getId(), e.getName(), null))
                .collect(Collectors.toList());
    }

    @Override
    public List<Integer> getAllCalendarYearsForDropdown() {
        return this.subjectYearQueryService.getCalendarYears();
    }

    @Override
    public ReportsFilters getSelectiveDropdowns(
            Boolean students,
            Boolean teachers,
            Boolean courseworks,
            Boolean classrooms,
            Boolean subjects,
            Boolean extraCurriculars,
            Boolean calendarYears
    ) {
        ReportsFilters reportsFilters = new ReportsFilters();

        if (students != null && students) {
            reportsFilters.setStudents(this.getAllStudentsForDropdown());
        }
        if (teachers != null && teachers) {
            reportsFilters.setTeachers(this.getAllTeachersForDropdown());
        }
        if (courseworks != null && courseworks) {
            reportsFilters.setCourseworks(this.getAllCourseworksForDropdown());
        }
        if (classrooms != null && classrooms) {
            reportsFilters.setClassrooms(this.getAllClassroomsForDropdown());
        }
        if (subjects != null && subjects) {
            reportsFilters.setStudents(this.getAllSubjectsForDropdown());
        }
        if (extraCurriculars != null && extraCurriculars) {
            reportsFilters.setExtraCurriculars(this.getAllExtraCurricularsForDropdown());
        }
        if (calendarYears != null && calendarYears) {
            reportsFilters.setCalendarYears(this.getAllCalendarYearsForDropdown());
        }

        return reportsFilters;
    }
}