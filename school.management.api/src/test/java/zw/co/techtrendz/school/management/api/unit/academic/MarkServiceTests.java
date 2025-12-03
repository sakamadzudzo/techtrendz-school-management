package zw.co.techtrendz.school.management.api.unit.academic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import zw.co.techtrendz.school.management.api.academic.entity.Mark;
import zw.co.techtrendz.school.management.api.academic.enums.CourseworkType;
import zw.co.techtrendz.school.management.api.academic.querydsl.MarkQueryService;
import zw.co.techtrendz.school.management.api.academic.repository.MarkRepository;
import zw.co.techtrendz.school.management.api.academic.service.impl.MarkServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MarkServiceTests {

    @InjectMocks
    private MarkServiceImpl markService;

    @Mock
    private MarkRepository markRepository;

    @Mock
    private MarkQueryService markQueryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveMark() {
        Mark mark = new Mark();
        mark.setMark(85.5f);

        when(markRepository.save(mark)).thenReturn(mark);

        Mark savedMark = markService.save(mark);

        assertNotNull(savedMark);
        assertEquals(85.5f, savedMark.getMark());
        verify(markRepository, times(1)).save(mark);
    }

    @Test
    void testSaveAllMarks() {
        Mark mark1 = new Mark();
        mark1.setMark(85.5f);
        Mark mark2 = new Mark();
        mark2.setMark(92.0f);

        List<Mark> marks = Arrays.asList(mark1, mark2);

        when(markRepository.saveAll(marks)).thenReturn(marks);

        List<Mark> savedMarks = markService.saveAll(marks);

        assertNotNull(savedMarks);
        assertEquals(2, savedMarks.size());
        verify(markRepository, times(1)).saveAll(marks);
    }

    @Test
    void testGetMarkById() {
        Mark mark = new Mark();
        mark.setId(1L);
        mark.setMark(85.5f);

        when(markQueryService.getById(1L)).thenReturn(mark);

        Mark foundMark = markService.getById(1L);

        assertNotNull(foundMark);
        assertEquals(1L, foundMark.getId());
        assertEquals(85.5f, foundMark.getMark());
        verify(markQueryService, times(1)).getById(1L);
    }

    @Test
    void testGetMarkByIdNotFound() {
        when(markQueryService.getById(1L)).thenReturn(null);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            markService.getById(1L);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertTrue(exception.getReason().contains("Mark with id '1' was not found"));
        verify(markQueryService, times(1)).getById(1L);
    }

    @Test
    void testGetAllMarks() {
        Mark mark1 = new Mark();
        mark1.setId(1L);
        mark1.setMark(85.5f);
        Mark mark2 = new Mark();
        mark2.setId(2L);
        mark2.setMark(92.0f);

        List<Mark> marks = Arrays.asList(mark1, mark2);

        when(markQueryService.getAll()).thenReturn(marks);

        List<Mark> foundMarks = markService.getAll();

        assertNotNull(foundMarks);
        assertEquals(2, foundMarks.size());
        verify(markQueryService, times(1)).getAll();
    }

    @Test
    void testGetAllByCourseworkId() {
        Mark mark = new Mark();
        mark.setId(1L);
        mark.setMark(85.5f);

        List<Mark> marks = Arrays.asList(mark);

        when(markQueryService.getAllByCourseworkId(1L)).thenReturn(marks);

        List<Mark> foundMarks = markService.getAllByCourseworkId(1L);

        assertNotNull(foundMarks);
        assertEquals(1, foundMarks.size());
        verify(markQueryService, times(1)).getAllByCourseworkId(1L);
    }

    @Test
    void testGetAllByStudentId() {
        Mark mark = new Mark();
        mark.setId(1L);
        mark.setMark(85.5f);

        List<Mark> marks = Arrays.asList(mark);

        when(markQueryService.getAllByStudentId(1L)).thenReturn(marks);

        List<Mark> foundMarks = markService.getAllByStudentId(1L);

        assertNotNull(foundMarks);
        assertEquals(1, foundMarks.size());
        verify(markQueryService, times(1)).getAllByStudentId(1L);
    }

    @Test
    void testGetAllBySubjectId() {
        Mark mark = new Mark();
        mark.setId(1L);
        mark.setMark(85.5f);

        List<Mark> marks = Arrays.asList(mark);

        when(markQueryService.getAllBySubjectId(1L)).thenReturn(marks);

        List<Mark> foundMarks = markService.getAllBySubjectId(1L);

        assertNotNull(foundMarks);
        assertEquals(1, foundMarks.size());
        verify(markQueryService, times(1)).getAllBySubjectId(1L);
    }

    @Test
    void testGetAllBySubjectIdByCourseworkId() {
        Mark mark = new Mark();
        mark.setId(1L);
        mark.setMark(85.5f);

        List<Mark> marks = Arrays.asList(mark);

        when(markQueryService.getAllBySubjectIdByCourseworkId(1L, 2L)).thenReturn(marks);

        List<Mark> foundMarks = markService.getAllBySubjectIdByCourseworkId(1L, 2L);

        assertNotNull(foundMarks);
        assertEquals(1, foundMarks.size());
        verify(markQueryService, times(1)).getAllBySubjectIdByCourseworkId(1L, 2L);
    }

    @Test
    void testGetAllByStudentIdBySubjectId() {
        Mark mark = new Mark();
        mark.setId(1L);
        mark.setMark(85.5f);

        List<Mark> marks = Arrays.asList(mark);

        when(markQueryService.getAllByStudentIdBySubjectId(1L, 2L)).thenReturn(marks);

        List<Mark> foundMarks = markService.getAllByStudentIdBySubjectId(1L, 2L);

        assertNotNull(foundMarks);
        assertEquals(1, foundMarks.size());
        verify(markQueryService, times(1)).getAllByStudentIdBySubjectId(1L, 2L);
    }

    @Test
    void testGetByStudentIdByCourseworkId() {
        Mark mark = new Mark();
        mark.setId(1L);
        mark.setMark(85.5f);

        when(markQueryService.getByStudentIdByCourseworkId(1L, 2L)).thenReturn(mark);

        Mark foundMark = markService.getByStudentIdByCourseworkId(1L, 2L);

        assertNotNull(foundMark);
        assertEquals(1L, foundMark.getId());
        verify(markQueryService, times(1)).getByStudentIdByCourseworkId(1L, 2L);
    }

    @Test
    void testGetAllBySubjectIdByCourseworkType() {
        Mark mark = new Mark();
        mark.setId(1L);
        mark.setMark(85.5f);

        List<Mark> marks = Arrays.asList(mark);

        when(markQueryService.getAllBySubjectIdByCourseworkType(1L, CourseworkType.TEST)).thenReturn(marks);

        List<Mark> foundMarks = markService.getAllBySubjectIdByCourseworkType(1L, CourseworkType.TEST);

        assertNotNull(foundMarks);
        assertEquals(1, foundMarks.size());
        verify(markQueryService, times(1)).getAllBySubjectIdByCourseworkType(1L, CourseworkType.TEST);
    }

    @Test
    void testGetAllByStudentIdByCourseworkType() {
        Mark mark = new Mark();
        mark.setId(1L);
        mark.setMark(85.5f);

        List<Mark> marks = Arrays.asList(mark);

        when(markQueryService.getAllByStudentIdByCourseworkType(1L, CourseworkType.TEST)).thenReturn(marks);

        List<Mark> foundMarks = markService.getAllByStudentIdByCourseworkType(1L, CourseworkType.TEST);

        assertNotNull(foundMarks);
        assertEquals(1, foundMarks.size());
        verify(markQueryService, times(1)).getAllByStudentIdByCourseworkType(1L, CourseworkType.TEST);
    }

    @Test
    void testGetAllByStudentIdBySubjectIdByCourseworkType() {
        Mark mark = new Mark();
        mark.setId(1L);
        mark.setMark(85.5f);

        List<Mark> marks = Arrays.asList(mark);

        when(markQueryService.getAllByStudentIdBySubjectIdByCourseworkType(1L, 2L, CourseworkType.TEST)).thenReturn(marks);

        List<Mark> foundMarks = markService.getAllByStudentIdBySubjectIdByCourseworkType(1L, 2L, CourseworkType.TEST);

        assertNotNull(foundMarks);
        assertEquals(1, foundMarks.size());
        verify(markQueryService, times(1)).getAllByStudentIdBySubjectIdByCourseworkType(1L, 2L, CourseworkType.TEST);
    }

    @Test
    void testGetAllByCourseworkIds() {
        Mark mark = new Mark();
        mark.setId(1L);
        mark.setMark(85.5f);

        List<Mark> marks = Arrays.asList(mark);
        List<Long> courseworkIds = Arrays.asList(1L, 2L);

        when(markQueryService.getAllByCourseworkIds(courseworkIds)).thenReturn(marks);

        List<Mark> foundMarks = markService.getAllByCourseworkIds(courseworkIds);

        assertNotNull(foundMarks);
        assertEquals(1, foundMarks.size());
        verify(markQueryService, times(1)).getAllByCourseworkIds(courseworkIds);
    }

    @Test
    void testGetAllByStudentIdByClassroomIdByYear() {
        Mark mark = new Mark();
        mark.setId(1L);
        mark.setMark(85.5f);

        List<Mark> marks = Arrays.asList(mark);

        when(markQueryService.getAllByStudentIdByClassroomIdByYear(1L, 2L, 2023)).thenReturn(marks);

        List<Mark> foundMarks = markService.getAllByStudentIdByClassroomIdByYear(1L, 2L, 2023);

        assertNotNull(foundMarks);
        assertEquals(1, foundMarks.size());
        verify(markQueryService, times(1)).getAllByStudentIdByClassroomIdByYear(1L, 2L, 2023);
    }

    @Test
    void testGetAllByStudentIdBySubjectIdByYear() {
        Mark mark = new Mark();
        mark.setId(1L);
        mark.setMark(85.5f);

        List<Mark> marks = Arrays.asList(mark);

        when(markQueryService.getAllByStudentIdBySubjectIdByYear(1L, 2L, 2023)).thenReturn(marks);

        List<Mark> foundMarks = markService.getAllByStudentIdBySubjectIdByYear(1L, 2L, 2023);

        assertNotNull(foundMarks);
        assertEquals(1, foundMarks.size());
        verify(markQueryService, times(1)).getAllByStudentIdBySubjectIdByYear(1L, 2L, 2023);
    }

    @Test
    void testGetAllByStudentIdByYear() {
        Mark mark = new Mark();
        mark.setId(1L);
        mark.setMark(85.5f);

        List<Mark> marks = Arrays.asList(mark);

        when(markQueryService.getAllByStudentIdByYear(1L, 2023)).thenReturn(marks);

        List<Mark> foundMarks = markService.getAllByStudentIdByYear(1L, 2023);

        assertNotNull(foundMarks);
        assertEquals(1, foundMarks.size());
        verify(markQueryService, times(1)).getAllByStudentIdByYear(1L, 2023);
    }

    @Test
    void testGetAllByClassroomIdByYear() {
        Mark mark = new Mark();
        mark.setId(1L);
        mark.setMark(85.5f);

        List<Mark> marks = Arrays.asList(mark);

        when(markQueryService.getAllByClassroomIdByYear(1L, 2023)).thenReturn(marks);

        List<Mark> foundMarks = markService.getAllByClassroomIdByYear(1L, 2023);

        assertNotNull(foundMarks);
        assertEquals(1, foundMarks.size());
        verify(markQueryService, times(1)).getAllByClassroomIdByYear(1L, 2023);
    }

    @Test
    void testGetAllBySubjectIdByYear() {
        Mark mark = new Mark();
        mark.setId(1L);
        mark.setMark(85.5f);

        List<Mark> marks = Arrays.asList(mark);

        when(markQueryService.getAllBySubjectIdByYear(1L, 2023)).thenReturn(marks);

        List<Mark> foundMarks = markService.getAllBySubjectIdByYear(1L, 2023);

        assertNotNull(foundMarks);
        assertEquals(1, foundMarks.size());
        verify(markQueryService, times(1)).getAllBySubjectIdByYear(1L, 2023);
    }

    @Test
    void testGetAllByTeacherIdByYear() {
        Mark mark = new Mark();
        mark.setId(1L);
        mark.setMark(85.5f);

        List<Mark> marks = Arrays.asList(mark);

        when(markQueryService.getAllByTeacherIdByYear(1L, 2023)).thenReturn(marks);

        List<Mark> foundMarks = markService.getAllByTeacherIdByYear(1L, 2023);

        assertNotNull(foundMarks);
        assertEquals(1, foundMarks.size());
        verify(markQueryService, times(1)).getAllByTeacherIdByYear(1L, 2023);
    }

    @Test
    void testGetAllByYear() {
        Mark mark = new Mark();
        mark.setId(1L);
        mark.setMark(85.5f);

        List<Mark> marks = Arrays.asList(mark);

        when(markQueryService.getAllByYear(2023)).thenReturn(marks);

        List<Mark> foundMarks = markService.getAllByYear(2023);

        assertNotNull(foundMarks);
        assertEquals(1, foundMarks.size());
        verify(markQueryService, times(1)).getAllByYear(2023);
    }
}