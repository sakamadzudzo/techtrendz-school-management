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
import zw.co.techtrendz.school.management.api.entity.Subject;
import zw.co.techtrendz.school.management.api.service.SubjectService;

import java.util.List;

@RestController
@RequestMapping("/subject")
@Tag(name = "Subjects", description = "Management of academic subjects")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Operation(
            summary = "Save subject",
            description = "Creates or updates a subject record"
    )
    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public Subject saveSubject(@RequestBody @Valid Subject subject) {
        return this.subjectService.save(subject);
    }

    @Operation(
            summary = "Save multiple subjects",
            description = "Creates or updates multiple subject records"
    )
    @PostMapping("/save/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public List<Subject> saveAllSubjects(@RequestBody @Valid List<Subject> subjects) {
        return this.subjectService.saveAll(subjects);
    }

    @Operation(
            summary = "Get subject by ID",
            description = "Retrieves a specific subject by its ID"
    )
    @GetMapping("/get/one")
    public Subject getSubject(@Parameter(description = "Subject ID") @RequestParam Long id) {
        return this.subjectService.getById(id);
    }

    @Operation(
            summary = "Get all subjects",
            description = "Retrieves all subjects with pagination and sorting"
    )
    @GetMapping("/get/all")
    public Page<Subject> getAllSubjects(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.subjectService.getAll(page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get subjects by example",
            description = "Retrieves subjects matching the provided example with pagination and sorting"
    )
    @GetMapping("/get/all/by/example")
    public Page<Subject> getAllSubjectsByExample(
            @RequestBody @Valid Subject subject,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.subjectService.getAllByExample(subject, page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get subjects by filter",
            description = "Retrieves subjects matching the provided filter text with pagination and sorting"
    )
    @GetMapping("/get/all/by/filter")
    public Page<Subject> getAllClassroomsByFilter(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir,
            @Parameter(description = "Filter text") @RequestParam(defaultValue = "", required = false) String filter) {
        return this.subjectService.getAllByFilter(page, size, sortBy, sortDir, filter);
    }

    @Operation(
            summary = "Get all subjects as list",
            description = "Retrieves all subjects as a simple list with sorting"
    )
    @GetMapping("/get/all/list")
    public List<Subject> getAllSubjectsList(
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.subjectService.getAllList(sortBy, sortDir);
    }
}
