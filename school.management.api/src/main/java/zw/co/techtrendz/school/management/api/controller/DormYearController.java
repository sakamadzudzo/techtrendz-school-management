package zw.co.techtrendz.school.management.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.entity.DormYear;
import zw.co.techtrendz.school.management.api.service.DormYearService;

import java.util.List;

@RestController
@RequestMapping("/dorm/year")
public class DormYearController {

    @Autowired
    private DormYearService dormYearService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public DormYear saveDormYear(@RequestBody @Valid DormYear dormYear) {
        return this.dormYearService.save(dormYear);
    }

    @PostMapping("/save/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public List<DormYear> saveAllDormYears(@RequestBody @Valid List<DormYear> dormYears) {
        return this.dormYearService.saveAll(dormYears);
    }

    @GetMapping("/get/one")
    public DormYear getDormYear(@RequestParam Long id) {
        return this.dormYearService.getById(id);
    }

    @GetMapping("/get/all")
    public List<DormYear> getAllDormYears(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "dorm.name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.dormYearService.getAll(page, size, sortBy, sortDir).getContent();
    }

    @GetMapping("/get/all/by/example")
    public List<DormYear> getAllDormYearsByExample(@RequestBody @Valid DormYear dormYear, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "dorm.name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.dormYearService.getAllByExample(dormYear, page, size, sortBy, sortDir).getContent();
    }
}
