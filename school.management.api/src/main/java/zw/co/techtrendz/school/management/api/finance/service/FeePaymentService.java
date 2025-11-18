package zw.co.techtrendz.school.management.api.finance.service;

import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.common.service.GenericService;
import zw.co.techtrendz.school.management.api.finance.entity.FeePayment;

import java.time.LocalDate;
import java.util.List;

@Service
public interface FeePaymentService extends GenericService<FeePayment, Long> {

    public FeePayment getByReference(String reference);

    public List<FeePayment> getAllByVerified(boolean verified);

    public List<FeePayment> getAllByPaymentDateBetween(LocalDate startDate, LocalDate endDate);

    public List<FeePayment> getAllByReferences(List<String> references);
}
