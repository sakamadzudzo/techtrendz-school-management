package zw.co.techtrendz.school.management.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.dto.MarksReportDto;
import zw.co.techtrendz.school.management.api.dto.ProgressReportDto;
import zw.co.techtrendz.school.management.api.dto.RatingsReportDto;
import zw.co.techtrendz.school.management.api.service.PdfReportService;

import java.util.List;

@RestController
@RequestMapping("/api/pdf-reports")
public class PdfReportController {

    @Autowired
    private PdfReportService pdfReportService;

    // Marks Reports Endpoints
    @PostMapping("/marks/student-classroom-year")
    public ResponseEntity<byte[]> generateMarksByStudentClassroomYearReport(
            @RequestParam Integer year,
            @RequestParam(required = false) Long classroomId,
            @RequestParam(required = false) Long studentId) {

        byte[] report = pdfReportService.generateMarksByStudentClassroomYearReport(year, classroomId, studentId);
        return createPdfResponse(report, "marks_by_student_classroom_year.pdf");
    }

    @PostMapping("/marks/student-subject-year")
    public ResponseEntity<byte[]> generateMarksByStudentSubjectYearReport(
            @RequestParam Integer year,
            @RequestParam Long subjectId,
            @RequestParam(required = false) Long studentId) {

        byte[] report = pdfReportService.generateMarksByStudentSubjectYearReport(year, subjectId, studentId);
        return createPdfResponse(report, "marks_by_student_subject_year.pdf");
    }

    @PostMapping("/marks/student-year")
    public ResponseEntity<byte[]> generateMarksByStudentYearReport(
            @RequestParam Integer year,
            @RequestParam Long studentId) {

        byte[] report = pdfReportService.generateMarksByStudentYearReport(year, studentId);
        return createPdfResponse(report, "marks_by_student_year.pdf");
    }

    @PostMapping("/marks/student")
    public ResponseEntity<byte[]> generateMarksByStudentReport(
            @RequestParam Long studentId) {

        byte[] report = pdfReportService.generateMarksByStudentReport(studentId);
        return createPdfResponse(report, "marks_by_student.pdf");
    }

    @PostMapping("/marks/year")
    public ResponseEntity<byte[]> generateMarksByYearReport(
            @RequestParam Integer year) {

        byte[] report = pdfReportService.generateMarksByYearReport(year);
        return createPdfResponse(report, "marks_by_year.pdf");
    }

    @PostMapping("/marks/classroom-year")
    public ResponseEntity<byte[]> generateMarksByClassroomYearReport(
            @RequestParam Integer year,
            @RequestParam Long classroomId) {

        byte[] report = pdfReportService.generateMarksByClassroomYearReport(year, classroomId);
        return createPdfResponse(report, "marks_by_classroom_year.pdf");
    }

    @PostMapping("/marks/subject-year")
    public ResponseEntity<byte[]> generateMarksBySubjectYearReport(
            @RequestParam Integer year,
            @RequestParam Long subjectId) {

        byte[] report = pdfReportService.generateMarksBySubjectYearReport(year, subjectId);
        return createPdfResponse(report, "marks_by_subject_year.pdf");
    }

    // Ratings Reports Endpoints
    @PostMapping("/ratings/student-activity-year")
    public ResponseEntity<byte[]> generateRatingsByStudentActivityYearReport(
            @RequestParam Integer year,
            @RequestParam Long activityId,
            @RequestParam(required = false) Long studentId) {

        byte[] report = pdfReportService.generateRatingsByStudentActivityYearReport(year, activityId, studentId);
        return createPdfResponse(report, "ratings_by_student_activity_year.pdf");
    }

    @PostMapping("/ratings/student-year")
    public ResponseEntity<byte[]> generateRatingsByStudentYearReport(
            @RequestParam Integer year,
            @RequestParam Long studentId) {

        byte[] report = pdfReportService.generateRatingsByStudentYearReport(year, studentId);
        return createPdfResponse(report, "ratings_by_student_year.pdf");
    }

