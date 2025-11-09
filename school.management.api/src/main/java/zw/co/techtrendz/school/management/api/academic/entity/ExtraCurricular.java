package zw.co.techtrendz.school.management.api.academic.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.envers.Audited;
import zw.co.techtrendz.school.management.api.common.entity.Base;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Audited
@Schema(description = "Entity representing extra-curricular activities")
public class ExtraCurricular extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the extra-curricular activity", example = "1")
    private Long id;

    @NotEmpty(message = "Name cannot be empty or null")
    @Column(nullable = false)
    @Schema(description = "Name of the extra-curricular activity", example = "Basketball Team", required = true)
    private String name;

    @Size(max = 255, message = "Description cannot exceed 255 characters")
    @Schema(description = "Description of the extra-curricular activity", example = "Competitive basketball team for students")
    private String description;

    public ExtraCurricular(long id) {
        this.id = id;
    }
}
