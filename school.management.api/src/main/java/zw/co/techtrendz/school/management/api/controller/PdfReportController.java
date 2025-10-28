package zw.co.techtrendz.school.management.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zw.co.techtrendz.school.management.api.service.PdfReportService;

@RestController
@RequestMapping("/reports/pdf")
@Tag(name = "PDF Reports", description = "Generation of PDF reports for various school data")
public class PdfReportController {

    private final PdfReportService pdfReportService;

    public PdfReportController(PdfReportService pdfReportService) {
        this.pdfReportService = pdfReportService;
    }

    // Marks Reports Endpoints
    @Operation(
            summary = "Generate marks report by student, classroom and year",
            description = "Generates a PDF report of marks for students in a specific classroom and year"
    )
    @PostMapping("/marks/student-classroom-year")
    public ResponseEntity<byte[]> generateMarksByStudentClassroomYearReport(
            @Parameter(description = "Academic year") @RequestParam Integer year,
            @Parameter(description = "Classroom ID") @RequestParam(required = false) Long classroomId,
            @Parameter(description = "Student ID") @RequestParam(required = false) Long studentId) {

        byte[] report = pdfReportService.generateMarksByStudentClassroomYearReport(year, classroomId, studentId);
        return createPdfResponse(report, "marks_by_student_classroom_year.pdf");
    }

    @Operation(
            summary = "Generate marks report by student, subject and year",
            description = "Generates a PDF report of marks for students in a specific subject and year"
    )
    @PostMapping("/marks/student-subject-year")
    public ResponseEntity<byte[]> generateMarksByStudentSubjectYearReport(
            @Parameter(description = "Academic year") @RequestParam Integer year,
            @Parameter(description = "Subject ID") @RequestParam Long subjectId,
            @Parameter(description = "Student ID") @RequestParam(required = false) Long studentId) {

        byte[] report = pdfReportService.generateMarksByStudentSubjectYearReport(year, subjectId, studentId);
        return createPdfResponse(report, "marks_by_student_subject_year.pdf");
    }

    @Operation(
            summary = "Generate marks report by student and year",
            description = "Generates a PDF report of marks for a specific student in a given year"
    )
    @PostMapping("/marks/student-year")
    public ResponseEntity<byte[]> generateMarksByStudentYearReport(
            @Parameter(description = "Academic year") @RequestParam Integer year,
            @Parameter(description = "Student ID") @RequestParam Long studentId) {

        byte[] report = pdfReportService.generateMarksByStudentYearReport(year, studentId);
        return createPdfResponse(report, "marks_by_student_year.pdf");
    }

    @Operation(
            summary = "Generate marks report by student",
            description = "Generates a comprehensive PDF report of all marks for a specific student"
    )
    @PostMapping("/marks/student")
    public ResponseEntity<byte[]> generateMarksByStudentReport(
            @Parameter(description = "Student ID") @RequestParam Long studentId) {

        byte[] report = pdfReportService.generateMarksByStudentReport(studentId);
        return createPdfResponse(report, "marks_by_student.pdf");
    }

    @Operation(
            summary = "Generate marks report by year",
            description = "Generates a PDF report of all marks for a specific academic year"
    )
    @PostMapping("/marks/year")
    public ResponseEntity<byte[]> generateMarksByYearReport(
            @Parameter(description = "Academic year") @RequestParam Integer year) {

        byte[] report = pdfReportService.generateMarksByYearReport(year);
        return createPdfResponse(report, "marks_by_year.pdf");
    }

    @Operation(
            summary = "Generate marks report by classroom and year",
            description = "Generates a PDF report of marks for a specific classroom in a given year"
    )
    @PostMapping("/marks/classroom-year")
    public ResponseEntity<byte[]> generateMarksByClassroomYearReport(
            @Parameter(description = "Academic year") @RequestParam Integer year,
            @Parameter(description = "Classroom ID") @RequestParam Long classroomId) {

        byte[] report = pdfReportService.generateMarksByClassroomYearReport(year, classroomId);
        return createPdfResponse(report, "marks_by_classroom_year.pdf");
    }

