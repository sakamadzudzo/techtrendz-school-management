package zw.co.techtrendz.school.management.api.controller;

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
public class ReportsDropdownsController {

    private final ReportsDropdownsService reportsDropdownsService;

    public ReportsDropdownsController(ReportsDropdownsService reportsDropdownsService) {
        this.reportsDropdownsService = reportsDropdownsService;
    }

    // Get all dropdown data for any report
    @GetMapping("/all")
    public ResponseEntity<ReportsFilters> getAllDropdowns() {
        return ResponseEntity.ok(this.reportsDropdownsService.getAllDropdowns());
    }

    // Marks Reports - need year, classroom, student, subject, teacher
    @GetMapping("/marks")
    public ResponseEntity<Map<String, List<?>>> getMarksReportDropdowns() {
        Map<String, List<?>> dropdowns = Map.of(
                "students", this.reportsDropdownsService.getAllStudentsForDropdown(),
                "teachers", this.reportsDropdownsService.getAllTeachersForDropdown(),
                "classrooms", this.reportsDropdownsService.getAllClassroomsForDropdown(),
                "subjects", this.reportsDropdownsService.getAllSubjectsForDropdown(),
                "calendarYears", this.reportsDropdownsService.getAllCalendarYearsForDropdown()
        );
        ReportsFilters reportsFilters = new ReportsFilters();
        reportsFilters.setStudents(this.reportsDropdownsService.getAllStudentsForDropdown());
        return ResponseEntity.ok(dropdowns);
    }

    // Marks by Student Classroom Year - need year, classroom, student
    @GetMapping("/marks/student-classroom-year")
    public ResponseEntity<Map<String, List<?>>> getMarksByStudentClassroomYearDropdowns() {
        Map<String, List<?>> dropdowns = Map.of(
                "students", this.reportsDropdownsService.getAllStudentsForDropdown(),
                "classrooms", this.reportsDropdownsService.getAllClassroomsForDropdown(),
                "calendarYears", this.reportsDropdownsService.getAllCalendarYearsForDropdown()
        );
        return ResponseEntity.ok(dropdowns);
    }

    // Marks by Student Subject Year - need year, subject, student
    @GetMapping("/marks/student-subject-year")
    public ResponseEntity<Map<String, List<?>>> getMarksByStudentSubjectYearDropdowns() {
        Map<String, List<?>> dropdowns = Map.of(
                "students", this.reportsDropdownsService.getAllStudentsForDropdown(),
                "subjects", this.reportsDropdownsService.getAllSubjectsForDropdown(),
                "calendarYears", this.reportsDropdownsService.getAllCalendarYearsForDropdown()
        );
        return ResponseEntity.ok(dropdowns);
    }

    // Marks by Student Year - need year, student
    @GetMapping("/marks/student-year")
    public ResponseEntity<Map<String, List<?>>> getMarksByStudentYearDropdowns() {
        Map<String, List<?>> dropdowns = Map.of(
                "students", this.reportsDropdownsService.getAllStudentsForDropdown(),
                "calendarYears", this.reportsDropdownsService.getAllCalendarYearsForDropdown()
        );
        return ResponseEntity.ok(dropdowns);
    }

    // Marks by Student - need student only
    @GetMapping("/marks/student")
    public ResponseEntity<Map<String, List<?>>> getMarksByStudentDropdowns() {
        Map<String, List<?>> dropdowns = Map.of(
                "students", this.reportsDropdownsService.getAllStudentsForDropdown()
        );
        return ResponseEntity.ok(dropdowns);
    }

    // Marks by Year - need year only
    @GetMapping("/marks/year")
    public ResponseEntity<Map<String, List<?>>> getMarksByYearDropdowns() {
        Map<String, List<?>> dropdowns = Map.of(
                "calendarYears", this.reportsDropdownsService.getAllCalendarYearsForDropdown()
        );
        return ResponseEntity.ok(dropdowns);
    }

    // Marks by Classroom Year - need year, classroom
    @GetMapping("/marks/classroom-year")
    public ResponseEntity<Map<String, List<?>>> getMarksByClassroomYearDropdowns() {
        Map<String, List<?>> dropdowns = Map.of(
                "classrooms", this.reportsDropdownsService.getAllClassroomsForDropdown(),
                "calendarYears", this.reportsDropdownsService.getAllCalendarYearsForDropdown()
        );
        return ResponseEntity.ok(dropdowns);
    }

    // Marks by Subject Year - need year, subject
    @GetMapping("/marks/subject-year")
    public ResponseEntity<Map<String, List<?>>> getMarksBySubjectYearDropdowns() {
        Map<String, List<?>> dropdowns = Map.of(
                "subjects", this.reportsDropdownsService.getAllSubjectsForDropdown(),
                "calendarYears", this.reportsDropdownsService.getAllCalendarYearsForDropdown()
        );
        return ResponseEntity.ok(dropdowns);
    }

    // Marks by Teacher Year - need year, teacher
    @GetMapping("/marks/teacher-year")
    public ResponseEntity<Map<String, List<?>>> getMarksByTeacherYearDropdowns() {
        Map<String, List<?>> dropdowns = Map.of(
                "teachers", this.reportsDropdownsService.getAllTeachersForDropdown(),
                "calendarYears", this.reportsDropdownsService.getAllCalendarYearsForDropdown()
        );
        return ResponseEntity.ok(dropdowns);
    }

