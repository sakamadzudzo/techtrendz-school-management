package zw.co.techtrendz.school.management.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zw.co.techtrendz.school.management.api.entity.SubjectYear;

@Repository
public interface SubjectYearRepository extends JpaRepository<SubjectYear, Long> {
}
