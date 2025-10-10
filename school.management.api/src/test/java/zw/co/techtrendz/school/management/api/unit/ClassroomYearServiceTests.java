package zw.co.techtrendz.school.management.api.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import zw.co.techtrendz.school.management.api.entity.ClassroomYear;
import zw.co.techtrendz.school.management.api.repository.ClassroomYearRepository;
import zw.co.techtrendz.school.management.api.service.impl.ClassroomYearServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClassroomYearServiceTests {

    @Mock
    private ClassroomYearRepository classroomYearRepository;

    @InjectMocks
    private ClassroomYearServiceImpl classroomYearService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveClassroomYear() {
        ClassroomYear classroomYear = new ClassroomYear();
        classroomYear.setCalendarYear(2023);

        when(classroomYearRepository.save(classroomYear)).thenReturn(classroomYear);

        ClassroomYear savedClassroomYear = classroomYearService.save(classroomYear);

        assertNotNull(savedClassroomYear);
        assertEquals(2023, savedClassroomYear.getCalendarYear());
        verify(classroomYearRepository, times(1)).save(classroomYear);
    }

    @Test
    void testSaveAllClassroomYears() {
        ClassroomYear classroomYear1 = new ClassroomYear();
        classroomYear1.setCalendarYear(2023);
        ClassroomYear classroomYear2 = new ClassroomYear();
        classroomYear2.setCalendarYear(2024);

        List<ClassroomYear> classroomYears = Arrays.asList(classroomYear1, classroomYear2);

        when(classroomYearRepository.saveAll(classroomYears)).thenReturn(classroomYears);

        List<ClassroomYear> savedClassroomYears = classroomYearService.saveAll(classroomYears);

        assertNotNull(savedClassroomYears);
        assertEquals(2, savedClassroomYears.size());
        verify(classroomYearRepository, times(1)).save(classroomYear1);
        verify(classroomYearRepository, times(1)).save(classroomYear2);
    }

    @Test
    void testGetClassroomYearById() {
        ClassroomYear classroomYear = new ClassroomYear();
        classroomYear.setId(1L);
        classroomYear.setCalendarYear(2023);

        when(classroomYearRepository.findById(1L)).thenReturn(Optional.of(classroomYear));

        ClassroomYear foundClassroomYear = classroomYearService.getById(1L);

        assertNotNull(foundClassroomYear);
        assertEquals(1L, foundClassroomYear.getId());
        assertEquals(2023, foundClassroomYear.getCalendarYear());
        verify(classroomYearRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllClassroomYears() {
        ClassroomYear classroomYear1 = new ClassroomYear();
        classroomYear1.setCalendarYear(2023);
        ClassroomYear classroomYear2 = new ClassroomYear();
        classroomYear2.setCalendarYear(2024);

        List<ClassroomYear> classroomYears = Arrays.asList(classroomYear1, classroomYear2);
        Page<ClassroomYear> page = new PageImpl<>(classroomYears);

        when(classroomYearRepository.findAll(PageRequest.of(0, 10, Sort.by("year").ascending()))).thenReturn(page);

        List<ClassroomYear> foundClassroomYears = classroomYearService.getAll(0, 10, List.of("year"), Sort.Direction.ASC).getContent();

        assertNotNull(foundClassroomYears);
        assertEquals(2, foundClassroomYears.size());
        verify(classroomYearRepository, times(1)).findAll(PageRequest.of(0, 10, Sort.by("year").ascending()));
    }

    @Test
    void testGetAllClassroomYearsByExample() {
        ClassroomYear example = new ClassroomYear();
        example.setCalendarYear(2023);

        ClassroomYear classroomYear = new ClassroomYear();
        classroomYear.setCalendarYear(2023);

        List<ClassroomYear> classroomYears = List.of(classroomYear);
        Page<ClassroomYear> page = new PageImpl<>(classroomYears);

        when(classroomYearRepository.findAll(any(Example.class), eq(PageRequest.of(0, 10, Sort.by("year").ascending())))).thenReturn(page);

        List<ClassroomYear> foundClassroomYears = classroomYearService.getAllByExample(example, 0, 10, List.of("year"), Sort.Direction.ASC).getContent();

        assertNotNull(foundClassroomYears);
        assertEquals(1, foundClassroomYears.size());
        assertEquals(2023, foundClassroomYears.get(0).getCalendarYear());
        verify(classroomYearRepository, times(1)).findAll(any(Example.class), eq(PageRequest.of(0, 10, Sort.by("year").ascending())));
    }
}
