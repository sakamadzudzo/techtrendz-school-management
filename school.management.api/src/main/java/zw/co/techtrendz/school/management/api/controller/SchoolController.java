package zw.co.techtrendz.school.management.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.entity.School;
import zw.co.techtrendz.school.management.api.service.SchoolService;

import java.util.List;

@RestController
@RequestMapping("/school")
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    public School saveSchool(@RequestBody School school) {
        return this.schoolService.save(school);
    }

    @PostMapping("/save/all")
    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    public List<School> saveAllSchools(@RequestBody List<School> schools) {
        return this.schoolService.saveAll(schools);
    }

    @GetMapping("/get/one")
    public School getSchool(@RequestParam Long id) {
        return this.schoolService.getById(id);
    }

    @GetMapping("/get/all")
    public List<School> getAllSchools(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.schoolService.getAll(page, size, sortBy, sortDir).getContent();
    }

    @GetMapping("/get/all/by/example")
    public List<School> getAllSchoolsByExample(@RequestBody School school, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.schoolService.getAllByExample(school, page, size, sortBy, sortDir).getContent();
    }
}
