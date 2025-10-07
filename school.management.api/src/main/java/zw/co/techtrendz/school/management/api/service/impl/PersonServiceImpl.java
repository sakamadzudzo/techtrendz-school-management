package zw.co.techtrendz.school.management.api.service.impl;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import zw.co.techtrendz.school.management.api.entity.Person;
import zw.co.techtrendz.school.management.api.repository.PersonRepository;
import zw.co.techtrendz.school.management.api.service.PersonService;
import zw.co.techtrendz.school.management.api.utility.SpecificationUtils;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person save(Person person) {
        return this.personRepository.save(person);
    }

    @Override
    public List<Person> saveAll(List<Person> persons) {
        return persons.parallelStream().map(this::save).toList();
    }

    @Override
    public Person getById(Long id) {
        Optional<Person> person = this.personRepository.findById(id);
        if (person.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Person with id '%d' was not found", id));
        }
        return person.get();
    }

    @Override
    public Page<Person> getAll(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (sortBy.isEmpty()) {
            sortBy.add("name");
        }
        String[] sortFields = sortBy.toArray(new String[sortBy.size()]);
        return this.personRepository.findAll(PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
    }

    @Override
    public Page<Person> getAllByExample(Person person, int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (person == null) {
            return this.getAll(page, size, sortBy, sortDir);
        } else {
            Example<Person> example = Example.of(person);

            if (sortBy.isEmpty()) {
                sortBy.add("name");
            }
            String[] sortFields = sortBy.toArray(new String[sortBy.size()]);

            return this.personRepository.findAll(example, PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
        }
    }

    @Override
    public Page<Person> getAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter) {
        if (filter == null || filter.isBlank()) {
            return this.getAll(page, size, sortBy, sortDir);
        } else {
            Specification<Person> spec = Specification.where(null);
            for (var field : Person.class.getDeclaredFields()) {
                field.setAccessible(true); // Ensure private fields are accessible
                if (field.getType().equals(String.class)) {
                    spec = spec.or(SpecificationUtils.likeMatch(field.getName(), filter));
                }
            }

            if (sortBy.isEmpty()) {
                sortBy.add("name");
            }
            String[] sortFields = sortBy.toArray(new String[sortBy.size()]);

            return this.personRepository.findAll(spec, PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
        }
    }
}
