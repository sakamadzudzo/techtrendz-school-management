package zw.co.techtrendz.school.management.api.unit.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import zw.co.techtrendz.school.management.api.common.dto.YearDropdown;
import zw.co.techtrendz.school.management.api.user.entity.Parent;
import zw.co.techtrendz.school.management.api.user.entity.Person;
import zw.co.techtrendz.school.management.api.user.entity.Student;
import zw.co.techtrendz.school.management.api.user.querydsl.ParentQueryService;
import zw.co.techtrendz.school.management.api.user.repository.ParentRepository;
import zw.co.techtrendz.school.management.api.user.repository.StudentRepository;
import zw.co.techtrendz.school.management.api.user.service.PersonService;
import zw.co.techtrendz.school.management.api.user.service.impl.ParentServiceImpl;
import zw.co.techtrendz.school.management.api.user.view.ParentDto;
import zw.co.techtrendz.school.management.api.user.view.StudentDto;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ParentServiceTests {

    @InjectMocks
    private ParentServiceImpl parentService;

    @Mock
    private ParentRepository parentRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private PersonService personService;

    @Mock
    private ParentQueryService parentQueryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveParent() {
        Parent parent = new Parent();
        parent.setId(1L);

        when(parentRepository.save(parent)).thenReturn(parent);

        Parent savedParent = parentService.save(parent);

        assertNotNull(savedParent);
        assertEquals(1L, savedParent.getId());
        verify(parentRepository, times(1)).save(parent);
    }

    @Test
    void testSaveParentWithNewPerson() {
        Parent parent = new Parent();
        parent.setId(1L);
        Person person = new Person();
        parent.setPerson(person);

        when(personService.save(any(Person.class))).thenReturn(person);
        when(parentRepository.save(parent)).thenReturn(parent);

        Parent savedParent = parentService.save(parent);

        assertNotNull(savedParent);
        verify(personService, times(1)).save(any());
        verify(parentRepository, times(1)).save(parent);
    }

    @Test
    void testSaveAllParents() {
        Parent parent1 = new Parent();
        parent1.setId(1L);
        Parent parent2 = new Parent();
        parent2.setId(2L);

        List<Parent> parents = Arrays.asList(parent1, parent2);

        when(parentRepository.save(any(Parent.class))).thenAnswer(invocation -> invocation.getArgument(0));

        List<Parent> savedParents = parentService.saveAll(parents);

        assertNotNull(savedParents);
        assertEquals(2, savedParents.size());
        verify(parentRepository, times(2)).save(any(Parent.class));
    }

    @Test
    void testGetById() {
        Parent parent = new Parent();
        parent.setId(1L);

        when(parentRepository.findById(1L)).thenReturn(Optional.of(parent));

        Parent foundParent = parentService.getById(1L);

        assertNotNull(foundParent);
        assertEquals(1L, foundParent.getId());
        verify(parentRepository, times(1)).findById(1L);
    }

    @Test
    void testGetByIdNotFound() {
        when(parentRepository.findById(1L)).thenReturn(Optional.empty());

        Parent foundParent = parentService.getById(1L);

        assertNull(foundParent);
        verify(parentRepository, times(1)).findById(1L);
    }

    @Test
    void testGetByPersonId() {
        Parent parent = new Parent();
        parent.setId(1L);

        when(parentRepository.findOne(any(Specification.class))).thenReturn(Optional.of(parent));

        Parent foundParent = parentService.getByPersonId(1L);

        assertNotNull(foundParent);
        assertEquals(1L, foundParent.getId());
        verify(parentRepository, times(1)).findOne(any(Specification.class));
    }

    @Test
    void testGetAll() {
        Parent parent = new Parent();
        parent.setId(1L);

        List<Parent> parents = Arrays.asList(parent);
        Page<Parent> page = new PageImpl<>(parents);

        when(parentRepository.findAll(any(PageRequest.class))).thenReturn(page);

        Page<Parent> result = parentService.getAll(0, 10, Arrays.asList("person.lastName"), Sort.Direction.ASC);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(parentRepository, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void testGetAllByFilter() {
        Parent parent = new Parent();
        parent.setId(1L);

        List<Parent> parents = Arrays.asList(parent);
        Page<Parent> page = new PageImpl<>(parents);

        when(parentRepository.findAll(any(Specification.class), any(PageRequest.class))).thenReturn(page);

        Page<Parent> result = parentService.getAllByFilter(0, 10, Arrays.asList("person.lastName"), Sort.Direction.ASC, "test");

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(parentRepository, times(1)).findAll(any(Specification.class), any(PageRequest.class));
    }

    @Test
    void testGetAllByFilterEmpty() {
        Parent parent = new Parent();
        parent.setId(1L);

        List<Parent> parents = Arrays.asList(parent);
        Page<Parent> page = new PageImpl<>(parents);

        when(parentRepository.findAll(any(PageRequest.class))).thenReturn(page);

        Page<Parent> result = parentService.getAllByFilter(0, 10, Arrays.asList("person.lastName"), Sort.Direction.ASC, "");

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(parentRepository, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void testGetViewById() {
        ParentDto parentDto = new ParentDto();
        parentDto.setId(1L);

        StudentDto studentDto = new StudentDto();
        studentDto.setParentId(1L);
        when(parentQueryService.getParentDetails(1L)).thenReturn(parentDto);
        when(parentQueryService.getStudentsForParents(Arrays.asList(1L))).thenReturn(Arrays.asList(studentDto));

        ParentDto result = parentService.getViewById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertNotNull(result.getStudents());
        verify(parentQueryService, times(1)).getParentDetails(1L);
        verify(parentQueryService, times(1)).getStudentsForParents(Arrays.asList(1L));
    }

    @Test
    void testGetViewByIdNotFound() {
        when(parentQueryService.getParentDetails(1L)).thenReturn(null);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            parentService.getViewById(1L);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertTrue(exception.getReason().contains("Parent with id '1' was not found"));
    }

    @Test
    void testGetViewByPersonId() {
        ParentDto parentDto = new ParentDto();
        parentDto.setId(1L);

        StudentDto studentDto = new StudentDto();
        studentDto.setParentId(1L);
        when(parentQueryService.getParentDetailsByPersonId(1L)).thenReturn(parentDto);
        when(parentQueryService.getStudentsForParents(Arrays.asList(1L))).thenReturn(Arrays.asList(studentDto));

        ParentDto result = parentService.getViewByPersonId(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertNotNull(result.getStudents());
        verify(parentQueryService, times(1)).getParentDetailsByPersonId(1L);
        verify(parentQueryService, times(1)).getStudentsForParents(Arrays.asList(1L));
    }

    @Test
    void testGetViewByPersonIdNotFound() {
        when(parentQueryService.getParentDetailsByPersonId(1L)).thenReturn(null);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            parentService.getViewByPersonId(1L);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertTrue(exception.getReason().contains("Parent with person id '1' was not found"));
    }

    @Test
    void testGetViewAll() {
        ParentDto parentDto = new ParentDto();
        parentDto.setId(1L);

        List<ParentDto> parentDtos = Arrays.asList(parentDto);
        Page<ParentDto> page = new PageImpl<>(parentDtos);

        StudentDto studentDto = new StudentDto();
        studentDto.setParentId(1L);
        when(parentQueryService.getAllParentDetails(0, 10, Arrays.asList("person.lastName"), Sort.Direction.ASC)).thenReturn(page);
        when(parentQueryService.getStudentsForParents(Arrays.asList(1L))).thenReturn(Arrays.asList(studentDto));

        Page<ParentDto> result = parentService.getViewAll(0, 10, Arrays.asList("person.lastName"), Sort.Direction.ASC);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(parentQueryService, times(1)).getAllParentDetails(0, 10, Arrays.asList("person.lastName"), Sort.Direction.ASC);
        verify(parentQueryService, times(1)).getStudentsForParents(Arrays.asList(1L));
    }

    @Test
    void testGetViewAllByFilter() {
        ParentDto parentDto = new ParentDto();
        parentDto.setId(1L);

        List<ParentDto> parentDtos = Arrays.asList(parentDto);
        Page<ParentDto> page = new PageImpl<>(parentDtos);

        StudentDto studentDto = new StudentDto();
        studentDto.setParentId(1L);
        when(parentQueryService.getAllParentDetailsByPredicate(eq(0), eq(10), anyList(), any(), any())).thenReturn(page);
        when(parentQueryService.getStudentsForParents(Arrays.asList(1L))).thenReturn(Arrays.asList(studentDto));

        Page<ParentDto> result = parentService.getViewAllByFilter(0, 10, Arrays.asList("person.lastName"), Sort.Direction.ASC, "test");

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(parentQueryService, times(1)).getAllParentDetailsByPredicate(eq(0), eq(10), anyList(), any(), any());
        verify(parentQueryService, times(1)).getStudentsForParents(Arrays.asList(1L));
    }

    @Test
    void testGetViewAllByFilterEmpty() {
        ParentDto parentDto = new ParentDto();
        parentDto.setId(1L);

        List<ParentDto> parentDtos = Arrays.asList(parentDto);
        Page<ParentDto> page = new PageImpl<>(parentDtos);

        StudentDto studentDto = new StudentDto();
        studentDto.setParentId(1L);
        when(parentQueryService.getAllParentDetails(0, 10, Arrays.asList("person.lastName"), Sort.Direction.ASC)).thenReturn(page);
        when(parentQueryService.getStudentsForParents(Arrays.asList(1L))).thenReturn(Arrays.asList(studentDto));

        Page<ParentDto> result = parentService.getViewAllByFilter(0, 10, Arrays.asList("person.lastName"), Sort.Direction.ASC, "");

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(parentQueryService, times(1)).getAllParentDetails(0, 10, Arrays.asList("person.lastName"), Sort.Direction.ASC);
        verify(parentQueryService, times(1)).getStudentsForParents(Arrays.asList(1L));
    }

    @Test
    void testGetAllParentsForDropdown() {
        YearDropdown dropdown = new YearDropdown();
        List<YearDropdown> dropdowns = Arrays.asList(dropdown);

        when(parentQueryService.getAllParentsForDropdown()).thenReturn(dropdowns);

        List<YearDropdown> result = parentService.getAllParentsForDropdown();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(parentQueryService, times(1)).getAllParentsForDropdown();
    }

    @Test
    void testManageStudents() {
        Parent parent = new Parent();
        parent.setId(1L);

        Student student = new Student();
        student.setId(1L);

        when(parentRepository.findById(1L)).thenReturn(Optional.of(parent));
        when(studentRepository.findAllById(Arrays.asList(1L))).thenReturn(Arrays.asList(student));
        when(parentRepository.save(parent)).thenReturn(parent);

        Parent result = parentService.manageStudents(1L, Arrays.asList(1L));

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertNotNull(result.getStudents());
        assertEquals(1, result.getStudents().size());
        verify(parentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).findAllById(Arrays.asList(1L));
        verify(parentRepository, times(1)).save(parent);
    }

    @Test
    void testManageStudentsParentNotFound() {
        when(parentRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            parentService.manageStudents(1L, Arrays.asList(1L));
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertTrue(exception.getReason().contains("Parent with id '1' was not found"));
    }
}