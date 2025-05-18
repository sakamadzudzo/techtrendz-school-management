package zw.co.techtrendz.school.management.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
    public ExtraCurricular saveExtraCurricular(@RequestBody ExtraCurricular extraCurricular) {
        return this.extraCurricularService.save(extraCurricular);
    }

    @PostMapping("/save/all")
    public List<ExtraCurricular> saveAllExtraCurriculars(@RequestBody List<ExtraCurricular> extraCurriculars) {
        return this.extraCurricularService.saveAll(extraCurriculars);
    }

    @GetMapping("/get/one")
    public ExtraCurricular getExtraCurricular(@RequestParam Long id) {
        return this.extraCurricularService.getById(id);
    }

    @GetMapping("/get/all")
    public List<ExtraCurricular> getAllExtraCurriculars(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.extraCurricularService.getAll(page, size, sortBy, sortDir).getContent();
    }

    @GetMapping("/get/all/by/example")
    public List<ExtraCurricular> getAllExtraCurricularsByExample(@RequestBody ExtraCurricular extraCurricular, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.extraCurricularService.getAllByExample(extraCurricular, page, size, sortBy, sortDir).getContent();
    }
}
