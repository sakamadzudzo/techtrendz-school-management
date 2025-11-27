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
import zw.co.techtrendz.school.management.api.common.dto.YearDropdown;
import zw.co.techtrendz.school.management.api.finance.entity.Bank;
import zw.co.techtrendz.school.management.api.finance.service.BankService;

import java.util.List;

@RestController
@RequestMapping("/bank")
@Tag(name = "Bank", description = "Management of bank names/list")
public class BankController {

    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @Operation(
            summary = "Save bank",
            description = "Creates or updates a bank record"
    )
    @PostMapping("/save")
    public Bank saveBank(@RequestBody @Valid Bank bank) {
        return this.bankService.save(bank);
    }

    @Operation(
            summary = "Save multiple banks",
            description = "Creates or updates multiple bank records"
    )
    @PostMapping("/save/all")
    public List<Bank> saveAllBanks(@RequestBody @Valid List<Bank> banks) {
        return this.bankService.saveAll(banks);
    }

    @Operation(
            summary = "Get bank by ID",
            description = "Retrieves a specific bank using id field"
    )
    @RequestMapping(name = "/get/one")
    public Bank getById(
            @Parameter(name = "id", description = "Identifier field of the entity")
            @RequestParam Long id
    ) {
        try {
            return this.bankService.getById(id);
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Bank with id %d was not found", id), ex);
        }
    }

    @Operation(
            summary = "Get all banks",
            description = "Retrieves all banks with pagination and sorting"
    )
    @GetMapping("/get/all")
    public Page<Bank> getAllBanks(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.bankService.getAll(page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get all banks",
            description = "Retrieves all banks with sorting"
    )
    @GetMapping("/get/all/list")
    public List<Bank> getAllBanks(
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.bankService.getAll(sortBy, sortDir);
    }

    @Operation(
            summary = "Get all banks by example",
            description = "Retrieves all banks with pagination and sorting using provided example"
    )
    @GetMapping("/get/all/by/example")
    public Page<Bank> getAllBanksByExample(
            @Parameter(description = "Bank object with only fields to used in search") @RequestBody Bank bank,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.bankService.getAllByExample(bank, page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get all banks by example",
            description = "Retrieves all banks with sorting using provided example"
    )
    @GetMapping("/get/all/by/example/list")
    public List<Bank> getAllBanksByExample(
            @Parameter(description = "Bank object with only fields to used in search") @RequestBody Bank bank,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.bankService.getAllByExample(bank, sortBy, sortDir);
    }

    @Operation(
            summary = "Get all banks for dropdown",
            description = "Retrieves all banks with sorting for dropdowns"
    )
    @GetMapping("/get/all/dropdown")
    public List<YearDropdown> getAllBanksForDropdown(
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.bankService.getAllForDropdown(sortBy, sortDir);
    }
}
