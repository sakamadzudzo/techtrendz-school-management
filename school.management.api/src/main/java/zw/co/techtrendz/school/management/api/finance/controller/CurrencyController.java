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
import zw.co.techtrendz.school.management.api.finance.entity.Currency;
import zw.co.techtrendz.school.management.api.finance.service.CurrencyService;

import java.util.List;

@RestController
@RequestMapping("/currency")
@Tag(name = "Currency", description = "Management of currencies")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Operation(
            summary = "Save currency",
            description = "Creates or updates a currency record"
    )
    @PostMapping("/save")
    public Currency saveCurrency(@RequestBody @Valid Currency currency) {
        return this.currencyService.save(currency);
    }

    @Operation(
            summary = "Save multiple currencies",
            description = "Creates or updates multiple currency records"
    )
    @PostMapping("/save/all")
    public List<Currency> saveAllCurrencies(@RequestBody @Valid List<Currency> currencies) {
        return this.currencyService.saveAll(currencies);
    }

    @Operation(
            summary = "Get currency by ID",
            description = "Retrieves a specific currency using id field"
    )
    @RequestMapping(name = "/get/one")
    public Currency getById(
            @Parameter(name = "id", description = "Identifier field of the entity")
            @RequestParam Long id
    ) {
        try {
            return this.currencyService.getById(id);
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Currency with id %d was not found", id), ex);
        }
    }

    @Operation(
            summary = "Get all currencies",
            description = "Retrieves all currencies with pagination and sorting"
    )
    @GetMapping("/get/all")
    public Page<Currency> getAllCurrencies(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.currencyService.getAll(page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get all currencies",
            description = "Retrieves all currencies with sorting"
    )
    @GetMapping("/get/all/list")
    public List<Currency> getAllCurrencies(
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.currencyService.getAll(sortBy, sortDir);
    }

    @Operation(
            summary = "Get all currencies by example",
            description = "Retrieves all currencies with pagination and sorting using provided example"
    )
    @GetMapping("/get/all/by/example")
    public Page<Currency> getAllCurrenciesByExample(
            @Parameter(description = "Currency object with only fields to used in search") @RequestBody Currency currency,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.currencyService.getAllByExample(currency, page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get all currencies by example",
            description = "Retrieves all currencies with sorting using provided example"
    )
    @GetMapping("/get/all/by/example/list")
    public List<Currency> getAllCurrenciesByExample(
            @Parameter(description = "Currency object with only fields to used in search") @RequestBody Currency currency,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.currencyService.getAllByExample(currency, sortBy, sortDir);
    }

    @Operation(
            summary = "Get all currencies for dropdown",
            description = "Retrieves all currencies with sorting for dropdowns"
    )
    @GetMapping("/get/all/dropdown")
    public List<YearDropdown> getAllCurrenciesForDropdown(
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.currencyService.getAllForDropdown(sortBy, sortDir);
    }
}