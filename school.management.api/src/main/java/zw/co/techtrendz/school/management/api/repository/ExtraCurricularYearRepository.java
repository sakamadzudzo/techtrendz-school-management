package zw.co.techtrendz.school.management.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zw.co.techtrendz.school.management.api.entity.ExtraCurricularYear;

@Repository
public interface ExtraCurricularYearRepository extends JpaRepository<ExtraCurricularYear, Long> {
}
