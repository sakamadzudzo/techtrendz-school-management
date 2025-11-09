package zw.co.techtrendz.school.management.api.academic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zw.co.techtrendz.school.management.api.academic.entity.Mark;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {
}