    // Ratings Reports - need year, activity, student, teacher
    @GetMapping("/ratings")
    public ResponseEntity<Map<String, List<?>>> getRatingsReportDropdowns() {
        Map<String, List<?>> dropdowns = Map.of(
                "students", this.reportsDropdownsService.getAllStudentsForDropdown(),
                "teachers", this.reportsDropdownsService.getAllTeachersForDropdown(),
                "extraCurriculars", this.reportsDropdownsService.getAllExtraCurricularsForDropdown(),
                "calendarYears", this.reportsDropdownsService.getAllCalendarYearsForDropdown()
        );
        return ResponseEntity.ok(dropdowns);
    }

    // Ratings by Student Activity Year - need year, activity, student
    @GetMapping("/ratings/student-activity-year")
    public ResponseEntity<Map<String, List<?>>> getRatingsByStudentActivityYearDropdowns() {
        Map<String, List<?>> dropdowns = Map.of(
                "students", this.reportsDropdownsService.getAllStudentsForDropdown(),
                "extraCurriculars", this.reportsDropdownsService.getAllExtraCurricularsForDropdown(),
                "calendarYears", this.reportsDropdownsService.getAllCalendarYearsForDropdown()
        );
        return ResponseEntity.ok(dropdowns);
    }

    // Ratings by Student Year - need year, student
    @GetMapping("/ratings/student-year")
    public ResponseEntity<Map<String, List<?>>> getRatingsByStudentYearDropdowns() {
        Map<String, List<?>> dropdowns = Map.of(
                "students", this.reportsDropdownsService.getAllStudentsForDropdown(),
                "calendarYears", this.reportsDropdownsService.getAllCalendarYearsForDropdown()
        );
        return ResponseEntity.ok(dropdowns);
    }

    // Ratings by Activity Year - need year, activity
    @GetMapping("/ratings/activity-year")
    public ResponseEntity<Map<String, List<?>>> getRatingsByActivityYearDropdowns() {
        Map<String, List<?>> dropdowns = Map.of(
                "extraCurriculars", this.reportsDropdownsService.getAllExtraCurricularsForDropdown(),
                "calendarYears", this.reportsDropdownsService.getAllCalendarYearsForDropdown()
        );
        return ResponseEntity.ok(dropdowns);
    }

    // Ratings by Activity - need activity only
    @GetMapping("/ratings/activity")
    public ResponseEntity<Map<String, List<?>>> getRatingsByActivityDropdowns() {
        Map<String, List<?>> dropdowns = Map.of(
                "extraCurriculars", this.reportsDropdownsService.getAllExtraCurricularsForDropdown()
        );
        return ResponseEntity.ok(dropdowns);
    }

    // Ratings by Teacher Year - need year, teacher
    @GetMapping("/ratings/teacher-year")
    public ResponseEntity<Map<String, List<?>>> getRatingsByTeacherYearDropdowns() {
        Map<String, List<?>> dropdowns = Map.of(
                "teachers", this.reportsDropdownsService.getAllTeachersForDropdown(),
                "calendarYears", this.reportsDropdownsService.getAllCalendarYearsForDropdown()
        );
        return ResponseEntity.ok(dropdowns);
    }

    // Progress Reports - need student, teacher, subject, classroom, activity, facilitator, entityType
    @GetMapping("/progress")
    public ResponseEntity<Map<String, List<?>>> getProgressReportDropdowns() {
        Map<String, List<?>> dropdowns = Map.of(
                "students", this.reportsDropdownsService.getAllStudentsForDropdown(),
                "teachers", this.reportsDropdownsService.getAllTeachersForDropdown(),
                "classrooms", this.reportsDropdownsService.getAllClassroomsForDropdown(),
                "subjects", this.reportsDropdownsService.getAllSubjectsForDropdown(),
                "extraCurriculars", this.reportsDropdownsService.getAllExtraCurricularsForDropdown()
        );
        return ResponseEntity.ok(dropdowns);
    }

    // Individual dropdown endpoints for flexibility
    @GetMapping("/students")
    public ResponseEntity<List<YearDropdown>> getStudentsDropdown() {
        return ResponseEntity.ok(this.reportsDropdownsService.getAllStudentsForDropdown());
    }

    @GetMapping("/teachers")
    public ResponseEntity<List<YearDropdown>> getTeachersDropdown() {
        return ResponseEntity.ok(this.reportsDropdownsService.getAllTeachersForDropdown());
    }

    @GetMapping("/courseworks")
    public ResponseEntity<List<CourseworkDropdown>> getCourseworksDropdown() {
        return ResponseEntity.ok(this.reportsDropdownsService.getAllCourseworksForDropdown());
    }

    @GetMapping("/classrooms")
    public ResponseEntity<List<YearDropdown>> getClassroomsDropdown() {
        return ResponseEntity.ok(this.reportsDropdownsService.getAllClassroomsForDropdown());
    }

    @GetMapping("/subjects")
    public ResponseEntity<List<YearDropdown>> getSubjectsDropdown() {
        return ResponseEntity.ok(this.reportsDropdownsService.getAllSubjectsForDropdown());
    }

    @GetMapping("/extra-curriculars")
    public ResponseEntity<List<YearDropdown>> getExtraCurricularsDropdown() {
        return ResponseEntity.ok(this.reportsDropdownsService.getAllExtraCurricularsForDropdown());
    }

    @GetMapping("/calendar-years")
    public ResponseEntity<List<Integer>> getCalendarYearsDropdown() {
        return ResponseEntity.ok(this.reportsDropdownsService.getAllCalendarYearsForDropdown());
    }
}