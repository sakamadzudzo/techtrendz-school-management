package zw.co.techtrendz.school.management.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.entity.School;
import zw.co.techtrendz.school.management.api.service.SchoolService;

import java.util.List;

@RestController
@RequestMapping("/school")
public class SchoolController {

    private final SchoolService schoolService;

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    public School saveSchool(@RequestBody @Valid School school) {
        return this.schoolService.save(school);
    }

    @PostMapping("/save/all")
    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    public List<School> saveAllSchools(@RequestBody @Valid List<School> schools) {
        return this.schoolService.saveAll(schools);
    }

    @GetMapping("/get/one")
    public School getSchool(@RequestParam Long id) {
        return this.schoolService.getById(id);
    }

    @GetMapping("/get/all")
    public Page<School> getAllSchools(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.schoolService.getAll(page, size, sortBy, sortDir);
    }

    @GetMapping("/get/all/by/example")
    public Page<School> getAllSchoolsByExample(@RequestBody @Valid School school, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.schoolService.getAllByExample(school, page, size, sortBy, sortDir);
    }

    @GetMapping("/get/all/by/filter")
    public Page<School> getAllClassroomsByFilter(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir, @RequestParam(defaultValue = "", required = false) String filter) {
        return this.schoolService.getAllByFilter(page, size, sortBy, sortDir, filter);
    }

    @GetMapping("/get/all/list")
    public List<School> getAllSchoolsList(@RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.schoolService.getAllList(sortBy, sortDir);
    }
}
