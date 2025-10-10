package zw.co.techtrendz.school.management.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.dto.CourseworkDropdown;
import zw.co.techtrendz.school.management.api.dto.ReportsFilters;
import zw.co.techtrendz.school.management.api.dto.YearDropdown;
import zw.co.techtrendz.school.management.api.service.ReportsDropdownsService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reports/dropdowns")
@Tag(name = "Reports Dropdown Data", description = "Dropdown data for reports filtering")
public class ReportsDropdownsController {

    private final ReportsDropdownsService reportsDropdownsService;

    public ReportsDropdownsController(ReportsDropdownsService reportsDropdownsService) {
        this.reportsDropdownsService = reportsDropdownsService;
    }

    // Get all dropdown data for any report
    @Operation(
            summary = "Get all dropdown data",
            description = "Retrieves all defined dropdown data for any/all reports"
    )
    @GetMapping("/all")
    public ResponseEntity<ReportsFilters> getAllDropdowns() {
        return ResponseEntity.ok(this.reportsDropdownsService.getAllDropdowns());
    }

    @Operation(
            summary = "Get select dropdown data",
            description = "Retrieves defined dropdown data for parameters passed 'true'"
    )
    @GetMapping("/selective")
    public ResponseEntity<ReportsFilters> getSelectiveDropdowns(
            @Parameter(description = "Should student dropdown data be returned or not")
            @RequestParam(required = false, defaultValue = "false") boolean students,
            @Parameter(description = "Should teacher dropdown data be returned or not")
            @RequestParam(required = false, defaultValue = "false") boolean teachers,
            @Parameter(description = "Should coursework dropdown data be returned or not")
            @RequestParam(required = false, defaultValue = "false") boolean courseworks,
            @Parameter(description = "Should classroom dropdown data be returned or not")
            @RequestParam(required = false, defaultValue = "false") boolean classrooms,
            @Parameter(description = "Should subject dropdown data be returned or not")
            @RequestParam(required = false, defaultValue = "false") boolean subjects,
            @Parameter(description = "Should extra curricular dropdown data be returned or not")
            @RequestParam(required = false, defaultValue = "false") boolean extraCurriculars,
            @Parameter(description = "Should calendar year dropdown data be returned or not")
            @RequestParam(required = false, defaultValue = "true") boolean calendarYears) {
        return ResponseEntity.ok(this.reportsDropdownsService.getSelectiveDropdowns(students, teachers, courseworks, classrooms, subjects, extraCurriculars, calendarYears));
    }

    // Marks Reports - need year, classroom, student, subject, teacher
    @GetMapping("/marks")
    public ResponseEntity<ReportsFilters> getMarksReportDropdowns() {
        ReportsFilters reportsFilters = new ReportsFilters();
        reportsFilters.setStudents(this.reportsDropdownsService.getAllStudentsForDropdown());
        reportsFilters.setTeachers(this.reportsDropdownsService.getAllTeachersForDropdown());
        reportsFilters.setClassrooms(this.reportsDropdownsService.getAllClassroomsForDropdown());
        reportsFilters.setSubjects(this.reportsDropdownsService.getAllSubjectsForDropdown());
        reportsFilters.setCalendarYears(this.reportsDropdownsService.getAllCalendarYearsForDropdown());
        return ResponseEntity.ok(reportsFilters);
    }

    // Marks by Student Classroom Year - need year, classroom, student
    @Operation(
            summary = "Get marks dropdown data by student, classroom and year",
            description = "Retrieves dropdown data for marks reports filtered by student, classroom and year"
    )
    @GetMapping("/marks/student-classroom-year")
    public ResponseEntity<ReportsFilters> getMarksByStudentClassroomYearDropdowns() {
        ReportsFilters reportsFilters = new ReportsFilters();
        reportsFilters.setStudents(this.reportsDropdownsService.getAllStudentsForDropdown());
        reportsFilters.setClassrooms(this.reportsDropdownsService.getAllClassroomsForDropdown());
        reportsFilters.setCalendarYears(this.reportsDropdownsService.getAllCalendarYearsForDropdown());
        return ResponseEntity.ok(reportsFilters);
    }

