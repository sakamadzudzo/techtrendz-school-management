package zw.co.techtrendz.school.management.api.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import zw.co.techtrendz.school.management.api.academic.entity.SubjectYear;
import zw.co.techtrendz.school.management.api.academic.repository.SubjectYearRepository;
import zw.co.techtrendz.school.management.api.academic.service.impl.SubjectYearServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SubjectYearServiceTests {

    @Mock
    private SubjectYearRepository subjectYearRepository;

    @InjectMocks
    private SubjectYearServiceImpl subjectYearService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveSubjectYear() {
        SubjectYear subjectYear = new SubjectYear();
        subjectYear.setCalendarYear(2023);

        when(subjectYearRepository.save(subjectYear)).thenReturn(subjectYear);

        SubjectYear savedSubjectYear = subjectYearService.save(subjectYear);

        assertNotNull(savedSubjectYear);
        assertEquals(2023, savedSubjectYear.getCalendarYear());
        verify(subjectYearRepository, times(1)).save(subjectYear);
    }

    @Test
    void testSaveAllSubjectYears() {
        SubjectYear subjectYear1 = new SubjectYear();
        subjectYear1.setCalendarYear(2023);
        SubjectYear subjectYear2 = new SubjectYear();
        subjectYear2.setCalendarYear(2024);

        List<SubjectYear> subjectYears = Arrays.asList(subjectYear1, subjectYear2);

        when(subjectYearRepository.saveAll(subjectYears)).thenReturn(subjectYears);

        List<SubjectYear> savedSubjectYears = subjectYearService.saveAll(subjectYears);

        assertNotNull(savedSubjectYears);
        assertEquals(2, savedSubjectYears.size());
        verify(subjectYearRepository, times(1)).save(subjectYear1);
        verify(subjectYearRepository, times(1)).save(subjectYear2);
    }

    @Test
    void testGetSubjectYearById() {
        SubjectYear subjectYear = new SubjectYear();
        subjectYear.setId(1L);
        subjectYear.setCalendarYear(2023);

        when(subjectYearRepository.findById(1L)).thenReturn(Optional.of(subjectYear));

        SubjectYear foundSubjectYear = subjectYearService.getById(1L);

        assertNotNull(foundSubjectYear);
        assertEquals(1L, foundSubjectYear.getId());
        assertEquals(2023, foundSubjectYear.getCalendarYear());
        verify(subjectYearRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllSubjectYears() {
        SubjectYear subjectYear1 = new SubjectYear();
        subjectYear1.setCalendarYear(2023);
        SubjectYear subjectYear2 = new SubjectYear();
        subjectYear2.setCalendarYear(2024);

        List<SubjectYear> subjectYears = Arrays.asList(subjectYear1, subjectYear2);
        Page<SubjectYear> page = new PageImpl<>(subjectYears);

        when(subjectYearRepository.findAll(PageRequest.of(0, 10, Sort.by("year").ascending()))).thenReturn(page);

        List<SubjectYear> foundSubjectYears = subjectYearService.getAll(0, 10, List.of("year"), Sort.Direction.ASC).getContent();

        assertNotNull(foundSubjectYears);
        assertEquals(2, foundSubjectYears.size());
        verify(subjectYearRepository, times(1)).findAll(PageRequest.of(0, 10, Sort.by("year").ascending()));
    }

    @Test
    void testGetAllSubjectYearsByExample() {
        SubjectYear example = new SubjectYear();
        example.setCalendarYear(2023);

        SubjectYear subjectYear = new SubjectYear();
        subjectYear.setCalendarYear(2023);

        List<SubjectYear> subjectYears = List.of(subjectYear);
        Page<SubjectYear> page = new PageImpl<>(subjectYears);

        when(subjectYearRepository.findAll(any(Example.class), eq(PageRequest.of(0, 10, Sort.by("year").ascending())))).thenReturn(page);

        List<SubjectYear> foundSubjectYears = subjectYearService.getAllByExample(example, 0, 10, List.of("year"), Sort.Direction.ASC).getContent();

        assertNotNull(foundSubjectYears);
        assertEquals(1, foundSubjectYears.size());
        assertEquals(2023, foundSubjectYears.get(0).getCalendarYear());
        verify(subjectYearRepository, times(1)).findAll(any(Example.class), eq(PageRequest.of(0, 10, Sort.by("year").ascending())));
    }
}
