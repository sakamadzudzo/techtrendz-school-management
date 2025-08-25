package zw.co.techtrendz.school.management.api.controller;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zw.co.techtrendz.school.management.api.entity.Work;
import zw.co.techtrendz.school.management.api.service.WorkService;

import java.util.List;

@RestController
@RequestMapping("/work")
public class WorkController {

    private final WorkService workService;

    public WorkController(WorkService workService) {
        this.workService = workService;
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('TEACHER')")
    public Work save(@RequestBody @Valid Work work) {
        return this.workService.save(work);
    }

    @PostMapping("/save/all")
    @PreAuthorize("hasRole('TEACHER')")
    public List<Work> saveAll(@RequestBody @Valid List<Work> works) {
        return this.workService.saveAll(works);
    }
}
