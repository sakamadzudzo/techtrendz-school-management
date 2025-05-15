package zw.co.techtrendz.school.management.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zw.co.techtrendz.school.management.api.entity.Classroom;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
}
