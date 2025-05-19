package zw.co.techtrendz.school.management.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.entity.ExtraCurricularYear;
import zw.co.techtrendz.school.management.api.service.ExtraCurricularYearService;

import java.util.List;

@RestController
@RequestMapping("/extra-curricular/year")
public class ExtraCurricularYearController {

    @Autowired
    private ExtraCurricularYearService extraCurricularYearService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER') or hasRole('STAFF')")
    public ExtraCurricularYear saveExtraCurricularYear(@RequestBody ExtraCurricularYear extraCurricularYear) {
        return this.extraCurricularYearService.save(extraCurricularYear);
    }

    @PostMapping("/save/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER') or hasRole('STAFF')")
    public List<ExtraCurricularYear> saveAllExtraCurricularYears(@RequestBody List<ExtraCurricularYear> extraCurricularYears) {
        return this.extraCurricularYearService.saveAll(extraCurricularYears);
    }

    @GetMapping("/get/one")
    public ExtraCurricularYear getExtraCurricularYear(@RequestParam Long id) {
        return this.extraCurricularYearService.getById(id);
    }

    @GetMapping("/get/all")
    public List<ExtraCurricularYear> getAllExtraCurricularYears(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "extraCurricular.name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.extraCurricularYearService.getAll(page, size, sortBy, sortDir).getContent();
    }

    @GetMapping("/get/all/by/example")
    public List<ExtraCurricularYear> getAllExtraCurricularYearsByExample(@RequestBody ExtraCurricularYear extraCurricularYear, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "extraCurricular.name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.extraCurricularYearService.getAllByExample(extraCurricularYear, page, size, sortBy, sortDir).getContent();
    }
}
