package zw.co.techtrendz.school.management.api.finance.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import zw.co.techtrendz.school.management.api.finance.dto.OutstandingBalance;
import zw.co.techtrendz.school.management.api.finance.entity.Fee;
import zw.co.techtrendz.school.management.api.finance.service.FeeService;

import java.util.List;

@RestController
@RequestMapping("/fee")
@Tag(name = "Fee", description = "Management of fees")
public class FeeController {

    private final FeeService feeService;

    public FeeController(FeeService feeService) {
        this.feeService = feeService;
    }

    @Operation(
            summary = "Save fee",
            description = "Creates or updates a fee record"
    )
    @PostMapping("/save")
    public Fee saveFee(@RequestBody @Valid Fee fee) {
        return this.feeService.save(fee);
    }

    @Operation(
            summary = "Save multiple fees",
            description = "Creates or updates multiple fee records"
    )
    @PostMapping("/save/all")
    public List<Fee> saveAllFees(@RequestBody @Valid List<Fee> fees) {
        return this.feeService.saveAll(fees);
    }

    @Operation(
            summary = "Get fee by ID",
            description = "Retrieves a specific fee using id field"
    )
    @RequestMapping(name = "/get/one")
    public Fee getById(
            @Parameter(name = "id", description = "Identifier field of the entity")
            @RequestParam Long id
    ) {
        try {
            return this.feeService.getById(id);
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Fee with id %d was not found", id), ex);
        }
    }

    @Operation(
            summary = "Get all fees",
            description = "Retrieves all fees with pagination and sorting"
    )
    @GetMapping("/get/all")
    public Page<Fee> getAllFees(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "calendarYear") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.feeService.getAll(page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get all fees",
            description = "Retrieves all fees with sorting"
    )
    @GetMapping("/get/all/list")
    public List<Fee> getAllFees(
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "calendarYear") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.feeService.getAll(sortBy, sortDir);
    }

    @Operation(
            summary = "Get all fees by example",
            description = "Retrieves all fees with pagination and sorting using provided example"
    )
    @GetMapping("/get/all/by/example")
    public Page<Fee> getAllFeesByExample(
            @Parameter(description = "Fee object with only fields to used in search") @RequestBody Fee fee,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "calendarYear") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.feeService.getAllByExample(fee, page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get all fees by example",
            description = "Retrieves all fees with sorting using provided example"
    )
    @GetMapping("/get/all/by/example/list")
    public List<Fee> getAllFeesByExample(
            @Parameter(description = "Fee object with only fields to used in search") @RequestBody Fee fee,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "calendarYear") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.feeService.getAllByExample(fee, sortBy, sortDir);
    }

    @Operation(
            summary = "Get all fees by student ID",
            description = "Retrieves all fees for a student with pagination and sorting"
    )
    @GetMapping("/get/all/by/student/id")
    public Page<Fee> getAllByStudentId(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "calendarYear") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir,
            @Parameter(description = "Student ID") @RequestParam long studentId) {
        return this.feeService.getAllByStudentId(page, size, sortBy, sortDir, studentId);
    }

    @Operation(
            summary = "Get all fees by student ID",
            description = "Retrieves all fees for a student with sorting"
    )
    @GetMapping("/get/all/by/student/id/list")
    public List<Fee> getAllByStudentId(
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "calendarYear") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir,
            @Parameter(description = "Student ID") @RequestParam long studentId) {
        return this.feeService.getAllByStudentId(sortBy, sortDir, studentId);
    }

    @Operation(
            summary = "Get all fees by calendar year",
            description = "Retrieves all fees for a calendar year with pagination and sorting"
    )
    @GetMapping("/get/all/by/calendar/year")
    public Page<Fee> getAllByCalendarYear(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "calendarYear") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir,
            @Parameter(description = "Calendar year") @RequestParam int calendarYear) {
        return this.feeService.getAllByCalendarYear(page, size, sortBy, sortDir, calendarYear);
    }

    @Operation(
            summary = "Get all fees by calendar year",
            description = "Retrieves all fees for a calendar year with sorting"
    )
    @GetMapping("/get/all/by/calendar/year/list")
    public List<Fee> getAllByCalendarYear(
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "calendarYear") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir,
            @Parameter(description = "Calendar year") @RequestParam int calendarYear) {
        return this.feeService.getAllByCalendarYear(sortBy, sortDir, calendarYear);
    }

    @Operation(
            summary = "Get all fees by student ID and calendar year",
            description = "Retrieves all fees for a student and calendar year with pagination and sorting"
    )
    @GetMapping("/get/all/by/student/id/and/calendar/year")
    public Page<Fee> getAllByStudentIdAndCalendarYear(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "calendarYear") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir,
            @Parameter(description = "Student ID") @RequestParam long studentId,
            @Parameter(description = "Calendar year") @RequestParam int calendarYear) {
        return this.feeService.getAllByStudentIdAndCalendarYear(page, size, sortBy, sortDir, studentId, calendarYear);
    }

    @Operation(
            summary = "Get all fees by student ID and calendar year",
            description = "Retrieves all fees for a student and calendar year with sorting"
    )
    @GetMapping("/get/all/by/student/id/and/calendar/year/list")
    public List<Fee> getAllByStudentIdAndCalendarYear(
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "calendarYear") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir,
            @Parameter(description = "Student ID") @RequestParam long studentId,
            @Parameter(description = "Calendar year") @RequestParam int calendarYear) {
        return this.feeService.getAllByStudentIdAndCalendarYear(sortBy, sortDir, studentId, calendarYear);
    }

    @Operation(
            summary = "Get outstanding balance for student",
            description = "Retrieves the outstanding balance for a student"
    )
    @GetMapping("/get/outstanding/balance")
    public OutstandingBalance getOutstandingBalance(
            @Parameter(description = "Student ID") @RequestParam long studentId) {
        return this.feeService.getOutstandingBalance(studentId);
    }
}