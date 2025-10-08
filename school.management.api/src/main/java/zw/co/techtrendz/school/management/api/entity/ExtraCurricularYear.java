package zw.co.techtrendz.school.management.api.entity;

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
public class ExtraCurricularYear extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer calendarYear;

    @ManyToOne
    @QueryInit("*.*")
    private ExtraCurricular extraCurricular;

    @ManyToMany
    @QueryInit("*.*")
    private List<Teacher> facilitators;

    @ManyToMany
    @QueryInit("*.*")
    private List<Student> students;

    public ExtraCurricularYear(long id) {
        this.id = id;
    }
}
