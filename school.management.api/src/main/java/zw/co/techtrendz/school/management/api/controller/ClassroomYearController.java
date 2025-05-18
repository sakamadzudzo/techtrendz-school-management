package zw.co.techtrendz.school.management.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.entity.ClassroomYear;
import zw.co.techtrendz.school.management.api.service.ClassroomYearService;

import java.util.List;

@RestController
@RequestMapping("/classroom/year")
public class ClassroomYearController {
    
    @Autowired
    private ClassroomYearService classroomYearService;

    @PostMapping("/save")
    public ClassroomYear saveClassroomYear(@RequestBody ClassroomYear classroomYear) {
        return this.classroomYearService.save(classroomYear);
    }

    @PostMapping("/save/all")
    public List<ClassroomYear> saveAllClassroomYears(@RequestBody List<ClassroomYear> classroomYears) {
        return this.classroomYearService.saveAll(classroomYears);
    }

    @GetMapping("/get/one")
    public ClassroomYear getClassroomYear(@RequestParam Long id) {
        return this.classroomYearService.getById(id);
    }

    @GetMapping("/get/all")
    public List<ClassroomYear> getAllClassroomYears(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "classroom.name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.classroomYearService.getAll(page, size, sortBy, sortDir).getContent();
    }

    @GetMapping("/get/all/by/example")
    public List<ClassroomYear> getAllClassroomYearsByExample(@RequestBody ClassroomYear classroomYear, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "classroom.name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.classroomYearService.getAllByExample(classroomYear, page, size, sortBy, sortDir).getContent();
    }
}
