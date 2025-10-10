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
import zw.co.techtrendz.school.management.api.entity.ActivityPoint;
import zw.co.techtrendz.school.management.api.entity.ExtraCurricularYear;
import zw.co.techtrendz.school.management.api.querydsl.ExtraCurricularYearQueryService;
import zw.co.techtrendz.school.management.api.querydsl.StudentQueryService;
import zw.co.techtrendz.school.management.api.querydsl.TeacherQueryService;
import zw.co.techtrendz.school.management.api.repository.ExtraCurricularYearRepository;
import zw.co.techtrendz.school.management.api.service.ActivityPointService;
import zw.co.techtrendz.school.management.api.service.ExtraCurricularYearService;
import zw.co.techtrendz.school.management.api.utility.AppUtils;
import zw.co.techtrendz.school.management.api.utility.SpecificationUtils;
import zw.co.techtrendz.school.management.api.view.ExtraCurricularYearDto;
import zw.co.techtrendz.school.management.api.view.StudentDto;
import zw.co.techtrendz.school.management.api.view.TeacherDto;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ExtraCurricularYearServiceImpl implements ExtraCurricularYearService {

    private final ExtraCurricularYearRepository extraCurricularYearRepository;
    private final ExtraCurricularYearQueryService extraCurricularYearQueryService;
    private final TeacherQueryService teacherQueryService;
    private final StudentQueryService studentQueryService;
    private final ActivityPointService activityPointService;

    public ExtraCurricularYearServiceImpl(
            ExtraCurricularYearRepository extraCurricularYearRepository,
            ExtraCurricularYearQueryService extraCurricularYearQueryService,
            TeacherQueryService teacherQueryService,
            StudentQueryService studentQueryService,
            ActivityPointService activityPointService) {
        this.extraCurricularYearRepository = extraCurricularYearRepository;
        this.extraCurricularYearQueryService = extraCurricularYearQueryService;
        this.teacherQueryService = teacherQueryService;
        this.studentQueryService = studentQueryService;
        this.activityPointService = activityPointService;
    }

    @Override
    public ExtraCurricularYear save(ExtraCurricularYear extraCurricularYear) {
        return this.extraCurricularYearRepository.save(extraCurricularYear);
    }

    @Override
    public List<ExtraCurricularYear> saveAll(List<ExtraCurricularYear> extraCurricularYears) {
        return extraCurricularYears.parallelStream().map(this::save).toList();
    }

    @Override
    public ExtraCurricularYear getById(Long id) {
        return this.extraCurricularYearRepository.findById(id).orElse(null);
    }

    @Override
    public Page<ExtraCurricularYear> getAll(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (sortBy.isEmpty()) {
            sortBy.add("extraCurricular.name");
        }
        String[] sortFields = sortBy.toArray(new String[sortBy.size()]);
        return this.extraCurricularYearRepository.findAll(PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
    }

    @Override
    public Page<ExtraCurricularYear> getAllByExample(ExtraCurricularYear extraCurricularYear, int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (extraCurricularYear == null) {
            return this.getAll(page, size, sortBy, sortDir);
        } else {
            Example<ExtraCurricularYear> example = Example.of(extraCurricularYear);

            if (sortBy.isEmpty()) {
                sortBy.add("extraCurricular.name");
            }
            String[] sortFields = sortBy.toArray(new String[sortBy.size()]);

            return this.extraCurricularYearRepository.findAll(example, PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
        }
    }

    @Override
    public Page<ExtraCurricularYear> getAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter) {
        if (filter == null || filter.isBlank()) {
            return this.getAll(page, size, sortBy, sortDir);
        } else {
            Specification<ExtraCurricularYear> spec = Specification.where(null);
            for (var field : ExtraCurricularYear.class.getDeclaredFields()) {
                field.setAccessible(true); // Ensure private fields are accessible
                if (field.getType().equals(String.class)) {
                    spec = spec.or(SpecificationUtils.likeMatch(field.getName(), filter));
                }
            }

            if (sortBy.isEmpty()) {
                sortBy.add("extraCurricular.name");
            }
            String[] sortFields = sortBy.toArray(new String[sortBy.size()]);

            return this.extraCurricularYearRepository.findAll(spec, PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
        }
    }

    @Override
    public ExtraCurricularYearDto getViewById(Long id) {
        ExtraCurricularYearDto extraCurricularYearDto = this.extraCurricularYearQueryService.getExtraCurricularYearDetails(id);
        if (extraCurricularYearDto != null) {
            extraCurricularYearDto.setFacilitators(this.teacherQueryService.getFacilitatorsByExtraCurricular(Collections.singletonList(extraCurricularYearDto.getId())));
            extraCurricularYearDto.setStudents(this.studentQueryService.getStudentsByExtraCurricular(Collections.singletonList(extraCurricularYearDto.getId())));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Extra curricular year with id '%d' was not found", id));
        }
        return extraCurricularYearDto;
    }

    @Override
    public Page<ExtraCurricularYearDto> getViewAll(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        Page<ExtraCurricularYearDto> extraCurricularYearDtoPage = this.extraCurricularYearQueryService.getAllExtraCurricularYearDetails(page, size, sortBy, sortDir);
        return getExtraCurricularYearClassesAndSubjects(extraCurricularYearDtoPage);
    }

    @Override
    public Page<ExtraCurricularYearDto> getViewAllByExample(ExtraCurricularYear extraCurricularYear, int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (extraCurricularYear == null) {
            return this.getViewAll(page, size, sortBy, sortDir);
        } else {
            Example<ExtraCurricularYear> example = Example.of(extraCurricularYear);

            if (sortBy.isEmpty()) {
                sortBy.add("extraCurricular.name");
            }

            Page<ExtraCurricularYearDto> extraCurricularYearDtoPage = this.extraCurricularYearQueryService.getAllExtraCurricularYearDetailsByExample(page, size, sortBy, sortDir, example);
            return getExtraCurricularYearClassesAndSubjects(extraCurricularYearDtoPage);
        }
    }

    @Override
    public Page<ExtraCurricularYearDto> getViewAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter) {
        if (filter == null || filter.isBlank()) {
            return this.getViewAll(page, size, sortBy, sortDir);
        } else {
            BooleanExpression predicate = null;
            predicate = AppUtils.getBooleanExpression(filter, predicate, ExtraCurricularYear.class, "extraCurricularYear", Arrays.asList("students", "facilitators"));

            Page<ExtraCurricularYearDto> extraCurricularYearDtoPage = this.extraCurricularYearQueryService.getAllExtraCurricularYearDetailsByPredicate(page, size, sortBy, sortDir, predicate);
            return getExtraCurricularYearClassesAndSubjects(extraCurricularYearDtoPage);
        }
    }

    private Page<ExtraCurricularYearDto> getExtraCurricularYearClassesAndSubjects(Page<ExtraCurricularYearDto> extraCurricularYearDtoPage) {
        List<Long> extraCurricularYearIds = extraCurricularYearDtoPage.getContent().parallelStream().map(ExtraCurricularYearDto::getId).collect(Collectors.toCollection(CopyOnWriteArrayList::new));
        List<TeacherDto> facilitatorDtoList = this.teacherQueryService.getFacilitatorsByExtraCurricular(extraCurricularYearIds);
        List<ActivityPoint> activityPointList = this.activityPointService.getAllByActivityIds(extraCurricularYearIds);
        Map<Long, List<TeacherDto>> mappedFacilitatorList = facilitatorDtoList
                .parallelStream()
                .collect(Collectors.groupingBy(TeacherDto::getExtraCurricularYearId));
        Map<Long, ActivityPoint> mappedActivityPointList = activityPointList
                .parallelStream()
                .collect(Collectors.toMap(
                        a -> a.getStudent().getId(), Function.identity(), (oldValue, newValue) -> oldValue
                ));
        List<StudentDto> studentDtoList = this.studentQueryService.getStudentsByExtraCurricular(extraCurricularYearIds);
        List<StudentDto> newStudentDtoList = studentDtoList
                .parallelStream()
                .map(s -> {
                    s.setActivityPoint(mappedActivityPointList.get(s.getId()));
                    return s;
                })
                .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
        Map<Long, List<StudentDto>> mappedStudentDtoList = newStudentDtoList
                .parallelStream()
                .collect(Collectors.groupingBy(StudentDto::getExtraCurricularYearId));

        return extraCurricularYearDtoPage.map(extraCurricularYear -> {
            extraCurricularYear.setFacilitators(mappedFacilitatorList.get(extraCurricularYear.getId()));
            extraCurricularYear.setStudents(mappedStudentDtoList.get(extraCurricularYear.getId()));
            return extraCurricularYear;
        });
    }
}
