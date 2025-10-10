package zw.co.techtrendz.school.management.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.entity.ActivityPoint;
import zw.co.techtrendz.school.management.api.service.ActivityPointService;

import java.util.List;

@RestController
@RequestMapping("/activity-point")
@Tag(name = "Activity Points", description = "Management of student activity points and ratings")
public class ActivityPointController {

    private final ActivityPointService activityPointService;

    public ActivityPointController(ActivityPointService activityPointService) {
        this.activityPointService = activityPointService;
    }

    @Operation(
            summary = "Save activity point",
            description = "Creates or updates a student activity point record"
    )
    @PostMapping("/save")
    @PreAuthorize("hasRole('TEACHER')")
    public ActivityPoint save(@RequestBody @Valid ActivityPoint activityPoint) {
        return this.activityPointService.save(activityPoint);
    }

    @Operation(
            summary = "Save multiple activity points",
            description = "Creates or updates multiple student activity point records"
    )
    @PostMapping("/save/all")
    @PreAuthorize("hasRole('TEACHER')")
    public List<ActivityPoint> saveAll(@RequestBody @Valid List<ActivityPoint> activityPoints) {
        return this.activityPointService.saveAll(activityPoints);
    }

    @Operation(
            summary = "Get activity point by ID",
            description = "Retrieves a specific activity point by its ID"
    )
    @GetMapping("/get/by/id")
    public ActivityPoint getById(@Parameter(description = "Activity point ID") @RequestParam long id) {
        return this.activityPointService.getById(id);
    }

    @Operation(
            summary = "Get all activity points",
            description = "Retrieves all activity point records"
    )
    @GetMapping("/get/all")
    public List<ActivityPoint> getAll() {
        return this.activityPointService.getAll();
    }

    @Operation(
            summary = "Get activity points by activity",
            description = "Retrieves all activity points for a specific activity"
    )
    @GetMapping("/get/all/by/activity")
    public List<ActivityPoint> getAllByActivityId(@Parameter(description = "Activity ID") @RequestParam long activityId) {
        return this.activityPointService.getAllByActivityId(activityId);
    }

    @Operation(
            summary = "Get activity points by student",
            description = "Retrieves all activity points for a specific student"
    )
    @GetMapping("/get/all/by/student")
    public List<ActivityPoint> getAllByStudentId(@Parameter(description = "Student ID") @RequestParam long studentId) {
        return this.activityPointService.getAllByStudentId(studentId);
    }

    @Operation(
            summary = "Get activity point by student and activity",
            description = "Retrieves a specific activity point for a student in a particular activity"
    )
    @GetMapping("/get/by/student/by/activity")
    public ActivityPoint getByStudentIdByActivityId(
            @Parameter(description = "Student ID") @RequestParam long studentId,
            @Parameter(description = "Activity ID") @RequestParam long activityId) {
        return this.activityPointService.getByStudentIdByActivityId(studentId, activityId);
    }
}
