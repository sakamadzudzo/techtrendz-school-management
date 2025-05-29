package zw.co.techtrendz.school.management.api.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import zw.co.techtrendz.school.management.api.entity.School;
import zw.co.techtrendz.school.management.api.repository.SchoolRepository;
import zw.co.techtrendz.school.management.api.service.impl.SchoolServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SchoolServiceTests {

    @Mock
    private SchoolRepository schoolRepository;

    @InjectMocks
    private SchoolServiceImpl schoolService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveSchool() {
        School school = new School();
        school.setName("TechTrendz Academy");

        when(schoolRepository.save(school)).thenReturn(school);

        School savedSchool = schoolService.save(school);

        assertNotNull(savedSchool);
        assertEquals("TechTrendz Academy", savedSchool.getName());
        verify(schoolRepository, times(1)).save(school);
    }

    @Test
    void testSaveAllSchools() {
        School school1 = new School();
        school1.setName("TechTrendz Academy");
        School school2 = new School();
        school2.setName("Future Leaders School");

        List<School> schools = Arrays.asList(school1, school2);

        when(schoolRepository.saveAll(schools)).thenReturn(schools);

        List<School> savedSchools = schoolService.saveAll(schools);

        assertNotNull(savedSchools);
        assertEquals(2, savedSchools.size());
        verify(schoolRepository, times(1)).save(school1);
        verify(schoolRepository, times(1)).save(school2);
    }

    @Test
    void testGetSchoolById() {
        School school = new School();
        school.setId(1L);
        school.setName("TechTrendz Academy");

        when(schoolRepository.findById(1L)).thenReturn(Optional.of(school));

        School foundSchool = schoolService.getById(1L);

        assertNotNull(foundSchool);
        assertEquals(1L, foundSchool.getId());
        assertEquals("TechTrendz Academy", foundSchool.getName());
        verify(schoolRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllSchools() {
        School school1 = new School();
        school1.setName("TechTrendz Academy");
        School school2 = new School();
        school2.setName("Future Leaders School");

        List<School> schools = Arrays.asList(school1, school2);
        Page<School> page = new PageImpl<>(schools);

        when(schoolRepository.findAll(PageRequest.of(0, 10, Sort.by("name").ascending()))).thenReturn(page);

        List<School> foundSchools = schoolService.getAll(0, 10, List.of("name"), Sort.Direction.ASC).getContent();

        assertNotNull(foundSchools);
        assertEquals(2, foundSchools.size());
        verify(schoolRepository, times(1)).findAll(PageRequest.of(0, 10, Sort.by("name").ascending()));
    }

    @Test
    void testGetAllSchoolsByExample() {
        School example = new School();
        example.setName("TechTrendz Academy");

        School school = new School();
        school.setName("TechTrendz Academy");

        List<School> schools = List.of(school);
        Page<School> page = new PageImpl<>(schools);

        when(schoolRepository.findAll(any(Example.class), eq(PageRequest.of(0, 10, Sort.by("name").ascending())))).thenReturn(page);

        List<School> foundSchools = schoolService.getAllByExample(example, 0, 10, List.of("name"), Sort.Direction.ASC).getContent();

        assertNotNull(foundSchools);
        assertEquals(1, foundSchools.size());
        assertEquals("TechTrendz Academy", foundSchools.get(0).getName());
        verify(schoolRepository, times(1)).findAll(any(Example.class), eq(PageRequest.of(0, 10, Sort.by("name").ascending())));
    }
}
