package zw.co.techtrendz.school.management.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.dto.CourseworkDropdown;
import zw.co.techtrendz.school.management.api.dto.ReportsFilters;
import zw.co.techtrendz.school.management.api.dto.YearDropdown;
import zw.co.techtrendz.school.management.api.service.ReportsDropdownsService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reports/dropdowns")
@Tag(name = "Reports Dropdown Data", description = "Dropdown data for reports filtering")
public class ReportsDropdownsController {

    private final ReportsDropdownsService reportsDropdownsService;

    public ReportsDropdownsController(ReportsDropdownsService reportsDropdownsService) {
        this.reportsDropdownsService = reportsDropdownsService;
    }

    // Get all dropdown data for any report
    @Operation(
            summary = "Get all dropdown data",
            description = "Retrieves all defined dropdown data for any/all reports"
    )
    @GetMapping("/all")
    public ResponseEntity<ReportsFilters> getAllDropdowns() {
        return ResponseEntity.ok(this.reportsDropdownsService.getAllDropdowns());
    }

    @Operation(
            summary = "Get select dropdown data",
            description = "Retrieves defined dropdown data for parameters passed 'true'"
    )
    @GetMapping("/selective")
    public ResponseEntity<ReportsFilters> getSelectiveDropdowns(
            @Parameter(description = "Should student dropdown data be returned or not")
            @RequestParam(required = false, defaultValue = "false") boolean students,
            @Parameter(description = "Should teacher dropdown data be returned or not")
            @RequestParam(required = false, defaultValue = "false") boolean teachers,
            @Parameter(description = "Should coursework dropdown data be returned or not")
            @RequestParam(required = false, defaultValue = "false") boolean courseworks,
            @Parameter(description = "Should classroom dropdown data be returned or not")
            @RequestParam(required = false, defaultValue = "false") boolean classrooms,
            @Parameter(description = "Should subject dropdown data be returned or not")
            @RequestParam(required = false, defaultValue = "false") boolean subjects,
            @Parameter(description = "Should extra curricular dropdown data be returned or not")
            @RequestParam(required = false, defaultValue = "false") boolean extraCurriculars,
            @Parameter(description = "Should calendar year dropdown data be returned or not")
            @RequestParam(required = false, defaultValue = "true") boolean calendarYears) {
        return ResponseEntity.ok(this.reportsDropdownsService.getSelectiveDropdowns(students, teachers, courseworks, classrooms, subjects, extraCurriculars, calendarYears));
    }
}