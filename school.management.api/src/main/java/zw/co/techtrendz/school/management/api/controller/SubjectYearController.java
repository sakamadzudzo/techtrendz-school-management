package zw.co.techtrendz.school.management.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.dto.YearDropdown;
import zw.co.techtrendz.school.management.api.entity.SubjectYear;
import zw.co.techtrendz.school.management.api.service.SubjectYearService;
import zw.co.techtrendz.school.management.api.view.SubjectYearDto;

import java.util.List;

@RestController
@RequestMapping("/subject/year")
public class SubjectYearController {

    private final SubjectYearService subjectYearService;

    public SubjectYearController(SubjectYearService subjectYearService) {
        this.subjectYearService = subjectYearService;
    }

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

    @GetMapping("/get/all/by/filter")
    public Page<SubjectYear> getAllClassroomsByFilter(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir, @RequestParam(defaultValue = "", required = false) String filter) {
        return this.subjectYearService.getAllByFilter(page, size, sortBy, sortDir, filter);
    }

    @GetMapping("/view/get/one")
    public SubjectYearDto getSubjectYearView(@RequestParam Long id) {
        return this.subjectYearService.getViewById(id);
    }

    @GetMapping("/view/get/all")
    public Page<SubjectYearDto> getAllSubjectYearViews(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "subject.name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.subjectYearService.getViewAll(page, size, sortBy, sortDir);
    }

    @GetMapping("/view/get/all/by/example")
    public Page<SubjectYearDto> getAllSubjectYearViewsByExample(@RequestBody @Valid SubjectYear subjectYear, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "subject.name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.subjectYearService.getViewAllByExample(subjectYear, page, size, sortBy, sortDir);
    }

    @GetMapping("/view/get/all/by/filter")
    public Page<SubjectYearDto> getAllClassroomViewsByFilter(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir, @RequestParam(defaultValue = "", required = false) String filter) {
        return this.subjectYearService.getViewAllByFilter(page, size, sortBy, sortDir, filter);
    }

    @GetMapping("/dropdown/get/all")
    public List<YearDropdown> getAllSubjectYearForDropdown() {
        return this.subjectYearService.getAllSubjectYearForDropdown();
    }
}
