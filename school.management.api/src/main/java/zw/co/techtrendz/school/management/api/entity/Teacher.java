package zw.co.techtrendz.school.management.api.entity;

import com.querydsl.core.annotations.QueryEntity;
import com.querydsl.core.annotations.QueryInit;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Audited
@QueryEntity
public class Teacher extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @QueryInit("*.*")
    private Person person;

    @ManyToMany(mappedBy = "facilitators")
    private List<ExtraCurricularYear> extraCurricularYears;

    public Teacher(long id) {
        this.id = id;
    }
}
