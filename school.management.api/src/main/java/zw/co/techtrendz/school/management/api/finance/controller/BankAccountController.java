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
import zw.co.techtrendz.school.management.api.finance.entity.BankAccount;
import zw.co.techtrendz.school.management.api.finance.service.BankAccountService;

import java.util.List;

@RestController
@RequestMapping("/bank-account")
@Tag(name = "BankAccount", description = "Management of bank accounts")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @Operation(
            summary = "Save bank account",
            description = "Creates or updates a bank account record"
    )
    @PostMapping("/save")
    public BankAccount saveBankAccount(@RequestBody @Valid BankAccount bankAccount) {
        return this.bankAccountService.save(bankAccount);
    }

    @Operation(
            summary = "Save multiple bank accounts",
            description = "Creates or updates multiple bank account records"
    )
    @PostMapping("/save/all")
    public List<BankAccount> saveAllBankAccounts(@RequestBody @Valid List<BankAccount> bankAccounts) {
        return this.bankAccountService.saveAll(bankAccounts);
    }

    @Operation(
            summary = "Get bank account by ID",
            description = "Retrieves a specific bank account using id field"
    )
    @RequestMapping(name = "/get/one")
    public BankAccount getById(
            @Parameter(name = "id", description = "Identifier field of the entity")
            @RequestParam Long id
    ) {
        try {
            return this.bankAccountService.getById(id);
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("BankAccount with id %d was not found", id), ex);
        }
    }

    @Operation(
            summary = "Get bank account by account number",
            description = "Retrieves a specific bank account using account number"
    )
    @GetMapping("/get/by/account/number")
    public BankAccount getByAccountNumber(
            @Parameter(name = "accountNumber", description = "Account number of the bank account")
            @RequestParam String accountNumber
    ) {
        try {
            return this.bankAccountService.getByAccountNumber(accountNumber);
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("BankAccount with account number %s was not found", accountNumber), ex);
        }
    }

    @Operation(
            summary = "Get all bank accounts",
            description = "Retrieves all bank accounts with pagination and sorting"
    )
    @GetMapping("/get/all")
    public Page<BankAccount> getAllBankAccounts(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.bankAccountService.getAll(page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get all bank accounts",
            description = "Retrieves all bank accounts with sorting"
    )
    @GetMapping("/get/all/list")
    public List<BankAccount> getAllBankAccounts(
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.bankAccountService.getAll(sortBy, sortDir);
    }

    @Operation(
            summary = "Get all bank accounts by example",
            description = "Retrieves all bank accounts with pagination and sorting using provided example"
    )
    @GetMapping("/get/all/by/example")
    public Page<BankAccount> getAllBankAccountsByExample(
            @Parameter(description = "BankAccount object with only fields to used in search") @RequestBody BankAccount bankAccount,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.bankAccountService.getAllByExample(bankAccount, page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get all bank accounts by example",
            description = "Retrieves all bank accounts with sorting using provided example"
    )
    @GetMapping("/get/all/by/example/list")
    public List<BankAccount> getAllBankAccountsByExample(
            @Parameter(description = "BankAccount object with only fields to used in search") @RequestBody BankAccount bankAccount,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.bankAccountService.getAllByExample(bankAccount, sortBy, sortDir);
    }
}