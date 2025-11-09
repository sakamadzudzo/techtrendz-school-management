package zw.co.techtrendz.school.management.api.academic.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.academic.entity.ExtraCurricular;
import zw.co.techtrendz.school.management.api.academic.service.ExtraCurricularService;

import java.util.List;

@RestController
@RequestMapping("/extra-curricular")
@Tag(name = "Extra Curricular Activities", description = "Management of extra-curricular activities")
public class ExtraCurricularController {

    private final ExtraCurricularService extraCurricularService;

    public ExtraCurricularController(ExtraCurricularService extraCurricularService) {
        this.extraCurricularService = extraCurricularService;
    }

    @Operation(
            summary = "Save extra curricular activity",
            description = "Creates or updates an extra-curricular activity record"
    )
    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER') or hasRole('STAFF')")
    public ExtraCurricular saveExtraCurricular(@RequestBody @Valid ExtraCurricular extraCurricular) {
        return this.extraCurricularService.save(extraCurricular);
    }

    @Operation(
            summary = "Save multiple extra curricular activities",
            description = "Creates or updates multiple extra-curricular activity records"
    )
    @PostMapping("/save/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER') or hasRole('STAFF')")
    public List<ExtraCurricular> saveAllExtraCurriculars(@RequestBody @Valid List<ExtraCurricular> extraCurriculars) {
        return this.extraCurricularService.saveAll(extraCurriculars);
    }

    @Operation(
            summary = "Get extra curricular activity by ID",
            description = "Retrieves a specific extra-curricular activity by its ID"
    )
    @GetMapping("/get/one")
    public ExtraCurricular getExtraCurricular(@Parameter(description = "Extra curricular activity ID") @RequestParam Long id) {
        return this.extraCurricularService.getById(id);
    }

    @Operation(
            summary = "Get all extra curricular activities",
            description = "Retrieves all extra-curricular activities with pagination and sorting"
    )
    @GetMapping("/get/all")
    public Page<ExtraCurricular> getAllExtraCurriculars(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.extraCurricularService.getAll(page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get extra curricular activities by example",
            description = "Retrieves extra-curricular activities matching the provided example with pagination and sorting"
    )
    @GetMapping("/get/all/by/example")
    public Page<ExtraCurricular> getAllExtraCurricularsByExample(
            @RequestBody @Valid ExtraCurricular extraCurricular,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.extraCurricularService.getAllByExample(extraCurricular, page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get extra curricular activities by filter",
            description = "Retrieves extra-curricular activities matching the provided filter text with pagination and sorting"
    )
    @GetMapping("/get/all/by/filter")
    public Page<ExtraCurricular> getAllClassroomsByFilter(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir,
            @Parameter(description = "Filter text") @RequestParam(defaultValue = "", required = false) String filter) {
        return this.extraCurricularService.getAllByFilter(page, size, sortBy, sortDir, filter);
    }

    @Operation(
            summary = "Get all extra curricular activities as list",
            description = "Retrieves all extra-curricular activities as a simple list with sorting"
    )
    @GetMapping("/get/all/list")
    public List<ExtraCurricular> getAllExtraCurricularsList(
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.extraCurricularService.getAllList(sortBy, sortDir);
    }
}
