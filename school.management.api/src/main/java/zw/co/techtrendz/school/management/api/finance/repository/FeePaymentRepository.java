package zw.co.techtrendz.school.management.api.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zw.co.techtrendz.school.management.api.finance.entity.FeePayment;

@Repository
public interface FeePaymentRepository extends JpaRepository<FeePayment, Long> {
}
