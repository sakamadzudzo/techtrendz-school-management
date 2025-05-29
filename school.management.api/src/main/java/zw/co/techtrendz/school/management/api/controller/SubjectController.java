package zw.co.techtrendz.school.management.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.entity.Subject;
import zw.co.techtrendz.school.management.api.service.SubjectService;

import java.util.List;

@RestController
@RequestMapping("/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public Subject saveSubject(@RequestBody @Valid Subject subject) {
        return this.subjectService.save(subject);
    }

    @PostMapping("/save/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public List<Subject> saveAllSubjects(@RequestBody @Valid List<Subject> subjects) {
        return this.subjectService.saveAll(subjects);
    }

    @GetMapping("/get/one")
    public Subject getSubject(@RequestParam Long id) {
        return this.subjectService.getById(id);
    }

    @GetMapping("/get/all")
    public Page<Subject> getAllSubjects(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.subjectService.getAll(page, size, sortBy, sortDir);
    }

    @GetMapping("/get/all/by/example")
    public Page<Subject> getAllSubjectsByExample(@RequestBody @Valid Subject subject, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.subjectService.getAllByExample(subject, page, size, sortBy, sortDir);
    }

    @GetMapping("/get/all/by/filter")
    public Page<Subject> getAllClassroomsByFilter(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir, @RequestParam(defaultValue = "", required = false) String filter) {
        return this.subjectService.getAllByFilter(page, size, sortBy, sortDir, filter);
    }
}
