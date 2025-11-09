package zw.co.techtrendz.school.management.api.academic.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.academic.entity.Dorm;
import zw.co.techtrendz.school.management.api.academic.service.DormService;
import zw.co.techtrendz.school.management.api.academic.view.DormDto;

import java.util.List;

@RestController
@RequestMapping("/dorm")
@Tag(name = "Dorms", description = "Management of student dormitory information")
public class DormController {

    private final DormService dormService;

    public DormController(DormService dormService) {
        this.dormService = dormService;
    }

    @Operation(
            summary = "Save dorm",
            description = "Creates or updates a dormitory record"
    )
    @PostMapping("/save")
    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    public Dorm saveDorm(@RequestBody @Valid Dorm dorm) {
        return this.dormService.save(dorm);
    }

    @Operation(
            summary = "Save multiple dorms",
            description = "Creates or updates multiple dormitory records"
    )
    @PostMapping("/save/all")
    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    public List<Dorm> saveAllDorms(@RequestBody @Valid List<Dorm> dorms) {
        return this.dormService.saveAll(dorms);
    }

    @Operation(
            summary = "Get dorm by ID",
            description = "Retrieves a specific dormitory with detailed view information"
    )
    @GetMapping("/get/one")
    public DormDto getDorm(@Parameter(description = "Dorm ID") @RequestParam Long id) {
        return this.dormService.getViewById(id);
    }

    @Operation(
            summary = "Get all dorms",
            description = "Retrieves all dormitories with detailed view information, pagination and sorting"
    )
    @GetMapping("/get/all")
    public Page<DormDto> getAllDorms(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.dormService.getViewAll(page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get dorms by example",
            description = "Retrieves dormitories matching the provided example with detailed view information, pagination and sorting"
    )
    @GetMapping("/get/all/by/example")
    public Page<DormDto> getAllDormsByExample(
            @RequestBody @Valid Dorm dorm,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.dormService.getViewAllByExample(dorm, page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get dorms by filter",
            description = "Retrieves dormitories matching the provided filter text with detailed view information, pagination and sorting"
    )
    @GetMapping("/get/all/by/filter")
    public Page<DormDto> getAllClassroomsByFilter(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir,
            @Parameter(description = "Filter text") @RequestParam(defaultValue = "", required = false) String filter) {
        return this.dormService.getViewAllByFilter(page, size, sortBy, sortDir, filter);
    }

    @Operation(
            summary = "Get all dorms as list",
            description = "Retrieves all dormitories as a simple list with sorting"
    )
    @GetMapping("/get/all/list")
    public List<Dorm> getAllDormsList(
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.dormService.getAllList(sortBy, sortDir);
    }
}