    @Operation(
            summary = "Generate marks report by subject and year",
            description = "Generates a PDF report of marks for a specific subject in a given year"
    )
    @PostMapping("/marks/subject-year")
    public ResponseEntity<byte[]> generateMarksBySubjectYearReport(
            @Parameter(description = "Academic year") @RequestParam Integer year,
            @Parameter(description = "Subject ID") @RequestParam Long subjectId) {

        byte[] report = pdfReportService.generateMarksBySubjectYearReport(year, subjectId);
        return createPdfResponse(report, "marks_by_subject_year.pdf");
    }

    // Ratings Reports Endpoints
    @Operation(
            summary = "Generate ratings report by student, activity and year",
            description = "Generates a PDF report of ratings for students in a specific activity and year"
    )
    @PostMapping("/ratings/student-activity-year")
    public ResponseEntity<byte[]> generateRatingsByStudentActivityYearReport(
            @Parameter(description = "Academic year") @RequestParam Integer year,
            @Parameter(description = "Activity ID") @RequestParam Long activityId,
            @Parameter(description = "Student ID") @RequestParam(required = false) Long studentId) {

        byte[] report = pdfReportService.generateRatingsByStudentActivityYearReport(year, activityId, studentId);
        return createPdfResponse(report, "ratings_by_student_activity_year.pdf");
    }

    @Operation(
            summary = "Generate ratings report by student and year",
            description = "Generates a PDF report of ratings for a specific student in a given year"
    )
    @PostMapping("/ratings/student-year")
    public ResponseEntity<byte[]> generateRatingsByStudentYearReport(
            @Parameter(description = "Academic year") @RequestParam Integer year,
            @Parameter(description = "Student ID") @RequestParam Long studentId) {

        byte[] report = pdfReportService.generateRatingsByStudentYearReport(year, studentId);
        return createPdfResponse(report, "ratings_by_student_year.pdf");
    }

    @Operation(
            summary = "Generate ratings report by activity and year",
            description = "Generates a PDF report of ratings for a specific activity in a given year"
    )
    @PostMapping("/ratings/activity-year")
    public ResponseEntity<byte[]> generateRatingsByActivityYearReport(
            @Parameter(description = "Academic year") @RequestParam Integer year,
            @Parameter(description = "Activity ID") @RequestParam Long activityId) {

        byte[] report = pdfReportService.generateRatingsByActivityYearReport(year, activityId);
        return createPdfResponse(report, "ratings_by_activity_year.pdf");
    }

    @Operation(
            summary = "Generate ratings report by activity",
            description = "Generates a comprehensive PDF report of all ratings for a specific activity"
    )
    @PostMapping("/ratings/activity")
    public ResponseEntity<byte[]> generateRatingsByActivityReport(
            @Parameter(description = "Activity ID") @RequestParam Long activityId) {

        byte[] report = pdfReportService.generateRatingsByActivityReport(activityId);
        return createPdfResponse(report, "ratings_by_activity.pdf");
    }

    // Teacher Reports Endpoints
    @Operation(
            summary = "Generate marks report by teacher and year",
            description = "Generates a PDF report of marks for a specific teacher in a given year"
    )
    @PostMapping("/marks/teacher-year")
    public ResponseEntity<byte[]> generateMarksByTeacherYearReport(
            @Parameter(description = "Academic year") @RequestParam Integer year,
            @Parameter(description = "Teacher ID") @RequestParam Long teacherId) {

        byte[] report = pdfReportService.generateMarksByTeacherYearReport(year, teacherId);
        return createPdfResponse(report, "marks_by_teacher_year.pdf");
    }

