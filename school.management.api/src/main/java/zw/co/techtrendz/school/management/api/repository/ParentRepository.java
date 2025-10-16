package zw.co.techtrendz.school.management.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import zw.co.techtrendz.school.management.api.entity.Parent;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long>, JpaSpecificationExecutor<Parent> {
}