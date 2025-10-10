package zw.co.techtrendz.school.management.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.dto.YearDropdown;
import zw.co.techtrendz.school.management.api.entity.ClassroomYear;
import zw.co.techtrendz.school.management.api.service.ClassroomYearService;
import zw.co.techtrendz.school.management.api.view.ClassroomYearDto;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/classroom/year")
@Tag(name = "Classroom Years", description = "Management of classroom-year associations")
public class ClassroomYearController {

    private final ClassroomYearService classroomYearService;

    public ClassroomYearController(ClassroomYearService classroomYearService) {
        this.classroomYearService = classroomYearService;
    }

    @Operation(
            summary = "Save classroom year",
            description = "Creates or updates a classroom-year association record"
    )
    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public ClassroomYear saveClassroomYear(@RequestBody @Valid ClassroomYear classroomYear) {
        return this.classroomYearService.save(classroomYear);
    }

    @Operation(
            summary = "Save multiple classroom years",
            description = "Creates or updates multiple classroom-year association records"
    )
    @PostMapping("/save/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public List<ClassroomYear> saveAllClassroomYears(@RequestBody @Valid List<ClassroomYear> classroomYears) {
        return this.classroomYearService.saveAll(classroomYears);
    }

    @Operation(
            summary = "Get classroom year by ID",
            description = "Retrieves a specific classroom-year association by its ID"
    )
    @GetMapping("/get/one")
    public ClassroomYear getClassroomYear(@Parameter(description = "Classroom year ID") @RequestParam Long id) {
        return this.classroomYearService.getById(id);
    }

    @Operation(
            summary = "Get all classroom years",
            description = "Retrieves all classroom-year associations with pagination and sorting"
    )
    @GetMapping("/get/all")
    public Page<ClassroomYear> getAllClassroomYears(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "classroom.name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.classroomYearService.getAll(page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get classroom years by example",
            description = "Retrieves classroom-year associations matching the provided example with pagination and sorting"
    )
    @GetMapping("/get/all/by/example")
    public Page<ClassroomYear> getAllClassroomYearsByExample(
            @RequestBody @Valid ClassroomYear classroomYear,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "classroom.name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.classroomYearService.getAllByExample(classroomYear, page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get classroom years by filter",
            description = "Retrieves classroom-year associations matching the provided filter text with pagination and sorting"
    )
    @GetMapping("/get/all/by/filter")
    public Page<ClassroomYear> getAllClassroomsByFilter(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir,
            @Parameter(description = "Filter text") @RequestParam(defaultValue = "", required = false) String filter) {
        return this.classroomYearService.getAllByFilter(page, size, sortBy, sortDir, filter);
    }

    @Operation(
            summary = "Get classroom year view by ID",
            description = "Retrieves a detailed view of a specific classroom-year association"
    )
    @GetMapping("/view/get/one")
    public ClassroomYearDto getClassroomYearView(@Parameter(description = "Classroom year ID") @RequestParam Long id) {
        return this.classroomYearService.getViewById(id);
    }

    @Operation(
            summary = "Get all classroom year views",
            description = "Retrieves detailed views of all classroom-year associations with pagination and sorting"
    )
    @GetMapping("/view/get/all")
    public Page<ClassroomYearDto> getAllClassroomYearViews(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "classroom.name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.classroomYearService.getViewAll(page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get classroom year views by example",
            description = "Retrieves detailed views of classroom-year associations matching the provided example with pagination and sorting"
    )
    @GetMapping("/view/get/all/by/example")
    public Page<ClassroomYearDto> getAllClassroomYearViewsByExample(
            @RequestBody @Valid ClassroomYear classroomYear,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "classroom.name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.classroomYearService.getViewAllByExample(classroomYear, page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get classroom year views by filter",
            description = "Retrieves detailed views of classroom-year associations matching the provided filter text with pagination and sorting"
    )
    @GetMapping("/view/get/all/by/filter")
    public Page<ClassroomYearDto> getAllClassroomYearViewsByFilter(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir,
            @Parameter(description = "Filter text") @RequestParam(defaultValue = "", required = false) String filter) {
        return this.classroomYearService.getViewAllByFilter(page, size, sortBy, sortDir, filter);
    }

    @Operation(
            summary = "Get classroom years for dropdown",
            description = "Retrieves all classroom-year associations formatted for use in dropdown selections"
    )
    @GetMapping("/dropdown/get/all")
    public List<YearDropdown> getAllClassroomYearForDropdown() {
        return this.classroomYearService.getAllClassroomYearForDropdown();
    }
}
