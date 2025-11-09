package zw.co.techtrendz.school.management.api.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.common.dto.YearDropdown;
import zw.co.techtrendz.school.management.api.user.entity.Parent;
import zw.co.techtrendz.school.management.api.user.service.ParentService;
import zw.co.techtrendz.school.management.api.user.view.ParentDto;

import java.util.List;

@RestController
@RequestMapping("/parent")
@Tag(name = "Parents", description = "Management of parent information and student relationships")
public class ParentController {

    private final ParentService parentService;

    public ParentController(ParentService parentService) {
        this.parentService = parentService;
    }

    @Operation(
            summary = "Save parent",
            description = "Creates or updates a parent record"
    )
    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public Parent saveParent(@RequestBody @Valid Parent parent) {
        return this.parentService.save(parent);
    }

    @Operation(
            summary = "Save multiple parents",
            description = "Creates or updates multiple parent records"
    )
    @PostMapping("/save/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Parent> saveAllParents(@RequestBody @Valid List<Parent> parents) {
        return this.parentService.saveAll(parents);
    }

    @Operation(
            summary = "Get parent by ID",
            description = "Retrieves a specific parent by their ID"
    )
    @GetMapping("/get/one")
    public Parent getParent(@Parameter(description = "Parent ID") @RequestParam Long id) {
        return this.parentService.getById(id);
    }

    @Operation(
            summary = "Get parent by person ID",
            description = "Retrieves a specific parent by their person ID"
    )
    @GetMapping("/get/one/by/person")
    public Parent getParentByPersonId(@Parameter(description = "Person ID") @RequestParam Long personId) {
        return this.parentService.getByPersonId(personId);
    }

    @Operation(
            summary = "Get all parents",
            description = "Retrieves all parents with pagination and sorting"
    )
    @GetMapping("/get/all")
    public Page<Parent> getAllParents(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "person.lastName") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.parentService.getAll(page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get parents by filter",
            description = "Retrieves parents matching the provided filter text with pagination and sorting"
    )
    @GetMapping("/get/all/by/filter")
    public Page<Parent> getAllParentsByFilter(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "person.lastName") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir,
            @Parameter(description = "Filter text") @RequestParam(defaultValue = "", required = false) String filter) {
        return this.parentService.getAllByFilter(page, size, sortBy, sortDir, filter);
    }

    @Operation(
            summary = "Get parent view by ID",
            description = "Retrieves a detailed view of a specific parent with students"
    )
    @GetMapping("/view/get/one")
    public ParentDto getParentView(@Parameter(description = "Parent ID") @RequestParam Long id) {
        return this.parentService.getViewById(id);
    }

    @Operation(
            summary = "Get parent view by person ID",
            description = "Retrieves a detailed view of a specific parent by person ID with students"
    )
    @GetMapping("/view/get/one/by/person")
    public ParentDto getParentViewByPersonId(@Parameter(description = "Person ID") @RequestParam Long personId) {
        return this.parentService.getViewByPersonId(personId);
    }

    @Operation(
            summary = "Get all parent views",
            description = "Retrieves detailed views of all parents with students, pagination and sorting"
    )
    @GetMapping("/view/get/all")
    public Page<ParentDto> getAllParentViews(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "person.lastName") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.parentService.getViewAll(page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get parent views by filter",
            description = "Retrieves detailed views of parents matching the provided filter text with pagination and sorting"
    )
    @GetMapping("/view/get/all/by/filter")
    public Page<ParentDto> getAllParentViewsByFilter(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "person.lastName") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir,
            @Parameter(description = "Filter text") @RequestParam(defaultValue = "", required = false) String filter) {
        return this.parentService.getViewAllByFilter(page, size, sortBy, sortDir, filter);
    }

    @Operation(
            summary = "Get parents for dropdown",
            description = "Retrieves all parents formatted for use in dropdown selections"
    )
    @GetMapping("/dropdown/get/all")
    public List<YearDropdown> getAllParentsForDropdown() {
        return this.parentService.getAllParentsForDropdown();
    }

    @Operation(
            summary = "Manage parent students",
            description = "Adds or updates the list of students associated with a parent"
    )
    @PostMapping("/manage/students")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public Parent manageParentStudents(
            @Parameter(description = "Parent ID") @RequestParam Long parentId,
            @Parameter(description = "List of student IDs") @RequestBody List<Long> studentIds) {
        return this.parentService.manageStudents(parentId, studentIds);
    }
}