package zw.co.techtrendz.school.management.api.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import zw.co.techtrendz.school.management.api.academic.entity.ExtraCurricular;
import zw.co.techtrendz.school.management.api.academic.repository.ExtraCurricularRepository;
import zw.co.techtrendz.school.management.api.academic.service.impl.ExtraCurricularServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExtraCurricularServiceTests {

    @Mock
    private ExtraCurricularRepository extraCurricularRepository;

    @InjectMocks
    private ExtraCurricularServiceImpl extraCurricularService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveExtraCurricular() {
        ExtraCurricular extraCurricular = new ExtraCurricular();
        extraCurricular.setName("Basketball");

        when(extraCurricularRepository.save(extraCurricular)).thenReturn(extraCurricular);

        ExtraCurricular savedExtraCurricular = extraCurricularService.save(extraCurricular);

        assertNotNull(savedExtraCurricular);
        assertEquals("Basketball", savedExtraCurricular.getName());
        verify(extraCurricularRepository, times(1)).save(extraCurricular);
    }

    @Test
    void testSaveAllExtraCurriculars() {
        ExtraCurricular extraCurricular1 = new ExtraCurricular();
        extraCurricular1.setName("Basketball");
        ExtraCurricular extraCurricular2 = new ExtraCurricular();
        extraCurricular2.setName("Drama Club");

        List<ExtraCurricular> extraCurriculars = Arrays.asList(extraCurricular1, extraCurricular2);

        when(extraCurricularRepository.saveAll(extraCurriculars)).thenReturn(extraCurriculars);

        List<ExtraCurricular> savedExtraCurriculars = extraCurricularService.saveAll(extraCurriculars);

        assertNotNull(savedExtraCurriculars);
        assertEquals(2, savedExtraCurriculars.size());
        verify(extraCurricularRepository, times(1)).save(extraCurricular1);
        verify(extraCurricularRepository, times(1)).save(extraCurricular2);
    }

    @Test
    void testGetExtraCurricularById() {
        ExtraCurricular extraCurricular = new ExtraCurricular();
        extraCurricular.setId(1L);
        extraCurricular.setName("Basketball");

        when(extraCurricularRepository.findById(1L)).thenReturn(Optional.of(extraCurricular));

        ExtraCurricular foundExtraCurricular = extraCurricularService.getById(1L);

        assertNotNull(foundExtraCurricular);
        assertEquals(1L, foundExtraCurricular.getId());
        assertEquals("Basketball", foundExtraCurricular.getName());
        verify(extraCurricularRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllExtraCurriculars() {
        ExtraCurricular extraCurricular1 = new ExtraCurricular();
        extraCurricular1.setName("Basketball");
        ExtraCurricular extraCurricular2 = new ExtraCurricular();
        extraCurricular2.setName("Drama Club");

        List<ExtraCurricular> extraCurriculars = Arrays.asList(extraCurricular1, extraCurricular2);
        Page<ExtraCurricular> page = new PageImpl<>(extraCurriculars);

        when(extraCurricularRepository.findAll(PageRequest.of(0, 10, Sort.by("name").ascending()))).thenReturn(page);

        List<ExtraCurricular> foundExtraCurriculars = extraCurricularService.getAll(0, 10, List.of("name"), Sort.Direction.ASC).getContent();

        assertNotNull(foundExtraCurriculars);
        assertEquals(2, foundExtraCurriculars.size());
        verify(extraCurricularRepository, times(1)).findAll(PageRequest.of(0, 10, Sort.by("name").ascending()));
    }

    @Test
    void testGetAllExtraCurricularsByExample() {
        ExtraCurricular example = new ExtraCurricular();
        example.setName("Basketball");

        ExtraCurricular extraCurricular = new ExtraCurricular();
        extraCurricular.setName("Basketball");

        List<ExtraCurricular> extraCurriculars = List.of(extraCurricular);
        Page<ExtraCurricular> page = new PageImpl<>(extraCurriculars);

        when(extraCurricularRepository.findAll(any(Example.class), eq(PageRequest.of(0, 10, Sort.by("name").ascending())))).thenReturn(page);

        List<ExtraCurricular> foundExtraCurriculars = extraCurricularService.getAllByExample(example, 0, 10, List.of("name"), Sort.Direction.ASC).getContent();

        assertNotNull(foundExtraCurriculars);
        assertEquals(1, foundExtraCurriculars.size());
        assertEquals("Basketball", foundExtraCurriculars.get(0).getName());
        verify(extraCurricularRepository, times(1)).findAll(any(Example.class), eq(PageRequest.of(0, 10, Sort.by("name").ascending())));
    }
}
