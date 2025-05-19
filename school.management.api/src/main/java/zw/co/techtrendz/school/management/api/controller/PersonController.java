package zw.co.techtrendz.school.management.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.entity.Person;
import zw.co.techtrendz.school.management.api.service.PersonService;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public Person savePerson(@RequestBody Person person) {
        return this.personService.save(person);
    }

    @PostMapping("/save/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Person> saveAllPersons(@RequestBody List<Person> persons) {
        return this.personService.saveAll(persons);
    }

    @GetMapping("/get/one")
    public Person getPerson(@RequestParam Long id) {
        return this.personService.getById(id);
    }

    @GetMapping("/get/all")
    public List<Person> getAllPersons(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.personService.getAll(page, size, sortBy, sortDir).getContent();
    }

    @GetMapping("/get/all/by/example")
    public List<Person> getAllPersonsByExample(@RequestBody Person person, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.personService.getAllByExample(person, page, size, sortBy, sortDir).getContent();
    }
}
