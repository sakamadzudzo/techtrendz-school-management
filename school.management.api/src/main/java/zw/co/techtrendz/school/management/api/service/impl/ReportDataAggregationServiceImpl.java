package zw.co.techtrendz.school.management.api.service.impl;

import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.dto.MarksReportDto;
import zw.co.techtrendz.school.management.api.dto.ProgressReportDto;
import zw.co.techtrendz.school.management.api.dto.RatingsReportDto;
import zw.co.techtrendz.school.management.api.entity.*;
import zw.co.techtrendz.school.management.api.service.ReportDataAggregationService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportDataAggregationServiceImpl implements ReportDataAggregationService {

    // Marks data aggregation methods
    @Override
    public List<MarksReportDto> aggregateMarksData(List<Mark> marks) {
        return marks.stream()
                .map(this::mapMarkToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MarksReportDto> aggregateMarksDataByStudentClassroomYear(List<Mark> marks, Long studentId, Long classroomId, Integer year) {
        return marks.stream()
                .filter(mark -> matchesStudentClassroomYear(mark, studentId, classroomId, year))
                .map(this::mapMarkToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MarksReportDto> aggregateMarksDataByStudentSubjectYear(List<Mark> marks, Long studentId, Long subjectId, Integer year) {
        return marks.stream()
                .filter(mark -> matchesStudentSubjectYear(mark, studentId, subjectId, year))
                .map(this::mapMarkToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MarksReportDto> aggregateMarksDataByStudentYear(List<Mark> marks, Long studentId, Integer year) {
        return marks.stream()
                .filter(mark -> matchesStudentYear(mark, studentId, year))
                .map(this::mapMarkToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MarksReportDto> aggregateMarksDataByClassroomYear(List<Mark> marks, Long classroomId, Integer year) {
        return marks.stream()
                .filter(mark -> matchesClassroomYear(mark, classroomId, year))
                .map(this::mapMarkToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MarksReportDto> aggregateMarksDataBySubjectYear(List<Mark> marks, Long subjectId, Integer year) {
        return marks.stream()
                .filter(mark -> matchesSubjectYear(mark, subjectId, year))
                .map(this::mapMarkToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MarksReportDto> aggregateMarksDataByTeacherYear(List<Mark> marks, Long teacherId, Integer year) {
        return marks.stream()
                .filter(mark -> matchesTeacherYear(mark, teacherId, year))
                .map(this::mapMarkToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MarksReportDto> aggregateMarksDataByYear(List<Mark> marks, Integer year) {
        return marks.stream()
                .filter(mark -> matchesYear(mark, year))
                .map(this::mapMarkToDto)
                .collect(Collectors.toList());
    }

    // Ratings data aggregation methods
    @Override
    public List<RatingsReportDto> aggregateRatingsData(List<Rating> ratings) {
        return ratings.stream()
                .map(this::mapRatingToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RatingsReportDto> aggregateRatingsDataByStudentActivityYear(List<Rating> ratings, Long studentId, Long activityId, Integer year) {
        return ratings.stream()
                .filter(rating -> matchesStudentActivityYear(rating, studentId, activityId, year))
                .map(this::mapRatingToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RatingsReportDto> aggregateRatingsDataByStudentYear(List<Rating> ratings, Long studentId, Integer year) {
        return ratings.stream()
                .filter(rating -> matchesStudentYear(rating, studentId, year))
                .map(this::mapRatingToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RatingsReportDto> aggregateRatingsDataByActivityYear(List<Rating> ratings, Long activityId, Integer year) {
        return ratings.stream()
                .filter(rating -> matchesActivityYear(rating, activityId, year))
                .map(this::mapRatingToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RatingsReportDto> aggregateRatingsDataByTeacherYear(List<Rating> ratings, Long teacherId, Integer year) {
        return ratings.stream()
                .filter(rating -> matchesTeacherYear(rating, teacherId, year))
                .map(this::mapRatingToDto)
                .collect(Collectors.toList());
    }

    // ActivityPoint-based ratings data aggregation methods (for enhanced reports)
    @Override
    public List<RatingsReportDto> aggregateRatingsDataFromActivityPoints(List<ActivityPoint> activityPoints) {
        return activityPoints.stream()
                .flatMap(activityPoint -> mapActivityPointToRatingsDto(activityPoint).stream())
                .collect(Collectors.toList());
    }

    @Override
    public List<RatingsReportDto> aggregateRatingsDataFromActivityPointsByStudentActivityYear(List<ActivityPoint> activityPoints, Long studentId, Long activityId, Integer year) {
        return activityPoints.stream()
                .filter(point -> matchesStudentActivityYearFromActivityPoint(point, studentId, activityId, year))
                .flatMap(activityPoint -> mapActivityPointToRatingsDto(activityPoint).stream())
                .collect(Collectors.toList());
    }

    @Override
    public List<RatingsReportDto> aggregateRatingsDataFromActivityPointsByStudentYear(List<ActivityPoint> activityPoints, Long studentId, Integer year) {
        return activityPoints.stream()
                .filter(point -> matchesStudentYearFromActivityPoint(point, studentId, year))
                .flatMap(activityPoint -> mapActivityPointToRatingsDto(activityPoint).stream())
                .collect(Collectors.toList());
    }

    @Override
    public List<RatingsReportDto> aggregateRatingsDataFromActivityPointsByActivityYear(List<ActivityPoint> activityPoints, Long activityId, Integer year) {
        return activityPoints.stream()
                .filter(point -> matchesActivityYearFromActivityPoint(point, activityId, year))
                .flatMap(activityPoint -> mapActivityPointToRatingsDto(activityPoint).stream())
                .collect(Collectors.toList());
    }

    @Override
    public List<RatingsReportDto> aggregateRatingsDataFromActivityPointsByTeacherYear(List<ActivityPoint> activityPoints, Long teacherId, Integer year) {
        return activityPoints.stream()
                .filter(point -> matchesTeacherYearFromActivityPoint(point, teacherId, year))
                .flatMap(activityPoint -> mapActivityPointToRatingsDto(activityPoint).stream())
                .collect(Collectors.toList());
    }

    // Progress data aggregation methods
    @Override
    public List<ProgressReportDto> aggregateProgressData(List<ActivityPoint> activityPoints) {
        return activityPoints.stream()
                .map(this::mapActivityPointToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProgressReportDto> aggregateProgressDataByStudent(List<ActivityPoint> activityPoints, Long studentId, String entityType) {
        return activityPoints.stream()
                .filter(point -> matchesStudent(point, studentId))
                .map(point -> mapActivityPointToDto(point, entityType))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProgressReportDto> aggregateProgressDataByTeacher(List<ActivityPoint> activityPoints, Long teacherId, String entityType) {
        return activityPoints.stream()
                .filter(point -> matchesTeacher(point, teacherId))
                .map(point -> mapActivityPointToDto(point, entityType))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProgressReportDto> aggregateProgressDataBySubject(List<ActivityPoint> activityPoints, Long subjectId, String entityType) {
        return activityPoints.stream()
                .filter(point -> matchesSubject(point, subjectId))
                .map(point -> mapActivityPointToDto(point, entityType))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProgressReportDto> aggregateProgressDataByClassroom(List<ActivityPoint> activityPoints, Long classroomId, String entityType) {
        return activityPoints.stream()
                .filter(point -> matchesClassroom(point, classroomId))
                .map(point -> mapActivityPointToDto(point, entityType))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProgressReportDto> aggregateProgressDataByActivity(List<ActivityPoint> activityPoints, Long activityId, String entityType) {
        return activityPoints.stream()
                .filter(point -> matchesActivity(point, activityId))
                .map(point -> mapActivityPointToDto(point, entityType))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProgressReportDto> aggregateProgressDataByFacilitator(List<ActivityPoint> activityPoints, Long facilitatorId, String entityType) {
        return activityPoints.stream()
                .filter(point -> matchesFacilitator(point, facilitatorId))
                .map(point -> mapActivityPointToDto(point, entityType))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProgressReportDto> aggregateProgressDataBySubjectFromMarks(List<Mark> marks, Long subjectId, String entityType) {
        return marks.stream()
                .filter(mark -> matchesSubjectFromMark(mark, subjectId))
                .map(mark -> mapMarkToProgressDto(mark, entityType))
                .collect(Collectors.toList());
    }

    // Helper methods for entity to DTO mapping
    private MarksReportDto mapMarkToDto(Mark mark) {
        return new MarksReportDto(
                mark.getStudent() != null ? mark.getStudent().getPerson().getName(): "",
                mark.getStudent() != null ? mark.getStudent().getId().toString() : "",
                mark.getStudent() != null && mark.getStudent().getClassroom() != null ? mark.getStudent().getClassroom().getClassroom().getName() : "",
                mark.getCoursework() != null && mark.getCoursework().getSubject() != null && mark.getCoursework().getSubject().getSubject() != null ?
                    mark.getCoursework().getSubject().getSubject().getName() : "",
                mark.getCoursework() != null ? mark.getCoursework().getTitle() : "",
                round2(BigDecimal.valueOf(mark.getMark())),
                mark.getCoursework() != null ? round2(BigDecimal.valueOf(mark.getCoursework().getPassMark())) : BigDecimal.ZERO,
                round2(BigDecimal.valueOf(mark.getMark())),
                calculateGrade(BigDecimal.valueOf(mark.getMark()), mark.getCoursework() != null ? BigDecimal.valueOf(mark.getCoursework().getPassMark()) : BigDecimal.ZERO),
                mark.getCoursework() != null && mark.getCoursework().getSubject() != null ?
                    mark.getCoursework().getSubject().getCalendarYear() : null,
                mark.getCoursework() != null && mark.getCoursework().getSubject() != null &&
                    mark.getCoursework().getSubject().getTeacher() != null ?
                    mark.getCoursework().getSubject().getTeacher().getPerson().getName() : ""
        );
    }

    private RatingsReportDto mapRatingToDto(Rating rating) {
        return new RatingsReportDto(
                rating.getActivityPoint() != null && rating.getActivityPoint().getStudent() != null ?
                    rating.getActivityPoint().getStudent().getPerson().getName() : "",
                rating.getActivityPoint() != null && rating.getActivityPoint().getStudent() != null ?
                    rating.getActivityPoint().getStudent().getId().toString() : "",
                rating.getActivityPoint() != null && rating.getActivityPoint().getExtraCurricularYear() != null &&
                    rating.getActivityPoint().getExtraCurricularYear().getExtraCurricular() != null ?
                    rating.getActivityPoint().getExtraCurricularYear().getExtraCurricular().getName() : "",
                rating.getRating() != null ? round2(BigDecimal.valueOf(rating.getRating())) : BigDecimal.ZERO,
                rating.getRating() != null ? round2(BigDecimal.valueOf(rating.getRating())) : BigDecimal.ZERO, // Percentage is same as rating
                round2(BigDecimal.valueOf(50)), // Set pass percentage to 50 as default threshold
                rating.getActivityPoint() != null && rating.getActivityPoint().getExtraCurricularYear() != null ?
                    rating.getActivityPoint().getExtraCurricularYear().getLeadFacilitator() != null ?
                        rating.getActivityPoint().getExtraCurricularYear().getLeadFacilitator().getPerson().getName() : "" : "",
                rating.getActivityPoint() != null && rating.getActivityPoint().getExtraCurricularYear() != null ?
                    rating.getActivityPoint().getExtraCurricularYear().getCalendarYear() : null
        );
    }

    private ProgressReportDto mapActivityPointToDto(ActivityPoint activityPoint) {
        return new ProgressReportDto(
                activityPoint.getStudent() != null ? activityPoint.getStudent().getPerson().getName() : "Unknown",
                activityPoint.getStudent() != null ? activityPoint.getStudent().getId().toString() : "",
                activityPoint.getExtraCurricularYear() != null ? activityPoint.getExtraCurricularYear().getCalendarYear() : null,
                round2(BigDecimal.valueOf(activityPoint.getOveralRating())),
                BigDecimal.ZERO, // Previous value would need to be calculated based on historical data
                BigDecimal.ZERO, // Percentage change would need to be calculated
                "STABLE", // Trend would need to be calculated
                null // Yearly data would need to be populated separately
        );
    }

    private ProgressReportDto mapActivityPointToDto(ActivityPoint activityPoint, String entityType) {
        ProgressReportDto dto = mapActivityPointToDto(activityPoint);
        dto.setEntityName(entityType);
        return dto;
    }

    // Helper methods for filtering
    private boolean matchesStudentClassroomYear(Mark mark, Long studentId, Long classroomId, Integer year) {
        return mark.getStudent() != null && mark.getStudent().getId().equals(studentId) &&
               mark.getStudent().getClassroom() != null && mark.getStudent().getClassroom().getId().equals(classroomId) &&
               mark.getCoursework() != null && mark.getCoursework().getSubject() != null &&
               mark.getCoursework().getSubject().getCalendarYear().equals(year);
    }

    private boolean matchesStudentSubjectYear(Mark mark, Long studentId, Long subjectId, Integer year) {
        return mark.getStudent() != null && mark.getStudent().getId().equals(studentId) &&
               mark.getCoursework() != null && mark.getCoursework().getSubject() != null &&
               mark.getCoursework().getSubject().getSubject() != null &&
               mark.getCoursework().getSubject().getSubject().getId().equals(subjectId) &&
               mark.getCoursework().getSubject().getCalendarYear().equals(year);
    }

    private boolean matchesStudentYear(Mark mark, Long studentId, Integer year) {
        return mark.getStudent() != null && mark.getStudent().getId().equals(studentId) &&
               mark.getCoursework() != null && mark.getCoursework().getSubject() != null &&
               mark.getCoursework().getSubject().getCalendarYear().equals(year);
    }

    private boolean matchesClassroomYear(Mark mark, Long classroomId, Integer year) {
        return mark.getStudent() != null && mark.getStudent().getClassroom() != null &&
               mark.getStudent().getClassroom().getId().equals(classroomId) &&
               mark.getCoursework() != null && mark.getCoursework().getSubject() != null &&
               mark.getCoursework().getSubject().getCalendarYear().equals(year);
    }

    private boolean matchesSubjectYear(Mark mark, Long subjectId, Integer year) {
        return mark.getCoursework() != null && mark.getCoursework().getSubject() != null &&
               mark.getCoursework().getSubject().getSubject() != null &&
               mark.getCoursework().getSubject().getSubject().getId().equals(subjectId) &&
               mark.getCoursework().getSubject().getCalendarYear().equals(year);
    }

    private boolean matchesTeacherYear(Mark mark, Long teacherId, Integer year) {
        return mark.getCoursework() != null && mark.getCoursework().getSubject() != null &&
               mark.getCoursework().getSubject().getTeacher() != null &&
               mark.getCoursework().getSubject().getTeacher().getId().equals(teacherId) &&
               mark.getCoursework().getSubject().getCalendarYear().equals(year);
    }

    private boolean matchesYear(Mark mark, Integer year) {
        return mark.getCoursework() != null && mark.getCoursework().getSubject() != null &&
               mark.getCoursework().getSubject().getCalendarYear().equals(year);
    }

    private boolean matchesStudentActivityYear(Rating rating, Long studentId, Long activityId, Integer year) {
        return rating.getActivityPoint() != null && rating.getActivityPoint().getStudent() != null &&
               rating.getActivityPoint().getStudent().getId().equals(studentId) &&
               rating.getActivityPoint().getExtraCurricularYear() != null && rating.getActivityPoint().getExtraCurricularYear().getId().equals(activityId) &&
               rating.getActivityPoint().getExtraCurricularYear().getCalendarYear().equals(year);
    }

    private boolean matchesStudentYear(Rating rating, Long studentId, Integer year) {
        return rating.getActivityPoint() != null && rating.getActivityPoint().getStudent() != null &&
               rating.getActivityPoint().getStudent().getId().equals(studentId) &&
               rating.getActivityPoint().getExtraCurricularYear() != null && rating.getActivityPoint().getExtraCurricularYear().getCalendarYear().equals(year);
    }

    private boolean matchesActivityYear(Rating rating, Long activityId, Integer year) {
        return rating.getActivityPoint().getExtraCurricularYear() != null && rating.getActivityPoint().getExtraCurricularYear().getId().equals(activityId) &&
               rating.getActivityPoint().getExtraCurricularYear().getCalendarYear().equals(year);
    }

    private boolean matchesTeacherYear(Rating rating, Long teacherId, Integer year) {
        return rating.getActivityPoint() != null && rating.getActivityPoint().getExtraCurricularYear() != null &&
               rating.getActivityPoint().getExtraCurricularYear().getFacilitators() != null &&
               !rating.getActivityPoint().getExtraCurricularYear().getFacilitators().isEmpty() &&
               rating.getActivityPoint().getExtraCurricularYear().getFacilitators().stream()
                   .anyMatch(facilitator -> facilitator.getId().equals(teacherId)) &&
               rating.getActivityPoint().getExtraCurricularYear().getCalendarYear().equals(year);
    }

    private boolean matchesStudent(ActivityPoint activityPoint, Long studentId) {
        return activityPoint.getStudent() != null && activityPoint.getStudent().getId().equals(studentId);
    }

    private boolean matchesTeacher(ActivityPoint activityPoint, Long teacherId) {
        return activityPoint.getExtraCurricularYear() != null &&
               activityPoint.getExtraCurricularYear().getFacilitators() != null &&
               !activityPoint.getExtraCurricularYear().getFacilitators().isEmpty() &&
               activityPoint.getExtraCurricularYear().getFacilitators().stream()
                   .anyMatch(facilitator -> facilitator.getId().equals(teacherId));
    }

    private boolean matchesSubject(ActivityPoint activityPoint, Long subjectId) {
        // This would need to be implemented based on how subjects relate to activity points
        return false; // Placeholder - needs proper implementation
    }

    private boolean matchesClassroom(ActivityPoint activityPoint, Long classroomId) {
        return activityPoint.getStudent() != null && activityPoint.getStudent().getClassroom() != null &&
               activityPoint.getStudent().getClassroom().getId().equals(classroomId);
    }

    private boolean matchesActivity(ActivityPoint activityPoint, Long activityId) {
        return activityPoint.getExtraCurricularYear() != null &&
               activityPoint.getExtraCurricularYear().getId().equals(activityId);
    }

    private boolean matchesFacilitator(ActivityPoint activityPoint, Long facilitatorId) {
        return activityPoint.getExtraCurricularYear() != null &&
                activityPoint.getExtraCurricularYear().getFacilitators() != null &&
                !activityPoint.getExtraCurricularYear().getFacilitators().isEmpty() &&
                activityPoint.getExtraCurricularYear().getFacilitators().stream()
                    .anyMatch(facilitator -> facilitator.getId().equals(facilitatorId));
    }

    // ActivityPoint-based filtering methods for ratings reports
    private boolean matchesStudentActivityYearFromActivityPoint(ActivityPoint activityPoint, Long studentId, Long activityId, Integer year) {
        return activityPoint.getStudent() != null && activityPoint.getStudent().getId().equals(studentId) &&
                activityPoint.getExtraCurricularYear() != null && activityPoint.getExtraCurricularYear().getId().equals(activityId) &&
                activityPoint.getExtraCurricularYear().getCalendarYear().equals(year);
    }

    private boolean matchesStudentYearFromActivityPoint(ActivityPoint activityPoint, Long studentId, Integer year) {
        return activityPoint.getStudent() != null && activityPoint.getStudent().getId().equals(studentId) &&
                activityPoint.getExtraCurricularYear() != null && activityPoint.getExtraCurricularYear().getCalendarYear().equals(year);
    }

    private boolean matchesActivityYearFromActivityPoint(ActivityPoint activityPoint, Long activityId, Integer year) {
        return activityPoint.getExtraCurricularYear() != null && activityPoint.getExtraCurricularYear().getId().equals(activityId) &&
                activityPoint.getExtraCurricularYear().getCalendarYear().equals(year);
    }

    private boolean matchesTeacherYearFromActivityPoint(ActivityPoint activityPoint, Long teacherId, Integer year) {
        return activityPoint.getExtraCurricularYear() != null &&
                activityPoint.getExtraCurricularYear().getFacilitators() != null &&
                !activityPoint.getExtraCurricularYear().getFacilitators().isEmpty() &&
                activityPoint.getExtraCurricularYear().getFacilitators().stream()
                    .anyMatch(facilitator -> facilitator.getId().equals(teacherId)) &&
                activityPoint.getExtraCurricularYear().getCalendarYear().equals(year);
    }

    // Helper method to map ActivityPoint to RatingsReportDto
    private List<RatingsReportDto> mapActivityPointToRatingsDto(ActivityPoint activityPoint) {
        if (activityPoint.getRatings() == null || activityPoint.getRatings().isEmpty()) {
            // If no individual ratings, create a single DTO with overall rating
            return List.of(new RatingsReportDto(
                activityPoint.getStudent() != null ? activityPoint.getStudent().getPerson().getName() : "",
                activityPoint.getStudent() != null ? activityPoint.getStudent().getId().toString() : "",
                activityPoint.getExtraCurricularYear() != null &&
                    activityPoint.getExtraCurricularYear().getExtraCurricular() != null ?
                    activityPoint.getExtraCurricularYear().getExtraCurricular().getName() : "",
                round2(BigDecimal.valueOf(activityPoint.getOveralRating())),
                round2(BigDecimal.valueOf(activityPoint.getOveralRating())), // Percentage is same as rating
                round2(BigDecimal.valueOf(50)), // Set pass percentage to 50 as default threshold
                activityPoint.getExtraCurricularYear() != null ?
                    activityPoint.getExtraCurricularYear().getLeadFacilitator() != null ?
                        activityPoint.getExtraCurricularYear().getLeadFacilitator().getPerson().getName() : "" : "",
                activityPoint.getExtraCurricularYear() != null ?
                    activityPoint.getExtraCurricularYear().getCalendarYear() : null
            ));
        } else {
            // If individual ratings exist, create DTOs for each rating
            return activityPoint.getRatings().stream()
                .map(rating -> new RatingsReportDto(
                    activityPoint.getStudent() != null ? activityPoint.getStudent().getPerson().getName() : "",
                    activityPoint.getStudent() != null ? activityPoint.getStudent().getId().toString() : "",
                    activityPoint.getExtraCurricularYear() != null &&
                        activityPoint.getExtraCurricularYear().getExtraCurricular() != null ?
                        activityPoint.getExtraCurricularYear().getExtraCurricular().getName() : "",
                    rating.getRating() != null ? round2(BigDecimal.valueOf(rating.getRating())) : BigDecimal.ZERO,
                    rating.getRating() != null ? round2(BigDecimal.valueOf(rating.getRating())) : BigDecimal.ZERO, // Percentage is same as rating
                    round2(BigDecimal.valueOf(50)), // Set pass percentage to 50 as default threshold
                    activityPoint.getExtraCurricularYear() != null ?
                        activityPoint.getExtraCurricularYear().getLeadFacilitator() != null ?
                            activityPoint.getExtraCurricularYear().getLeadFacilitator().getPerson().getName() : "" : "",
                    activityPoint.getExtraCurricularYear() != null ?
                        activityPoint.getExtraCurricularYear().getCalendarYear() : null
                ))
                .collect(Collectors.toList());
        }
    }

    // Rounds to 2 decimal places, HALF_UP policy
    private BigDecimal round2(BigDecimal val) {
        if (val == null) return null;
        return val.setScale(2, RoundingMode.HALF_UP);
    }

    private String calculateGrade(BigDecimal mark, BigDecimal passMark) {
        if (mark == null || passMark == null) return "N/A";

        if (mark.compareTo(passMark) >= 0) {
            if (mark.compareTo(BigDecimal.valueOf(90)) >= 0) return "A";
            if (mark.compareTo(BigDecimal.valueOf(80)) >= 0) return "B";
            if (mark.compareTo(BigDecimal.valueOf(70)) >= 0) return "C";
            if (mark.compareTo(BigDecimal.valueOf(60)) >= 0) return "D";
            return "E";
        }
        return "F";
    }

    // Helper methods for marks-based progress data
    private boolean matchesSubjectFromMark(Mark mark, Long subjectId) {
        return mark.getCoursework() != null && mark.getCoursework().getSubject() != null &&
               mark.getCoursework().getSubject().getSubject() != null &&
               mark.getCoursework().getSubject().getSubject().getId().equals(subjectId);
    }

    private ProgressReportDto mapMarkToProgressDto(Mark mark, String entityType) {
        return new ProgressReportDto(
                mark.getStudent() != null ? mark.getStudent().getPerson().getName() : "Unknown",
                mark.getStudent() != null ? mark.getStudent().getId().toString() : "",
                mark.getCoursework() != null && mark.getCoursework().getSubject() != null ?
                    mark.getCoursework().getSubject().getCalendarYear() : null,
                round2(BigDecimal.valueOf(mark.getMark())),
                BigDecimal.ZERO, // Previous value would need to be calculated based on historical data
                BigDecimal.ZERO, // Percentage change would need to be calculated
                "STABLE", // Trend would need to be calculated
                null
        );
    }
}