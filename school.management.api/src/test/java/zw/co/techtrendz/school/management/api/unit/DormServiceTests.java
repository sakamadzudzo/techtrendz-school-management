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
import zw.co.techtrendz.school.management.api.entity.Dorm;
import zw.co.techtrendz.school.management.api.repository.DormRepository;
import zw.co.techtrendz.school.management.api.service.impl.DormServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DormServiceTests {

    @Mock
    private DormRepository dormRepository;

    @InjectMocks
    private DormServiceImpl dormService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveDorm() {
        Dorm dorm = new Dorm();
        dorm.setName("Dorm A");

        when(dormRepository.save(dorm)).thenReturn(dorm);

        Dorm savedDorm = dormService.save(dorm);

        assertNotNull(savedDorm);
        assertEquals("Dorm A", savedDorm.getName());
        verify(dormRepository, times(1)).save(dorm);
    }

    @Test
    void testSaveAllDorms() {
        Dorm dorm1 = new Dorm();
        dorm1.setName("Dorm A");
        Dorm dorm2 = new Dorm();
        dorm2.setName("Dorm B");

        List<Dorm> dorms = Arrays.asList(dorm1, dorm2);

        when(dormRepository.saveAll(dorms)).thenReturn(dorms);

        List<Dorm> savedDorms = dormService.saveAll(dorms);

        assertNotNull(savedDorms);
        assertEquals(2, savedDorms.size());
        verify(dormRepository, times(1)).save(dorm1);
        verify(dormRepository, times(1)).save(dorm2);
    }

    @Test
    void testGetDormById() {
        Dorm dorm = new Dorm();
        dorm.setId(1L);
        dorm.setName("Dorm A");

        when(dormRepository.findById(1L)).thenReturn(Optional.of(dorm));

        Dorm foundDorm = dormService.getById(1L);

        assertNotNull(foundDorm);
        assertEquals(1L, foundDorm.getId());
        assertEquals("Dorm A", foundDorm.getName());
        verify(dormRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllDorms() {
        Dorm dorm1 = new Dorm();
        dorm1.setName("Dorm A");
        Dorm dorm2 = new Dorm();
        dorm2.setName("Dorm B");

        List<Dorm> dorms = Arrays.asList(dorm1, dorm2);
        Page<Dorm> page = new PageImpl<>(dorms);

        when(dormRepository.findAll(PageRequest.of(0, 10, Sort.by("name").ascending()))).thenReturn(page);

        List<Dorm> foundDorms = dormService.getAll(0, 10, List.of("name"), Sort.Direction.ASC).getContent();

        assertNotNull(foundDorms);
        assertEquals(2, foundDorms.size());
        verify(dormRepository, times(1)).findAll(PageRequest.of(0, 10, Sort.by("name").ascending()));
    }

    @Test
    void testGetAllDormsByExample() {
        Dorm example = new Dorm();
        example.setName("Dorm A");

        Dorm dorm = new Dorm();
        dorm.setName("Dorm A");

        List<Dorm> dorms = List.of(dorm);
        Page<Dorm> page = new PageImpl<>(dorms);

        when(dormRepository.findAll(any(), eq(PageRequest.of(0, 10, Sort.by("name").ascending())))).thenReturn(page);

        List<Dorm> foundDorms = dormService.getAllByExample(example, 0, 10, List.of("name"), Sort.Direction.ASC).getContent();

        assertNotNull(foundDorms);
        assertEquals(1, foundDorms.size());
        assertEquals("Dorm A", foundDorms.get(0).getName());
        verify(dormRepository, times(1)).findAll(any(), eq(PageRequest.of(0, 10, Sort.by("name").ascending())));
    }
}
