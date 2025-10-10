package zw.co.techtrendz.school.management.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.entity.Classroom;
import zw.co.techtrendz.school.management.api.service.ClassroomService;

import java.util.List;

@RestController
@RequestMapping("/classroom")
@Tag(name = "Classrooms", description = "Management of school classrooms")
public class ClassroomController {

    private final ClassroomService classroomService;

    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @Operation(
            summary = "Save classroom",
            description = "Creates or updates a classroom record"
    )
    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public Classroom saveClassroom(@RequestBody @Valid Classroom classroom) {
        return this.classroomService.save(classroom);
    }

    @Operation(
            summary = "Save multiple classrooms",
            description = "Creates or updates multiple classroom records"
    )
    @PostMapping("/save/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public List<Classroom> saveAllClassrooms(@RequestBody @Valid List<Classroom> classrooms) {
        return this.classroomService.saveAll(classrooms);
    }

    @Operation(
            summary = "Get classroom by ID",
            description = "Retrieves a specific classroom by its ID"
    )
    @GetMapping("/get/one")
    public Classroom getClassroom(@Parameter(description = "Classroom ID") @RequestParam Long id) {
        return this.classroomService.getById(id);
    }

    @Operation(
            summary = "Get all classrooms",
            description = "Retrieves all classrooms with pagination and sorting"
    )
    @GetMapping("/get/all")
    public Page<Classroom> getAllClassrooms(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.classroomService.getAll(page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get classrooms by example",
            description = "Retrieves classrooms matching the provided example with pagination and sorting"
    )
    @GetMapping("/get/all/by/example")
    public Page<Classroom> getAllClassroomsByExample(
            @RequestBody @Valid Classroom classroom,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.classroomService.getAllByExample(classroom, page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get classrooms by filter",
            description = "Retrieves classrooms matching the provided filter text with pagination and sorting"
    )
    @GetMapping("/get/all/by/filter")
    public Page<Classroom> getAllClassroomsByFilter(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir,
            @Parameter(description = "Filter text") @RequestParam(defaultValue = "", required = false) String filter) {
        return this.classroomService.getAllByFilter(page, size, sortBy, sortDir, filter);
    }
}
