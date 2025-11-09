package zw.co.techtrendz.school.management.api.academic.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.academic.entity.ExtraCurricularYear;
import zw.co.techtrendz.school.management.api.academic.service.ExtraCurricularYearService;
import zw.co.techtrendz.school.management.api.academic.view.ExtraCurricularYearDto;

import java.util.List;

@RestController
@RequestMapping("/extra-curricular/year")
@Tag(name = "Extra Curricular Year Activities", description = "Management of extra-curricular activities by year")
public class ExtraCurricularYearController {

    private final ExtraCurricularYearService extraCurricularYearService;

    public ExtraCurricularYearController(ExtraCurricularYearService extraCurricularYearService) {
        this.extraCurricularYearService = extraCurricularYearService;
    }

    @Operation(
            summary = "Save extra curricular year",
            description = "Creates or updates an extra-curricular activity year record"
    )
    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER') or hasRole('STAFF')")
    public ExtraCurricularYear saveExtraCurricularYear(@RequestBody @Valid ExtraCurricularYear extraCurricularYear) {
        return this.extraCurricularYearService.save(extraCurricularYear);
    }

    @Operation(
            summary = "Save multiple extra curricular years",
            description = "Creates or updates multiple extra-curricular activity year records"
    )
    @PostMapping("/save/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER') or hasRole('STAFF')")
    public List<ExtraCurricularYear> saveAllExtraCurricularYears(@RequestBody @Valid List<ExtraCurricularYear> extraCurricularYears) {
        return this.extraCurricularYearService.saveAll(extraCurricularYears);
    }

    @Operation(
            summary = "Get extra curricular year by ID",
            description = "Retrieves a specific extra-curricular activity year by its ID"
    )
    @GetMapping("/get/one")
    public ExtraCurricularYear getExtraCurricularYear(@Parameter(description = "Extra curricular year ID") @RequestParam Long id) {
        return this.extraCurricularYearService.getById(id);
    }

    @Operation(
            summary = "Get all extra curricular years",
            description = "Retrieves all extra-curricular activity years with pagination and sorting"
    )
    @GetMapping("/get/all")
    public Page<ExtraCurricularYear> getAllExtraCurricularYears(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "extraCurricular.name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.extraCurricularYearService.getAll(page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get extra curricular years by example",
            description = "Retrieves extra-curricular activity years matching the provided example with pagination and sorting"
    )
    @GetMapping("/get/all/by/example")
    public Page<ExtraCurricularYear> getAllExtraCurricularYearsByExample(
            @RequestBody @Valid ExtraCurricularYear extraCurricularYear,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "extraCurricular.name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.extraCurricularYearService.getAllByExample(extraCurricularYear, page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get extra curricular years by filter",
            description = "Retrieves extra-curricular activity years matching the provided filter text with pagination and sorting"
    )
    @GetMapping("/get/all/by/filter")
    public Page<ExtraCurricularYear> getAllClassroomsByFilter(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir,
            @Parameter(description = "Filter text") @RequestParam(defaultValue = "", required = false) String filter) {
        return this.extraCurricularYearService.getAllByFilter(page, size, sortBy, sortDir, filter);
    }

    @Operation(
            summary = "Get extra curricular year view by ID",
            description = "Retrieves a detailed view of a specific extra-curricular activity year"
    )
    @GetMapping("/view/get/one")
    public ExtraCurricularYearDto getExtraCurricularYearView(@Parameter(description = "Extra curricular year ID") @RequestParam Long id) {
        return this.extraCurricularYearService.getViewById(id);
    }

    @Operation(
            summary = "Get all extra curricular year views",
            description = "Retrieves detailed views of all extra-curricular activity years with pagination and sorting"
    )
    @GetMapping("/view/get/all")
    public Page<ExtraCurricularYearDto> getAllExtraCurricularYearViews(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "extraCurricular.name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.extraCurricularYearService.getViewAll(page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get extra curricular year views by example",
            description = "Retrieves detailed views of extra-curricular activity years matching the provided example with pagination and sorting"
    )
    @GetMapping("/view/get/all/by/example")
    public Page<ExtraCurricularYearDto> getAllExtraCurricularYearViewsByExample(
            @RequestBody @Valid ExtraCurricularYear extraCurricularYear,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "extraCurricular.name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.extraCurricularYearService.getViewAllByExample(extraCurricularYear, page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get extra curricular year views by filter",
            description = "Retrieves detailed views of extra-curricular activity years matching the provided filter text with pagination and sorting"
    )
    @GetMapping("/view/get/all/by/filter")
    public Page<ExtraCurricularYearDto> getAllExtraCurricularYearViewsByFilter(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir,
            @Parameter(description = "Filter text") @RequestParam(defaultValue = "", required = false) String filter) {
        return this.extraCurricularYearService.getViewAllByFilter(page, size, sortBy, sortDir, filter);
    }
}
