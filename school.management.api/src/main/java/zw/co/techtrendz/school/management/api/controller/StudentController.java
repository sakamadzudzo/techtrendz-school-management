package zw.co.techtrendz.school.management.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.entity.Student;
import zw.co.techtrendz.school.management.api.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public Student saveStudent(@RequestBody Student student) {
        return this.studentService.save(student);
    }

    @PostMapping("/save/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public List<Student> saveAllStudents(@RequestBody List<Student> students) {
        return this.studentService.saveAll(students);
    }

    @GetMapping("/get/one")
    public Student getStudent(@RequestParam Long id) {
        return this.studentService.getById(id);
    }

    @GetMapping("/get/all")
    public List<Student> getAllStudents(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.studentService.getAll(page, size, sortBy, sortDir).getContent();
    }

    @GetMapping("/get/all/by/example")
    public List<Student> getAllStudentsByExample(@RequestBody Student student, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.studentService.getAllByExample(student, page, size, sortBy, sortDir).getContent();
    }

    @GetMapping("/get/all/by/classroom")
    public List<Student> getAllStudentsByClassroomId(@RequestParam long classroomId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.studentService.getAllByClassroomId(classroomId, page, size, sortBy, sortDir).getContent();
    }
}
