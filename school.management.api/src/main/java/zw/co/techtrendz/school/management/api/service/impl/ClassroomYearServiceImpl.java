package zw.co.techtrendz.school.management.api.service.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.ClassroomYear;
import zw.co.techtrendz.school.management.api.querydsl.ClassroomYearQueryService;
import zw.co.techtrendz.school.management.api.querydsl.SubjectYearQueryService;
import zw.co.techtrendz.school.management.api.repository.ClassroomYearRepository;
import zw.co.techtrendz.school.management.api.service.ClassroomYearService;
import zw.co.techtrendz.school.management.api.utility.QuerydslUtils;
import zw.co.techtrendz.school.management.api.utility.SpecificationUtils;
import zw.co.techtrendz.school.management.api.view.ClassroomYearDto;
import zw.co.techtrendz.school.management.api.view.SubjectYearDto;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class ClassroomYearServiceImpl implements ClassroomYearService {

    @Autowired
    private ClassroomYearRepository classroomYearRepository;
    @Autowired
    private ClassroomYearQueryService classroomYearQueryService;
    @Autowired
    private SubjectYearQueryService subjectYearQueryService;

    @Override
    public ClassroomYear save(ClassroomYear classroomYear) {
        return this.classroomYearRepository.save(classroomYear);
    }

    @Override
    public List<ClassroomYear> saveAll(List<ClassroomYear> classroomYears) {
        return classroomYears.stream().map(this::save).toList();
    }

    @Override
    public ClassroomYear getById(Long id) {
        return this.classroomYearRepository.findById(id).orElse(null);
    }

    @Override
    public Page<ClassroomYear> getAll(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (sortBy.isEmpty()) {
            sortBy.add("name");
        }
        String[] sortFields = sortBy.toArray(new String[sortBy.size()]);
        return this.classroomYearRepository.findAll(PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
    }

    @Override
    public Page<ClassroomYear> getAllByExample(ClassroomYear classroomYear, int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (classroomYear == null) {
            return this.getAll(page, size, sortBy, sortDir);
        } else {
            Example<ClassroomYear> example = Example.of(classroomYear);

            if (sortBy.isEmpty()) {
                sortBy.add("name");
            }
            String[] sortFields = sortBy.toArray(new String[sortBy.size()]);

            return this.classroomYearRepository.findAll(example, PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
        }
    }

    @Override
    public Page<ClassroomYear> getAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter) {
        if (filter == null || filter.isBlank()) {
            return this.getAll(page, size, sortBy, sortDir);
        } else {
            Specification<ClassroomYear> spec = Specification.where(null);
            for (var field : ClassroomYear.class.getDeclaredFields()) {
                field.setAccessible(true); // Ensure private fields are accessible
                if (field.getType().equals(String.class)) {
                    spec = spec.or(SpecificationUtils.likeMatch(field.getName(), filter));
                }
            }

            if (sortBy.isEmpty()) {
                sortBy.add("name");
            }
            String[] sortFields = sortBy.toArray(new String[sortBy.size()]);

            return this.classroomYearRepository.findAll(spec, PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
        }
    }

    @Override
    public ClassroomYearDto getViewById(Long id) {
        ClassroomYearDto classroomYearDto = this.classroomYearQueryService.getClassroomYearDetails(id);
        if (classroomYearDto != null) {
            classroomYearDto.setSubjects(this.subjectYearQueryService.getSubjectYears(Arrays.asList(id)));
        }
        return classroomYearDto;
    }

    @Override
    public Page<ClassroomYearDto> getViewAll(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        Page<ClassroomYearDto> classroomYearDtoPage = this.classroomYearQueryService.getAllClassroomYearDetails(page, size, sortBy, sortDir);
        return getClassroomYearClassesAndSubjects(classroomYearDtoPage);
    }

    @Override
    public Page<ClassroomYearDto> getViewAllByExample(ClassroomYear classroomYear, int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (classroomYear == null) {
            return this.getViewAll(page, size, sortBy, sortDir);
        } else {
            Example<ClassroomYear> example = Example.of(classroomYear);

            if (sortBy.isEmpty()) {
                sortBy.add("classroom.name");
            }

            Page<ClassroomYearDto> classroomYearDtoPage = this.classroomYearQueryService.getAllClassroomYearDetailsByExample(page, size, sortBy, sortDir, example);
            return getClassroomYearClassesAndSubjects(classroomYearDtoPage);
        }
    }

    @Override
    public Page<ClassroomYearDto> getViewAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter) {
        if (filter == null || filter.isBlank()) {
            return this.getViewAll(page, size, sortBy, sortDir);
        } else {
            BooleanExpression predicate = null;
            for (var field : ClassroomYear.class.getDeclaredFields()) {
                field.setAccessible(true); // Ensure private fields are accessible
                if (field.getType().equals(String.class)) {
                    predicate = QuerydslUtils.chainPredicate(predicate, QuerydslUtils.likeMatch(ClassroomYear.class, field.getName(), filter), false);
                }
            }

            if (sortBy.isEmpty()) {
                sortBy.add("classroom.name");
            }

            Page<ClassroomYearDto> classroomYearDtoPage = this.classroomYearQueryService.getAllClassroomYearDetailsByPredicate(page, size, sortBy, sortDir, predicate);
            return getClassroomYearClassesAndSubjects(classroomYearDtoPage);
        }
    }

    private Page<ClassroomYearDto> getClassroomYearClassesAndSubjects(Page<ClassroomYearDto> classroomYearDtoPage) {
        List<Long> classroomYearIds = classroomYearDtoPage.getContent().stream().map(ClassroomYearDto::getId).collect(Collectors.toCollection(CopyOnWriteArrayList::new));
        List<SubjectYearDto> subjectYearDtoList = this.subjectYearQueryService.getSubjectYears(classroomYearIds);
        return classroomYearDtoPage.map(classroomYear -> {
            classroomYear.setSubjects(subjectYearDtoList
                    .stream()
                    .filter(subject -> subject
                            .getClassroomId()
                            .equals(classroomYear.getId()))
                    .collect(Collectors.toCollection(CopyOnWriteArrayList::new)));
            return classroomYear;
        });
    }
}
