package zw.co.techtrendz.school.management.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.dto.MarksReportDto;
import zw.co.techtrendz.school.management.api.dto.ProgressReportDto;
import zw.co.techtrendz.school.management.api.dto.RatingsReportDto;
import zw.co.techtrendz.school.management.api.service.impl.PdfReportServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/pdf-reports")
public class PdfReportController {

    @Autowired
    private PdfReportServiceImpl pdfReportService;

    // Marks Reports Endpoints
    @PostMapping("/marks/student-classroom-year")
    public ResponseEntity<byte[]> generateMarksByStudentClassroomYearReport(
            @RequestBody List<MarksReportDto> data,
            @RequestParam Integer year,
            @RequestParam(required = false) Long classroomId,
            @RequestParam(required = false) Long studentId) {

        byte[] report = pdfReportService.generateMarksByStudentClassroomYearReport(data, year, classroomId, studentId);
        return createPdfResponse(report, "marks_by_student_classroom_year.pdf");
    }

    @PostMapping("/marks/student-subject-year")
    public ResponseEntity<byte[]> generateMarksByStudentSubjectYearReport(
            @RequestBody List<MarksReportDto> data,
            @RequestParam Integer year,
            @RequestParam Long subjectId,
            @RequestParam(required = false) Long studentId) {

        byte[] report = pdfReportService.generateMarksByStudentSubjectYearReport(data, year, subjectId, studentId);
        return createPdfResponse(report, "marks_by_student_subject_year.pdf");
    }

    @PostMapping("/marks/student-year")
    public ResponseEntity<byte[]> generateMarksByStudentYearReport(
            @RequestBody List<MarksReportDto> data,
            @RequestParam Integer year,
            @RequestParam Long studentId) {

        byte[] report = pdfReportService.generateMarksByStudentYearReport(data, year, studentId);
        return createPdfResponse(report, "marks_by_student_year.pdf");
    }

    @PostMapping("/marks/student")
    public ResponseEntity<byte[]> generateMarksByStudentReport(
            @RequestBody List<MarksReportDto> data,
            @RequestParam Long studentId) {

        byte[] report = pdfReportService.generateMarksByStudentReport(data, studentId);
        return createPdfResponse(report, "marks_by_student.pdf");
    }

    @PostMapping("/marks/year")
    public ResponseEntity<byte[]> generateMarksByYearReport(
            @RequestBody List<MarksReportDto> data,
            @RequestParam Integer year) {

        byte[] report = pdfReportService.generateMarksByYearReport(data, year);
        return createPdfResponse(report, "marks_by_year.pdf");
    }

    @PostMapping("/marks/classroom-year")
    public ResponseEntity<byte[]> generateMarksByClassroomYearReport(
            @RequestBody List<MarksReportDto> data,
            @RequestParam Integer year,
            @RequestParam Long classroomId) {

        byte[] report = pdfReportService.generateMarksByClassroomYearReport(data, year, classroomId);
        return createPdfResponse(report, "marks_by_classroom_year.pdf");
    }

    @PostMapping("/marks/subject-year")
    public ResponseEntity<byte[]> generateMarksBySubjectYearReport(
            @RequestBody List<MarksReportDto> data,
            @RequestParam Integer year,
            @RequestParam Long subjectId) {

        byte[] report = pdfReportService.generateMarksBySubjectYearReport(data, year, subjectId);
        return createPdfResponse(report, "marks_by_subject_year.pdf");
    }

    // Ratings Reports Endpoints
    @PostMapping("/ratings/student-activity-year")
    public ResponseEntity<byte[]> generateRatingsByStudentActivityYearReport(
            @RequestBody List<RatingsReportDto> data,
            @RequestParam Integer year,
            @RequestParam Long activityId,
            @RequestParam(required = false) Long studentId) {

        byte[] report = pdfReportService.generateRatingsByStudentActivityYearReport(data, year, activityId, studentId);
        return createPdfResponse(report, "ratings_by_student_activity_year.pdf");
    }

    @PostMapping("/ratings/student-year")
    public ResponseEntity<byte[]> generateRatingsByStudentYearReport(
            @RequestBody List<RatingsReportDto> data,
            @RequestParam Integer year,
            @RequestParam Long studentId) {

        byte[] report = pdfReportService.generateRatingsByStudentYearReport(data, year, studentId);
        return createPdfResponse(report, "ratings_by_student_year.pdf");
    }

