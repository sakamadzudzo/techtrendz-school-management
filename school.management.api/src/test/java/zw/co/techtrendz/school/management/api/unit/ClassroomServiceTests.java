package zw.co.techtrendz.school.management.api.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import zw.co.techtrendz.school.management.api.academic.entity.Classroom;
import zw.co.techtrendz.school.management.api.academic.repository.ClassroomRepository;
import zw.co.techtrendz.school.management.api.academic.service.impl.ClassroomServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClassroomServiceTests {

    @InjectMocks
    private ClassroomServiceImpl classroomService;

    @Mock
    private ClassroomRepository classroomRepository;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveClassroom() {
        Classroom classroom = new Classroom();
        classroom.setName("Math Class");

        when(classroomRepository.save(classroom)).thenReturn(classroom);

        Classroom savedClassroom = classroomService.save(classroom);

        assertNotNull(savedClassroom);
        assertEquals("Math Class", savedClassroom.getName());
        verify(classroomRepository, times(1)).save(classroom);
    }

    @Test
    void testSaveAllClassrooms() {
        Classroom classroom1 = new Classroom();
        classroom1.setName("Math Class");
        Classroom classroom2 = new Classroom();
        classroom2.setName("Science Class");

        List<Classroom> classrooms = Arrays.asList(classroom1, classroom2);

        when(classroomRepository.saveAll(classrooms)).thenReturn(classrooms);

        List<Classroom> savedClassrooms = classroomService.saveAll(classrooms);

        assertNotNull(savedClassrooms);
        assertEquals(2, savedClassrooms.size());
        verify(classroomRepository, times(1)).save(classroom1);
        verify(classroomRepository, times(1)).save(classroom2);
    }

    @Test
    void testGetClassroomById() {
        Classroom classroom = new Classroom();
        classroom.setId(1L);
        classroom.setName("Math Class");

        when(classroomRepository.findById(1L)).thenReturn(Optional.of(classroom));

        Classroom foundClassroom = classroomService.getById(1L);

        assertNotNull(foundClassroom);
        assertEquals(1L, foundClassroom.getId());
        assertEquals("Math Class", foundClassroom.getName());
        verify(classroomRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllClassrooms() {
        Classroom classroom1 = new Classroom();
        classroom1.setName("Math Class");
        Classroom classroom2 = new Classroom();
        classroom2.setName("Science Class");

        List<Classroom> classrooms = Arrays.asList(classroom1, classroom2);
        Page<Classroom> page = new PageImpl<>(classrooms);

        when(classroomRepository.findAll(PageRequest.of(0, 10, Sort.by("name").ascending()))).thenReturn(page);

        List<Classroom> foundClassrooms = classroomService.getAll(0, 10, List.of("name"), Sort.Direction.ASC).getContent();

        assertNotNull(foundClassrooms);
        assertEquals(2, foundClassrooms.size());
        verify(classroomRepository, times(1)).findAll(PageRequest.of(0, 10, Sort.by("name").ascending()));
    }

    @Test
    void testGetAllClassroomsByExample() {
        Classroom example = new Classroom();
        example.setName("Math Class");

        Classroom classroom = new Classroom();
        classroom.setName("Math Class");

        List<Classroom> classrooms = List.of(classroom);
        Page<Classroom> page = new PageImpl<>(classrooms);

        when(classroomRepository.findAll(any(Example.class), eq(PageRequest.of(0, 10, Sort.by("name").ascending())))).thenReturn(page);

        List<Classroom> foundClassrooms = classroomService.getAllByExample(example, 0, 10, List.of("name"), Sort.Direction.ASC).getContent();

        assertNotNull(foundClassrooms);
        assertEquals(1, foundClassrooms.size());
        assertEquals("Math Class", foundClassrooms.get(0).getName());
        verify(classroomRepository, times(1)).findAll(any(Example.class), eq(PageRequest.of(0, 10, Sort.by("name").ascending())));
    }

    @Test
    void testSaveClassroomWithNoName() {
        Classroom classroom = new Classroom();
        classroom.setName(null);

        when(classroomRepository.save(classroom)).thenThrow(new IllegalArgumentException("Classroom name cannot be null"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            classroomService.save(classroom);
        });

        String expectedMessage = "Classroom name cannot be null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(classroomRepository, times(1)).save(classroom);
    }

    @Test
    void testSaveClassroomWithLongDescription() {
        Classroom classroom = new Classroom();
        classroom.setName("Math Class");
        String longDescription = "This is a very long description that exceeds the maximum allowed length for a classroom description.";
        classroom.setDescription(longDescription);

        when(classroomRepository.save(classroom)).thenThrow(new IllegalArgumentException("Classroom description is too long"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            classroomService.save(classroom);
        });

        String expectedMessage = "Classroom description is too long";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(classroomRepository, times(1)).save(classroom);
    }
}
