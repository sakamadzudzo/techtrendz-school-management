package zw.co.techtrendz.school.management.api.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import zw.co.techtrendz.school.management.api.user.entity.Person;
import zw.co.techtrendz.school.management.api.user.entity.Student;
import zw.co.techtrendz.school.management.api.user.repository.StudentRepository;
import zw.co.techtrendz.school.management.api.user.service.impl.StudentServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTests {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveStudent() {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        Student student = new Student();
        student.setPerson(person);

        Person person1 = new Person();
        person1.setId(1L);
        person1.setFirstName("John");
        person1.setLastName("Doe");
        Student student1 = new Student();
        student1.setId(1L);
        student1.setPerson(person1);

        when(studentRepository.save(student)).thenReturn(student1);

        Student savedStudent = studentService.save(student);

        assertNotNull(savedStudent);
        assertEquals("John", savedStudent.getPerson().getFirstName());
        assertEquals("Doe", savedStudent.getPerson().getLastName());
        assertEquals(1L, savedStudent.getId());
        assertEquals(1L, savedStudent.getPerson().getId());
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void testSaveAllStudents() {
        Person person1 = new Person();
        person1.setFirstName("John");
        person1.setLastName("Doe");
        Student student1 = new Student();
        student1.setPerson(person1);

        Person person2 = new Person();
        person2.setFirstName("John");
        person2.setLastName("Doe");
        Student student2 = new Student();
        student2.setPerson(person2);

        Person result_person1 = new Person();
        result_person1.setId(1L);
        result_person1.setFirstName("John");
        result_person1.setLastName("Doe");
        Student result_student1 = new Student();
        result_student1.setId(1L);
        result_student1.setPerson(result_person1);

        Person result_person2 = new Person();
        result_person2.setId(1L);
        result_person2.setFirstName("Jane");
        result_person2.setLastName("Doe");
        Student result_student2 = new Student();
        result_student2.setId(1L);
        result_student2.setPerson(result_person2);

        List<Student> students = Arrays.asList(student1, student2);

        when(studentRepository.saveAll(students)).thenReturn(Arrays.asList(result_student1,result_student2));

        List<Student> savedStudents = studentService.saveAll(students);

        assertNotNull(savedStudents);
        assertEquals(2, savedStudents.size());
        verify(studentRepository, times(1)).save(student1);
        verify(studentRepository, times(1)).save(student2);
    }

    @Test
    void testGetStudentById() {
        Person person = new Person();
        person.setId(1L);
        person.setFirstName("John");
        person.setLastName("Doe");
        Student student = new Student();
        student.setId(1L);
        student.setPerson(person);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Student foundStudent = studentService.getById(1L);

        assertNotNull(foundStudent);
        assertEquals(1L, foundStudent.getId());
        assertEquals("John", foundStudent.getPerson().getFirstName());
        assertEquals("Doe", foundStudent.getPerson().getLastName());
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllStudents() {
        Person result_person1 = new Person();
        result_person1.setId(1L);
        result_person1.setFirstName("John");
        result_person1.setLastName("Doe");
        Student result_student1 = new Student();
        result_student1.setId(1L);
        result_student1.setPerson(result_person1);

        Person result_person2 = new Person();
        result_person2.setId(1L);
        result_person2.setFirstName("Jane");
        result_person2.setLastName("Doe");
        Student result_student2 = new Student();
        result_student2.setId(1L);
        result_student2.setPerson(result_person2);

        List<Student> students = Arrays.asList(result_student1, result_student2);
        Page<Student> page = new PageImpl<>(students);

        when(studentRepository.findAll(PageRequest.of(0, 10, Sort.by("lastName").ascending()))).thenReturn(page);

        List<Student> foundStudents = studentService.getAll(0, 10, List.of("lastName"), Sort.Direction.ASC).getContent();

        assertNotNull(foundStudents);
        assertEquals(2, foundStudents.size());
        verify(studentRepository, times(1)).findAll(PageRequest.of(0, 10, Sort.by("lastName").ascending()));
    }

    @Test
    void testGetAllStudentsByExample() {
        Person person = new Person();
        person.setId(1L);
        person.setFirstName("John");
        person.setLastName("Doe");
        Student example = new Student();
        example.setId(1L);
        example.setPerson(person);

        Person result_person1 = new Person();
        result_person1.setId(1L);
        result_person1.setFirstName("John");
        result_person1.setLastName("Doe");
        Student result_student1 = new Student();
        result_student1.setId(1L);
        result_student1.setPerson(result_person1);

        Person result_person2 = new Person();
        result_person2.setId(1L);
        result_person2.setFirstName("Jane");
        result_person2.setLastName("Doe");
        Student result_student2 = new Student();
        result_student2.setId(1L);
        result_student2.setPerson(result_person2);

        List<Student> students = List.of(result_student1, result_student2);
        Page<Student> page = new PageImpl<>(students);

        when(studentRepository.findAll(any(Example.class), eq(PageRequest.of(0, 10, Sort.by("lastName").ascending())))).thenReturn(page);

        List<Student> foundStudents = studentService.getAllByExample(example, 0, 10, List.of("lastName"), Sort.Direction.ASC).getContent();

        assertNotNull(foundStudents);
        assertEquals(2, foundStudents.size());
        assertEquals("John", foundStudents.get(0).getPerson().getFirstName());
        assertEquals("Doe", foundStudents.get(0).getPerson().getLastName());
        verify(studentRepository, times(1)).findAll(any(Example.class), eq(PageRequest.of(0, 10, Sort.by("lastName").ascending())));
    }
}
