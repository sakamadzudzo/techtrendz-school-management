package zw.co.techtrendz.school.management.api.report.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface PdfReportService {

    // Marks Reports
    byte[] generateMarksByStudentClassroomYearReport(Integer year, Long classroomId, Long studentId);

    byte[] generateMarksByStudentSubjectYearReport(Integer year, Long subjectId, Long studentId);

    byte[] generateMarksByStudentYearReport(Integer year, Long studentId);

    byte[] generateMarksByStudentReport(Long studentId);

    byte[] generateMarksByYearReport(Integer year);

    CompletableFuture<byte[]> generateMarksByYearReportAsync(Integer year);

    CompletableFuture<byte[]> generateMarksByClassroomYearReportAsync(Integer year, Long classroomId);

    byte[] generateMarksByClassroomYearReport(Integer year, Long classroomId);

    byte[] generateMarksBySubjectYearReport(Integer year, Long subjectId);

    // Ratings Reports
    byte[] generateRatingsByStudentActivityYearReport(Integer year, Long activityId, Long studentId);

    byte[] generateRatingsByStudentYearReport(Integer year, Long studentId);

    byte[] generateRatingsByActivityYearReport(Integer year, Long activityId);

    byte[] generateRatingsByActivityReport(Long activityId);

    CompletableFuture<byte[]> generateRatingsByActivityReportAsync(Long activityId);

    // Teacher Reports
    byte[] generateMarksByTeacherYearReport(Integer year, Long teacherId);

    byte[] generateRatingsByTeacherYearReport(Integer year, Long teacherId);

    // Progress Reports
    byte[] generateProgressReportByStudent(Long studentId, String entityType);

    byte[] generateProgressReportByTeacher(Long teacherId, String entityType);

    byte[] generateProgressReportBySubject(Long subjectId, String entityType);

    byte[] generateProgressReportByClassroom(Long classroomId, String entityType);

    byte[] generateProgressReportByActivity(Long activityId, String entityType);

    byte[] generateProgressReportByFacilitator(Long facilitatorId, String entityType);

    CompletableFuture<byte[]> generateProgressReportByClassroomAsync(Long classroomId, String entityType);

    // Generic async report generation
    CompletableFuture<byte[]> generateLargeReportAsync(String reportName, Map<String, Object> parameters, List<?> data);
}
