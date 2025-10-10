package zw.co.techtrendz.school.management.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.dto.YearDropdown;
import zw.co.techtrendz.school.management.api.entity.Student;
import zw.co.techtrendz.school.management.api.service.StudentService;
import zw.co.techtrendz.school.management.api.view.StudentDto;

import java.util.List;

@RestController
@RequestMapping("/student")
@Tag(name = "Students", description = "Management of student information and enrollment")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @Operation(
            summary = "Save student",
            description = "Creates or updates a student record"
    )
    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public Student saveStudent(@RequestBody @Valid Student student) {
        return this.studentService.save(student);
    }

    @Operation(
            summary = "Save multiple students",
            description = "Creates or updates multiple student records"
    )
    @PostMapping("/save/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public List<Student> saveAllStudents(@RequestBody @Valid List<Student> students) {
        return this.studentService.saveAll(students);
    }

    @Operation(
            summary = "Get student by ID",
            description = "Retrieves a specific student by their ID"
    )
    @GetMapping("/get/one")
    public Student getStudent(@Parameter(description = "Student ID") @RequestParam Long id) {
        return this.studentService.getById(id);
    }

    @Operation(
            summary = "Get all students",
            description = "Retrieves all students with pagination and sorting"
    )
    @GetMapping("/get/all")
    public Page<Student> getAllStudents(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.studentService.getAll(page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get students by example",
            description = "Retrieves students matching the provided example with pagination and sorting"
    )
    @GetMapping("/get/all/by/example")
    public Page<Student> getAllStudentsByExample(
            @RequestBody @Valid Student student,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.studentService.getAllByExample(student, page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get students by classroom",
            description = "Retrieves all students in a specific classroom with pagination and sorting"
    )
    @GetMapping("/get/all/by/classroom")
    public Page<Student> getAllStudentsByClassroomId(
            @Parameter(description = "Classroom ID") @RequestParam long classroomId,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.studentService.getAllByClassroomId(classroomId, page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get students by filter",
            description = "Retrieves students matching the provided filter text with pagination and sorting"
    )
    @GetMapping("/get/all/by/filter")
    public Page<Student> getAllClassroomsByFilter(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir,
            @Parameter(description = "Filter text") @RequestParam(defaultValue = "", required = false) String filter) {
        return this.studentService.getAllByFilter(page, size, sortBy, sortDir, filter);
    }

    @Operation(
            summary = "Get student view by ID",
            description = "Retrieves a detailed view of a specific student"
    )
    @GetMapping("/view/get/one")
    public StudentDto getStudentView(@Parameter(description = "Student ID") @RequestParam Long id) {
        return this.studentService.getViewById(id);
    }

    @Operation(
            summary = "Get all student views",
            description = "Retrieves detailed views of all students with pagination and sorting"
    )
    @GetMapping("/view/get/all")
    public Page<StudentDto> getAllStudentViews(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "person.lastName") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.studentService.getViewAll(page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get student views by example",
            description = "Retrieves detailed views of students matching the provided example with pagination and sorting"
    )
    @GetMapping("/view/get/all/by/example")
    public Page<StudentDto> getAllStudentViewsByExample(
            @RequestBody Student student,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "person.lastName") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.studentService.getViewAllByExample(student, page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get student views by filter",
            description = "Retrieves detailed views of students matching the provided filter text with pagination and sorting"
    )
    @GetMapping("/view/get/all/by/filter")
    public Page<StudentDto> getAllStudentViewsByFilter(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "person.lastName") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir,
            @Parameter(description = "Filter text") @RequestParam(defaultValue = "", required = false) String filter) {
        return this.studentService.getViewAllByFilter(page, size, sortBy, sortDir, filter);
    }

    @Operation(
            summary = "Get students for dropdown",
            description = "Retrieves all students formatted for use in dropdown selections"
    )
    @GetMapping("/dropdown/get/all")
    public List<YearDropdown> getAllStudentsForDropdown() {
        return this.studentService.getAllStudentsForDropdown();
    }

    @Operation(
            summary = "Get students for dropdown by example",
            description = "Retrieves students matching the provided example formatted for use in dropdown selections"
    )
    @PostMapping("/dropdown/get/all/by/example")
    public List<YearDropdown> getAllStudentsForDropdown(@RequestBody Student student) {
        return this.studentService.getAllStudentsForDropdown(student);
    }

    @Operation(
            summary = "Get students for subject dropdown",
            description = "Retrieves students enrolled in a specific subject formatted for dropdown selections"
    )
    @GetMapping("/dropdown/get/all/for/subject")
    public List<YearDropdown> getAllStudentsForSubject(@Parameter(description = "Subject year ID") @RequestParam long subjectYearId) {
        return this.studentService.getAllStudentsForSubjectYear(subjectYearId);
    }

    @Operation(
            summary = "Get students for classroom dropdown",
            description = "Retrieves students enrolled in a specific classroom formatted for dropdown selections"
    )
    @GetMapping("/dropdown/get/all/for/classroom")
    public List<YearDropdown> getAllStudentsForClassroom(@Parameter(description = "Classroom year ID") @RequestParam long classroomYearId) {
        return this.studentService.getAllStudentsForClassroomYear(classroomYearId);
    }
}
