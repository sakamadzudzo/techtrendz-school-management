package zw.co.techtrendz.school.management.api.service.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.SubjectYear;
import zw.co.techtrendz.school.management.api.querydsl.SubjectYearQueryService;
import zw.co.techtrendz.school.management.api.repository.SubjectYearRepository;
import zw.co.techtrendz.school.management.api.service.SubjectYearService;
import zw.co.techtrendz.school.management.api.utility.QuerydslUtils;
import zw.co.techtrendz.school.management.api.utility.SpecificationUtils;
import zw.co.techtrendz.school.management.api.view.SubjectYearDto;

import java.util.List;

@Service
public class SubjectYearServiceImpl implements SubjectYearService {

    @Autowired
    private SubjectYearRepository subjectYearRepository;
    @Autowired
    private SubjectYearQueryService subjectYearQueryService;

    @Override
    public SubjectYear save(SubjectYear subjectYear) {
        return this.subjectYearRepository.save(subjectYear);
    }

    @Override
    public List<SubjectYear> saveAll(List<SubjectYear> subjectYears) {
        return subjectYears.stream().map(this::save).toList();
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
        return this.subjectYearQueryService.getSubjectYearDetails(id);
    }

    @Override
    public Page<SubjectYearDto> getViewAll(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        return this.subjectYearQueryService.getAllSubjectYearDetails(page, size, sortBy, sortDir);
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

            return this.subjectYearQueryService.getAllSubjectYearDetailsByExample(page, size, sortBy, sortDir, example);
        }
    }

    @Override
    public Page<SubjectYearDto> getViewAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter) {
        if (filter == null || filter.isBlank()) {
            return this.getViewAll(page, size, sortBy, sortDir);
        } else {
            BooleanExpression predicate = null;
            for (var field : SubjectYear.class.getDeclaredFields()) {
                field.setAccessible(true); // Ensure private fields are accessible
                if (field.getType().equals(String.class)) {
                    predicate = QuerydslUtils.chainPredicate(predicate, QuerydslUtils.likeMatch(SubjectYear.class, field.getName(), filter), false);
                }
            }

            if (sortBy.isEmpty()) {
                sortBy.add("subject.name");
            }

            return this.subjectYearQueryService.getAllSubjectYearDetailsByPredicate(page, size, sortBy, sortDir, predicate);
        }
    }
}
