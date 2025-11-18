package zw.co.techtrendz.school.management.api.finance.service.impl;

import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.common.service.impl.AbstractGenericServiceImpl;
import zw.co.techtrendz.school.management.api.finance.entity.FeePayment;
import zw.co.techtrendz.school.management.api.finance.entity.PaymentReference;
import zw.co.techtrendz.school.management.api.finance.repository.FeePaymentRepository;
import zw.co.techtrendz.school.management.api.finance.repository.PaymentReferenceRepository;
import zw.co.techtrendz.school.management.api.finance.service.FeePaymentService;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class FeePaymentServiceImpl extends AbstractGenericServiceImpl<FeePayment, Long> implements FeePaymentService {

    private final PaymentReferenceRepository paymentReferenceRepository;

    public FeePaymentServiceImpl(
            FeePaymentRepository feePaymentRepository,
            PaymentReferenceRepository paymentReferenceRepository
    ) {
        super(feePaymentRepository);
        this.paymentReferenceRepository = paymentReferenceRepository;
    }

    @Override
    public FeePayment save(FeePayment feePayment) {
        FeePayment feePaymentNew = super.save(feePayment);
        this.verifyPayment(feePaymentNew);
        return feePaymentNew;
    }

    @Override
    public List<FeePayment> saveAll(List<FeePayment> feePayments) {
        return feePayments
                .stream()
                .map(this::save)
                .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
    }

    @Override
    public FeePayment getByReference(String reference) {
        return null;
    }

    @Override
    public List<FeePayment> getAllByVerified(boolean verified) {
        return null;
    }

    @Override
    public List<FeePayment> getAllByPaymentDateBetween(LocalDate startDate, LocalDate endDate) {
        return null;
    }

    @Override
    public List<FeePayment> getAllByReferences(List<String> references) {
        return null;
    }

    private void verifyPayment(FeePayment feePayment) {
        if (feePayment != null && feePayment.getVerified()) return;
        PaymentReference paymentReference = this.paymentReferenceRepository.findByReference(feePayment.getReference());
        if (paymentReference != null) {
            feePayment.setVerified(true);
            this.save(feePayment);
        }
    }
}
