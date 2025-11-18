package zw.co.techtrendz.school.management.api.finance.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import zw.co.techtrendz.school.management.api.finance.entity.Bank;
import zw.co.techtrendz.school.management.api.finance.service.BankService;

@RestController
@RequestMapping("/bank")
@Tag(name = "Bank", description = "Management of bank names/list")
public class BankController {

    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    // Save
    // Save all

    // Get by id
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

    // Get all page
    // get all list
    // Get all by example page
    // Get all by example list
}
