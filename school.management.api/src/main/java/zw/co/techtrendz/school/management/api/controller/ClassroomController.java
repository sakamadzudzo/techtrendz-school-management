package zw.co.techtrendz.school.management.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.entity.Classroom;
import zw.co.techtrendz.school.management.api.service.ClassroomService;

import java.util.List;

@RestController
@RequestMapping("/classroom")
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public Classroom saveClassroom(@RequestBody @Valid Classroom classroom) {
        return this.classroomService.save(classroom);
    }

    @PostMapping("/save/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public List<Classroom> saveAllClassrooms(@RequestBody @Valid List<Classroom> classrooms) {
        return this.classroomService.saveAll(classrooms);
    }

    @GetMapping("/get/one")
    public Classroom getClassroom(@RequestParam Long id) {
        return this.classroomService.getById(id);
    }

    @GetMapping("/get/all")
    public Page<Classroom> getAllClassrooms(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.classroomService.getAll(page, size, sortBy, sortDir);
    }

    @GetMapping("/get/all/by/example")
    public Page<Classroom> getAllClassroomsByExample(@RequestBody @Valid Classroom classroom, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.classroomService.getAllByExample(classroom, page, size, sortBy, sortDir);
    }

    @GetMapping("/get/all/by/filter")
    public Page<Classroom> getAllClassroomsByFilter(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir, @RequestParam(defaultValue = "", required = false) String filter) {
        return this.classroomService.getAllByFilter(page, size, sortBy, sortDir, filter);
    }
}
