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
import zw.co.techtrendz.school.management.api.finance.entity.PaymentReference;
import zw.co.techtrendz.school.management.api.finance.service.PaymentReferenceService;

import java.util.List;

@RestController
@RequestMapping("/payment-reference")
@Tag(name = "PaymentReference", description = "Management of payment references")
public class PaymentReferenceController {

    private final PaymentReferenceService paymentReferenceService;

    public PaymentReferenceController(PaymentReferenceService paymentReferenceService) {
        this.paymentReferenceService = paymentReferenceService;
    }

    @Operation(
            summary = "Save payment reference",
            description = "Creates or updates a payment reference record"
    )
    @PostMapping("/save")
    public PaymentReference savePaymentReference(@RequestBody @Valid PaymentReference paymentReference) {
        return this.paymentReferenceService.save(paymentReference);
    }

    @Operation(
            summary = "Save multiple payment references",
            description = "Creates or updates multiple payment reference records"
    )
    @PostMapping("/save/all")
    public List<PaymentReference> saveAllPaymentReferences(@RequestBody @Valid List<PaymentReference> paymentReferences) {
        return this.paymentReferenceService.saveAll(paymentReferences);
    }

    @Operation(
            summary = "Get payment reference by ID",
            description = "Retrieves a specific payment reference using id field"
    )
    @RequestMapping(name = "/get/one")
    public PaymentReference getById(
            @Parameter(name = "id", description = "Identifier field of the entity")
            @RequestParam Long id
    ) {
        try {
            return this.paymentReferenceService.getById(id);
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("PaymentReference with id %d was not found", id), ex);
        }
    }

    @Operation(
            summary = "Get all payment references",
            description = "Retrieves all payment references with pagination and sorting"
    )
    @GetMapping("/get/all")
    public Page<PaymentReference> getAllPaymentReferences(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "reference") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.paymentReferenceService.getAll(page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get all payment references",
            description = "Retrieves all payment references with sorting"
    )
    @GetMapping("/get/all/list")
    public List<PaymentReference> getAllPaymentReferences(
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "reference") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.paymentReferenceService.getAll(sortBy, sortDir);
    }

    @Operation(
            summary = "Get all payment references by example",
            description = "Retrieves all payment references with pagination and sorting using provided example"
    )
    @GetMapping("/get/all/by/example")
    public Page<PaymentReference> getAllPaymentReferencesByExample(
            @Parameter(description = "PaymentReference object with only fields to used in search") @RequestBody PaymentReference paymentReference,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "reference") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.paymentReferenceService.getAllByExample(paymentReference, page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get all payment references by example",
            description = "Retrieves all payment references with sorting using provided example"
    )
    @GetMapping("/get/all/by/example/list")
    public List<PaymentReference> getAllPaymentReferencesByExample(
            @Parameter(description = "PaymentReference object with only fields to used in search") @RequestBody PaymentReference paymentReference,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "reference") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.paymentReferenceService.getAllByExample(paymentReference, sortBy, sortDir);
    }
}