package zw.co.techtrendz.school.management.api.report.service;

import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.academic.entity.ActivityPoint;
import zw.co.techtrendz.school.management.api.academic.entity.Mark;
import zw.co.techtrendz.school.management.api.academic.entity.Rating;
import zw.co.techtrendz.school.management.api.report.dto.MarksReportDto;
import zw.co.techtrendz.school.management.api.report.dto.ProgressReportDto;
import zw.co.techtrendz.school.management.api.report.dto.RatingsReportDto;

import java.util.List;

@Service
public interface ReportDataAggregationService {

    // Marks data aggregation methods
    List<MarksReportDto> aggregateMarksData(List<Mark> marks);

    List<MarksReportDto> aggregateMarksDataByStudentClassroomYear(List<Mark> marks, Long studentId, Long classroomId, Integer year);

    List<MarksReportDto> aggregateMarksDataByStudentSubjectYear(List<Mark> marks, Long studentId, Long subjectId, Integer year);

    List<MarksReportDto> aggregateMarksDataByStudentYear(List<Mark> marks, Long studentId, Integer year);

    List<MarksReportDto> aggregateMarksDataByClassroomYear(List<Mark> marks, Long classroomId, Integer year);

    List<MarksReportDto> aggregateMarksDataBySubjectYear(List<Mark> marks, Long subjectId, Integer year);

    List<MarksReportDto> aggregateMarksDataByTeacherYear(List<Mark> marks, Long teacherId, Integer year);

    List<MarksReportDto> aggregateMarksDataByYear(List<Mark> marks, Integer year);

    // Ratings data aggregation methods
    List<RatingsReportDto> aggregateRatingsData(List<Rating> ratings);

    List<RatingsReportDto> aggregateRatingsDataByStudentActivityYear(List<Rating> ratings, Long studentId, Long activityId, Integer year);

    List<RatingsReportDto> aggregateRatingsDataByStudentYear(List<Rating> ratings, Long studentId, Integer year);

    List<RatingsReportDto> aggregateRatingsDataByActivityYear(List<Rating> ratings, Long activityId, Integer year);

    List<RatingsReportDto> aggregateRatingsDataByTeacherYear(List<Rating> ratings, Long teacherId, Integer year);

    // ActivityPoint-based ratings data aggregation methods (for enhanced reports)
    List<RatingsReportDto> aggregateRatingsDataFromActivityPoints(List<ActivityPoint> activityPoints);

    List<RatingsReportDto> aggregateRatingsDataFromActivityPointsByStudentActivityYear(List<ActivityPoint> activityPoints, Long studentId, Long activityId, Integer year);

    List<RatingsReportDto> aggregateRatingsDataFromActivityPointsByStudentYear(List<ActivityPoint> activityPoints, Long studentId, Integer year);

    List<RatingsReportDto> aggregateRatingsDataFromActivityPointsByActivityYear(List<ActivityPoint> activityPoints, Long activityId, Integer year);

    List<RatingsReportDto> aggregateRatingsDataFromActivityPointsByTeacherYear(List<ActivityPoint> activityPoints, Long teacherId, Integer year);

    // Progress data aggregation methods
    List<ProgressReportDto> aggregateProgressData(List<ActivityPoint> activityPoints);

    List<ProgressReportDto> aggregateProgressDataByStudent(List<ActivityPoint> activityPoints, Long studentId, String entityType);

    List<ProgressReportDto> aggregateProgressDataByTeacher(List<ActivityPoint> activityPoints, Long teacherId, String entityType);

    List<ProgressReportDto> aggregateProgressDataBySubject(List<ActivityPoint> activityPoints, Long subjectId, String entityType);

    List<ProgressReportDto> aggregateProgressDataByClassroom(List<ActivityPoint> activityPoints, Long classroomId, String entityType);

    List<ProgressReportDto> aggregateProgressDataByActivity(List<ActivityPoint> activityPoints, Long activityId, String entityType);

    List<ProgressReportDto> aggregateProgressDataByFacilitator(List<ActivityPoint> activityPoints, Long facilitatorId, String entityType);

    List<ProgressReportDto> aggregateProgressDataBySubjectFromMarks(List<Mark> marks, Long subjectId, String entityType);
}