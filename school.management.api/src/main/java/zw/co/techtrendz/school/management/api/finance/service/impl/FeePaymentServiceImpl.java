package zw.co.techtrendz.school.management.api.finance.service.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import zw.co.techtrendz.school.management.api.common.service.impl.AbstractGenericServiceImpl;
import zw.co.techtrendz.school.management.api.common.utility.QuerydslUtils;
import zw.co.techtrendz.school.management.api.finance.entity.BankAccount;
import zw.co.techtrendz.school.management.api.finance.entity.FeePayment;
import zw.co.techtrendz.school.management.api.finance.entity.PaymentReference;
import zw.co.techtrendz.school.management.api.finance.querydsl.FeePaymentQueryService;
import zw.co.techtrendz.school.management.api.finance.repository.FeePaymentRepository;
import zw.co.techtrendz.school.management.api.finance.repository.PaymentReferenceRepository;
import zw.co.techtrendz.school.management.api.finance.service.FeePaymentService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class FeePaymentServiceImpl extends AbstractGenericServiceImpl<FeePayment, Long> implements FeePaymentService {

    private final PaymentReferenceRepository paymentReferenceRepository;
    private final FeePaymentQueryService feePaymentQueryService;

    public FeePaymentServiceImpl(
            FeePaymentRepository feePaymentRepository,
            PaymentReferenceRepository paymentReferenceRepository,
            FeePaymentQueryService feePaymentQueryService
    ) {
        super(feePaymentRepository);
        this.paymentReferenceRepository = paymentReferenceRepository;
        this.feePaymentQueryService = feePaymentQueryService;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public FeePayment save(FeePayment feePayment) {
        // Handle race conditions and duplicate calls
        FeePayment feePaymentNew = super.save(feePayment);
        return this.verifyPayment(feePaymentNew);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public List<FeePayment> saveAll(List<FeePayment> feePayments) {
        // Handle race conditions and duplicate calls
        List<FeePayment> feePaymentsNew = super.saveAll(feePayments);
        return this.verifyPayments(feePaymentsNew);
    }

    @Override
    public FeePayment getByReference(String reference) {
        BooleanExpression predicate = null;
        predicate = QuerydslUtils.exactMatch(FeePayment.class, "reference", reference, "feePayment");

        FeePayment feePayment = this.feePaymentQueryService.getByPredicate(predicate);
        if (feePayment == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Fee payment with reference %s not found", reference));

        return feePayment;
    }

    @Override
    public List<FeePayment> getAllByVerified(boolean verified) {
        BooleanExpression predicate = null;
        predicate = QuerydslUtils.exactMatch(FeePayment.class, "verified", verified, "feePayment");

        return this.feePaymentQueryService.getByPredicate(null, null, predicate);
    }

    @Override
    public List<FeePayment> getAllByPaymentDateBetween(LocalDate startDate, LocalDate endDate) {
        if (startDate == null && endDate == null) return new ArrayList<FeePayment>();

        BooleanExpression predicate = null;
        predicate = QuerydslUtils.dateBetween(FeePayment.class, "paymentDate", LocalDateTime.of(startDate, LocalTime.of(0, 0, 0)), LocalDateTime.of(endDate, LocalTime.of(23, 59, 59)), "feePayment");

        return this.feePaymentQueryService.getByPredicate(null, null, predicate);
    }

    @Override
    public List<FeePayment> getAllByReferences(List<String> references) {
        if (references == null || references.isEmpty()) return new ArrayList<FeePayment>();

        BooleanExpression predicate = null;
        predicate = QuerydslUtils.in(FeePayment.class, "reference", references, "feePayment");

        return this.feePaymentQueryService.getByPredicate(null, null, predicate);
    }

    @Override
    public List<FeePayment> getAllByExample(FeePayment feePayment, List<String> sortBy, Sort.Direction sortDir) {
        if (feePayment == null) return new ArrayList<FeePayment>();

        Example<FeePayment> example = Example.of(feePayment);
        BooleanExpression predicate = QuerydslUtils.toPredicate(example, FeePayment.class, "feePayment");

        return this.feePaymentQueryService.getByPredicate(sortBy, sortDir, predicate);
    }

    @Override
    public Page<FeePayment> getAllByExample(FeePayment feePayment, int page, int size, List<String> sortBy, Sort.Direction sortDir) {

        if (feePayment == null) return new PageImpl<>(new ArrayList<>());

        Example<FeePayment> example = Example.of(feePayment);
        BooleanExpression predicate = QuerydslUtils.toPredicate(example, FeePayment.class, "feePayment");

        return this.feePaymentQueryService.getByPredicate(page, size, sortBy, sortDir, predicate);
    }

    private FeePayment verifyPayment(FeePayment feePayment) {
        if (feePayment != null && feePayment.getVerified()) return feePayment;
        PaymentReference paymentReference = this.paymentReferenceRepository.findByReference(feePayment.getReference());
        if (paymentReference != null) {
            feePayment.setVerified(true);
            return this.save(feePayment);
        }
        return feePayment;
    }

    private List<FeePayment> verifyPayments(List<FeePayment> feePayments) {
        List<String> references = feePayments
                .parallelStream()
                .filter(feePayment -> !feePayment.getVerified())
                .map(FeePayment::getReference)
                .collect(Collectors.toCollection(CopyOnWriteArrayList::new));

        if (references.isEmpty()) return feePayments;

        List<PaymentReference> paymentReferences = this.paymentReferenceRepository.findAllByReferenceIn(references);
        Map<String, PaymentReference> mappedPaymentReferences = paymentReferences
                .parallelStream()
                .collect(
                        Collectors.toMap(PaymentReference::getReference,
                                Function.identity(),
                                (existing, replacement) -> existing,
                                ConcurrentHashMap::new
                        )
                );

        feePayments
                .parallelStream()
                .forEach(feePayment -> {
                    PaymentReference paymentReference = mappedPaymentReferences.get(feePayment.getReference());
                    if (paymentReference != null) {
                        feePayment.setVerified(true);
                    }
                });

        return this.saveAll(feePayments);
    }

    @Scheduled(cron = "0 0 7,12,17 * * *")
    private void verifyPayments() {
        List<FeePayment> feePayments = this.getAllByVerified(false);
        this.verifyPayments(feePayments);
    }
}
