package zw.co.techtrendz.school.management.api.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import zw.co.techtrendz.school.management.api.entity.Person;
import zw.co.techtrendz.school.management.api.repository.PersonRepository;
import zw.co.techtrendz.school.management.api.service.impl.PersonServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonServiceTests {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSavePerson() {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");

        when(personRepository.save(person)).thenReturn(person);

        Person savedPerson = personService.save(person);

        assertNotNull(savedPerson);
        assertEquals("John", savedPerson.getFirstName());
        assertEquals("Doe", savedPerson.getLastName());
        verify(personRepository, times(1)).save(person);
    }

    @Test
    void testSaveAllPersons() {
        Person person1 = new Person();
        person1.setFirstName("John");
        person1.setLastName("Doe");
        Person person2 = new Person();
        person2.setFirstName("Jane");
        person2.setLastName("Doe");

        List<Person> persons = Arrays.asList(person1, person2);

        when(personRepository.saveAll(persons)).thenReturn(persons);

        List<Person> savedPersons = personService.saveAll(persons);

        assertNotNull(savedPersons);
        assertEquals(2, savedPersons.size());
        verify(personRepository, times(1)).save(person1);
        verify(personRepository, times(1)).save(person2);
    }

    @Test
    void testGetPersonById() {
        Person person = new Person();
        person.setId(1L);
        person.setFirstName("John");
        person.setLastName("Doe");

        when(personRepository.findById(1L)).thenReturn(Optional.of(person));

        Person foundPerson = personService.getById(1L);

        assertNotNull(foundPerson);
        assertEquals(1L, foundPerson.getId());
        assertEquals("John", foundPerson.getFirstName());
        assertEquals("Doe", foundPerson.getLastName());
        verify(personRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllPersons() {
        Person person1 = new Person();
        person1.setFirstName("John");
        person1.setLastName("Doe");
        Person person2 = new Person();
        person2.setFirstName("Jane");
        person2.setLastName("Doe");

        List<Person> persons = Arrays.asList(person1, person2);
        Page<Person> page = new PageImpl<>(persons);

        when(personRepository.findAll(PageRequest.of(0, 10, Sort.by("name").ascending()))).thenReturn(page);

        List<Person> foundPersons = personService.getAll(0, 10, List.of("name"), Sort.Direction.ASC).getContent();

        assertNotNull(foundPersons);
        assertEquals(2, foundPersons.size());
        verify(personRepository, times(1)).findAll(PageRequest.of(0, 10, Sort.by("name").ascending()));
    }

    @Test
    void testGetAllPersonsByExample() {
        Person example = new Person();
        example.setFirstName("John");
        example.setLastName("Doe");

        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");

        List<Person> persons = List.of(person);
        Page<Person> page = new PageImpl<>(persons);

        when(personRepository.findAll(any(Example.class), eq(PageRequest.of(0, 10, Sort.by("name").ascending())))).thenReturn(page);

        List<Person> foundPersons = personService.getAllByExample(example, 0, 10, List.of("name"), Sort.Direction.ASC).getContent();

        assertNotNull(foundPersons);
        assertEquals(1, foundPersons.size());
        assertEquals("John", foundPersons.get(0).getFirstName());
        assertEquals("Doe", foundPersons.get(0).getLastName());
        verify(personRepository, times(1)).findAll(any(Example.class), eq(PageRequest.of(0, 10, Sort.by("name").ascending())));
    }
}
