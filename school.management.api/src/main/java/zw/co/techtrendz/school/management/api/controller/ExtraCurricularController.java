package zw.co.techtrendz.school.management.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.entity.ExtraCurricular;
import zw.co.techtrendz.school.management.api.service.ExtraCurricularService;

import java.util.List;

@RestController
@RequestMapping("/extra-curricular")
public class ExtraCurricularController {

    @Autowired
    private ExtraCurricularService extraCurricularService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER') or hasRole('STAFF')")
    public ExtraCurricular saveExtraCurricular(@RequestBody @Valid ExtraCurricular extraCurricular) {
        return this.extraCurricularService.save(extraCurricular);
    }

    @PostMapping("/save/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER') or hasRole('STAFF')")
    public List<ExtraCurricular> saveAllExtraCurriculars(@RequestBody @Valid List<ExtraCurricular> extraCurriculars) {
        return this.extraCurricularService.saveAll(extraCurriculars);
    }

    @GetMapping("/get/one")
    public ExtraCurricular getExtraCurricular(@RequestParam Long id) {
        return this.extraCurricularService.getById(id);
    }

    @GetMapping("/get/all")
    public Page<ExtraCurricular> getAllExtraCurriculars(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.extraCurricularService.getAll(page, size, sortBy, sortDir);
    }

    @GetMapping("/get/all/by/example")
    public Page<ExtraCurricular> getAllExtraCurricularsByExample(@RequestBody @Valid ExtraCurricular extraCurricular, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.extraCurricularService.getAllByExample(extraCurricular, page, size, sortBy, sortDir);
    }

    @GetMapping("/get/all/by/filter")
    public Page<ExtraCurricular> getAllClassroomsByFilter(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir, @RequestParam(defaultValue = "", required = false) String filter) {
        return this.extraCurricularService.getAllByFilter(page, size, sortBy, sortDir, filter);
    }

    @GetMapping("/get/all/list")
    public List<ExtraCurricular> getAllExtraCurricularsList(@RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.extraCurricularService.getAllList(sortBy, sortDir);
    }
}
