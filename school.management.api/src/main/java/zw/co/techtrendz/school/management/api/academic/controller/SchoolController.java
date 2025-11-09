package zw.co.techtrendz.school.management.api.academic.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.academic.entity.School;
import zw.co.techtrendz.school.management.api.academic.service.SchoolService;

import java.util.List;

@RestController
@RequestMapping("/school")
@Tag(name = "Schools", description = "Management of school information")
public class SchoolController {

    private final SchoolService schoolService;

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @Operation(
            summary = "Save school",
            description = "Creates or updates a school record"
    )
    @PostMapping("/save")
    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    public School saveSchool(@RequestBody @Valid School school) {
        return this.schoolService.save(school);
    }

    @Operation(
            summary = "Save multiple schools",
            description = "Creates or updates multiple school records"
    )
    @PostMapping("/save/all")
    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    public List<School> saveAllSchools(@RequestBody @Valid List<School> schools) {
        return this.schoolService.saveAll(schools);
    }

    @Operation(
            summary = "Get school by ID",
            description = "Retrieves a specific school by its ID"
    )
    @GetMapping("/get/one")
    public School getSchool(@Parameter(description = "School ID") @RequestParam Long id) {
        return this.schoolService.getById(id);
    }

    @Operation(
            summary = "Get all schools",
            description = "Retrieves all schools with pagination and sorting"
    )
    @GetMapping("/get/all")
    public Page<School> getAllSchools(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.schoolService.getAll(page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get schools by example",
            description = "Retrieves schools matching the provided example with pagination and sorting"
    )
    @GetMapping("/get/all/by/example")
    public Page<School> getAllSchoolsByExample(
            @RequestBody @Valid School school,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.schoolService.getAllByExample(school, page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get schools by filter",
            description = "Retrieves schools matching the provided filter text with pagination and sorting"
    )
    @GetMapping("/get/all/by/filter")
    public Page<School> getAllClassroomsByFilter(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir,
            @Parameter(description = "Filter text") @RequestParam(defaultValue = "", required = false) String filter) {
        return this.schoolService.getAllByFilter(page, size, sortBy, sortDir, filter);
    }

    @Operation(
            summary = "Get all schools as list",
            description = "Retrieves all schools as a simple list with sorting"
    )
    @GetMapping("/get/all/list")
    public List<School> getAllSchoolsList(
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.schoolService.getAllList(sortBy, sortDir);
    }
}
