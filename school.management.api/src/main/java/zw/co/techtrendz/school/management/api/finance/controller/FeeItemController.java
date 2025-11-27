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
import zw.co.techtrendz.school.management.api.finance.entity.FeeItem;
import zw.co.techtrendz.school.management.api.finance.service.FeeItemService;

import java.util.List;

@RestController
@RequestMapping("/fee-item")
@Tag(name = "FeeItem", description = "Management of fee items")
public class FeeItemController {

    private final FeeItemService feeItemService;

    public FeeItemController(FeeItemService feeItemService) {
        this.feeItemService = feeItemService;
    }

    @Operation(
            summary = "Save fee item",
            description = "Creates or updates a fee item record"
    )
    @PostMapping("/save")
    public FeeItem saveFeeItem(@RequestBody @Valid FeeItem feeItem) {
        return this.feeItemService.save(feeItem);
    }

    @Operation(
            summary = "Save multiple fee items",
            description = "Creates or updates multiple fee item records"
    )
    @PostMapping("/save/all")
    public List<FeeItem> saveAllFeeItems(@RequestBody @Valid List<FeeItem> feeItems) {
        return this.feeItemService.saveAll(feeItems);
    }

    @Operation(
            summary = "Get fee item by ID",
            description = "Retrieves a specific fee item using id field"
    )
    @RequestMapping(name = "/get/one")
    public FeeItem getById(
            @Parameter(name = "id", description = "Identifier field of the entity")
            @RequestParam Long id
    ) {
        try {
            return this.feeItemService.getById(id);
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("FeeItem with id %d was not found", id), ex);
        }
    }

    @Operation(
            summary = "Get all fee items",
            description = "Retrieves all fee items with pagination and sorting"
    )
    @GetMapping("/get/all")
    public Page<FeeItem> getAllFeeItems(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.feeItemService.getAll(page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get all fee items",
            description = "Retrieves all fee items with sorting"
    )
    @GetMapping("/get/all/list")
    public List<FeeItem> getAllFeeItems(
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.feeItemService.getAll(sortBy, sortDir);
    }

    @Operation(
            summary = "Get all fee items by example",
            description = "Retrieves all fee items with pagination and sorting using provided example"
    )
    @GetMapping("/get/all/by/example")
    public Page<FeeItem> getAllFeeItemsByExample(
            @Parameter(description = "FeeItem object with only fields to used in search") @RequestBody FeeItem feeItem,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.feeItemService.getAllByExample(feeItem, page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get all fee items by example",
            description = "Retrieves all fee items with sorting using provided example"
    )
    @GetMapping("/get/all/by/example/list")
    public List<FeeItem> getAllFeeItemsByExample(
            @Parameter(description = "FeeItem object with only fields to used in search") @RequestBody FeeItem feeItem,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.feeItemService.getAllByExample(feeItem, sortBy, sortDir);
    }
}