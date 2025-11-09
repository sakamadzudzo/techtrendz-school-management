package zw.co.techtrendz.school.management.api.report.controller;

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
import zw.co.techtrendz.school.management.api.report.service.ExcelReportService;

@RestController
@RequestMapping("/reports/excel")
@Tag(name = "Excel Reports", description = "Generation of Excel reports for various school data")
public class ExcelReportController {

    private final ExcelReportService excelReportService;

    public ExcelReportController(ExcelReportService excelReportService) {
        this.excelReportService = excelReportService;
    }

    // Marks Reports Endpoints
    @Operation(
            summary = "Generate Excel marks report by student, classroom and year",
            description = "Generates an Excel report of marks for students in a specific classroom and year"
    )
    @PostMapping("/marks/student-classroom-year")
    public ResponseEntity<byte[]> generateMarksByStudentClassroomYearReport(
            @Parameter(description = "Academic year") @RequestParam Integer year,
            @Parameter(description = "Classroom ID") @RequestParam(required = false) Long classroomId,
            @Parameter(description = "Student ID") @RequestParam(required = false) Long studentId) {

        byte[] report = excelReportService.generateMarksByStudentClassroomYearReport(year, classroomId, studentId);
        return createExcelResponse(report, "marks_by_student_classroom_year.xlsx");
    }

    @Operation(
            summary = "Generate Excel marks report by student, subject and year",
            description = "Generates an Excel report of marks for students in a specific subject and year"
    )
    @PostMapping("/marks/student-subject-year")
    public ResponseEntity<byte[]> generateMarksByStudentSubjectYearReport(
            @Parameter(description = "Academic year") @RequestParam Integer year,
            @Parameter(description = "Subject ID") @RequestParam Long subjectId,
            @Parameter(description = "Student ID") @RequestParam(required = false) Long studentId) {

        byte[] report = excelReportService.generateMarksByStudentSubjectYearReport(year, subjectId, studentId);
        return createExcelResponse(report, "marks_by_student_subject_year.xlsx");
    }

    @Operation(
            summary = "Generate Excel marks report by student and year",
            description = "Generates an Excel report of marks for a specific student in a given year"
    )
    @PostMapping("/marks/student-year")
    public ResponseEntity<byte[]> generateMarksByStudentYearReport(
            @Parameter(description = "Academic year") @RequestParam Integer year,
            @Parameter(description = "Student ID") @RequestParam Long studentId) {

        byte[] report = excelReportService.generateMarksByStudentYearReport(year, studentId);
        return createExcelResponse(report, "marks_by_student_year.xlsx");
    }

    @Operation(
            summary = "Generate Excel marks report by student",
            description = "Generates a comprehensive Excel report of all marks for a specific student"
    )
    @PostMapping("/marks/student")
    public ResponseEntity<byte[]> generateMarksByStudentReport(
            @Parameter(description = "Student ID") @RequestParam Long studentId) {

        byte[] report = excelReportService.generateMarksByStudentReport(studentId);
        return createExcelResponse(report, "marks_by_student.xlsx");
    }

    @Operation(
            summary = "Generate Excel marks report by year",
            description = "Generates an Excel report of all marks for a specific academic year"
    )
    @PostMapping("/marks/year")
    public ResponseEntity<byte[]> generateMarksByYearReport(
            @Parameter(description = "Academic year") @RequestParam Integer year) {

        byte[] report = excelReportService.generateMarksByYearReport(year);
        return createExcelResponse(report, "marks_by_year.xlsx");
    }

    @Operation(
            summary = "Generate Excel marks report by classroom and year",
            description = "Generates an Excel report of marks for a specific classroom in a given year"
    )
    @PostMapping("/marks/classroom-year")
    public ResponseEntity<byte[]> generateMarksByClassroomYearReport(
            @Parameter(description = "Academic year") @RequestParam Integer year,
            @Parameter(description = "Classroom ID") @RequestParam Long classroomId) {

        byte[] report = excelReportService.generateMarksByClassroomYearReport(year, classroomId);
        return createExcelResponse(report, "marks_by_classroom_year.xlsx");
    }

