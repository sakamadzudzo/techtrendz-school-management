package zw.co.techtrendz.school.management.api.academic.service.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import zw.co.techtrendz.school.management.api.common.dto.YearDropdown;
import zw.co.techtrendz.school.management.api.academic.entity.DormYear;
import zw.co.techtrendz.school.management.api.academic.querydsl.DormYearQueryService;
import zw.co.techtrendz.school.management.api.user.querydsl.StudentQueryService;
import zw.co.techtrendz.school.management.api.academic.repository.DormYearRepository;
import zw.co.techtrendz.school.management.api.academic.service.DormYearService;
import zw.co.techtrendz.school.management.api.common.utility.AppUtils;
import zw.co.techtrendz.school.management.api.common.utility.SpecificationUtils;
import zw.co.techtrendz.school.management.api.academic.view.DormYearDto;
import zw.co.techtrendz.school.management.api.user.view.StudentDto;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class DormYearServiceImpl implements DormYearService {

    private final DormYearRepository dormYearRepository;
    private final DormYearQueryService dormYearQueryService;
    private final StudentQueryService studentQueryService;

    public DormYearServiceImpl(DormYearRepository dormYearRepository, DormYearQueryService dormYearQueryService, StudentQueryService studentQueryService) {
        this.dormYearRepository = dormYearRepository;
        this.dormYearQueryService = dormYearQueryService;
        this.studentQueryService = studentQueryService;
    }

    @Override
    public DormYear save(DormYear dormYear) {
        return this.dormYearRepository.save(dormYear);
    }

    @Override
    public List<DormYear> saveAll(List<DormYear> dormYears) {
        return dormYears.parallelStream().map(this::save).toList();
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
    public DormYearDto getViewById(Long id) {
        DormYearDto dormYearDto = this.dormYearQueryService.getDormYearDetails(id);
        if (dormYearDto != null) {
            dormYearDto.setStudents(this.studentQueryService.getStudentsByDormId(Collections.singletonList(id)));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Dorm year with id '%d' was not found", id));
        }
        return dormYearDto;
    }

    @Override
    public Page<DormYearDto> getViewAll(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        Page<DormYearDto> dormDtoPage = this.dormYearQueryService.getAllDormYearDetails(page, size, sortBy, sortDir);
        return this.getDormStudents(dormDtoPage);
    }

    @Override
    public Page<DormYearDto> getViewAllByExample(DormYear dormYear, int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (dormYear == null) {
            return this.getViewAll(page, size, sortBy, sortDir);
        } else {
            Example<DormYear> example = Example.of(dormYear);

            Page<DormYearDto> dormDtoPage = this.dormYearQueryService.getAllDormYearDetailsByExample(page, size, sortBy, sortDir, example);
            return this.getDormStudents(dormDtoPage);
        }
    }

    @Override
    public Page<DormYearDto> getViewAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter) {
        if (filter == null || filter.isBlank()) {
            return this.getViewAll(page, size, sortBy, sortDir);
        } else {
            BooleanExpression predicate = null;
            predicate = AppUtils.getBooleanExpression(filter, predicate, DormYear.class, "dormYear", Arrays.asList("school"));

            Page<DormYearDto> dormDtoPage = this.dormYearQueryService.getAllDormYearDetailsByPredicate(page, size, sortBy, sortDir, predicate);
            return this.getDormStudents(dormDtoPage);
        }
    }

    @Override
    public List<YearDropdown> getAllDormYearForDropdown() {
        return this.dormYearQueryService.getAllDormYearForDropdown();
    }

    private Page<DormYearDto> getDormStudents(Page<DormYearDto> dormDtoPage) {
        List<Long> dormIds = dormDtoPage.getContent().parallelStream().map(DormYearDto::getId).collect(Collectors.toCollection(CopyOnWriteArrayList::new));
        List<StudentDto> studentDtoList = this.studentQueryService.getStudentsByDormId(dormIds);
        Map<Long, List<StudentDto>> mappedStudentDtoList = studentDtoList
                .parallelStream()
                .collect(Collectors.groupingBy(StudentDto::getDormId));

        return dormDtoPage.map(dorm -> {
            dorm.setStudents(mappedStudentDtoList.get(dorm.getId()));
            return dorm;
        });
    }
}