    // Marks by Student Subject Year - need year, subject, student
    @Operation(
            summary = "Get marks dropdown data by student, subject and year",
            description = "Retrieves dropdown data for marks reports filtered by student, subject and year"
    )
    @GetMapping("/marks/student-subject-year")
    public ResponseEntity<ReportsFilters> getMarksByStudentSubjectYearDropdowns() {
        ReportsFilters reportsFilters = new ReportsFilters();
        reportsFilters.setStudents(this.reportsDropdownsService.getAllStudentsForDropdown());
        reportsFilters.setSubjects(this.reportsDropdownsService.getAllSubjectsForDropdown());
        reportsFilters.setCalendarYears(this.reportsDropdownsService.getAllCalendarYearsForDropdown());
        return ResponseEntity.ok(reportsFilters);
    }

    // Marks by Student Year - need year, student
    @Operation(
            summary = "Get marks dropdown data by student and year",
            description = "Retrieves dropdown data for marks reports filtered by student and year"
    )
    @GetMapping("/marks/student-year")
    public ResponseEntity<ReportsFilters> getMarksByStudentYearDropdowns() {
        ReportsFilters reportsFilters = new ReportsFilters();
        reportsFilters.setStudents(this.reportsDropdownsService.getAllStudentsForDropdown());
        reportsFilters.setCalendarYears(this.reportsDropdownsService.getAllCalendarYearsForDropdown());
        return ResponseEntity.ok(reportsFilters);
    }

    // Marks by Student - need student only
    @Operation(
            summary = "Get marks dropdown data by student",
            description = "Retrieves dropdown data for marks reports filtered by student only"
    )
    @GetMapping("/marks/student")
    public ResponseEntity<ReportsFilters> getMarksByStudentDropdowns() {
        ReportsFilters reportsFilters = new ReportsFilters();
        reportsFilters.setStudents(this.reportsDropdownsService.getAllStudentsForDropdown());
        return ResponseEntity.ok(reportsFilters);
    }

    // Marks by Year - need year only
    @Operation(
            summary = "Get marks dropdown data by year",
            description = "Retrieves dropdown data for marks reports filtered by year only"
    )
    @GetMapping("/marks/year")
    public ResponseEntity<ReportsFilters> getMarksByYearDropdowns() {
        ReportsFilters reportsFilters = new ReportsFilters();
        reportsFilters.setCalendarYears(this.reportsDropdownsService.getAllCalendarYearsForDropdown());
        return ResponseEntity.ok(reportsFilters);
    }

    // Marks by Classroom Year - need year, classroom
    @Operation(
            summary = "Get marks dropdown data by classroom and year",
            description = "Retrieves dropdown data for marks reports filtered by classroom and year"
    )
    @GetMapping("/marks/classroom-year")
    public ResponseEntity<ReportsFilters> getMarksByClassroomYearDropdowns() {
        ReportsFilters reportsFilters = new ReportsFilters();
        reportsFilters.setClassrooms(this.reportsDropdownsService.getAllClassroomsForDropdown());
        reportsFilters.setCalendarYears(this.reportsDropdownsService.getAllCalendarYearsForDropdown());
        return ResponseEntity.ok(reportsFilters);
    }

    // Marks by Subject Year - need year, subject
    @Operation(
            summary = "Get marks dropdown data by subject and year",
            description = "Retrieves dropdown data for marks reports filtered by subject and year"
    )
    @GetMapping("/marks/subject-year")
    public ResponseEntity<ReportsFilters> getMarksBySubjectYearDropdowns() {
        ReportsFilters reportsFilters = new ReportsFilters();
        reportsFilters.setSubjects(this.reportsDropdownsService.getAllSubjectsForDropdown());
        reportsFilters.setCalendarYears(this.reportsDropdownsService.getAllCalendarYearsForDropdown());
        return ResponseEntity.ok(reportsFilters);
    }

    // Marks by Teacher Year - need year, teacher
    @Operation(
            summary = "Get marks dropdown data by teacher and year",
            description = "Retrieves dropdown data for marks reports filtered by teacher and year"
    )
    @GetMapping("/marks/teacher-year")
    public ResponseEntity<ReportsFilters> getMarksByTeacherYearDropdowns() {
        ReportsFilters reportsFilters = new ReportsFilters();
        reportsFilters.setTeachers(this.reportsDropdownsService.getAllTeachersForDropdown());
        reportsFilters.setCalendarYears(this.reportsDropdownsService.getAllCalendarYearsForDropdown());
        return ResponseEntity.ok(reportsFilters);
    }

