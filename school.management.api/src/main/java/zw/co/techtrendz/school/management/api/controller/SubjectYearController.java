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
import zw.co.techtrendz.school.management.api.entity.SubjectYear;
import zw.co.techtrendz.school.management.api.service.SubjectYearService;
import zw.co.techtrendz.school.management.api.view.SubjectYearDto;

import java.util.List;

@RestController
@RequestMapping("/subject/year")
@Tag(name = "Subject Years", description = "Management of subject-year associations")
public class SubjectYearController {

    private final SubjectYearService subjectYearService;

    public SubjectYearController(SubjectYearService subjectYearService) {
        this.subjectYearService = subjectYearService;
    }

    @Operation(
            summary = "Save subject year",
            description = "Creates or updates a subject-year association record"
    )
    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public SubjectYear saveSubjectYear(@RequestBody @Valid SubjectYear subjectYear) {
        return this.subjectYearService.save(subjectYear);
    }

    @Operation(
            summary = "Save multiple subject years",
            description = "Creates or updates multiple subject-year association records"
    )
    @PostMapping("/save/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public List<SubjectYear> saveAllSubjectYears(@RequestBody @Valid List<SubjectYear> subjectYears) {
        return this.subjectYearService.saveAll(subjectYears);
    }

    @Operation(
            summary = "Get subject year by ID",
            description = "Retrieves a specific subject-year association by its ID"
    )
    @GetMapping("/get/one")
    public SubjectYear getSubjectYear(@Parameter(description = "Subject year ID") @RequestParam Long id) {
        return this.subjectYearService.getById(id);
    }

    @Operation(
            summary = "Get all subject years",
            description = "Retrieves all subject-year associations with pagination and sorting"
    )
    @GetMapping("/get/all")
    public Page<SubjectYear> getAllSubjectYears(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "subject.name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.subjectYearService.getAll(page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get subject years by example",
            description = "Retrieves subject-year associations matching the provided example with pagination and sorting"
    )
    @GetMapping("/get/all/by/example")
    public Page<SubjectYear> getAllSubjectYearsByExample(
            @RequestBody @Valid SubjectYear subjectYear,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "subject.name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.subjectYearService.getAllByExample(subjectYear, page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get subject years by filter",
            description = "Retrieves subject-year associations matching the provided filter text with pagination and sorting"
    )
    @GetMapping("/get/all/by/filter")
    public Page<SubjectYear> getAllClassroomsByFilter(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir,
            @Parameter(description = "Filter text") @RequestParam(defaultValue = "", required = false) String filter) {
        return this.subjectYearService.getAllByFilter(page, size, sortBy, sortDir, filter);
    }

    @Operation(
            summary = "Get subject year view by ID",
            description = "Retrieves a detailed view of a specific subject-year association"
    )
    @GetMapping("/view/get/one")
    public SubjectYearDto getSubjectYearView(@Parameter(description = "Subject year ID") @RequestParam Long id) {
        return this.subjectYearService.getViewById(id);
    }

    @Operation(
            summary = "Get all subject year views",
            description = "Retrieves detailed views of all subject-year associations with pagination and sorting"
    )
    @GetMapping("/view/get/all")
    public Page<SubjectYearDto> getAllSubjectYearViews(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "subject.name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.subjectYearService.getViewAll(page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get subject year views by example",
            description = "Retrieves detailed views of subject-year associations matching the provided example with pagination and sorting"
    )
    @GetMapping("/view/get/all/by/example")
    public Page<SubjectYearDto> getAllSubjectYearViewsByExample(
            @RequestBody @Valid SubjectYear subjectYear,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "subject.name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.subjectYearService.getViewAllByExample(subjectYear, page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get subject year views by filter",
            description = "Retrieves detailed views of subject-year associations matching the provided filter text with pagination and sorting"
    )
    @GetMapping("/view/get/all/by/filter")
    public Page<SubjectYearDto> getAllClassroomViewsByFilter(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir,
            @Parameter(description = "Filter text") @RequestParam(defaultValue = "", required = false) String filter) {
        return this.subjectYearService.getViewAllByFilter(page, size, sortBy, sortDir, filter);
    }

    @Operation(
            summary = "Get subject years for dropdown",
            description = "Retrieves all subject-year associations formatted for use in dropdown selections"
    )
    @GetMapping("/dropdown/get/all")
    public List<YearDropdown> getAllSubjectYearForDropdown() {
        return this.subjectYearService.getAllSubjectYearForDropdown();
    }
}
