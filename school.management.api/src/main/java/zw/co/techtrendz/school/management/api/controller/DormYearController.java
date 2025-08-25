package zw.co.techtrendz.school.management.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.dto.YearDropdown;
import zw.co.techtrendz.school.management.api.entity.DormYear;
import zw.co.techtrendz.school.management.api.service.DormYearService;
import zw.co.techtrendz.school.management.api.view.DormYearDto;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dorm/year")
public class DormYearController {

    private final DormYearService dormYearService;

    public DormYearController(DormYearService dormYearService) {
        this.dormYearService = dormYearService;
    }

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
    public DormYearDto getDormYear(@RequestParam Long id) {
        return this.dormYearService.getViewById(id);
    }

    @GetMapping("/get/all")
    public Page<DormYear> getAllDormYears(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "dorm.name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.dormYearService.getAll(page, size, sortBy, sortDir);
    }

    @GetMapping("/get/all/by/example")
    public Page<DormYear> getAllDormYearsByExample(@RequestBody @Valid DormYear dormYear, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "dorm.name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.dormYearService.getAllByExample(dormYear, page, size, sortBy, sortDir);
    }

    @GetMapping("/get/all/by/filter")
    public Page<DormYear> getAllClassroomsByFilter(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir, @RequestParam(defaultValue = "", required = false) String filter) {
        return this.dormYearService.getAllByFilter(page, size, sortBy, sortDir, filter);
    }

    @GetMapping("/dropdown/get/all")
    public List<YearDropdown> getAllDormYearForDropdown() {
        return this.dormYearService.getAllDormYearForDropdown();
    }
}
