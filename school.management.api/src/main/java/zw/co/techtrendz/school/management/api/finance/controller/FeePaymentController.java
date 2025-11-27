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
import zw.co.techtrendz.school.management.api.finance.entity.FeePayment;
import zw.co.techtrendz.school.management.api.finance.service.FeePaymentService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/fee-payment")
@Tag(name = "FeePayment", description = "Management of fee payments")
public class FeePaymentController {

    private final FeePaymentService feePaymentService;

    public FeePaymentController(FeePaymentService feePaymentService) {
        this.feePaymentService = feePaymentService;
    }

    @Operation(
            summary = "Save fee payment",
            description = "Creates or updates a fee payment record"
    )
    @PostMapping("/save")
    public FeePayment saveFeePayment(@RequestBody @Valid FeePayment feePayment) {
        return this.feePaymentService.save(feePayment);
    }

    @Operation(
            summary = "Save multiple fee payments",
            description = "Creates or updates multiple fee payment records"
    )
    @PostMapping("/save/all")
    public List<FeePayment> saveAllFeePayments(@RequestBody @Valid List<FeePayment> feePayments) {
        return this.feePaymentService.saveAll(feePayments);
    }

    @Operation(
            summary = "Get fee payment by ID",
            description = "Retrieves a specific fee payment using id field"
    )
    @RequestMapping(name = "/get/one")
    public FeePayment getById(
            @Parameter(name = "id", description = "Identifier field of the entity")
            @RequestParam Long id
    ) {
        try {
            return this.feePaymentService.getById(id);
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("FeePayment with id %d was not found", id), ex);
        }
    }

    @Operation(
            summary = "Get fee payment by reference",
            description = "Retrieves a specific fee payment using reference"
    )
    @GetMapping("/get/by/reference")
    public FeePayment getByReference(
            @Parameter(name = "reference", description = "Reference of the fee payment")
            @RequestParam String reference
    ) {
        return this.feePaymentService.getByReference(reference);
    }

    @Operation(
            summary = "Get all fee payments",
            description = "Retrieves all fee payments with pagination and sorting"
    )
    @GetMapping("/get/all")
    public Page<FeePayment> getAllFeePayments(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "paymentDate") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.feePaymentService.getAll(page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get all fee payments",
            description = "Retrieves all fee payments with sorting"
    )
    @GetMapping("/get/all/list")
    public List<FeePayment> getAllFeePayments(
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "paymentDate") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.feePaymentService.getAll(sortBy, sortDir);
    }

    @Operation(
            summary = "Get all fee payments by example",
            description = "Retrieves all fee payments with pagination and sorting using provided example"
    )
    @GetMapping("/get/all/by/example")
    public Page<FeePayment> getAllFeePaymentsByExample(
            @Parameter(description = "FeePayment object with only fields to used in search") @RequestBody FeePayment feePayment,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "paymentDate") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.feePaymentService.getAllByExample(feePayment, page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get all fee payments by example",
            description = "Retrieves all fee payments with sorting using provided example"
    )
    @GetMapping("/get/all/by/example/list")
    public List<FeePayment> getAllFeePaymentsByExample(
            @Parameter(description = "FeePayment object with only fields to used in search") @RequestBody FeePayment feePayment,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "paymentDate") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.feePaymentService.getAllByExample(feePayment, sortBy, sortDir);
    }

    @Operation(
            summary = "Get all fee payments by verified status",
            description = "Retrieves all fee payments by verified status"
    )
    @GetMapping("/get/all/by/verified")
    public List<FeePayment> getAllByVerified(
            @Parameter(description = "Verified status") @RequestParam boolean verified,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "paymentDate") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.feePaymentService.getAllByVerified(verified);
    }

    @Operation(
            summary = "Get all fee payments by payment date between",
            description = "Retrieves all fee payments between specified dates"
    )
    @GetMapping("/get/all/by/payment/date/between")
    public List<FeePayment> getAllByPaymentDateBetween(
            @Parameter(description = "Start date") @RequestParam LocalDate startDate,
            @Parameter(description = "End date") @RequestParam LocalDate endDate) {
        return this.feePaymentService.getAllByPaymentDateBetween(startDate, endDate);
    }

    @Operation(
            summary = "Get all fee payments by references",
            description = "Retrieves all fee payments by list of references"
    )
    @GetMapping("/get/all/by/references")
    public List<FeePayment> getAllByReferences(@RequestBody List<String> references) {
        return this.feePaymentService.getAllByReferences(references);
    }
}