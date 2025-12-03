package zw.co.techtrendz.school.management.api.unit.academic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import zw.co.techtrendz.school.management.api.academic.entity.Coursework;
import zw.co.techtrendz.school.management.api.academic.entity.Mark;
import zw.co.techtrendz.school.management.api.academic.enums.CourseworkType;
import zw.co.techtrendz.school.management.api.academic.querydsl.CourseworkQueryService;
import zw.co.techtrendz.school.management.api.academic.repository.CourseworkRepository;
import zw.co.techtrendz.school.management.api.academic.service.MarkService;
import zw.co.techtrendz.school.management.api.academic.service.impl.CourseworkServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseworkServiceTests {

    @InjectMocks
    private CourseworkServiceImpl courseworkService;

    @Mock
    private CourseworkRepository courseworkRepository;

    @Mock
    private CourseworkQueryService courseworkQueryService;

    @Mock
    private MarkService markService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveCoursework() {
        Coursework coursework = new Coursework();
        coursework.setTitle("Mid-term Exam");
        coursework.setType(CourseworkType.TEST);

        when(courseworkRepository.save(coursework)).thenReturn(coursework);

        Coursework savedCoursework = courseworkService.save(coursework);

        assertNotNull(savedCoursework);
        assertEquals("Mid-term Exam", savedCoursework.getTitle());
        assertEquals(CourseworkType.TEST, savedCoursework.getType());
        verify(courseworkRepository, times(1)).save(coursework);
    }

    @Test
    void testSaveAllCourseworks() {
        Coursework coursework1 = new Coursework();
        coursework1.setTitle("Mid-term Exam");
        coursework1.setType(CourseworkType.TEST);
        Coursework coursework2 = new Coursework();
        coursework2.setTitle("Assignment 1");
        coursework2.setType(CourseworkType.ASSIGNMENT);

        List<Coursework> courseworks = Arrays.asList(coursework1, coursework2);

        when(courseworkRepository.saveAll(courseworks)).thenReturn(courseworks);

        List<Coursework> savedCourseworks = courseworkService.saveAll(courseworks);

        assertNotNull(savedCourseworks);
        assertEquals(2, savedCourseworks.size());
        verify(courseworkRepository, times(1)).saveAll(courseworks);
    }

    @Test
    void testGetCourseworkById() {
        Coursework coursework = new Coursework();
        coursework.setId(1L);
        coursework.setTitle("Mid-term Exam");

        when(courseworkQueryService.getById(1L)).thenReturn(coursework);

        Coursework foundCoursework = courseworkService.getById(1L);

        assertNotNull(foundCoursework);
        assertEquals(1L, foundCoursework.getId());
        assertEquals("Mid-term Exam", foundCoursework.getTitle());
        verify(courseworkQueryService, times(1)).getById(1L);
    }

    @Test
    void testGetCourseworkByIdNotFound() {
        when(courseworkQueryService.getById(1L)).thenReturn(null);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            courseworkService.getById(1L);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertTrue(exception.getReason().contains("Coursework with id '1' was not found"));
        verify(courseworkQueryService, times(1)).getById(1L);
    }

    @Test
    void testGetAllCourseworks() {
        Coursework coursework1 = new Coursework();
        coursework1.setId(1L);
        coursework1.setTitle("Mid-term Exam");
        Coursework coursework2 = new Coursework();
        coursework2.setId(2L);
        coursework2.setTitle("Assignment 1");

        List<Coursework> courseworks = Arrays.asList(coursework1, coursework2);

        when(courseworkQueryService.getAll()).thenReturn(courseworks);

        List<Coursework> foundCourseworks = courseworkService.getAll();

        assertNotNull(foundCourseworks);
        assertEquals(2, foundCourseworks.size());
        verify(courseworkQueryService, times(1)).getAll();
    }

    @Test
    void testGetAllByType() {
        Coursework coursework = new Coursework();
        coursework.setId(1L);
        coursework.setTitle("Mid-term Exam");
        coursework.setType(CourseworkType.TEST);

        List<Coursework> courseworks = Arrays.asList(coursework);

        when(courseworkQueryService.getAllByType(CourseworkType.TEST)).thenReturn(courseworks);

        List<Coursework> foundCourseworks = courseworkService.getAllByType(CourseworkType.TEST);

        assertNotNull(foundCourseworks);
        assertEquals(1, foundCourseworks.size());
        assertEquals(CourseworkType.TEST, foundCourseworks.get(0).getType());
        verify(courseworkQueryService, times(1)).getAllByType(CourseworkType.TEST);
    }

    @Test
    void testGetAllBySubjectId() {
        Coursework coursework = new Coursework();
        coursework.setId(1L);
        coursework.setTitle("Mid-term Exam");

        List<Coursework> courseworks = Arrays.asList(coursework);

        Mark mark = new Mark();
        mark.setCoursework(coursework);
        when(courseworkQueryService.getAllBySubjectId(1L)).thenReturn(courseworks);
        when(markService.getAllByCourseworkIds(Arrays.asList(1L))).thenReturn(Arrays.asList(mark));

        List<Coursework> foundCourseworks = courseworkService.getAllBySubjectId(1L);

        assertNotNull(foundCourseworks);
        assertEquals(1, foundCourseworks.size());
        verify(courseworkQueryService, times(1)).getAllBySubjectId(1L);
        verify(markService, times(1)).getAllByCourseworkIds(Arrays.asList(1L));
    }

    @Test
    void testGetAllBySubjectIdNullList() {
        when(courseworkQueryService.getAllBySubjectId(1L)).thenReturn(null);

        List<Coursework> foundCourseworks = courseworkService.getAllBySubjectId(1L);

        assertNull(foundCourseworks);
        verify(courseworkQueryService, times(1)).getAllBySubjectId(1L);
        verify(markService, never()).getAllByCourseworkIds(any());
    }

    @Test
    void testGetAllByTypeBySubjectId() {
        Coursework coursework = new Coursework();
        coursework.setId(1L);
        coursework.setTitle("Mid-term Exam");
        coursework.setType(CourseworkType.TEST);

        List<Coursework> courseworks = Arrays.asList(coursework);

        Mark mark = new Mark();
        mark.setCoursework(coursework);
        when(courseworkQueryService.getAllByTypeBySubjectId(CourseworkType.TEST, 1L)).thenReturn(courseworks);
        when(markService.getAllByCourseworkIds(Arrays.asList(1L))).thenReturn(Arrays.asList(mark));

        List<Coursework> foundCourseworks = courseworkService.getAllByTypeBySubjectId(CourseworkType.TEST, 1L);

        assertNotNull(foundCourseworks);
        assertEquals(1, foundCourseworks.size());
        assertEquals(CourseworkType.TEST, foundCourseworks.get(0).getType());
        verify(courseworkQueryService, times(1)).getAllByTypeBySubjectId(CourseworkType.TEST, 1L);
        verify(markService, times(1)).getAllByCourseworkIds(Arrays.asList(1L));
    }

    @Test
    void testGetAllByTypeBySubjectIdNullList() {
        when(courseworkQueryService.getAllByTypeBySubjectId(CourseworkType.TEST, 1L)).thenReturn(null);

        List<Coursework> foundCourseworks = courseworkService.getAllByTypeBySubjectId(CourseworkType.TEST, 1L);

        assertNull(foundCourseworks);
        verify(courseworkQueryService, times(1)).getAllByTypeBySubjectId(CourseworkType.TEST, 1L);
        verify(markService, never()).getAllByCourseworkIds(any());
    }

    @Test
    void testGetAllBySubjectIds() {
        Coursework coursework = new Coursework();
        coursework.setId(1L);
        coursework.setTitle("Mid-term Exam");

        List<Coursework> courseworks = Arrays.asList(coursework);
        List<Long> subjectIds = Arrays.asList(1L, 2L);

        Mark mark = new Mark();
        mark.setCoursework(coursework);
        when(courseworkQueryService.getAllBySubjectIds(subjectIds)).thenReturn(courseworks);
        when(markService.getAllByCourseworkIds(Arrays.asList(1L))).thenReturn(Arrays.asList(mark));

        List<Coursework> foundCourseworks = courseworkService.getAllBySubjectIds(subjectIds);

        assertNotNull(foundCourseworks);
        assertEquals(1, foundCourseworks.size());
        verify(courseworkQueryService, times(1)).getAllBySubjectIds(subjectIds);
        verify(markService, times(1)).getAllByCourseworkIds(Arrays.asList(1L));
    }

    @Test
    void testGetAllBySubjectIdsNullList() {
        List<Long> subjectIds = Arrays.asList(1L, 2L);

        when(courseworkQueryService.getAllBySubjectIds(subjectIds)).thenReturn(null);

        List<Coursework> foundCourseworks = courseworkService.getAllBySubjectIds(subjectIds);

        assertNull(foundCourseworks);
        verify(courseworkQueryService, times(1)).getAllBySubjectIds(subjectIds);
        verify(markService, never()).getAllByCourseworkIds(any());
    }
}