    @Operation(
            summary = "Generate Excel marks report by subject and year",
            description = "Generates an Excel report of marks for a specific subject in a given year"
    )
    @PostMapping("/marks/subject-year")
    public ResponseEntity<byte[]> generateMarksBySubjectYearReport(
            @Parameter(description = "Academic year") @RequestParam Integer year,
            @Parameter(description = "Subject ID") @RequestParam Long subjectId) {

        byte[] report = excelReportService.generateMarksBySubjectYearReport(year, subjectId);
        return createExcelResponse(report, "marks_by_subject_year.xlsx");
    }

    // Ratings Reports Endpoints
    @Operation(
            summary = "Generate Excel ratings report by student, activity and year",
            description = "Generates an Excel report of ratings for students in a specific activity and year"
    )
    @PostMapping("/ratings/student-activity-year")
    public ResponseEntity<byte[]> generateRatingsByStudentActivityYearReport(
            @Parameter(description = "Academic year") @RequestParam Integer year,
            @Parameter(description = "Activity ID") @RequestParam Long activityId,
            @Parameter(description = "Student ID") @RequestParam(required = false) Long studentId) {

        byte[] report = excelReportService.generateRatingsByStudentActivityYearReport(year, activityId, studentId);
        return createExcelResponse(report, "ratings_by_student_activity_year.xlsx");
    }

    @Operation(
            summary = "Generate Excel ratings report by student and year",
            description = "Generates an Excel report of ratings for a specific student in a given year"
    )
    @PostMapping("/ratings/student-year")
    public ResponseEntity<byte[]> generateRatingsByStudentYearReport(
            @Parameter(description = "Academic year") @RequestParam Integer year,
            @Parameter(description = "Student ID") @RequestParam Long studentId) {

        byte[] report = excelReportService.generateRatingsByStudentYearReport(year, studentId);
        return createExcelResponse(report, "ratings_by_student_year.xlsx");
    }

    @Operation(
            summary = "Generate Excel ratings report by activity and year",
            description = "Generates an Excel report of ratings for a specific activity in a given year"
    )
    @PostMapping("/ratings/activity-year")
    public ResponseEntity<byte[]> generateRatingsByActivityYearReport(
            @Parameter(description = "Academic year") @RequestParam Integer year,
            @Parameter(description = "Activity ID") @RequestParam Long activityId) {

        byte[] report = excelReportService.generateRatingsByActivityYearReport(year, activityId);
        return createExcelResponse(report, "ratings_by_activity_year.xlsx");
    }

    @Operation(
            summary = "Generate Excel ratings report by activity",
            description = "Generates a comprehensive Excel report of all ratings for a specific activity"
    )
    @PostMapping("/ratings/activity")
    public ResponseEntity<byte[]> generateRatingsByActivityReport(
            @Parameter(description = "Activity ID") @RequestParam Long activityId) {

        byte[] report = excelReportService.generateRatingsByActivityReport(activityId);
        return createExcelResponse(report, "ratings_by_activity.xlsx");
    }

    // Teacher Reports Endpoints
    @Operation(
            summary = "Generate Excel marks report by teacher and year",
            description = "Generates an Excel report of marks for a specific teacher in a given year"
    )
    @PostMapping("/marks/teacher-year")
    public ResponseEntity<byte[]> generateMarksByTeacherYearReport(
            @Parameter(description = "Academic year") @RequestParam Integer year,
            @Parameter(description = "Teacher ID") @RequestParam Long teacherId) {

        byte[] report = excelReportService.generateMarksByTeacherYearReport(year, teacherId);
        return createExcelResponse(report, "marks_by_teacher_year.xlsx");
    }

    @Operation(
            summary = "Generate Excel ratings report by teacher and year",
            description = "Generates an Excel report of ratings for a specific teacher in a given year"
    )
    @PostMapping("/ratings/teacher-year")
    public ResponseEntity<byte[]> generateRatingsByTeacherYearReport(
            @Parameter(description = "Academic year") @RequestParam Integer year,
            @Parameter(description = "Teacher ID") @RequestParam Long teacherId) {

        byte[] report = excelReportService.generateRatingsByTeacherYearReport(year, teacherId);
        return createExcelResponse(report, "ratings_by_teacher_year.xlsx");
    }

