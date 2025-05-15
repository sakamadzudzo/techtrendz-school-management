package zw.co.techtrendz.school.management.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.Person;
import zw.co.techtrendz.school.management.api.repository.PersonRepository;
import zw.co.techtrendz.school.management.api.service.PersonService;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Person save(Person person) {
        return this.personRepository.save(person);
    }

    @Override
    public List<Person> saveAll(List<Person> persons) {
        return this.personRepository.saveAll(persons);
    }

    @Override
    public Person getById(Long id) {
        return this.personRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Person> getAll(int page, int size, String[] sortBy, Sort.Direction sortDir) {
        return this.personRepository.findAll(PageRequest.of(page, size, Sort.by(sortDir, sortBy)));
    }

    @Override
    public Page<Person> getAllByExample(Person person, int page, int size, String[] sortBy, Sort.Direction sortDir) {
        if (person == null) {
            return null;
        } else {
            Example<Person> example = Example.of(person);
            return this.personRepository.findAll(example, PageRequest.of(page, size, Sort.by(sortDir, sortBy)));
        }
    }
}
