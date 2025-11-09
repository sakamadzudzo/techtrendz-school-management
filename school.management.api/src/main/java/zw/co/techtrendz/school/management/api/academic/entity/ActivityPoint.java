package zw.co.techtrendz.school.management.api.academic.entity;

import com.querydsl.core.annotations.QueryInit;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import zw.co.techtrendz.school.management.api.common.entity.Base;
import zw.co.techtrendz.school.management.api.user.entity.Student;
import zw.co.techtrendz.school.management.api.academic.enums.Grade;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Audited
@Schema(description = "Entity representing student activity points and performance")
public class ActivityPoint extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the activity point", example = "1")
    private Long id;

    @ManyToOne
    @QueryInit("*")
    @Schema(description = "Extra-curricular activity year this point belongs to")
    private ExtraCurricularYear extraCurricularYear;

    @ManyToOne
    @QueryInit("*")
    @Schema(description = "Student this activity point belongs to")
    private Student student;

    @Schema(description = "Additional remarks or comments", example = "Excellent performance")
    private String remarks;

    @OneToMany
    @QueryInit("*")
    @Schema(description = "List of ratings that contribute to this activity point")
    private List<Rating> ratings;

    public Float getOveralRating() {
        if (this.ratings != null && !this.ratings.isEmpty()) {
            Double overalRating = this.ratings
                    .parallelStream()
                    .mapToDouble(Rating::getRating)
                    .average()
                    .orElse(0.0);
            return overalRating.floatValue();
        } else {
            return 0.0F;
        }
    }

    public ActivityPoint(long id) {
        this.id = id;
    }

    public Grade getGrade() {
        Float rating = this.getOveralRating();

        if (rating.compareTo(95f) >= 0) return Grade.A_P;
        else if (rating.compareTo(90f) >= 0) return Grade.A;
        else if (rating.compareTo(85f) >= 0) return Grade.A_M;
        else if (rating.compareTo(80f) >= 0) return Grade.B_P;
        else if (rating.compareTo(75f) >= 0) return Grade.B;
        else if (rating.compareTo(70f) >= 0) return Grade.B_M;
        else if (rating.compareTo(65f) >= 0) return Grade.C_P;
        else if (rating.compareTo(60f) >= 0) return Grade.C;
        else if (rating.compareTo(55f) >= 0) return Grade.C_M;
        else if (rating.compareTo(50f) >= 0) return Grade.D_P;
        else if (rating.compareTo(40f) >= 0) return Grade.D;
        else if (rating.compareTo(35f) >= 0) return Grade.D_M;
        else if (rating.compareTo(30f) >= 0) return Grade.E;
        else if (rating.compareTo(15f) >= 0) return Grade.F;
        else return Grade.U;
    }

}
