package zw.co.techtrendz.school.management.api.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import zw.co.techtrendz.school.management.api.entity.ExtraCurricularYear;
import zw.co.techtrendz.school.management.api.repository.ExtraCurricularYearRepository;
import zw.co.techtrendz.school.management.api.service.impl.ExtraCurricularYearServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExtraCurricularYearServiceTests {

    @Mock
    private ExtraCurricularYearRepository extraCurricularYearRepository;

    @InjectMocks
    private ExtraCurricularYearServiceImpl extraCurricularYearService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveExtraCurricularYear() {
        ExtraCurricularYear extraCurricularYear = new ExtraCurricularYear();
        extraCurricularYear.setCalendarYear(2023);

        when(extraCurricularYearRepository.save(extraCurricularYear)).thenReturn(extraCurricularYear);

        ExtraCurricularYear savedExtraCurricularYear = extraCurricularYearService.save(extraCurricularYear);

        assertNotNull(savedExtraCurricularYear);
        assertEquals(2023, savedExtraCurricularYear.getCalendarYear());
        verify(extraCurricularYearRepository, times(1)).save(extraCurricularYear);
    }

    @Test
    void testSaveAllExtraCurricularYears() {
        ExtraCurricularYear year1 = new ExtraCurricularYear();
        year1.setCalendarYear(2023);
        ExtraCurricularYear year2 = new ExtraCurricularYear();
        year2.setCalendarYear(2024);

        List<ExtraCurricularYear> years = Arrays.asList(year1, year2);

        when(extraCurricularYearRepository.saveAll(years)).thenReturn(years);

        List<ExtraCurricularYear> savedYears = extraCurricularYearService.saveAll(years);

        assertNotNull(savedYears);
        assertEquals(2, savedYears.size());
        verify(extraCurricularYearRepository, times(1)).save(year1);
        verify(extraCurricularYearRepository, times(1)).save(year2);
    }

    @Test
    void testGetExtraCurricularYearById() {
        ExtraCurricularYear extraCurricularYear = new ExtraCurricularYear();
        extraCurricularYear.setId(1L);
        extraCurricularYear.setCalendarYear(2023);

        when(extraCurricularYearRepository.findById(1L)).thenReturn(Optional.of(extraCurricularYear));

        ExtraCurricularYear foundYear = extraCurricularYearService.getById(1L);

        assertNotNull(foundYear);
        assertEquals(1L, foundYear.getId());
        assertEquals(2023, foundYear.getCalendarYear());
        verify(extraCurricularYearRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllExtraCurricularYears() {
        ExtraCurricularYear year1 = new ExtraCurricularYear();
        year1.setCalendarYear(2023);
        ExtraCurricularYear year2 = new ExtraCurricularYear();
        year2.setCalendarYear(2024);

        List<ExtraCurricularYear> years = Arrays.asList(year1, year2);
        Page<ExtraCurricularYear> page = new PageImpl<>(years);

        when(extraCurricularYearRepository.findAll(PageRequest.of(0, 10, Sort.by("year").ascending()))).thenReturn(page);

        List<ExtraCurricularYear> foundYears = extraCurricularYearService.getAll(0, 10, List.of("year"), Sort.Direction.ASC).getContent();

        assertNotNull(foundYears);
        assertEquals(2, foundYears.size());
        verify(extraCurricularYearRepository, times(1)).findAll(PageRequest.of(0, 10, Sort.by("year").ascending()));
    }

    @Test
    void testGetAllExtraCurricularYearsByExample() {
        ExtraCurricularYear example = new ExtraCurricularYear();
        example.setCalendarYear(2023);

        ExtraCurricularYear year = new ExtraCurricularYear();
        year.setCalendarYear(2023);

        List<ExtraCurricularYear> years = List.of(year);
        Page<ExtraCurricularYear> page = new PageImpl<>(years);

        when(extraCurricularYearRepository.findAll(any(), eq(PageRequest.of(0, 10, Sort.by("year").ascending())))).thenReturn(page);

        List<ExtraCurricularYear> foundYears = extraCurricularYearService.getAllByExample(example, 0, 10, List.of("year"), Sort.Direction.ASC).getContent();

        assertNotNull(foundYears);
        assertEquals(1, foundYears.size());
        assertEquals(2023, foundYears.get(0).getCalendarYear());
        verify(extraCurricularYearRepository, times(1)).findAll(any(), eq(PageRequest.of(0, 10, Sort.by("year").ascending())));
    }
}
