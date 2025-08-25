package zw.co.techtrendz.school.management.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.entity.Dorm;
import zw.co.techtrendz.school.management.api.service.DormService;
import zw.co.techtrendz.school.management.api.view.DormDto;

import java.util.List;

@RestController
@RequestMapping("/dorm")
public class DormController {

    private final DormService dormService;

    public DormController(DormService dormService) {
        this.dormService = dormService;
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    public Dorm saveDorm(@RequestBody @Valid Dorm dorm) {
        return this.dormService.save(dorm);
    }

    @PostMapping("/save/all")
    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    public List<Dorm> saveAllDorms(@RequestBody @Valid List<Dorm> dorms) {
        return this.dormService.saveAll(dorms);
    }

    @GetMapping("/get/one")
    public DormDto getDorm(@RequestParam Long id) {
        return this.dormService.getViewById(id);
    }

    @GetMapping("/get/all")
    public Page<DormDto> getAllDorms(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.dormService.getViewAll(page, size, sortBy, sortDir);
    }

    @GetMapping("/get/all/by/example")
    public Page<DormDto> getAllDormsByExample(@RequestBody @Valid Dorm dorm, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.dormService.getViewAllByExample(dorm, page, size, sortBy, sortDir);
    }

    @GetMapping("/get/all/by/filter")
    public Page<DormDto> getAllClassroomsByFilter(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir, @RequestParam(defaultValue = "", required = false) String filter) {
        return this.dormService.getViewAllByFilter(page, size, sortBy, sortDir, filter);
    }


    @GetMapping("/get/all/list")
    public List<Dorm> getAllDormsList(@RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.dormService.getAllList(sortBy, sortDir);
    }
}
