package zw.co.techtrendz.school.management.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import zw.co.techtrendz.school.management.api.entity.ClassroomYear;

@Repository
public interface ClassroomYearRepository extends JpaRepository<ClassroomYear, Long>, JpaSpecificationExecutor<ClassroomYear> {
}
