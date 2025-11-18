package zw.co.techtrendz.school.management.api.finance.service.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.common.service.impl.AbstractGenericServiceImpl;
import zw.co.techtrendz.school.management.api.common.utility.QuerydslUtils;
import zw.co.techtrendz.school.management.api.finance.entity.Fee;
import zw.co.techtrendz.school.management.api.finance.querydsl.FeeQueryService;
import zw.co.techtrendz.school.management.api.finance.repository.FeeRepository;
import zw.co.techtrendz.school.management.api.finance.service.FeeService;
import zw.co.techtrendz.school.management.api.user.entity.Student;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeeServiceImpl extends AbstractGenericServiceImpl<Fee, Long> implements FeeService {

    private final FeeRepository feeRepository;
    private final FeeQueryService feeQueryService;

    public FeeServiceImpl(FeeRepository feeRepository, FeeQueryService feeQueryService) {
        super(feeRepository);
        this.feeRepository = feeRepository;
        this.feeQueryService = feeQueryService;
    }

    @Override
    public Page<Fee> getAllByStudentId(int page, int size, List<String> sortBy, Sort.Direction sortDir, long studentId) {
        if (sortBy == null) sortBy = new ArrayList<>(List.of("student.name"));
        if (sortDir == null) sortDir = Sort.Direction.DESC;

        Student student = new Student(studentId);
        student.setDatestamp(null);
        student.setStatus(null);

        Fee fee = new Fee();
        fee.setStudent(student);
        fee.setStatus(null);
        fee.setDatestamp(null);

        Example<Fee> example = Example.of(fee);
        BooleanExpression predicate = QuerydslUtils.toPredicate(example, Fee.class, "fee");

        return this.feeQueryService.getByPredicate(page, size, sortBy, sortDir, predicate);
    }

    @Override
    public Page<Fee> getAllByCalendarYear(int page, int size, List<String> sortBy, Sort.Direction sortDir, int calendarYear) {
        if (sortBy == null) sortBy = new ArrayList<>(List.of("student.name"));
        if (sortDir == null) sortDir = Sort.Direction.DESC;

        Fee fee = new Fee();
        fee.setCalendarYear(calendarYear);
        fee.setStatus(null);
        fee.setDatestamp(null);

        Example<Fee> example = Example.of(fee);
        BooleanExpression predicate = QuerydslUtils.toPredicate(example, Fee.class, "fee");

        return this.feeQueryService.getByPredicate(page, size, sortBy, sortDir, predicate);
    }

    @Override
    public Page<Fee> getAllByStudentIdAndCalendarYear(int page, int size, List<String> sortBy, Sort.Direction sortDir, long studentId, int calendarYear) {
        if (sortBy == null) sortBy = new ArrayList<>(List.of("student.name"));
        if (sortDir == null) sortDir = Sort.Direction.DESC;

        Student student = new Student(studentId);
        student.setDatestamp(null);
        student.setStatus(null);

        Fee fee = new Fee();
        fee.setStudent(student);
        fee.setCalendarYear(calendarYear);
        fee.setStatus(null);
        fee.setDatestamp(null);

        Example<Fee> example = Example.of(fee);
        BooleanExpression predicate = QuerydslUtils.toPredicate(example, Fee.class, "fee");

        return this.feeQueryService.getByPredicate(page, size, sortBy, sortDir, predicate);
    }

    @Override
    public List<Fee> getAllByStudentId(List<String> sortBy, Sort.Direction sortDir, long studentId) {
        if (sortBy == null) sortBy = new ArrayList<>(List.of("student.name"));
        if (sortDir == null) sortDir = Sort.Direction.DESC;

        Student student = new Student(studentId);
        student.setDatestamp(null);
        student.setStatus(null);

        Fee fee = new Fee();
        fee.setStudent(student);
        fee.setStatus(null);
        fee.setDatestamp(null);

        Example<Fee> example = Example.of(fee);
        BooleanExpression predicate = QuerydslUtils.toPredicate(example, Fee.class, "fee");

        return this.feeQueryService.getByPredicate(sortBy, sortDir, predicate);
    }

    @Override
    public List<Fee> getAllByCalendarYear(List<String> sortBy, Sort.Direction sortDir, int calendarYear) {
        if (sortBy == null) sortBy = new ArrayList<>(List.of("student.name"));
        if (sortDir == null) sortDir = Sort.Direction.DESC;

        Fee fee = new Fee();
        fee.setCalendarYear(calendarYear);
        fee.setStatus(null);
        fee.setDatestamp(null);

        Example<Fee> example = Example.of(fee);
        BooleanExpression predicate = QuerydslUtils.toPredicate(example, Fee.class, "fee");

        return this.feeQueryService.getByPredicate(sortBy, sortDir, predicate);
    }

    @Override
    public List<Fee> getAllByStudentIdAndCalendarYear(List<String> sortBy, Sort.Direction sortDir, long studentId, int calendarYear) {
        if (sortBy == null) sortBy = new ArrayList<>(List.of("student.name"));
        if (sortDir == null) sortDir = Sort.Direction.DESC;

        Student student = new Student(studentId);
        student.setDatestamp(null);
        student.setStatus(null);

        Fee fee = new Fee();
        fee.setStudent(student);
        fee.setCalendarYear(calendarYear);
        fee.setStatus(null);
        fee.setDatestamp(null);

        Example<Fee> example = Example.of(fee);
        BooleanExpression predicate = QuerydslUtils.toPredicate(example, Fee.class, "fee");

        return this.feeQueryService.getByPredicate(sortBy, sortDir, predicate);
    }
}
