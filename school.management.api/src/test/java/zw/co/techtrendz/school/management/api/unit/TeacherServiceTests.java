package zw.co.techtrendz.school.management.api.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import zw.co.techtrendz.school.management.api.user.entity.Person;
import zw.co.techtrendz.school.management.api.user.entity.Teacher;
import zw.co.techtrendz.school.management.api.user.repository.TeacherRepository;
import zw.co.techtrendz.school.management.api.user.service.impl.TeacherServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TeacherServiceTests {

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherServiceImpl teacherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveTeacher() {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        Teacher teacher = new Teacher();
        teacher.setPerson(person);

        Person person1 = new Person();
        person1.setId(1L);
        person1.setFirstName("John");
        person1.setLastName("Doe");
        Teacher teacher1 = new Teacher();
        teacher1.setId(1L);
        teacher1.setPerson(person1);

        when(teacherRepository.save(teacher)).thenReturn(teacher);

        Teacher savedTeacher = teacherService.save(teacher);

        assertNotNull(savedTeacher);
        assertEquals("John", savedTeacher.getPerson().getFirstName());
        assertEquals("Doe", savedTeacher.getPerson().getLastName());
        verify(teacherRepository, times(1)).save(teacher);
    }

    @Test
    void testSaveAllTeachers() {
        Person person1 = new Person();
        person1.setFirstName("John");
        person1.setLastName("Doe");
        Teacher teacher1 = new Teacher();
        teacher1.setPerson(person1);

        Person person2 = new Person();
        person2.setFirstName("Jane");
        person2.setLastName("Doe");
        Teacher teacher2 = new Teacher();
        teacher2.setPerson(person2);

        Person result_person1 = new Person();
        result_person1.setId(1L);
        result_person1.setFirstName("John");
        result_person1.setLastName("Doe");
        Teacher result_teacher1 = new Teacher();
        result_teacher1.setId(1L);
        result_teacher1.setPerson(result_person1);

        Person result_person2 = new Person();
        result_person2.setId(2L);
        result_person2.setFirstName("Jane");
        result_person2.setLastName("Doe");
        Teacher result_teacher2 = new Teacher();
        result_teacher2.setId(2L);
        result_teacher2.setPerson(result_person2);

        List<Teacher> teachers = Arrays.asList(teacher1, teacher2);
        List<Teacher> newTeachers = Arrays.asList(result_teacher1, result_teacher2);

        when(teacherRepository.saveAll(teachers)).thenReturn(newTeachers);

        List<Teacher> savedTeachers = teacherService.saveAll(teachers);

        assertNotNull(savedTeachers);
        assertEquals(2, savedTeachers.size());
        verify(teacherRepository, times(1)).save(teacher1);
        verify(teacherRepository, times(1)).save(teacher2);
    }

    @Test
    void testGetTeacherById() {
        Person person = new Person();
        person.setId(1L);
        person.setFirstName("John");
        person.setLastName("Doe");
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setPerson(person);

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));

        Teacher foundTeacher = teacherService.getById(1L);

        assertNotNull(foundTeacher);
        assertEquals(1L, foundTeacher.getId());
        assertEquals("John", foundTeacher.getPerson().getFirstName());
        assertEquals("Doe", foundTeacher.getPerson().getLastName());
        verify(teacherRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllTeachers() {
        Person result_person1 = new Person();
        result_person1.setId(1L);
        result_person1.setFirstName("John");
        result_person1.setLastName("Doe");
        Teacher result_teacher1 = new Teacher();
        result_teacher1.setId(1L);
        result_teacher1.setPerson(result_person1);

        Person result_person2 = new Person();
        result_person2.setId(1L);
        result_person2.setFirstName("Jane");
        result_person2.setLastName("Doe");
        Teacher result_teacher2 = new Teacher();
        result_teacher2.setId(2L);
        result_teacher2.setPerson(result_person2);

        List<Teacher> teachers = List.of(result_teacher1, result_teacher2);
        Page<Teacher> page = new PageImpl<>(teachers);

        when(teacherRepository.findAll(PageRequest.of(0, 10, Sort.by("name").ascending()))).thenReturn(page);

        List<Teacher> foundTeachers = teacherService.getAll(0, 10, List.of("name"), Sort.Direction.ASC).getContent();

        assertNotNull(foundTeachers);
        assertEquals(2, foundTeachers.size());
        verify(teacherRepository, times(1)).findAll(PageRequest.of(0, 10, Sort.by("name").ascending()));
    }

    @Test
    void testGetAllTeachersByExample() {
        Person person = new Person();
        person.setId(1L);
        person.setFirstName("John");
        person.setLastName("Doe");
        Teacher example = new Teacher();
        example.setPerson(person);

        Person result_person1 = new Person();
        result_person1.setId(1L);
        result_person1.setFirstName("John");
        result_person1.setLastName("Doe");
        Teacher result_teacher1 = new Teacher();
        result_teacher1.setId(1L);
        result_teacher1.setPerson(result_person1);

        Person result_person2 = new Person();
        result_person2.setId(1L);
        result_person2.setFirstName("Jane");
        result_person2.setLastName("Doe");
        Teacher result_teacher2 = new Teacher();
        result_teacher2.setId(2L);
        result_teacher2.setPerson(result_person2);

        List<Teacher> teachers = List.of(result_teacher1, result_teacher2);
        Page<Teacher> page = new PageImpl<>(teachers);

        when(teacherRepository.findAll(any(Example.class), eq(PageRequest.of(0, 10, Sort.by("name").ascending())))).thenReturn(page);

        List<Teacher> foundTeachers = teacherService.getAllByExample(example, 0, 10, List.of("name"), Sort.Direction.ASC).getContent();

        assertNotNull(foundTeachers);
        assertEquals(2, foundTeachers.size());
        assertEquals("John", foundTeachers.get(0).getPerson().getFirstName());
        assertEquals("Doe", foundTeachers.get(0).getPerson().getLastName());
        verify(teacherRepository, times(1)).findAll(any(Example.class), eq(PageRequest.of(0, 10, Sort.by("name").ascending())));
    }
}
