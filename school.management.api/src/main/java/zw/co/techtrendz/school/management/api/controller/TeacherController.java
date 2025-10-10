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
import zw.co.techtrendz.school.management.api.entity.Teacher;
import zw.co.techtrendz.school.management.api.service.TeacherService;
import zw.co.techtrendz.school.management.api.view.TeacherDto;

import java.util.List;

@RestController
@RequestMapping("/teacher")
@Tag(name = "Teachers", description = "Management of teacher information and assignments")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Operation(
            summary = "Save teacher",
            description = "Creates or updates a teacher record"
    )
    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public Teacher saveTeacher(@RequestBody @Valid Teacher teacher) {
        return this.teacherService.save(teacher);
    }

    @Operation(
            summary = "Save multiple teachers",
            description = "Creates or updates multiple teacher records"
    )
    @PostMapping("/save/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Teacher> saveAllTeachers(@RequestBody @Valid List<Teacher> teachers) {
        return this.teacherService.saveAll(teachers);
    }

    @Operation(
            summary = "Get teacher by ID",
            description = "Retrieves a specific teacher by their ID"
    )
    @GetMapping("/get/one")
    @PreAuthorize("hasRole('ADMIN')")
    public Teacher getTeacher(@Parameter(description = "Teacher ID") @RequestParam Long id) {
        return this.teacherService.getById(id);
    }

    @Operation(
            summary = "Get all teachers",
            description = "Retrieves all teachers with pagination and sorting"
    )
    @GetMapping("/get/all")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<Teacher> getAllTeachers(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "person.lastName") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.teacherService.getAll(page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get teachers by example",
            description = "Retrieves teachers matching the provided example with pagination and sorting"
    )
    @GetMapping("/get/all/by/example")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<Teacher> getAllTeachersByExample(
            @RequestBody @Valid Teacher teacher,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "person.lastName") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.teacherService.getAllByExample(teacher, page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get teachers by filter",
            description = "Retrieves teachers matching the provided filter text with pagination and sorting"
    )
    @GetMapping("/get/all/by/filter")
    public Page<Teacher> getAllTeachersByFilter(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "person.lastName") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir,
            @Parameter(description = "Filter text") @RequestParam(defaultValue = "", required = false) String filter) {
        return this.teacherService.getAllByFilter(page, size, sortBy, sortDir, filter);
    }

    @Operation(
            summary = "Get teacher view by ID",
            description = "Retrieves a detailed view of a specific teacher"
    )
    @GetMapping("/view/get/one")
    @PreAuthorize("hasRole('ADMIN')")
    public TeacherDto getTeacherView(@Parameter(description = "Teacher ID") @RequestParam Long id) {
        return this.teacherService.getViewById(id);
    }

    @Operation(
            summary = "Get all teacher views",
            description = "Retrieves detailed views of all teachers with pagination and sorting"
    )
    @GetMapping("/view/get/all")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<TeacherDto> getAllTeacherViews(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "person.lastName") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.teacherService.getViewAll(page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get teacher views by example",
            description = "Retrieves detailed views of teachers matching the provided example with pagination and sorting"
    )
    @GetMapping("/view/get/all/by/example")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<TeacherDto> getAllTeacherViewsByExample(
            @RequestBody @Valid Teacher teacher,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "person.lastName") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.teacherService.getViewAllByExample(teacher, page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get teacher views by filter",
            description = "Retrieves detailed views of teachers matching the provided filter text with pagination and sorting"
    )
    @GetMapping("/view/get/all/by/filter")
    public Page<TeacherDto> getAllTeacherViewsByFilter(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "person.lastName") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir,
            @Parameter(description = "Filter text") @RequestParam(defaultValue = "", required = false) String filter) {
        return this.teacherService.getViewAllByFilter(page, size, sortBy, sortDir, filter);
    }

    @Operation(
            summary = "Get teachers for dropdown",
            description = "Retrieves all teachers formatted for use in dropdown selections"
    )
    @GetMapping("/dropdown/get/all")
    public List<YearDropdown> getAllTeachersForDropdown() {
        return this.teacherService.getAllTeachersForDropdown();
    }
}
