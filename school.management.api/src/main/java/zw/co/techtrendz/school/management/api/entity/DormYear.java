package zw.co.techtrendz.school.management.api.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Audited
@Schema(description = "Entity representing a dormitory for a specific academic year")
public class DormYear extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the dorm-year", example = "1")
    private Long id;

    @ManyToOne
    @Schema(description = "Dormitory this year assignment belongs to")
    private Dorm dorm;

    @Schema(description = "Academic year for this dormitory assignment", example = "2024")
    private Integer calendarYear;

    public DormYear(long id) {
        this.id = id;
    }
}
