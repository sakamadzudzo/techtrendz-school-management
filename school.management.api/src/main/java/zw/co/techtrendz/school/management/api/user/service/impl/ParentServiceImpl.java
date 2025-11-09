package zw.co.techtrendz.school.management.api.user.service.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import zw.co.techtrendz.school.management.api.common.dto.YearDropdown;
import zw.co.techtrendz.school.management.api.common.utility.AppUtils;
import zw.co.techtrendz.school.management.api.common.utility.SpecificationUtils;
import zw.co.techtrendz.school.management.api.user.entity.Parent;
import zw.co.techtrendz.school.management.api.user.entity.Student;
import zw.co.techtrendz.school.management.api.user.querydsl.ParentQueryService;
import zw.co.techtrendz.school.management.api.user.repository.ParentRepository;
import zw.co.techtrendz.school.management.api.user.repository.StudentRepository;
import zw.co.techtrendz.school.management.api.user.service.ParentService;
import zw.co.techtrendz.school.management.api.user.service.PersonService;
import zw.co.techtrendz.school.management.api.user.view.ParentDto;
import zw.co.techtrendz.school.management.api.user.view.StudentDto;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class ParentServiceImpl implements ParentService {

    private final ParentRepository parentRepository;
    private final StudentRepository studentRepository;
    private final PersonService personService;
    private final ParentQueryService parentQueryService;

    public ParentServiceImpl(
            ParentRepository parentRepository,
            StudentRepository studentRepository,
            PersonService personService,
            ParentQueryService parentQueryService) {
        this.parentRepository = parentRepository;
        this.studentRepository = studentRepository;
        this.personService = personService;
        this.parentQueryService = parentQueryService;
    }

    @Override
    public Parent save(Parent parent) {
        if (parent.getPerson() != null && (parent.getPerson().getId() == null || parent.getPerson().getId().equals(0L))) {
            parent.setPerson(this.personService.save(parent.getPerson()));
        }
        return this.parentRepository.save(parent);
    }

    @Override
    public List<Parent> saveAll(List<Parent> parents) {
        return parents.parallelStream().map(this::save).toList();
    }

    @Override
    public Parent getById(Long id) {
        return this.parentRepository.findById(id).orElse(null);
    }

    @Override
    public Parent getByPersonId(Long personId) {
        Specification<Parent> spec = Specification.where(
                SpecificationUtils.<Parent>exactMatch("person.id", personId)
        );
        return this.parentRepository.findOne(spec).orElse(null);
    }

    @Override
    public Page<Parent> getAll(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (sortBy.isEmpty()) {
            sortBy.add("person.lastName");
        }
        String[] sortFields = sortBy.toArray(new String[sortBy.size()]);
        return this.parentRepository.findAll(PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
    }

    @Override
    public Page<Parent> getAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter) {
        if (filter == null || filter.isBlank()) {
            return this.getAll(page, size, sortBy, sortDir);
        } else {
            Specification<Parent> spec = Specification.where(null);
            for (var field : Parent.class.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.getType().equals(String.class)) {
                    spec = spec.or(SpecificationUtils.likeMatch(field.getName(), filter));
                }
            }

            if (sortBy.isEmpty()) {
                sortBy.add("person.lastName");
            }
            String[] sortFields = sortBy.toArray(new String[sortBy.size()]);

            return this.parentRepository.findAll(spec, PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
        }
    }

    @Override
    public ParentDto getViewById(Long id) {
        ParentDto parentDto = this.parentQueryService.getParentDetails(id);
        if (parentDto != null) {
            List<StudentDto> students = this.parentQueryService.getStudentsForParents(List.of(id));
            parentDto.setStudents(students);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Parent with id '%d' was not found", id));
        }
        return parentDto;
    }

    @Override
    public ParentDto getViewByPersonId(Long personId) {
        ParentDto parentDto = this.parentQueryService.getParentDetailsByPersonId(personId);
        if (parentDto != null) {
            List<StudentDto> students = this.parentQueryService.getStudentsForParents(List.of(parentDto.getId()));
            parentDto.setStudents(students);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Parent with person id '%d' was not found", personId));
        }
        return parentDto;
    }

    @Override
    public Page<ParentDto> getViewAll(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        Page<ParentDto> parentDtoPage = this.parentQueryService.getAllParentDetails(page, size, sortBy, sortDir);
        return this.addStudentsToParents(parentDtoPage);
    }

    @Override
    public Page<ParentDto> getViewAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter) {
        if (filter == null || filter.isBlank()) {
            return this.getViewAll(page, size, sortBy, sortDir);
        } else {
            BooleanExpression predicate = null;
            predicate = AppUtils.getBooleanExpression(filter, predicate, Parent.class, "parent", Arrays.asList("students"));

            Page<ParentDto> parentDtoPage = this.parentQueryService.getAllParentDetailsByPredicate(page, size, sortBy, sortDir, predicate);
            return this.addStudentsToParents(parentDtoPage);
        }
    }

    @Override
    public List<YearDropdown> getAllParentsForDropdown() {
        return this.parentQueryService.getAllParentsForDropdown();
    }

    @Override
    public Parent manageStudents(Long parentId, List<Long> studentIds) {
        Parent parent = this.getById(parentId);
        if (parent == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Parent with id '%d' was not found", parentId));
        }

        List<Student> students = this.studentRepository.findAllById(studentIds);
        parent.setStudents(students);
        return this.parentRepository.save(parent);
    }

    private Page<ParentDto> addStudentsToParents(Page<ParentDto> parentDtoPage) {
        if (parentDtoPage.getContent().isEmpty()) {
            return parentDtoPage;
        }

        List<Long> parentIds = parentDtoPage.getContent().parallelStream()
                .map(ParentDto::getId)
                .collect(Collectors.toCollection(CopyOnWriteArrayList::new));

        List<StudentDto> students = this.parentQueryService.getStudentsForParents(parentIds);

        Map<Long, List<StudentDto>> mappedStudentDtoList = students
                .parallelStream()
                .collect(Collectors.groupingBy(StudentDto::getParentId));

        return parentDtoPage.map(parentDto -> {
            parentDto.setStudents(mappedStudentDtoList.getOrDefault(parentDto.getId(), List.of()));
            return parentDto;
        });
    }
}