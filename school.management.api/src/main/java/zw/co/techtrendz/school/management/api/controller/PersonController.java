package zw.co.techtrendz.school.management.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.entity.Person;
import zw.co.techtrendz.school.management.api.service.PersonService;

import java.util.List;

@RestController
@RequestMapping("/person")
@Tag(name = "Persons", description = "Management of person information")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @Operation(
            summary = "Save person",
            description = "Creates or updates a person record"
    )
    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public Person savePerson(@RequestBody @Valid Person person) {
        return this.personService.save(person);
    }

    @Operation(
            summary = "Save multiple persons",
            description = "Creates or updates multiple person records"
    )
    @PostMapping("/save/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Person> saveAllPersons(@RequestBody @Valid List<Person> persons) {
        return this.personService.saveAll(persons);
    }

    @Operation(
            summary = "Get person by ID",
            description = "Retrieves a specific person by their ID"
    )
    @GetMapping("/get/one")
    public Person getPerson(@Parameter(description = "Person ID") @RequestParam Long id) {
        return this.personService.getById(id);
    }

    @Operation(
            summary = "Get all persons",
            description = "Retrieves all persons with pagination and sorting"
    )
    @GetMapping("/get/all")
    public Page<Person> getAllPersons(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.personService.getAll(page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get persons by example",
            description = "Retrieves persons matching the provided example with pagination and sorting"
    )
    @GetMapping("/get/all/by/example")
    public Page<Person> getAllPersonsByExample(
            @RequestBody @Valid Person person,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.personService.getAllByExample(person, page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get persons by filter",
            description = "Retrieves persons matching the provided filter text with pagination and sorting"
    )
    @GetMapping("/get/all/by/filter")
    public Page<Person> getAllClassroomsByFilter(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir,
            @Parameter(description = "Filter text") @RequestParam(defaultValue = "", required = false) String filter) {
        return this.personService.getAllByFilter(page, size, sortBy, sortDir, filter);
    }
}
