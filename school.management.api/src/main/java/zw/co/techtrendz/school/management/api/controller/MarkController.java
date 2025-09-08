package zw.co.techtrendz.school.management.api.controller;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.entity.Mark;
import zw.co.techtrendz.school.management.api.enums.CourseworkType;
import zw.co.techtrendz.school.management.api.service.MarkService;

import java.util.List;

@RestController
@RequestMapping("/mark")
public class MarkController {

    private final MarkService markService;

    public MarkController(MarkService markService) {
        this.markService = markService;
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('TEACHER')")
    public Mark save(@RequestBody @Valid Mark mark) {
        return this.markService.save(mark);
    }

    @PostMapping("/save/all")
    @PreAuthorize("hasRole('TEACHER')")
    public List<Mark> saveAll(@RequestBody @Valid List<Mark> marks) {
        return this.markService.saveAll(marks);
    }

    @GetMapping("/get/by/id")
    public Mark getById(@RequestParam long id) {
        return this.markService.getById(id);
    }

    @GetMapping("/get/all")
    public List<Mark> getAll() {
        return this.markService.getAll();
    }

    @GetMapping("/get/all/by/work")
    public List<Mark> getAllByCourseworkId(@RequestParam long courseworkId) {
        return this.markService.getAllByCourseworkId(courseworkId);
    }

    @GetMapping("/get/all/by/student")
    public List<Mark> getAllByStudentId(@RequestParam long studentId) {
        return this.markService.getAllByStudentId(studentId);
    }

    @GetMapping("/get/all/by/subject")
    public List<Mark> getAllBySubjectId(@RequestParam long subjectId) {
        return this.markService.getAllBySubjectId(subjectId);
    }

    @GetMapping("/get/all/by/subject/by/work")
    public List<Mark> getAllBySubjectIdByCourseworkId(@RequestParam long subjectId, @RequestParam long courseworkId) {
        return this.markService.getAllBySubjectIdByCourseworkId(subjectId, courseworkId);
    }

    @GetMapping("/get/all/by/student/by/subject")
    public List<Mark> getAllByStudentIdBySubjectId(@RequestParam long studentId, @RequestParam long subjectId) {
        return this.markService.getAllByStudentIdBySubjectId(studentId, subjectId);
    }

    @GetMapping("/get/by/student/by/work")
    public Mark getByStudentIdByCourseworkId(@RequestParam long studentId, @RequestParam long courseworkId) {
        return this.markService.getByStudentIdByCourseworkId(studentId, courseworkId);
    }

    @GetMapping("/get/all/by/subject/by/work/type")
    public List<Mark> getAllBySubjectIdByCourseworkType(@RequestParam long subjectId, @RequestParam CourseworkType type) {
        return this.markService.getAllBySubjectIdByCourseworkType(subjectId, type);
    }

    @GetMapping("/get/all/by/student/work/type")
    public List<Mark> getAllByStudentIdByCourseworkType(@RequestParam long studentId, @RequestParam CourseworkType type) {
        return this.markService.getAllByStudentIdByCourseworkType(studentId, type);
    }

    @GetMapping("/get/all/by/student/by/subject/by/work/type")
    public List<Mark> getAllByStudentIdBySubjectIdByCourseworkType(@RequestParam long studentId, @RequestParam long subjectId, @RequestParam CourseworkType type) {
        return this.markService.getAllByStudentIdBySubjectIdByCourseworkType(studentId, subjectId, type);
    }
}
