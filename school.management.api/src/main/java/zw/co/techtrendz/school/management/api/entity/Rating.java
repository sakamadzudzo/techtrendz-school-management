package zw.co.techtrendz.school.management.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Range;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Audited
public class Rating extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ActivityPoint activityPoint;

    @NotNull(message = "Rating is mandatory")
    @Range(min = 0, max = 100, message = "Rating must be between 0 and 100")
    private Float rating;

    public Rating(long id) {
        this.id = id;
    }
}
