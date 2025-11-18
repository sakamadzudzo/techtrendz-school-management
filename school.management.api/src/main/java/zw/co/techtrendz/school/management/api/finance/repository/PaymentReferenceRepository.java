package zw.co.techtrendz.school.management.api.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zw.co.techtrendz.school.management.api.finance.entity.PaymentReference;

import java.util.List;

@Repository
public interface PaymentReferenceRepository extends JpaRepository<PaymentReference, Long> {

    public PaymentReference findByReference(String reference);

    public List<PaymentReference> findAllByReferenceIn(List<String> reference);
}
