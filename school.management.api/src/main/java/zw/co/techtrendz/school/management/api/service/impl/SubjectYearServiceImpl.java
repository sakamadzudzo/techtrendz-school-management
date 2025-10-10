package zw.co.techtrendz.school.management.api.service.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import zw.co.techtrendz.school.management.api.dto.YearDropdown;
import zw.co.techtrendz.school.management.api.entity.Coursework;
import zw.co.techtrendz.school.management.api.entity.SubjectYear;
import zw.co.techtrendz.school.management.api.querydsl.SubjectYearQueryService;
import zw.co.techtrendz.school.management.api.repository.SubjectYearRepository;
import zw.co.techtrendz.school.management.api.service.SubjectYearService;
import zw.co.techtrendz.school.management.api.service.CourseworkService;
import zw.co.techtrendz.school.management.api.utility.AppUtils;
import zw.co.techtrendz.school.management.api.utility.SpecificationUtils;
import zw.co.techtrendz.school.management.api.view.SubjectYearDto;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class SubjectYearServiceImpl implements SubjectYearService {

    private final SubjectYearRepository subjectYearRepository;
    private final SubjectYearQueryService subjectYearQueryService;
    private final CourseworkService courseworkService;

    public SubjectYearServiceImpl(SubjectYearRepository subjectYearRepository, SubjectYearQueryService subjectYearQueryService, CourseworkService courseworkService) {
        this.subjectYearRepository = subjectYearRepository;
        this.subjectYearQueryService = subjectYearQueryService;
        this.courseworkService = courseworkService;
    }

    @Override
    public SubjectYear save(SubjectYear subjectYear) {
        return this.subjectYearRepository.save(subjectYear);
    }

    @Override
    public List<SubjectYear> saveAll(List<SubjectYear> subjectYears) {
        return subjectYears.parallelStream().map(this::save).toList();
    }

    @Override
    public SubjectYear getById(Long id) {
        return this.subjectYearRepository.findById(id).orElse(null);
    }

    @Override
    public Page<SubjectYear> getAll(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (sortBy.isEmpty()) {
            sortBy.add("subject.name");
        }
        String[] sortFields = sortBy.toArray(new String[sortBy.size()]);
        return this.subjectYearRepository.findAll(PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
    }

    @Override
    public Page<SubjectYear> getAllByExample(SubjectYear subjectYear, int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (subjectYear == null) {
            return this.getAll(page, size, sortBy, sortDir);
        } else {
            Example<SubjectYear> example = Example.of(subjectYear);

            if (sortBy.isEmpty()) {
                sortBy.add("subject.name");
            }
            String[] sortFields = sortBy.toArray(new String[sortBy.size()]);

            return this.subjectYearRepository.findAll(example, PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
        }
    }

    @Override
    public Page<SubjectYear> getAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter) {
        if (filter == null || filter.isBlank()) {
            return this.getAll(page, size, sortBy, sortDir);
        } else {
            Specification<SubjectYear> spec = Specification.where(null);
            for (var field : SubjectYear.class.getDeclaredFields()) {
                field.setAccessible(true); // Ensure private fields are accessible
                if (field.getType().equals(String.class)) {
                    spec = spec.or(SpecificationUtils.likeMatch(field.getName(), filter));
                }
            }

            if (sortBy.isEmpty()) {
                sortBy.add("subject.name");
            }
            String[] sortFields = sortBy.toArray(new String[sortBy.size()]);

            return this.subjectYearRepository.findAll(spec, PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
        }
    }

    @Override
    public SubjectYearDto getViewById(Long id) {
        SubjectYearDto subjectYearDto = this.subjectYearQueryService.getSubjectYearDetails(id);
        if (subjectYearDto == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Subject year with id '%d' was not found", id));
        } else {
            subjectYearDto.setCourseworks(this.courseworkService.getAllBySubjectId(subjectYearDto.getId()));
        }
        return subjectYearDto;
    }

    @Override
    public Page<SubjectYearDto> getViewAll(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        Page<SubjectYearDto> subjectYearDtoPage = this.subjectYearQueryService.getAllSubjectYearDetails(page, size, sortBy, sortDir);
        return this.getCourseworksForSubjectYearDto(subjectYearDtoPage);
    }

    @Override
    public Page<SubjectYearDto> getViewAllByExample(SubjectYear subjectYear, int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (subjectYear == null) {
            return this.getViewAll(page, size, sortBy, sortDir);
        } else {
            Example<SubjectYear> example = Example.of(subjectYear);

            if (sortBy.isEmpty()) {
                sortBy.add("subject.name");
            }

            Page<SubjectYearDto> subjectYearDtoPage = this.subjectYearQueryService.getAllSubjectYearDetailsByExample(page, size, sortBy, sortDir, example);
            return this.getCourseworksForSubjectYearDto(subjectYearDtoPage);
        }
    }

    @Override
    public Page<SubjectYearDto> getViewAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter) {
        if (filter == null || filter.isBlank()) {
            return this.getViewAll(page, size, sortBy, sortDir);
        } else {
            BooleanExpression predicate = null;
            predicate = AppUtils.getBooleanExpression(filter, predicate, SubjectYear.class, "subjectYear", Arrays.asList("teacher", "classrooms"));

            if (sortBy.isEmpty()) {
                sortBy.add("subject.name");
            }

            Page<SubjectYearDto> subjectYearDtoPage = this.subjectYearQueryService.getAllSubjectYearDetailsByPredicate(page, size, sortBy, sortDir, predicate);
            return this.getCourseworksForSubjectYearDto(subjectYearDtoPage);
        }
    }

    @Override
    public List<YearDropdown> getAllSubjectYearForDropdown() {
        return this.subjectYearQueryService.getAllSubjectYearForDropdown();
    }

    private Page<SubjectYearDto> getCourseworksForSubjectYearDto(Page<SubjectYearDto> subjectYearDtoPage) {
        List<Long> ids = subjectYearDtoPage
                .getContent()
                .parallelStream()
                .map(SubjectYearDto::getId)
                .collect(
                        Collectors
                                .toCollection(
                                        CopyOnWriteArrayList::new
                                )
                );
        List<Coursework> courseworks = this.courseworkService.getAllBySubjectIds(ids);
        Map<Long, List<Coursework>> mappedCourseworks = courseworks
                .parallelStream()
                .collect(Collectors.groupingBy(w -> w.getSubject().getId()));

        return subjectYearDtoPage
                .map(dto -> {
                    dto.setCourseworks(mappedCourseworks.get(dto.getId()));
                    return dto;
                });
    }
}
