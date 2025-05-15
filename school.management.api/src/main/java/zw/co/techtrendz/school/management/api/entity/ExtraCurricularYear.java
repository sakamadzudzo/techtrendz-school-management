package zw.co.techtrendz.school.management.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Audited
public class ExtraCurricularYear extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer year;

    @ManyToOne
    private ExtraCurricular extraCurricular;

    @ManyToMany
    private List<Teacher> facilitators;

    @ManyToMany
    private List<Student> students;
}
