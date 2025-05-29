package zw.co.techtrendz.school.management.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.entity.Teacher;
import zw.co.techtrendz.school.management.api.service.TeacherService;

import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public Teacher saveTeacher(@RequestBody @Valid Teacher teacher) {
        return this.teacherService.save(teacher);
    }

    @PostMapping("/save/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Teacher> saveAllTeachers(@RequestBody @Valid List<Teacher> teachers) {
        return this.teacherService.saveAll(teachers);
    }

    @GetMapping("/get/one")
    @PreAuthorize("hasRole('ADMIN')")
    public Teacher getTeacher(@RequestParam Long id) {
        return this.teacherService.getById(id);
    }

    @GetMapping("/get/all")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<Teacher> getAllTeachers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.teacherService.getAll(page, size, sortBy, sortDir);
    }

    @GetMapping("/get/all/by/example")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<Teacher> getAllTeachersByExample(@RequestBody @Valid Teacher teacher, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.teacherService.getAllByExample(teacher, page, size, sortBy, sortDir);
    }

    @GetMapping("/get/all/by/filter")
    public Page<Teacher> getAllClassroomsByFilter(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir, @RequestParam(defaultValue = "", required = false) String filter) {
        return this.teacherService.getAllByFilter(page, size, sortBy, sortDir, filter);
    }
}
