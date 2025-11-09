package zw.co.techtrendz.school.management.api.academic.entity;

import com.querydsl.core.annotations.QueryInit;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Range;
import zw.co.techtrendz.school.management.api.common.entity.Base;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Audited
@Schema(description = "Entity representing individual ratings for activity points")
public class Rating extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the rating", example = "1")
    private Long id;

    @ManyToOne
    @QueryInit("*")
    @Schema(description = "Activity point this rating contributes to")
    private ActivityPoint activityPoint;

    @NotNull(message = "Rating is mandatory")
    @Range(min = 0, max = 100, message = "Rating must be between 0 and 100")
    @Schema(description = "Numerical rating value", example = "85.5", minimum = "0", maximum = "100")
    private Float rating;

    public Rating(long id) {
        this.id = id;
    }
}
