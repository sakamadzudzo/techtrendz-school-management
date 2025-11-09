package zw.co.techtrendz.school.management.api.academic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import zw.co.techtrendz.school.management.api.academic.entity.ExtraCurricular;

@Repository
public interface ExtraCurricularRepository extends JpaRepository<ExtraCurricular, Long>, JpaSpecificationExecutor<ExtraCurricular> {
}
