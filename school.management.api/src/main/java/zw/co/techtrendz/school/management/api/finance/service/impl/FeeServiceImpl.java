package zw.co.techtrendz.school.management.api.finance.service.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.common.service.impl.AbstractGenericServiceImpl;
import zw.co.techtrendz.school.management.api.common.utility.QuerydslUtils;
import zw.co.techtrendz.school.management.api.finance.dto.OutstandingBalance;
import zw.co.techtrendz.school.management.api.finance.entity.Fee;
import zw.co.techtrendz.school.management.api.finance.entity.FeeItem;
import zw.co.techtrendz.school.management.api.finance.entity.FeePayment;
import zw.co.techtrendz.school.management.api.finance.querydsl.FeeItemQueryService;
import zw.co.techtrendz.school.management.api.finance.querydsl.FeeQueryService;
import zw.co.techtrendz.school.management.api.finance.repository.FeeRepository;
import zw.co.techtrendz.school.management.api.finance.service.FeePaymentService;
import zw.co.techtrendz.school.management.api.finance.service.FeeService;
import zw.co.techtrendz.school.management.api.user.entity.Student;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class FeeServiceImpl extends AbstractGenericServiceImpl<Fee, Long> implements FeeService {

    private final FeeRepository feeRepository;
    private final FeeQueryService feeQueryService;
    private final FeePaymentService feePaymentService;
    private final FeeItemQueryService feeItemQueryService;

    public FeeServiceImpl(
            FeeRepository feeRepository,
            FeeQueryService feeQueryService,
            FeePaymentService feePaymentService,
            FeeItemQueryService feeItemQueryService
    ) {
        super(feeRepository);
        this.feeRepository = feeRepository;
        this.feeQueryService = feeQueryService;
        this.feePaymentService = feePaymentService;
        this.feeItemQueryService = feeItemQueryService;
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

    @Override
    public OutstandingBalance getOutstandingBalance(long studentId) {
        List<Fee> fees = this.getAllByStudentId(null, null, studentId);
        FeePayment feePayment = new FeePayment();
        Student student = new Student(studentId);
        student.setStatus(null);
        student.setDatestamp(null);
        feePayment.setStudent(student);
        feePayment.setStatus(null);
        feePayment.setDatestamp(null);
        List<FeePayment> feePayments = this.feePaymentService.getAllByExample(feePayment, null, null);

        BigDecimal feeTotal = fees
                .parallelStream()
                .map(Fee::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal paidTotal = feePayments
                .parallelStream()
                .map(FeePayment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal balance = feeTotal.subtract(paidTotal).setScale(2, RoundingMode.HALF_UP);
        OutstandingBalance outstandingBalance = new OutstandingBalance();
        outstandingBalance.setStudent(new Student(studentId));
        outstandingBalance.setBalance(balance);

        return outstandingBalance;
    }

    private List<Fee> getFeeItems(List<Fee> fees) {
        List<Long> feeIds = fees
                .parallelStream()
                .map(Fee::getId)
                .collect(Collectors.toCollection(CopyOnWriteArrayList::new));

        if (feeIds.isEmpty()) return fees;

        Map<Long, List<FeeItem>> mappedFeeItems = this.feeItemQueryService.getAllByFee(feeIds);

        fees
                .parallelStream()
                .forEach(fee -> fee.setItems(mappedFeeItems.get(fee.getId())));

        return fees;
    }
}
