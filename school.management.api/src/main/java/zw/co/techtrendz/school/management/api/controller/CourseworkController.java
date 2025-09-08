package zw.co.techtrendz.school.management.api.controller;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.entity.Coursework;
import zw.co.techtrendz.school.management.api.enums.CourseworkType;
import zw.co.techtrendz.school.management.api.service.CourseworkService;

import java.util.List;

@RestController
@RequestMapping("/coursework")
public class CourseworkController {

    private final CourseworkService courseworkService;

    public CourseworkController(CourseworkService courseworkService) {
        this.courseworkService = courseworkService;
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('TEACHER')")
    public Coursework save(@RequestBody @Valid Coursework coursework) {
        return this.courseworkService.save(coursework);
    }

    @PostMapping("/save/all")
    @PreAuthorize("hasRole('TEACHER')")
    public List<Coursework> saveAll(@RequestBody @Valid List<Coursework> courseworks) {
        return this.courseworkService.saveAll(courseworks);
    }

    @GetMapping("/get/by/id")
    public Coursework getById(@RequestParam long id) {
        return this.courseworkService.getById(id);
    }

    @GetMapping("/get/all")
    public List<Coursework> getAll() {
        return this.courseworkService.getAll();
    }

    @GetMapping("/get/all/by/work/type")
    public List<Coursework> getAllByType(@RequestParam CourseworkType type) {
        return this.courseworkService.getAllByType(type);
    }

    @GetMapping("/get/all/by/subject")
    public List<Coursework> getAllBySubjectId(@RequestParam long subjectId) {
        return this.courseworkService.getAllBySubjectId(subjectId);
    }

    @GetMapping("/get/all/by/type/by/subject")
    public List<Coursework> getAllByTypeBySubjectId(@RequestParam CourseworkType type, @RequestParam long subjectId) {
        return this.courseworkService.getAllByTypeBySubjectId(type, subjectId);
    }
}
