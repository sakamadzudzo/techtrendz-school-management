package zw.co.techtrendz.school.management.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.entity.Classroom;
import zw.co.techtrendz.school.management.api.service.ClassroomService;

import java.util.List;

@RestController
@RequestMapping("/classroom")
@PreAuthorize("hasRole('TEACHER')")
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;

    @PostMapping("/save")
    public Classroom saveClassroom(@RequestBody Classroom classroom) {
        return this.classroomService.save(classroom);
    }

    @PostMapping("/save/all")
    public List<Classroom> saveAllClassrooms(@RequestBody List<Classroom> classrooms) {
        return this.classroomService.saveAll(classrooms);
    }

    @GetMapping("/get/one")
    public Classroom getClassroom(@RequestParam Long id) {
        return this.classroomService.getById(id);
    }

    @GetMapping("/get/all")
    public List<Classroom> getAllClassrooms(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.classroomService.getAll(page, size, sortBy, sortDir).getContent();
    }

    @GetMapping("/get/all/by/example")
    public List<Classroom> getAllClassroomsByExample(@RequestBody Classroom classroom, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.classroomService.getAllByExample(classroom, page, size, sortBy, sortDir).getContent();
    }
}
