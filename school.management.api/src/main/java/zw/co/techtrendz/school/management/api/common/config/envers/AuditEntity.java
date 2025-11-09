package zw.co.techtrendz.school.management.api.common.config.envers;

import com.querydsl.core.annotations.QueryExclude;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

@Entity
@RevisionEntity(AuditUserListener.class)
@Table(name = "revinfo")
@Getter
@Setter
@QueryExclude
public class AuditEntity extends DefaultRevisionEntity {

    private Long userId;

    private String username;

}
