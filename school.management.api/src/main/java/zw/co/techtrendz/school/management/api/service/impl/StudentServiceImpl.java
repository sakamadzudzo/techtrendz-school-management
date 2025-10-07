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
import zw.co.techtrendz.school.management.api.entity.Student;
import zw.co.techtrendz.school.management.api.querydsl.StudentQueryService;
import zw.co.techtrendz.school.management.api.querydsl.SubjectYearQueryService;
import zw.co.techtrendz.school.management.api.repository.StudentRepository;
import zw.co.techtrendz.school.management.api.service.ActivityPointService;
import zw.co.techtrendz.school.management.api.service.MarkService;
import zw.co.techtrendz.school.management.api.service.PersonService;
import zw.co.techtrendz.school.management.api.service.StudentService;
import zw.co.techtrendz.school.management.api.utility.AppUtils;
import zw.co.techtrendz.school.management.api.utility.SpecificationUtils;
import zw.co.techtrendz.school.management.api.view.StudentClassDto;
import zw.co.techtrendz.school.management.api.view.StudentDto;
import zw.co.techtrendz.school.management.api.view.SubjectYearDto;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final PersonService personService;
    private final StudentQueryService studentQueryService;
    private final SubjectYearQueryService subjectYearQueryService;
    private final ActivityPointService activityPointService;
    private final MarkService markService;

    public StudentServiceImpl(
            StudentRepository studentRepository,
            PersonService personService,
            StudentQueryService studentQueryService,
            SubjectYearQueryService subjectYearQueryService,
            ActivityPointService activityPointService,
            MarkService markService) {
        this.studentRepository = studentRepository;
        this.personService = personService;
        this.studentQueryService = studentQueryService;
        this.subjectYearQueryService = subjectYearQueryService;
        this.activityPointService = activityPointService;
        this.markService = markService;
    }

    @Override
    public Student save(Student student) {
        if (student.getPerson() != null && (student.getPerson().getId() == null || student.getPerson().getId().equals(0L))) {
            student.setPerson(this.personService.save(student.getPerson()));
        }
        return this.studentRepository.save(student);
    }

    @Override
    public List<Student> saveAll(List<Student> students) {
        return students.parallelStream().map(this::save).toList();
    }

    @Override
    public Student getById(Long id) {
        return this.studentRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Student> getAll(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (sortBy.isEmpty()) {
            sortBy.add("name");
        }
        String[] sortFields = sortBy.toArray(new String[sortBy.size()]);
        return this.studentRepository.findAll(PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
    }

    @Override
    public Page<Student> getAllByExample(Student student, int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (student == null) {
            return this.getAll(page, size, sortBy, sortDir);
        } else {
            Example<Student> example = Example.of(student);

            if (sortBy.isEmpty()) {
                sortBy.add("name");
            }
            String[] sortFields = sortBy.toArray(new String[sortBy.size()]);

            return this.studentRepository.findAll(example, PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
        }
    }

    @Override
    public Page<Student> getAllByClassroomId(long classroomId, int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (sortBy.isEmpty()) {
            sortBy.add("name");
        }
        String[] sortFields = sortBy.toArray(new String[sortBy.size()]);

        Specification<Student> spec = Specification.where(
                SpecificationUtils.<Student>exactMatch("classroom.id", classroomId)
        );

        return this.studentRepository.findAll(spec, PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
    }

    @Override
    public Page<Student> getAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter) {
        if (filter == null || filter.isBlank()) {
            return this.getAll(page, size, sortBy, sortDir);
        } else {
            Specification<Student> spec = Specification.where(null);
            for (var field : Student.class.getDeclaredFields()) {
                field.setAccessible(true); // Ensure private fields are accessible
                if (field.getType().equals(String.class)) {
                    spec = spec.or(SpecificationUtils.likeMatch(field.getName(), filter));
                }
            }

            if (sortBy.isEmpty()) {
                sortBy.add("name");
            }
            String[] sortFields = sortBy.toArray(new String[sortBy.size()]);

            return this.studentRepository.findAll(spec, PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
        }
    }

    @Override
    public StudentDto getViewById(Long id) {
        StudentDto studentDto = this.studentQueryService.getStudentDetails(id);
        if (studentDto != null) {
            StudentClassDto classroom = studentDto.getClassroom();
            classroom
                    .setSubjects(
                            this.subjectYearQueryService
                                    .getSubjectYears(
                                            Collections.singletonList(classroom.getId())
                                    )
                    );
            studentDto.setClassroom(classroom);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Student with id '%d' was not found", id));
        }
        return studentDto;
    }

    @Override
    public Page<StudentDto> getViewAll(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        Page<StudentDto> studentDtoPage = this.studentQueryService.getAllStudentDetails(page, size, sortBy, sortDir);
        return this.getClassroomSubjects(studentDtoPage);
    }

    @Override
    public Page<StudentDto> getViewAllByExample(Student student, int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (student == null) {
            return this.getViewAll(page, size, sortBy, sortDir);
        } else {
            Example<Student> example = Example.of(student);

            if (sortBy.isEmpty()) {
                sortBy.add("person.lastName");
            }

            Page<StudentDto> studentDtoPage = this.studentQueryService.getAllStudentDetailsByExample(page, size, sortBy, sortDir, example);
            return this.getClassroomSubjects(studentDtoPage);
        }
    }

    @Override
    public Page<StudentDto> getViewAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter) {
        if (filter == null || filter.isBlank()) {
            return this.getViewAll(page, size, sortBy, sortDir);
        } else {
            BooleanExpression predicate = null;
            predicate = AppUtils.getBooleanExpression(filter, predicate, Student.class, "student", Arrays.asList("classroom", "extraCurricularYears", "dorm", "school"));

            Page<StudentDto> studentDtoPage = this.studentQueryService.getAllStudentDetailsByPredicate(page, size, sortBy, sortDir, predicate);
            return this.getClassroomSubjects(studentDtoPage);
        }
    }

    @Override
    public List<YearDropdown> getAllStudentsForDropdown() {
        return this.studentQueryService.getAllStudentsForDropdown();
    }

    @Override
    public List<YearDropdown> getAllStudentsForDropdown(Student student) {
        Example<Student> studentExample = Example.of(student);
        return this.studentQueryService.getAllStudentsForDropdown(studentExample);
    }

    @Override
    public List<YearDropdown> getAllStudentsForSubjectYear(long subjectYearId) {
        return this.studentQueryService.getAllStudentsForSubject(subjectYearId);
    }

    @Override
    public List<YearDropdown> getAllStudentsForClassroomYear(long classroomYearId) {
        return this.studentQueryService.getAllStudentsForClassroom(classroomYearId);
    }

    private Page<StudentDto> getClassroomSubjects(Page<StudentDto> studentDtoPage) {
        List<Long> classroomIds = studentDtoPage.getContent().parallelStream().map(student -> student.getClassroom().getId()).collect(Collectors.toCollection(CopyOnWriteArrayList::new));
        List<SubjectYearDto> subjectYearDtoList = this.subjectYearQueryService.getSubjectYears(classroomIds);
        Map<Long, List<SubjectYearDto>> mappedSubjectYearDtoList = subjectYearDtoList
                .parallelStream()
                .collect(Collectors.groupingBy(SubjectYearDto::getClassroomId));
        return studentDtoPage.map(student -> {
            StudentClassDto classroom = student.getClassroom();
            classroom.setSubjects(mappedSubjectYearDtoList.get(student.getId()));
            student.setClassroom(classroom);
            return student;
        });
    }
}
