package zw.co.techtrendz.school.management.api.controller;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.entity.ActivityPoint;
import zw.co.techtrendz.school.management.api.service.ActivityPointService;

import java.util.List;

@RestController
@RequestMapping("/activity-point")
public class ActivityPointController {

    private final ActivityPointService activityPointService;

    public ActivityPointController(ActivityPointService activityPointService) {
        this.activityPointService = activityPointService;
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('TEACHER')")
    public ActivityPoint save(@RequestBody @Valid ActivityPoint activityPoint) {
        return this.activityPointService.save(activityPoint);
    }

    @PostMapping("/save/all")
    @PreAuthorize("hasRole('TEACHER')")
    public List<ActivityPoint> saveAll(@RequestBody @Valid List<ActivityPoint> activityPoints) {
        return this.activityPointService.saveAll(activityPoints);
    }

    @GetMapping("/get/by/id")
    public ActivityPoint getById(@RequestParam long id) {
        return this.activityPointService.getById(id);
    }

    @GetMapping("/get/all")
    public List<ActivityPoint> getAll() {
        return this.activityPointService.getAll();
    }

    @GetMapping("/get/all/by/activity")
    public List<ActivityPoint> getAllByActivityId(@RequestParam long activityId) {
        return this.activityPointService.getAllByActivityId(activityId);
    }

    @GetMapping("/get/all/by/student")
    public List<ActivityPoint> getAllByStudentId(@RequestParam long studentId) {
        return this.activityPointService.getAllByStudentId(studentId);
    }

    @GetMapping("/get/by/student/by/activity")
    public ActivityPoint getByStudentIdByActivityId(@RequestParam long studentId, @RequestParam long activityId) {
        return this.activityPointService.getByStudentIdByActivityId(studentId, activityId);
    }
}
