package zw.co.techtrendz.school.management.api.service.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import zw.co.techtrendz.school.management.api.dto.YearDropdown;
import zw.co.techtrendz.school.management.api.entity.Teacher;
import zw.co.techtrendz.school.management.api.querydsl.TeacherQueryService;
import zw.co.techtrendz.school.management.api.repository.TeacherRepository;
import zw.co.techtrendz.school.management.api.service.PersonService;
import zw.co.techtrendz.school.management.api.service.TeacherService;
import zw.co.techtrendz.school.management.api.utility.AppUtils;
import zw.co.techtrendz.school.management.api.utility.SpecificationUtils;
import zw.co.techtrendz.school.management.api.view.ClassroomYearDto;
import zw.co.techtrendz.school.management.api.view.SubjectYearDto;
import zw.co.techtrendz.school.management.api.view.TeacherDto;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private PersonService personService;
    @Autowired
    private TeacherQueryService teacherQueryService;

    @Override
    public Teacher save(Teacher teacher) {
        if (teacher.getPerson() != null && (teacher.getPerson().getId() == null || teacher.getPerson().getId().equals(0L))) {
            teacher.setPerson(this.personService.save(teacher.getPerson()));
        }
        return this.teacherRepository.save(teacher);
    }

    @Override
    public List<Teacher> saveAll(List<Teacher> teachers) {
        return teachers.stream().map(this::save).toList();
    }

    @Override
    public Teacher getById(Long id) {
        return this.teacherRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Teacher> getAll(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (sortBy.isEmpty()) {
            sortBy.add("person.lastName");
        }
        String[] sortFields = sortBy.toArray(new String[sortBy.size()]);
        return this.teacherRepository.findAll(PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
    }

    @Override
    public Page<Teacher> getAllByExample(Teacher teacher, int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (teacher == null) {
            return this.getAll(page, size, sortBy, sortDir);
        } else {
            Example<Teacher> example = Example.of(teacher);

            if (sortBy.isEmpty()) {
                sortBy.add("person.lastName");
            }
            String[] sortFields = sortBy.toArray(new String[sortBy.size()]);

            return this.teacherRepository.findAll(example, PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
        }
    }

    @Override
    public Page<Teacher> getAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter) {
        if (filter == null || filter.isBlank()) {
            return this.getAll(page, size, sortBy, sortDir);
        } else {
            Specification<Teacher> spec = Specification.where(null);
            for (var field : Teacher.class.getDeclaredFields()) {
                field.setAccessible(true); // Ensure private fields are accessible
                if (field.getType().equals(String.class)) {
                    spec = spec.or(SpecificationUtils.likeMatch(field.getName(), filter));
                }
            }

            if (sortBy.isEmpty()) {
                sortBy.add("person.lastName");
            }
            String[] sortFields = sortBy.toArray(new String[sortBy.size()]);

            return this.teacherRepository.findAll(spec, PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
        }
    }

    @Override
    public TeacherDto getViewById(Long id) {
        TeacherDto teacherDto = this.teacherQueryService.getTeacherDetails(id);
        if (teacherDto != null) {
            teacherDto.setClassrooms(this.teacherQueryService.getClassroomYears(id));
            teacherDto.setSubjects(this.teacherQueryService.getSubjectYears(id));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Teacher with id '%d' was not found", id));
        }
        return teacherDto;
    }

    @Override
    public Page<TeacherDto> getViewAll(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        Page<TeacherDto> teacherDtoPage = this.teacherQueryService.getAllTeacherDetails(page, size, sortBy, sortDir);
        return getTeacherClassesAndSubjects(teacherDtoPage);
    }

    @Override
    public Page<TeacherDto> getViewAllByExample(Teacher teacher, int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (teacher == null) {
            return this.getViewAll(page, size, sortBy, sortDir);
        } else {
            Example<Teacher> example = Example.of(teacher);

            if (sortBy.isEmpty()) {
                sortBy.add("person.lastName");
            }

            Page<TeacherDto> teacherDtoPage = this.teacherQueryService.getAllTeacherDetailsByExample(page, size, sortBy, sortDir, example);
            return getTeacherClassesAndSubjects(teacherDtoPage);
        }
    }

    @Override
    public Page<TeacherDto> getViewAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter) {
        if (filter == null || filter.isBlank()) {
            return this.getViewAll(page, size, sortBy, sortDir);
        } else {
            BooleanExpression predicate = null;
            predicate = AppUtils.getBooleanExpression(filter, predicate, Teacher.class, "teacher", Arrays.asList("extraCurricularYears"));

            Page<TeacherDto> teacherDtoPage = this.teacherQueryService.getAllTeacherDetailsByPredicate(page, size, sortBy, sortDir, predicate);
            return getTeacherClassesAndSubjects(teacherDtoPage);
        }
    }

    @Override
    public List<YearDropdown> getAllTeachersForDropdown() {
        return this.teacherQueryService.getAllTeachersForDropdown();
    }

    private Page<TeacherDto> getTeacherClassesAndSubjects(Page<TeacherDto> teacherDtoPage) {
        List<Long> teacherIds = teacherDtoPage.getContent().stream().map(TeacherDto::getId).collect(Collectors.toCollection(CopyOnWriteArrayList::new));
        List<ClassroomYearDto> classroomYearDtoList = this.teacherQueryService.getClassroomYears(teacherIds);
        List<SubjectYearDto> subjectYearDtoList = this.teacherQueryService.getSubjectYears(teacherIds);
        return teacherDtoPage.map(teacher -> {
            teacher.setClassrooms(
                    classroomYearDtoList
                            .stream()
                            .filter(classroom -> classroom
                                    .getHomeroomTeacher()
                                    .equals(teacher.getId()))
                            .collect(Collectors.toCollection(CopyOnWriteArrayList::new)));
            teacher.setSubjects(subjectYearDtoList
                    .stream()
                    .filter(subject -> subject
                            .getTeacherId()
                            .equals(teacher.getId()))
                    .collect(Collectors.toCollection(CopyOnWriteArrayList::new)));
            return teacher;
        });
    }
}