    @PostMapping("/ratings/activity-year")
    public ResponseEntity<byte[]> generateRatingsByActivityYearReport(
            @RequestBody List<RatingsReportDto> data,
            @RequestParam Integer year,
            @RequestParam Long activityId) {

        byte[] report = pdfReportService.generateRatingsByActivityYearReport(data, year, activityId);
        return createPdfResponse(report, "ratings_by_activity_year.pdf");
    }

    @PostMapping("/ratings/activity")
    public ResponseEntity<byte[]> generateRatingsByActivityReport(
            @RequestBody List<RatingsReportDto> data,
            @RequestParam Long activityId) {

        byte[] report = pdfReportService.generateRatingsByActivityReport(data, activityId);
        return createPdfResponse(report, "ratings_by_activity.pdf");
    }

    // Teacher Reports Endpoints
    @PostMapping("/marks/teacher-year")
    public ResponseEntity<byte[]> generateMarksByTeacherYearReport(
            @RequestBody List<MarksReportDto> data,
            @RequestParam Integer year,
            @RequestParam Long teacherId) {

        byte[] report = pdfReportService.generateMarksByTeacherYearReport(data, year, teacherId);
        return createPdfResponse(report, "marks_by_teacher_year.pdf");
    }

    @PostMapping("/ratings/teacher-year")
    public ResponseEntity<byte[]> generateRatingsByTeacherYearReport(
            @RequestBody List<RatingsReportDto> data,
            @RequestParam Integer year,
            @RequestParam Long teacherId) {

        byte[] report = pdfReportService.generateRatingsByTeacherYearReport(data, year, teacherId);
        return createPdfResponse(report, "ratings_by_teacher_year.pdf");
    }

    // Progress Reports Endpoints
    @PostMapping("/progress/student")
    public ResponseEntity<byte[]> generateProgressReportByStudent(
            @RequestBody List<ProgressReportDto> data,
            @RequestParam Long studentId,
            @RequestParam String entityType) {

        byte[] report = pdfReportService.generateProgressReportByStudent(data, studentId, entityType);
        return createPdfResponse(report, "progress_report_by_student.pdf");
    }

    @PostMapping("/progress/teacher")
    public ResponseEntity<byte[]> generateProgressReportByTeacher(
            @RequestBody List<ProgressReportDto> data,
            @RequestParam Long teacherId,
            @RequestParam String entityType) {

        byte[] report = pdfReportService.generateProgressReportByTeacher(data, teacherId, entityType);
        return createPdfResponse(report, "progress_report_by_teacher.pdf");
    }

    @PostMapping("/progress/subject")
    public ResponseEntity<byte[]> generateProgressReportBySubject(
            @RequestBody List<ProgressReportDto> data,
            @RequestParam Long subjectId,
            @RequestParam String entityType) {

        byte[] report = pdfReportService.generateProgressReportBySubject(data, subjectId, entityType);
        return createPdfResponse(report, "progress_report_by_subject.pdf");
    }

    @PostMapping("/progress/classroom")
    public ResponseEntity<byte[]> generateProgressReportByClassroom(
            @RequestBody List<ProgressReportDto> data,
            @RequestParam Long classroomId,
            @RequestParam String entityType) {

        byte[] report = pdfReportService.generateProgressReportByClassroom(data, classroomId, entityType);
        return createPdfResponse(report, "progress_report_by_classroom.pdf");
    }

    @PostMapping("/progress/activity")
    public ResponseEntity<byte[]> generateProgressReportByActivity(
            @RequestBody List<ProgressReportDto> data,
            @RequestParam Long activityId,
            @RequestParam String entityType) {

        byte[] report = pdfReportService.generateProgressReportByActivity(data, activityId, entityType);
        return createPdfResponse(report, "progress_report_by_activity.pdf");
    }

    @PostMapping("/progress/facilitator")
    public ResponseEntity<byte[]> generateProgressReportByFacilitator(
            @RequestBody List<ProgressReportDto> data,
            @RequestParam Long facilitatorId,
            @RequestParam String entityType) {

        byte[] report = pdfReportService.generateProgressReportByFacilitator(data, facilitatorId, entityType);
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
