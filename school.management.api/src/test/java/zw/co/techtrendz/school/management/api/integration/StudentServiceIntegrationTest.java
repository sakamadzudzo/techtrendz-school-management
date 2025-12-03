package zw.co.techtrendz.school.management.api.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import zw.co.techtrendz.school.management.api.user.entity.Person;
import zw.co.techtrendz.school.management.api.user.entity.Student;
import zw.co.techtrendz.school.management.api.user.service.StudentService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class StudentServiceIntegrationTest {

    @Autowired
    private StudentService studentService;

    @Test
    void testSaveAndRetrieveStudent() {
        // Create a new student
        Person person = new Person();
        person.setFirstName("Integration");
        person.setLastName("Test");
        person.setEmail("integration@test.com");
        person.setIdentification("ID123456");

        Student student = new Student();
        student.setPerson(person);

        // Save the student
        Student savedStudent = studentService.save(student);
        assertNotNull(savedStudent);
        assertNotNull(savedStudent.getId());
        assertEquals("Integration", savedStudent.getPerson().getFirstName());

        // Retrieve the student
        Student retrievedStudent = studentService.getById(savedStudent.getId());
        assertNotNull(retrievedStudent);
        assertEquals(savedStudent.getId(), retrievedStudent.getId());
        assertEquals("Integration", retrievedStudent.getPerson().getFirstName());
    }

    @Test
    void testSaveAllStudents() {
        // Create multiple students
        Person person1 = new Person();
        person1.setFirstName("Student1");
        person1.setLastName("Test");
        person1.setEmail("student1@test.com");
        person1.setIdentification("ID111111");

        Person person2 = new Person();
        person2.setFirstName("Student2");
        person2.setLastName("Test");
        person2.setEmail("student2@test.com");
        person2.setIdentification("ID222222");

        Student student1 = new Student();
        student1.setPerson(person1);

        Student student2 = new Student();
        student2.setPerson(person2);

        List<Student> students = List.of(student1, student2);

        // Save all students
        List<Student> savedStudents = studentService.saveAll(students);
        assertNotNull(savedStudents);
        assertEquals(2, savedStudents.size());

        for (Student savedStudent : savedStudents) {
            assertNotNull(savedStudent.getId());
            assertNotNull(savedStudent.getPerson().getId());
        }
    }

    @Test
    void testGetAllStudents() {
        // Get all students (may include test data)
        var page = studentService.getAll(0, 10, List.of("person.lastName"), org.springframework.data.domain.Sort.Direction.ASC);
        assertNotNull(page);
        assertNotNull(page.getContent());
    }
}