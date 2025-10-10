package zw.co.techtrendz.school.management.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.entity.Coursework;
import zw.co.techtrendz.school.management.api.enums.CourseworkType;
import zw.co.techtrendz.school.management.api.service.CourseworkService;

import java.util.List;

@RestController
@RequestMapping("/coursework")
@Tag(name = "Coursework", description = "Management of student coursework and assessments")
public class CourseworkController {

    private final CourseworkService courseworkService;

    public CourseworkController(CourseworkService courseworkService) {
        this.courseworkService = courseworkService;
    }

    @Operation(
            summary = "Save coursework",
            description = "Creates or updates a coursework record"
    )
    @PostMapping("/save")
    @PreAuthorize("hasRole('TEACHER')")
    public Coursework save(@RequestBody @Valid Coursework coursework) {
        return this.courseworkService.save(coursework);
    }

    @Operation(
            summary = "Save multiple courseworks",
            description = "Creates or updates multiple coursework records"
    )
    @PostMapping("/save/all")
    @PreAuthorize("hasRole('TEACHER')")
    public List<Coursework> saveAll(@RequestBody @Valid List<Coursework> courseworks) {
        return this.courseworkService.saveAll(courseworks);
    }

    @Operation(
            summary = "Get coursework by ID",
            description = "Retrieves a specific coursework by its ID"
    )
    @GetMapping("/get/by/id")
    public Coursework getById(@Parameter(description = "Coursework ID") @RequestParam long id) {
        return this.courseworkService.getById(id);
    }

    @Operation(
            summary = "Get all courseworks",
            description = "Retrieves all coursework records"
    )
    @GetMapping("/get/all")
    public List<Coursework> getAll() {
        return this.courseworkService.getAll();
    }

    @Operation(
            summary = "Get courseworks by type",
            description = "Retrieves all courseworks of a specific type"
    )
    @GetMapping("/get/all/by/work/type")
    public List<Coursework> getAllByType(@Parameter(description = "Coursework type") @RequestParam CourseworkType type) {
        return this.courseworkService.getAllByType(type);
    }

    @Operation(
            summary = "Get courseworks by subject",
            description = "Retrieves all courseworks for a specific subject"
    )
    @GetMapping("/get/all/by/subject")
    public List<Coursework> getAllBySubjectId(@Parameter(description = "Subject ID") @RequestParam long subjectId) {
        return this.courseworkService.getAllBySubjectId(subjectId);
    }

    @Operation(
            summary = "Get courseworks by type and subject",
            description = "Retrieves all courseworks of a specific type for a specific subject"
    )
    @GetMapping("/get/all/by/type/by/subject")
    public List<Coursework> getAllByTypeBySubjectId(
            @Parameter(description = "Coursework type") @RequestParam CourseworkType type,
            @Parameter(description = "Subject ID") @RequestParam long subjectId) {
        return this.courseworkService.getAllByTypeBySubjectId(type, subjectId);
    }
}
