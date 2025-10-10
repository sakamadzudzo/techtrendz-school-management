package zw.co.techtrendz.school.management.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.entity.Mark;
import zw.co.techtrendz.school.management.api.enums.CourseworkType;
import zw.co.techtrendz.school.management.api.service.MarkService;

import java.util.List;

@RestController
@RequestMapping("/mark")
@Tag(name = "Marks", description = "Management of student marks and grades")
public class MarkController {

    private final MarkService markService;

    public MarkController(MarkService markService) {
        this.markService = markService;
    }

    @Operation(
            summary = "Save mark",
            description = "Creates or updates a student mark record"
    )
    @PostMapping("/save")
    @PreAuthorize("hasRole('TEACHER')")
    public Mark save(@RequestBody @Valid Mark mark) {
        return this.markService.save(mark);
    }

    @Operation(
            summary = "Save multiple marks",
            description = "Creates or updates multiple student mark records"
    )
    @PostMapping("/save/all")
    @PreAuthorize("hasRole('TEACHER')")
    public List<Mark> saveAll(@RequestBody @Valid List<Mark> marks) {
        return this.markService.saveAll(marks);
    }

    @Operation(
            summary = "Get mark by ID",
            description = "Retrieves a specific mark by its ID"
    )
    @GetMapping("/get/by/id")
    public Mark getById(@Parameter(description = "Mark ID") @RequestParam long id) {
        return this.markService.getById(id);
    }

    @Operation(
            summary = "Get all marks",
            description = "Retrieves all mark records"
    )
    @GetMapping("/get/all")
    public List<Mark> getAll() {
        return this.markService.getAll();
    }

    @Operation(
            summary = "Get marks by coursework",
            description = "Retrieves all marks for a specific coursework"
    )
    @GetMapping("/get/all/by/work")
    public List<Mark> getAllByCourseworkId(@Parameter(description = "Coursework ID") @RequestParam long courseworkId) {
        return this.markService.getAllByCourseworkId(courseworkId);
    }

    @Operation(
            summary = "Get marks by student",
            description = "Retrieves all marks for a specific student"
    )
    @GetMapping("/get/all/by/student")
    public List<Mark> getAllByStudentId(@Parameter(description = "Student ID") @RequestParam long studentId) {
        return this.markService.getAllByStudentId(studentId);
    }

    @Operation(
            summary = "Get marks by subject",
            description = "Retrieves all marks for a specific subject"
    )
    @GetMapping("/get/all/by/subject")
    public List<Mark> getAllBySubjectId(@Parameter(description = "Subject ID") @RequestParam long subjectId) {
        return this.markService.getAllBySubjectId(subjectId);
    }

    @Operation(
            summary = "Get marks by subject and coursework",
            description = "Retrieves all marks for a specific subject and coursework combination"
    )
    @GetMapping("/get/all/by/subject/by/work")
    public List<Mark> getAllBySubjectIdByCourseworkId(
            @Parameter(description = "Subject ID") @RequestParam long subjectId,
            @Parameter(description = "Coursework ID") @RequestParam long courseworkId) {
        return this.markService.getAllBySubjectIdByCourseworkId(subjectId, courseworkId);
    }

    @Operation(
            summary = "Get marks by student and subject",
            description = "Retrieves all marks for a specific student and subject combination"
    )
    @GetMapping("/get/all/by/student/by/subject")
    public List<Mark> getAllByStudentIdBySubjectId(
            @Parameter(description = "Student ID") @RequestParam long studentId,
            @Parameter(description = "Subject ID") @RequestParam long subjectId) {
        return this.markService.getAllByStudentIdBySubjectId(studentId, subjectId);
    }

    @Operation(
            summary = "Get mark by student and coursework",
            description = "Retrieves a specific mark for a student in a particular coursework"
    )
    @GetMapping("/get/by/student/by/work")
    public Mark getByStudentIdByCourseworkId(
            @Parameter(description = "Student ID") @RequestParam long studentId,
            @Parameter(description = "Coursework ID") @RequestParam long courseworkId) {
        return this.markService.getByStudentIdByCourseworkId(studentId, courseworkId);
    }

    @Operation(
            summary = "Get marks by subject and coursework type",
            description = "Retrieves all marks for a specific subject and coursework type combination"
    )
    @GetMapping("/get/all/by/subject/by/work/type")
    public List<Mark> getAllBySubjectIdByCourseworkType(
            @Parameter(description = "Subject ID") @RequestParam long subjectId,
            @Parameter(description = "Coursework type") @RequestParam CourseworkType type) {
        return this.markService.getAllBySubjectIdByCourseworkType(subjectId, type);
    }

    @Operation(
            summary = "Get marks by student and coursework type",
            description = "Retrieves all marks for a specific student and coursework type combination"
    )
    @GetMapping("/get/all/by/student/work/type")
    public List<Mark> getAllByStudentIdByCourseworkType(
            @Parameter(description = "Student ID") @RequestParam long studentId,
            @Parameter(description = "Coursework type") @RequestParam CourseworkType type) {
        return this.markService.getAllByStudentIdByCourseworkType(studentId, type);
    }

    @Operation(
            summary = "Get marks by student, subject and coursework type",
            description = "Retrieves all marks for a specific student, subject and coursework type combination"
    )
    @GetMapping("/get/all/by/student/by/subject/by/work/type")
    public List<Mark> getAllByStudentIdBySubjectIdByCourseworkType(
            @Parameter(description = "Student ID") @RequestParam long studentId,
            @Parameter(description = "Subject ID") @RequestParam long subjectId,
            @Parameter(description = "Coursework type") @RequestParam CourseworkType type) {
        return this.markService.getAllByStudentIdBySubjectIdByCourseworkType(studentId, subjectId, type);
    }
}
