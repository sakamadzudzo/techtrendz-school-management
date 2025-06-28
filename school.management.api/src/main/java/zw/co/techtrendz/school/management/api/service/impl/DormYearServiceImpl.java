package zw.co.techtrendz.school.management.api.service.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.DormYear;
import zw.co.techtrendz.school.management.api.entity.Teacher;
import zw.co.techtrendz.school.management.api.querydsl.DormYearQueryService;
import zw.co.techtrendz.school.management.api.repository.DormYearRepository;
import zw.co.techtrendz.school.management.api.service.DormYearService;
import zw.co.techtrendz.school.management.api.utility.QuerydslUtils;
import zw.co.techtrendz.school.management.api.utility.SpecificationUtils;
import zw.co.techtrendz.school.management.api.view.DormDto;

import java.util.List;

@Service
public class DormYearServiceImpl implements DormYearService {

    @Autowired
    private DormYearRepository dormYearRepository;
    @Autowired
    private DormYearQueryService dormYearQueryService;

    @Override
    public DormYear save(DormYear dormYear) {
        return this.dormYearRepository.save(dormYear);
    }

    @Override
    public List<DormYear> saveAll(List<DormYear> dormYears) {
        return dormYears.stream().map(this::save).toList();
    }

    @Override
    public DormYear getById(Long id) {
        return this.dormYearRepository.findById(id).orElse(null);
    }

    @Override
    public Page<DormYear> getAll(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (sortBy.isEmpty()) {
            sortBy.add("name");
        }
        String[] sortFields = sortBy.toArray(new String[sortBy.size()]);
        return this.dormYearRepository.findAll(PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
    }

    @Override
    public Page<DormYear> getAllByExample(DormYear dormYear, int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (dormYear == null) {
            return this.getAll(page, size, sortBy, sortDir);
        } else {
            Example<DormYear> example = Example.of(dormYear);

            if (sortBy.isEmpty()) {
                sortBy.add("name");
            }
            String[] sortFields = sortBy.toArray(new String[sortBy.size()]);

            return this.dormYearRepository.findAll(example, PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
        }
    }

    @Override
    public Page<DormYear> getAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter) {
        if (filter == null || filter.isBlank()) {
            return this.getAll(page, size, sortBy, sortDir);
        } else {
            Specification<DormYear> spec = Specification.where(null);
            for (var field : DormYear.class.getDeclaredFields()) {
                field.setAccessible(true); // Ensure private fields are accessible
                if (field.getType().equals(String.class)) {
                    spec = spec.or(SpecificationUtils.likeMatch(field.getName(), filter));
                }
            }

            if (sortBy.isEmpty()) {
                sortBy.add("name");
            }
            String[] sortFields = sortBy.toArray(new String[sortBy.size()]);

            return this.dormYearRepository.findAll(spec, PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
        }
    }

    @Override
    public DormDto getViewById(Long id) {
        return this.dormYearQueryService.getDormYearDetails(id);
    }

    @Override
    public Page<DormDto> getViewAll(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        return this.dormYearQueryService.getAllDormYearDetails(page, size, sortBy, sortDir);
    }

    @Override
    public Page<DormDto> getViewAllByExample(DormYear dormYear, int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (dormYear == null) {
            return this.getViewAll(page, size, sortBy, sortDir);
        } else {
            Example<DormYear> example = Example.of(dormYear);

            return this.dormYearQueryService.getAllDormYearDetailsByExample(page, size, sortBy, sortDir, example);
        }
    }

    @Override
    public Page<DormDto> getViewAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter) {
        if (filter == null || filter.isBlank()) {
            return this.getViewAll(page, size, sortBy, sortDir);
        } else {
            BooleanExpression predicate = null;
            for (var field : DormYear.class.getDeclaredFields()) {
                field.setAccessible(true); // Ensure private fields are accessible
                if (field.getType().equals(String.class)) {
                    predicate = QuerydslUtils.chainPredicate(predicate, QuerydslUtils.likeMatch(Teacher.class, field.getName(), filter), false);
                }
            }

            return this.dormYearQueryService.getAllDormYearDetailsByPredicate(page, size, sortBy, sortDir, predicate);
        }
    }
}
