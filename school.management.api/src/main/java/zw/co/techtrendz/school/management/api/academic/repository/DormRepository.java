package zw.co.techtrendz.school.management.api.academic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import zw.co.techtrendz.school.management.api.academic.entity.Dorm;

@Repository
public interface DormRepository extends JpaRepository<Dorm, Long>, JpaSpecificationExecutor<Dorm> {
}
