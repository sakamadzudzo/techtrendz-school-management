package zw.co.techtrendz.school.management.api.unit.academic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import zw.co.techtrendz.school.management.api.academic.entity.Subject;
import zw.co.techtrendz.school.management.api.academic.repository.SubjectRepository;
import zw.co.techtrendz.school.management.api.academic.service.impl.SubjectServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SubjectServiceTests {

    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    private SubjectServiceImpl subjectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveSubject() {
        Subject subject = new Subject();
        subject.setName("Mathematics");

        when(subjectRepository.save(subject)).thenReturn(subject);

        Subject savedSubject = subjectService.save(subject);

        assertNotNull(savedSubject);
        assertEquals("Mathematics", savedSubject.getName());
        verify(subjectRepository, times(1)).save(subject);
    }

    @Test
    void testSaveAllSubjects() {
        Subject subject1 = new Subject();
        subject1.setName("Mathematics");
        Subject subject2 = new Subject();
        subject2.setName("Science");

        List<Subject> subjects = Arrays.asList(subject1, subject2);

        when(subjectRepository.saveAll(subjects)).thenReturn(subjects);

        List<Subject> savedSubjects = subjectService.saveAll(subjects);

        assertNotNull(savedSubjects);
        assertEquals(2, savedSubjects.size());
        verify(subjectRepository, times(1)).save(subject1);
        verify(subjectRepository, times(1)).save(subject2);
    }

    @Test
    void testGetSubjectById() {
        Subject subject = new Subject();
        subject.setId(1L);
        subject.setName("Mathematics");

        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));

        Subject foundSubject = subjectService.getById(1L);

        assertNotNull(foundSubject);
        assertEquals(1L, foundSubject.getId());
        assertEquals("Mathematics", foundSubject.getName());
        verify(subjectRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllSubjects() {
        Subject subject1 = new Subject();
        subject1.setName("Mathematics");
        Subject subject2 = new Subject();
        subject2.setName("Science");

        List<Subject> subjects = Arrays.asList(subject1, subject2);
        Page<Subject> page = new PageImpl<>(subjects);

        when(subjectRepository.findAll(PageRequest.of(0, 10, Sort.by("name").ascending()))).thenReturn(page);

        List<Subject> foundSubjects = subjectService.getAll(0, 10, List.of("name"), Sort.Direction.ASC).getContent();

        assertNotNull(foundSubjects);
        assertEquals(2, foundSubjects.size());
        verify(subjectRepository, times(1)).findAll(PageRequest.of(0, 10, Sort.by("name").ascending()));
    }

    @Test
    void testGetAllSubjectsByExample() {
        Subject example = new Subject();
        example.setName("Mathematics");

        Subject subject = new Subject();
        subject.setName("Mathematics");

        List<Subject> subjects = List.of(subject);
        Page<Subject> page = new PageImpl<>(subjects);

        when(subjectRepository.findAll(any(Example.class), eq(PageRequest.of(0, 10, Sort.by("name").ascending())))).thenReturn(page);

        List<Subject> foundSubjects = subjectService.getAllByExample(example, 0, 10, List.of("name"), Sort.Direction.ASC).getContent();

        assertNotNull(foundSubjects);
        assertEquals(1, foundSubjects.size());
        assertEquals("Mathematics", foundSubjects.get(0).getName());
        verify(subjectRepository, times(1)).findAll(any(Example.class), eq(PageRequest.of(0, 10, Sort.by("name").ascending())));
    }
}
