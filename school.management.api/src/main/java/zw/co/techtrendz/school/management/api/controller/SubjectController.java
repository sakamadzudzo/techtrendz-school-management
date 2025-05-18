package zw.co.techtrendz.school.management.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
    public Subject saveSubject(@RequestBody Subject subject) {
        return this.subjectService.save(subject);
    }

    @PostMapping("/save/all")
    public List<Subject> saveAllSubjects(@RequestBody List<Subject> subjects) {
        return this.subjectService.saveAll(subjects);
    }

    @GetMapping("/get/one")
    public Subject getSubject(@RequestParam Long id) {
        return this.subjectService.getById(id);
    }

    @GetMapping("/get/all")
    public List<Subject> getAllSubjects(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.subjectService.getAll(page, size, sortBy, sortDir).getContent();
    }

    @GetMapping("/get/all/by/example")
    public List<Subject> getAllSubjectsByExample(@RequestBody Subject subject, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.subjectService.getAllByExample(subject, page, size, sortBy, sortDir).getContent();
    }
}
