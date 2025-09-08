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
public class SubjectYear extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @QueryInit("*.*")
    private Subject subject;

    private Integer calendarYear;

    @ManyToOne
    @QueryInit("*.*")
    private Teacher teacher;

    @ManyToMany(mappedBy = "subjects")
    private List<ClassroomYear> classrooms;

    public SubjectYear(long id) {
        this.id = id;
    }
}
