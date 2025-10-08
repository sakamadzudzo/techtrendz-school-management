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
public class Student extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Person person;

    private Integer startYear;

    private Integer endYear;

    @ManyToOne
    @QueryInit("*.*")
    private ClassroomYear classroom;

    @ManyToMany(mappedBy = "students")
    private List<ExtraCurricularYear> extraCurricularYears;

    @ManyToOne
    @QueryInit("*.*")
    private DormYear dorm;

    @ManyToOne
    private School school;

    public Student(long id) {
        this.id = id;
    }

    public Student(Long id, Person person) {
        this.id = id;
        this.person = person;
    }

    public Student(Long id, Person person, ClassroomYear classroom) {
        this.id = id;
        this.person = person;
        this.classroom = classroom;
    }
}