    // Ratings Reports - need year, activity, student, teacher
    @Operation(
            summary = "Get ratings dropdown data",
            description = "Retrieves dropdown data for ratings reports requiring year, activity, student, and teacher filters"
    )
    @GetMapping("/ratings")
    public ResponseEntity<ReportsFilters> getRatingsReportDropdowns() {
        ReportsFilters reportsFilters = new ReportsFilters();
        reportsFilters.setStudents(this.reportsDropdownsService.getAllStudentsForDropdown());
        reportsFilters.setTeachers(this.reportsDropdownsService.getAllTeachersForDropdown());
        reportsFilters.setExtraCurriculars(this.reportsDropdownsService.getAllExtraCurricularsForDropdown());
        reportsFilters.setCalendarYears(this.reportsDropdownsService.getAllCalendarYearsForDropdown());
        return ResponseEntity.ok(reportsFilters);
    }

    // Ratings by Student Activity Year - need year, activity, student
    @Operation(
            summary = "Get ratings dropdown data by student, activity and year",
            description = "Retrieves dropdown data for ratings reports filtered by student, activity and year"
    )
    @GetMapping("/ratings/student-activity-year")
    public ResponseEntity<ReportsFilters> getRatingsByStudentActivityYearDropdowns() {
        ReportsFilters reportsFilters = new ReportsFilters();
        reportsFilters.setStudents(this.reportsDropdownsService.getAllStudentsForDropdown());
        reportsFilters.setExtraCurriculars(this.reportsDropdownsService.getAllExtraCurricularsForDropdown());
        reportsFilters.setCalendarYears(this.reportsDropdownsService.getAllCalendarYearsForDropdown());
        return ResponseEntity.ok(reportsFilters);
    }

    // Ratings by Student Year - need year, student
    @Operation(
            summary = "Get ratings dropdown data by student and year",
            description = "Retrieves dropdown data for ratings reports filtered by student and year"
    )
    @GetMapping("/ratings/student-year")
    public ResponseEntity<ReportsFilters> getRatingsByStudentYearDropdowns() {
        ReportsFilters reportsFilters = new ReportsFilters();
        reportsFilters.setStudents(this.reportsDropdownsService.getAllStudentsForDropdown());
        reportsFilters.setCalendarYears(this.reportsDropdownsService.getAllCalendarYearsForDropdown());
        return ResponseEntity.ok(reportsFilters);
    }

    // Ratings by Activity Year - need year, activity
    @Operation(
            summary = "Get ratings dropdown data by activity and year",
            description = "Retrieves dropdown data for ratings reports filtered by activity and year"
    )
    @GetMapping("/ratings/activity-year")
    public ResponseEntity<ReportsFilters> getRatingsByActivityYearDropdowns() {
        ReportsFilters reportsFilters = new ReportsFilters();
        reportsFilters.setExtraCurriculars(this.reportsDropdownsService.getAllExtraCurricularsForDropdown());
        reportsFilters.setCalendarYears(this.reportsDropdownsService.getAllCalendarYearsForDropdown());
        return ResponseEntity.ok(reportsFilters);
    }

    // Ratings by Activity - need activity only
    @Operation(
            summary = "Get ratings dropdown data by activity",
            description = "Retrieves dropdown data for ratings reports filtered by activity only"
    )
    @GetMapping("/ratings/activity")
    public ResponseEntity<ReportsFilters> getRatingsByActivityDropdowns() {
        ReportsFilters reportsFilters = new ReportsFilters();
        reportsFilters.setExtraCurriculars(this.reportsDropdownsService.getAllExtraCurricularsForDropdown());
        return ResponseEntity.ok(reportsFilters);
    }

    // Ratings by Teacher Year - need year, teacher
    @Operation(
            summary = "Get ratings dropdown data by teacher and year",
            description = "Retrieves dropdown data for ratings reports filtered by teacher and year"
    )
    @GetMapping("/ratings/teacher-year")
    public ResponseEntity<ReportsFilters> getRatingsByTeacherYearDropdowns() {
        ReportsFilters reportsFilters = new ReportsFilters();
        reportsFilters.setTeachers(this.reportsDropdownsService.getAllTeachersForDropdown());
        reportsFilters.setCalendarYears(this.reportsDropdownsService.getAllCalendarYearsForDropdown());
        return ResponseEntity.ok(reportsFilters);
    }