    @PostMapping("/ratings/activity-year")
    public ResponseEntity<byte[]> generateRatingsByActivityYearReport(
            @RequestParam Integer year,
            @RequestParam Long activityId) {

        byte[] report = pdfReportService.generateRatingsByActivityYearReport(year, activityId);
        return createPdfResponse(report, "ratings_by_activity_year.pdf");
    }

    @PostMapping("/ratings/activity")
    public ResponseEntity<byte[]> generateRatingsByActivityReport(
            @RequestParam Long activityId) {

        byte[] report = pdfReportService.generateRatingsByActivityReport(activityId);
        return createPdfResponse(report, "ratings_by_activity.pdf");
    }

    // Teacher Reports Endpoints
    @PostMapping("/marks/teacher-year")
    public ResponseEntity<byte[]> generateMarksByTeacherYearReport(
            @RequestParam Integer year,
            @RequestParam Long teacherId) {

        byte[] report = pdfReportService.generateMarksByTeacherYearReport(year, teacherId);
        return createPdfResponse(report, "marks_by_teacher_year.pdf");
    }

    @PostMapping("/ratings/teacher-year")
    public ResponseEntity<byte[]> generateRatingsByTeacherYearReport(
            @RequestParam Integer year,
            @RequestParam Long teacherId) {

        byte[] report = pdfReportService.generateRatingsByTeacherYearReport(year, teacherId);
        return createPdfResponse(report, "ratings_by_teacher_year.pdf");
    }

    // Progress Reports Endpoints
    @PostMapping("/progress/student")
    public ResponseEntity<byte[]> generateProgressReportByStudent(
            @RequestParam Long studentId,
            @RequestParam String entityType) {

        byte[] report = pdfReportService.generateProgressReportByStudent(studentId, entityType);
        return createPdfResponse(report, "progress_report_by_student.pdf");
    }

    @PostMapping("/progress/teacher")
    public ResponseEntity<byte[]> generateProgressReportByTeacher(
            @RequestParam Long teacherId,
            @RequestParam String entityType) {

        byte[] report = pdfReportService.generateProgressReportByTeacher(teacherId, entityType);
        return createPdfResponse(report, "progress_report_by_teacher.pdf");
    }

    @PostMapping("/progress/subject")
    public ResponseEntity<byte[]> generateProgressReportBySubject(
            @RequestParam Long subjectId,
            @RequestParam String entityType) {

        byte[] report = pdfReportService.generateProgressReportBySubject(subjectId, entityType);
        return createPdfResponse(report, "progress_report_by_subject.pdf");
    }

    @PostMapping("/progress/classroom")
    public ResponseEntity<byte[]> generateProgressReportByClassroom(
            @RequestParam Long classroomId,
            @RequestParam String entityType) {

        byte[] report = pdfReportService.generateProgressReportByClassroom(classroomId, entityType);
        return createPdfResponse(report, "progress_report_by_classroom.pdf");
    }

    @PostMapping("/progress/activity")
    public ResponseEntity<byte[]> generateProgressReportByActivity(
            @RequestParam Long activityId,
            @RequestParam String entityType) {

        byte[] report = pdfReportService.generateProgressReportByActivity(activityId, entityType);
        return createPdfResponse(report, "progress_report_by_activity.pdf");
    }

    @PostMapping("/progress/facilitator")
    public ResponseEntity<byte[]> generateProgressReportByFacilitator(
            @RequestParam Long facilitatorId,
            @RequestParam String entityType) {

        byte[] report = pdfReportService.generateProgressReportByFacilitator(facilitatorId, entityType);
        return createPdfResponse(report, "progress_report_by_facilitator.pdf");
    }

    // Helper method to create PDF response
    private ResponseEntity<byte[]> createPdfResponse(byte[] reportData, String filename) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", filename);
        headers.setContentLength(reportData.length);

        return ResponseEntity.ok()
                .headers(headers)
                .body(reportData);
    }
}