    // Progress Reports Endpoints
    @Operation(
            summary = "Generate Excel progress report by student",
            description = "Generates a comprehensive Excel progress report for a specific student"
    )
    @PostMapping("/progress/student")
    public ResponseEntity<byte[]> generateProgressReportByStudent(
            @Parameter(description = "Student ID") @RequestParam Long studentId,
            @Parameter(description = "Entity type for report scope") @RequestParam String entityType) {

        byte[] report = excelReportService.generateProgressReportByStudent(studentId, entityType);
        return createExcelResponse(report, "progress_report_by_student.xlsx");
    }

    @Operation(
            summary = "Generate Excel progress report by teacher",
            description = "Generates a comprehensive Excel progress report for a specific teacher"
    )
    @PostMapping("/progress/teacher")
    public ResponseEntity<byte[]> generateProgressReportByTeacher(
            @Parameter(description = "Teacher ID") @RequestParam Long teacherId,
            @Parameter(description = "Entity type for report scope") @RequestParam String entityType) {

        byte[] report = excelReportService.generateProgressReportByTeacher(teacherId, entityType);
        return createExcelResponse(report, "progress_report_by_teacher.xlsx");
    }

    @Operation(
            summary = "Generate Excel progress report by subject",
            description = "Generates a comprehensive Excel progress report for a specific subject"
    )
    @PostMapping("/progress/subject")
    public ResponseEntity<byte[]> generateProgressReportBySubject(
            @Parameter(description = "Subject ID") @RequestParam Long subjectId,
            @Parameter(description = "Entity type for report scope") @RequestParam String entityType) {

        byte[] report = excelReportService.generateProgressReportBySubject(subjectId, entityType);
        return createExcelResponse(report, "progress_report_by_subject.xlsx");
    }

    @Operation(
            summary = "Generate Excel progress report by classroom",
            description = "Generates a comprehensive Excel progress report for a specific classroom"
    )
    @PostMapping("/progress/classroom")
    public ResponseEntity<byte[]> generateProgressReportByClassroom(
            @Parameter(description = "Classroom ID") @RequestParam Long classroomId,
            @Parameter(description = "Entity type for report scope") @RequestParam String entityType) {

        byte[] report = excelReportService.generateProgressReportByClassroom(classroomId, entityType);
        return createExcelResponse(report, "progress_report_by_classroom.xlsx");
    }

    @Operation(
            summary = "Generate Excel progress report by activity",
            description = "Generates a comprehensive Excel progress report for a specific activity"
    )
    @PostMapping("/progress/activity")
    public ResponseEntity<byte[]> generateProgressReportByActivity(
            @Parameter(description = "Activity ID") @RequestParam Long activityId,
            @Parameter(description = "Entity type for report scope") @RequestParam String entityType) {

        byte[] report = excelReportService.generateProgressReportByActivity(activityId, entityType);
        return createExcelResponse(report, "progress_report_by_activity.xlsx");
    }

    @Operation(
            summary = "Generate Excel progress report by facilitator",
            description = "Generates a comprehensive Excel progress report for a specific facilitator"
    )
    @PostMapping("/progress/facilitator")
    public ResponseEntity<byte[]> generateProgressReportByFacilitator(
            @Parameter(description = "Facilitator ID") @RequestParam Long facilitatorId,
            @Parameter(description = "Entity type for report scope") @RequestParam String entityType) {

        byte[] report = excelReportService.generateProgressReportByFacilitator(facilitatorId, entityType);
        return createExcelResponse(report, "progress_report_by_facilitator.xlsx");
    }

    // Helper method to create Excel response
    private ResponseEntity<byte[]> createExcelResponse(byte[] reportData, String filename) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDispositionFormData("attachment", filename);
        headers.setContentLength(reportData.length);

        return ResponseEntity.ok()
                .headers(headers)
                .body(reportData);
    }
}