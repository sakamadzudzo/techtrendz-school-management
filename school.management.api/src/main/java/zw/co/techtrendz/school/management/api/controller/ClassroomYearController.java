package zw.co.techtrendz.school.management.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.entity.ClassroomYear;
import zw.co.techtrendz.school.management.api.service.ClassroomYearService;
import zw.co.techtrendz.school.management.api.view.ClassroomYearDto;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/classroom/year")
public class ClassroomYearController {

    @Autowired
    private ClassroomYearService classroomYearService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public ClassroomYear saveClassroomYear(@RequestBody @Valid ClassroomYear classroomYear) {
        return this.classroomYearService.save(classroomYear);
    }

    @PostMapping("/save/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public List<ClassroomYear> saveAllClassroomYears(@RequestBody @Valid List<ClassroomYear> classroomYears) {
        return this.classroomYearService.saveAll(classroomYears);
    }

    @GetMapping("/get/one")
    public ClassroomYear getClassroomYear(@RequestParam Long id) {
        return this.classroomYearService.getById(id);
    }

    @GetMapping("/get/all")
    public Page<ClassroomYear> getAllClassroomYears(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "classroom.name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.classroomYearService.getAll(page, size, sortBy, sortDir);
    }

    @GetMapping("/get/all/by/example")
    public Page<ClassroomYear> getAllClassroomYearsByExample(@RequestBody @Valid ClassroomYear classroomYear, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "classroom.name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.classroomYearService.getAllByExample(classroomYear, page, size, sortBy, sortDir);
    }

    @GetMapping("/get/all/by/filter")
    public Page<ClassroomYear> getAllClassroomsByFilter(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir, @RequestParam(defaultValue = "", required = false) String filter) {
        return this.classroomYearService.getAllByFilter(page, size, sortBy, sortDir, filter);
    }

    @GetMapping("/view/get/one")
    public ClassroomYearDto getClassroomYearView(@RequestParam Long id) {
        return this.classroomYearService.getViewById(id);
    }

    @GetMapping("/view/get/all")
    public Page<ClassroomYearDto> getAllClassroomYearViews(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "classroom.name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.classroomYearService.getViewAll(page, size, sortBy, sortDir);
    }

    @GetMapping("/view/get/all/by/example")
    public Page<ClassroomYearDto> getAllClassroomYearViewsByExample(@RequestBody @Valid ClassroomYear classroomYear, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "classroom.name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.classroomYearService.getViewAllByExample(classroomYear, page, size, sortBy, sortDir);
    }

    @GetMapping("/view/get/all/by/filter")
    public Page<ClassroomYearDto> getAllClassroomYearViewsByFilter(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir, @RequestParam(defaultValue = "", required = false) String filter) {
        return this.classroomYearService.getViewAllByFilter(page, size, sortBy, sortDir, filter);
    }

    @GetMapping("/dropdown/get/all")
    public List<Map<String, Object>> getAllClassroomYearForDropdown() {
        return this.classroomYearService.getAllClassroomYearForDropdown();
    }
}
