package zw.co.techtrendz.school.management.api.entity;

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
    private ExtraCurricular extraCurricular;

    @ManyToMany
    private List<Teacher> facilitators;

    @ManyToMany
    private List<Student> students;

    public ExtraCurricularYear(long id) {
        this.id = id;
    }
}