    @Operation(
            summary = "Generate ratings report by teacher and year",
            description = "Generates a PDF report of ratings for a specific teacher in a given year"
    )
    @PostMapping("/ratings/teacher-year")
    public ResponseEntity<byte[]> generateRatingsByTeacherYearReport(
            @Parameter(description = "Academic year") @RequestParam Integer year,
            @Parameter(description = "Teacher ID") @RequestParam Long teacherId) {

        byte[] report = pdfReportService.generateRatingsByTeacherYearReport(year, teacherId);
        return createPdfResponse(report, "ratings_by_teacher_year.pdf");
    }

    // Progress Reports Endpoints
    @Operation(
            summary = "Generate progress report by student",
            description = "Generates a comprehensive PDF progress report for a specific student"
    )
    @PostMapping("/progress/student")
    public ResponseEntity<byte[]> generateProgressReportByStudent(
            @Parameter(description = "Student ID") @RequestParam Long studentId,
            @Parameter(description = "Entity type for report scope") @RequestParam String entityType) {

        byte[] report = pdfReportService.generateProgressReportByStudent(studentId, entityType);
        return createPdfResponse(report, "progress_report_by_student.pdf");
    }

    @Operation(
            summary = "Generate progress report by teacher",
            description = "Generates a comprehensive PDF progress report for a specific teacher"
    )
    @PostMapping("/progress/teacher")
    public ResponseEntity<byte[]> generateProgressReportByTeacher(
            @Parameter(description = "Teacher ID") @RequestParam Long teacherId,
            @Parameter(description = "Entity type for report scope") @RequestParam String entityType) {

        byte[] report = pdfReportService.generateProgressReportByTeacher(teacherId, entityType);
        return createPdfResponse(report, "progress_report_by_teacher.pdf");
    }

    @Operation(
            summary = "Generate progress report by subject",
            description = "Generates a comprehensive PDF progress report for a specific subject"
    )
    @PostMapping("/progress/subject")
    public ResponseEntity<byte[]> generateProgressReportBySubject(
            @Parameter(description = "Subject ID") @RequestParam Long subjectId,
            @Parameter(description = "Entity type for report scope") @RequestParam String entityType) {

        byte[] report = pdfReportService.generateProgressReportBySubject(subjectId, entityType);
        return createPdfResponse(report, "progress_report_by_subject.pdf");
    }

    @Operation(
            summary = "Generate progress report by classroom",
            description = "Generates a comprehensive PDF progress report for a specific classroom"
    )
    @PostMapping("/progress/classroom")
    public ResponseEntity<byte[]> generateProgressReportByClassroom(
            @Parameter(description = "Classroom ID") @RequestParam Long classroomId,
            @Parameter(description = "Entity type for report scope") @RequestParam String entityType) {

        byte[] report = pdfReportService.generateProgressReportByClassroom(classroomId, entityType);
        return createPdfResponse(report, "progress_report_by_classroom.pdf");
    }

    @Operation(
            summary = "Generate progress report by activity",
            description = "Generates a comprehensive PDF progress report for a specific activity"
    )
    @PostMapping("/progress/activity")
    public ResponseEntity<byte[]> generateProgressReportByActivity(
            @Parameter(description = "Activity ID") @RequestParam Long activityId,
            @Parameter(description = "Entity type for report scope") @RequestParam String entityType) {

        byte[] report = pdfReportService.generateProgressReportByActivity(activityId, entityType);
        return createPdfResponse(report, "progress_report_by_activity.pdf");
    }

    @Operation(
            summary = "Generate progress report by facilitator",
            description = "Generates a comprehensive PDF progress report for a specific facilitator"
    )
    @PostMapping("/progress/facilitator")
    public ResponseEntity<byte[]> generateProgressReportByFacilitator(
            @Parameter(description = "Facilitator ID") @RequestParam Long facilitatorId,
            @Parameter(description = "Entity type for report scope") @RequestParam String entityType) {

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
