package zw.co.techtrendz.school.management.api.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.user.entity.Person;

import java.util.List;

@Service
public interface PersonService {

    public Person save(Person person);

    public List<Person> saveAll(List<Person> persons);

    public Person getById(Long id);

    public Page<Person> getAll(int page, int size, List<String> sortBy, Sort.Direction sortDir);

    public Page<Person> getAllByExample(Person person, int page, int size, List<String> sortBy, Sort.Direction sortDir);

    public Page<Person> getAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter);
}
