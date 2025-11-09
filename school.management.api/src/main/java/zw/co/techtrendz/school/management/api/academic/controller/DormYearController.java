package zw.co.techtrendz.school.management.api.academic.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.common.dto.YearDropdown;
import zw.co.techtrendz.school.management.api.academic.entity.DormYear;
import zw.co.techtrendz.school.management.api.academic.service.DormYearService;
import zw.co.techtrendz.school.management.api.academic.view.DormYearDto;

import java.util.List;

@RestController
@RequestMapping("/dorm/year")
@Tag(name = "Dorm Years", description = "Management of dorm-year associations")
public class DormYearController {

    private final DormYearService dormYearService;

    public DormYearController(DormYearService dormYearService) {
        this.dormYearService = dormYearService;
    }

    @Operation(
            summary = "Save dorm year",
            description = "Creates or updates a dorm-year association record"
    )
    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public DormYear saveDormYear(@RequestBody @Valid DormYear dormYear) {
        return this.dormYearService.save(dormYear);
    }

    @Operation(
            summary = "Save multiple dorm years",
            description = "Creates or updates multiple dorm-year association records"
    )
    @PostMapping("/save/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public List<DormYear> saveAllDormYears(@RequestBody @Valid List<DormYear> dormYears) {
        return this.dormYearService.saveAll(dormYears);
    }

    @Operation(
            summary = "Get dorm year by ID",
            description = "Retrieves a specific dorm-year association with detailed view information"
    )
    @GetMapping("/get/one")
    public DormYearDto getDormYear(@Parameter(description = "Dorm year ID") @RequestParam Long id) {
        return this.dormYearService.getViewById(id);
    }

    @Operation(
            summary = "Get all dorm years",
            description = "Retrieves all dorm-year associations with pagination and sorting"
    )
    @GetMapping("/get/all")
    public Page<DormYear> getAllDormYears(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "dorm.name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.dormYearService.getAll(page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get dorm years by example",
            description = "Retrieves dorm-year associations matching the provided example with pagination and sorting"
    )
    @GetMapping("/get/all/by/example")
    public Page<DormYear> getAllDormYearsByExample(
            @RequestBody @Valid DormYear dormYear,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "dorm.name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.dormYearService.getAllByExample(dormYear, page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get dorm years by filter",
            description = "Retrieves dorm-year associations matching the provided filter text with pagination and sorting"
    )
    @GetMapping("/get/all/by/filter")
    public Page<DormYear> getAllClassroomsByFilter(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir,
            @Parameter(description = "Filter text") @RequestParam(defaultValue = "", required = false) String filter) {
        return this.dormYearService.getAllByFilter(page, size, sortBy, sortDir, filter);
    }

    @Operation(
            summary = "Get dorm years for dropdown",
            description = "Retrieves all dorm-year associations formatted for use in dropdown selections"
    )
    @GetMapping("/dropdown/get/all")
    public List<YearDropdown> getAllDormYearForDropdown() {
        return this.dormYearService.getAllDormYearForDropdown();
    }
}
