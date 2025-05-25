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
import zw.co.techtrendz.school.management.api.entity.DormYear;
import zw.co.techtrendz.school.management.api.repository.DormYearRepository;
import zw.co.techtrendz.school.management.api.service.impl.DormYearServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DormYearServiceTests {

    @Mock
    private DormYearRepository dormYearRepository;

    @InjectMocks
    private DormYearServiceImpl dormYearService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveDormYear() {
        DormYear dormYear = new DormYear();
        dormYear.setCalendarYear(2023);

        when(dormYearRepository.save(dormYear)).thenReturn(dormYear);

        DormYear savedDormYear = dormYearService.save(dormYear);

        assertNotNull(savedDormYear);
        assertEquals(2023, savedDormYear.getCalendarYear());
        verify(dormYearRepository, times(1)).save(dormYear);
    }

    @Test
    void testSaveAllDormYears() {
        DormYear dormYear1 = new DormYear();
        dormYear1.setCalendarYear(2023);
        DormYear dormYear2 = new DormYear();
        dormYear2.setCalendarYear(2024);

        List<DormYear> dormYears = Arrays.asList(dormYear1, dormYear2);

        when(dormYearRepository.saveAll(dormYears)).thenReturn(dormYears);

        List<DormYear> savedDormYears = dormYearService.saveAll(dormYears);

        assertNotNull(savedDormYears);
        assertEquals(2, savedDormYears.size());
        verify(dormYearRepository, times(1)).save(dormYear1);
        verify(dormYearRepository, times(1)).save(dormYear2);
    }

    @Test
    void testGetDormYearById() {
        DormYear dormYear = new DormYear();
        dormYear.setId(1L);
        dormYear.setCalendarYear(2023);

        when(dormYearRepository.findById(1L)).thenReturn(Optional.of(dormYear));

        DormYear foundDormYear = dormYearService.getById(1L);

        assertNotNull(foundDormYear);
        assertEquals(1L, foundDormYear.getId());
        assertEquals(2023, foundDormYear.getCalendarYear());
        verify(dormYearRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllDormYears() {
        DormYear dormYear1 = new DormYear();
        dormYear1.setCalendarYear(2023);
        DormYear dormYear2 = new DormYear();
        dormYear2.setCalendarYear(2024);

        List<DormYear> dormYears = Arrays.asList(dormYear1, dormYear2);
        Page<DormYear> page = new PageImpl<>(dormYears);

        when(dormYearRepository.findAll(PageRequest.of(0, 10, Sort.by("year").ascending()))).thenReturn(page);

        List<DormYear> foundDormYears = dormYearService.getAll(0, 10, List.of("year"), Sort.Direction.ASC).getContent();

        assertNotNull(foundDormYears);
        assertEquals(2, foundDormYears.size());
        verify(dormYearRepository, times(1)).findAll(PageRequest.of(0, 10, Sort.by("year").ascending()));
    }

    @Test
    void testGetAllDormYearsByExample() {
        DormYear example = new DormYear();
        example.setCalendarYear(2023);

        DormYear dormYear = new DormYear();
        dormYear.setCalendarYear(2023);

        List<DormYear> dormYears = List.of(dormYear);
        Page<DormYear> page = new PageImpl<>(dormYears);

        when(dormYearRepository.findAll(any(), eq(PageRequest.of(0, 10, Sort.by("year").ascending())))).thenReturn(page);

        List<DormYear> foundDormYears = dormYearService.getAllByExample(example, 0, 10, List.of("year"), Sort.Direction.ASC).getContent();

        assertNotNull(foundDormYears);
        assertEquals(1, foundDormYears.size());
        assertEquals(2023, foundDormYears.get(0).getCalendarYear());
        verify(dormYearRepository, times(1)).findAll(any(), eq(PageRequest.of(0, 10, Sort.by("year").ascending())));
    }
}
