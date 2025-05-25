package zw.co.techtrendz.school.management.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.entity.SubjectYear;
import zw.co.techtrendz.school.management.api.service.SubjectYearService;

import java.util.List;

@RestController
@RequestMapping("/subject/year")
public class SubjectYearController {

    @Autowired
    private SubjectYearService subjectYearService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public SubjectYear saveSubjectYear(@RequestBody @Valid SubjectYear subjectYear) {
        return this.subjectYearService.save(subjectYear);
    }

    @PostMapping("/save/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public List<SubjectYear> saveAllSubjectYears(@RequestBody @Valid List<SubjectYear> subjectYears) {
        return this.subjectYearService.saveAll(subjectYears);
    }

    @GetMapping("/get/one")
    public SubjectYear getSubjectYear(@RequestParam Long id) {
        return this.subjectYearService.getById(id);
    }

    @GetMapping("/get/all")
    public Page<SubjectYear> getAllSubjectYears(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "subject.name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.subjectYearService.getAll(page, size, sortBy, sortDir);
    }

    @GetMapping("/get/all/by/example")
    public Page<SubjectYear> getAllSubjectYearsByExample(@RequestBody @Valid SubjectYear subjectYear, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "subject.name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.subjectYearService.getAllByExample(subjectYear, page, size, sortBy, sortDir);
    }
}
