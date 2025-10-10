package zw.co.techtrendz.school.management.api.entity;

import com.querydsl.core.annotations.QueryEntity;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Entity representing a teacher in the school")
public class Teacher extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the teacher", example = "1")
    private Long id;

    @ManyToOne
    @Schema(description = "Person details associated with this teacher")
    private Person person;

    @ManyToMany(mappedBy = "facilitators")
    @Schema(description = "Extra-curricular activities facilitated by this teacher")
    private List<ExtraCurricularYear> extraCurricularYears;

    public Teacher(long id) {
        this.id = id;
    }

    public Teacher(Long id, Person person) {
        this.id = id;
        this.person = person;
    }
}