    // Progress Reports - need student, teacher, subject, classroom, activity, facilitator, entityType
    @Operation(
            summary = "Get progress report dropdown data",
            description = "Retrieves dropdown data for progress reports requiring student, teacher, subject, classroom, activity, and facilitator filters"
    )
    @GetMapping("/progress")
    public ResponseEntity<ReportsFilters> getProgressReportDropdowns() {
        ReportsFilters reportsFilters = new ReportsFilters();
        reportsFilters.setStudents(this.reportsDropdownsService.getAllStudentsForDropdown());
        reportsFilters.setTeachers(this.reportsDropdownsService.getAllTeachersForDropdown());
        reportsFilters.setClassrooms(this.reportsDropdownsService.getAllClassroomsForDropdown());
        reportsFilters.setSubjects(this.reportsDropdownsService.getAllSubjectsForDropdown());
        reportsFilters.setExtraCurriculars(this.reportsDropdownsService.getAllExtraCurricularsForDropdown());
        return ResponseEntity.ok(reportsFilters);
    }

    // Individual dropdown endpoints for flexibility
    @Operation(
            summary = "Get students dropdown",
            description = "Retrieves all students formatted for dropdown selections"
    )
    @GetMapping("/students")
    public ResponseEntity<ReportsFilters> getStudentsDropdown() {
        ReportsFilters reportsFilters = new ReportsFilters();
        reportsFilters.setStudents(this.reportsDropdownsService.getAllStudentsForDropdown());
        return ResponseEntity.ok(reportsFilters);
    }

    @Operation(
            summary = "Get teachers dropdown",
            description = "Retrieves all teachers formatted for dropdown selections"
    )
    @GetMapping("/teachers")
    public ResponseEntity<ReportsFilters> getTeachersDropdown() {
        ReportsFilters reportsFilters = new ReportsFilters();
        reportsFilters.setTeachers(this.reportsDropdownsService.getAllTeachersForDropdown());
        return ResponseEntity.ok(reportsFilters);
    }

    @Operation(
            summary = "Get courseworks dropdown",
            description = "Retrieves all courseworks formatted for dropdown selections"
    )
    @GetMapping("/courseworks")
    public ResponseEntity<ReportsFilters> getCourseworksDropdown() {
        ReportsFilters reportsFilters = new ReportsFilters();
        reportsFilters.setCourseworks(this.reportsDropdownsService.getAllCourseworksForDropdown());
        return ResponseEntity.ok(reportsFilters);
    }

    @Operation(
            summary = "Get classrooms dropdown",
            description = "Retrieves all classrooms formatted for dropdown selections"
    )
    @GetMapping("/classrooms")
    public ResponseEntity<ReportsFilters> getClassroomsDropdown() {
        ReportsFilters reportsFilters = new ReportsFilters();
        reportsFilters.setClassrooms(this.reportsDropdownsService.getAllClassroomsForDropdown());
        return ResponseEntity.ok(reportsFilters);
    }

    @Operation(
            summary = "Get subjects dropdown",
            description = "Retrieves all subjects formatted for dropdown selections"
    )
    @GetMapping("/subjects")
    public ResponseEntity<ReportsFilters> getSubjectsDropdown() {
        ReportsFilters reportsFilters = new ReportsFilters();
        reportsFilters.setSubjects(this.reportsDropdownsService.getAllSubjectsForDropdown());
        return ResponseEntity.ok(reportsFilters);
    }

    @Operation(
            summary = "Get extra-curriculars dropdown",
            description = "Retrieves all extra-curricular activities formatted for dropdown selections"
    )
    @GetMapping("/extra-curriculars")
    public ResponseEntity<ReportsFilters> getExtraCurricularsDropdown() {
        ReportsFilters reportsFilters = new ReportsFilters();
        reportsFilters.setExtraCurriculars(this.reportsDropdownsService.getAllExtraCurricularsForDropdown());
        return ResponseEntity.ok(reportsFilters);
    }

    @Operation(
            summary = "Get calendar years dropdown",
            description = "Retrieves all calendar years formatted for dropdown selections"
    )
    @GetMapping("/calendar-years")
    public ResponseEntity<ReportsFilters> getCalendarYearsDropdown() {
        ReportsFilters reportsFilters = new ReportsFilters();
        reportsFilters.setCalendarYears(this.reportsDropdownsService.getAllCalendarYearsForDropdown());
        return ResponseEntity.ok(reportsFilters);
    }
}