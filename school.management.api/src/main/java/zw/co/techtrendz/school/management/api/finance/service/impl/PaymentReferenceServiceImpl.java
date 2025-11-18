package zw.co.techtrendz.school.management.api.finance.service.impl;

import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.common.service.impl.AbstractGenericServiceImpl;
import zw.co.techtrendz.school.management.api.finance.entity.FeePayment;
import zw.co.techtrendz.school.management.api.finance.entity.PaymentReference;
import zw.co.techtrendz.school.management.api.finance.repository.PaymentReferenceRepository;
import zw.co.techtrendz.school.management.api.finance.service.FeePaymentService;
import zw.co.techtrendz.school.management.api.finance.service.PaymentReferenceService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PaymentReferenceServiceImpl extends AbstractGenericServiceImpl<PaymentReference, Long> implements PaymentReferenceService {

    private final FeePaymentService feePaymentService;

    public PaymentReferenceServiceImpl(
            PaymentReferenceRepository paymentReferenceRepository,
            FeePaymentService feePaymentService) {
        super(paymentReferenceRepository);
        this.feePaymentService = feePaymentService;
    }

    @Override
    public PaymentReference save(PaymentReference paymentReference) {
        PaymentReference paymentReferenceNew = super.save(paymentReference);
        this.verifyPayment(paymentReferenceNew);
        return paymentReferenceNew;
    }

    @Override
    public List<PaymentReference> saveAll(List<PaymentReference> paymentReferences) {
        List<PaymentReference> paymentReferencesNew = super.saveAll(paymentReferences);
        this.verifyPayments(paymentReferencesNew);
        return paymentReferencesNew;
    }

    private void verifyPayments(List<PaymentReference> paymentReferences) {
        List<String> references = paymentReferences
                .stream()
                .map(PaymentReference::getReference)
                .toList();
        List<FeePayment> feePayments = this.feePaymentService.getAllByReferences(references);
        final Map<String, FeePayment> feePaymentsMap = feePayments
                .parallelStream()
                .collect(Collectors.toMap(
                        FeePayment::getReference,
                        Function.identity(),
                        (existing, replacement) -> existing,
                        ConcurrentHashMap::new)
                );

        final List<FeePayment> feePaymentsNew = new ArrayList<>();
        paymentReferences
                .parallelStream()
                .forEach(paymentReference -> {
                    FeePayment feePayment = feePaymentsMap.get(paymentReference.getReference());
                    feePayment.setVerified(true);
                    feePaymentsNew.add(feePayment);
                });

        this.feePaymentService.saveAll(feePaymentsNew);
    }

    private void verifyPayment(PaymentReference paymentReference) {
        FeePayment feePayment = this.feePaymentService.getByReference(paymentReference.getReference());
        if (feePayment != null) {
            feePayment.setVerified(true);
            this.feePaymentService.save(feePayment);
        }
    }
}
