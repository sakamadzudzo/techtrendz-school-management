package zw.co.techtrendz.school.management.api.academic.entity;

import com.querydsl.core.annotations.QueryInit;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import zw.co.techtrendz.school.management.api.common.entity.Base;
import zw.co.techtrendz.school.management.api.user.entity.Student;
import zw.co.techtrendz.school.management.api.user.entity.Teacher;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Audited
@Schema(description = "Entity representing an extra-curricular activity for a specific academic year")
public class ExtraCurricularYear extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the extra-curricular year", example = "1")
    private Long id;

    @Schema(description = "Academic year for this extra-curricular activity", example = "2024")
    private Integer calendarYear;

    @ManyToOne
    @QueryInit("*")
    @Schema(description = "Extra-curricular activity this year assignment belongs to")
    private ExtraCurricular extraCurricular;

    @ManyToMany
    @QueryInit("*")
    @Schema(description = "Teachers facilitating this extra-curricular activity")
    private List<Teacher> facilitators;

    @ManyToMany
    @QueryInit("*")
    @Schema(description = "Students participating in this extra-curricular activity")
    private List<Student> students;

    @Schema(description = "ID of the lead facilitator for this activity", example = "5")
    private Long leadFacilitatorId;

    public ExtraCurricularYear(long id) {
        this.id = id;
    }

    /**
     * Returns the lead facilitator (Teacher) based on the leadFacilitatorId.
     * Returns null if facilitators list is empty or leadFacilitatorId is null/invalid.
     *
     * @return the lead facilitator Teacher object or null
     */
    public Teacher getLeadFacilitator() {
        if (facilitators == null || facilitators.isEmpty() || this.leadFacilitatorId == null) {
            return null;
        }

        return facilitators.stream()
            .filter(teacher -> teacher.getId().equals(this.leadFacilitatorId))
            .findFirst()
            .orElse(null);
    }
}
