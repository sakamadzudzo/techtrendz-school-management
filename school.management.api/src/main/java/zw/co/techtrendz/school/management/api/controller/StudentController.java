package zw.co.techtrendz.school.management.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.entity.Student;
import zw.co.techtrendz.school.management.api.service.StudentService;
import zw.co.techtrendz.school.management.api.view.StudentDto;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public Student saveStudent(@RequestBody @Valid Student student) {
        return this.studentService.save(student);
    }

    @PostMapping("/save/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public List<Student> saveAllStudents(@RequestBody @Valid List<Student> students) {
        return this.studentService.saveAll(students);
    }

    @GetMapping("/get/one")
    public Student getStudent(@RequestParam Long id) {
        return this.studentService.getById(id);
    }

    @GetMapping("/get/all")
    public Page<Student> getAllStudents(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.studentService.getAll(page, size, sortBy, sortDir);
    }

    @GetMapping("/get/all/by/example")
    public Page<Student> getAllStudentsByExample(@RequestBody @Valid Student student, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.studentService.getAllByExample(student, page, size, sortBy, sortDir);
    }

    @GetMapping("/get/all/by/classroom")
    public Page<Student> getAllStudentsByClassroomId(@RequestParam long classroomId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.studentService.getAllByClassroomId(classroomId, page, size, sortBy, sortDir);
    }

    @GetMapping("/get/all/by/filter")
    public Page<Student> getAllClassroomsByFilter(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir, @RequestParam(defaultValue = "", required = false) String filter) {
        return this.studentService.getAllByFilter(page, size, sortBy, sortDir, filter);
    }

    @GetMapping("/view/get/one")
    public StudentDto getStudentView(@RequestParam Long id) {
        return this.studentService.getViewById(id);
    }

    @GetMapping("/view/get/all")
    public Page<StudentDto> getAllStudentViews(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "person.lastName") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.studentService.getViewAll(page, size, sortBy, sortDir);
    }

    @GetMapping("/view/get/all/by/example")
    public Page<StudentDto> getAllStudentViewsByExample(@RequestBody @Valid Student student, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "person.lastName") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.studentService.getViewAllByExample(student, page, size, sortBy, sortDir);
    }

    @GetMapping("/view/get/all/by/filter")
    public Page<StudentDto> getAllStudentViewsByFilter(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "person.lastName") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir, @RequestParam(defaultValue = "", required = false) String filter) {
        return this.studentService.getViewAllByFilter(page, size, sortBy, sortDir, filter);
    }
}
