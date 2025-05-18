package zw.co.techtrendz.school.management.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.entity.Dorm;
import zw.co.techtrendz.school.management.api.service.DormService;

import java.util.List;

@RestController
@RequestMapping("/dorm")
public class DormController {

    @Autowired
    private DormService dormService;

    @PostMapping("/save")
    public Dorm saveDorm(@RequestBody Dorm dorm) {
        return this.dormService.save(dorm);
    }

    @PostMapping("/save/all")
    public List<Dorm> saveAllDorms(@RequestBody List<Dorm> dorms) {
        return this.dormService.saveAll(dorms);
    }

    @GetMapping("/get/one")
    public Dorm getDorm(@RequestParam Long id) {
        return this.dormService.getById(id);
    }

    @GetMapping("/get/all")
    public List<Dorm> getAllDorms(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.dormService.getAll(page, size, sortBy, sortDir).getContent();
    }

    @GetMapping("/get/all/by/example")
    public List<Dorm> getAllDormsByExample(@RequestBody Dorm dorm, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.dormService.getAllByExample(dorm, page, size, sortBy, sortDir).getContent();
    }
}
