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
@Schema(description = "Entity representing a student dormitory")
public class Dorm extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the dormitory", example = "1")
    private Long id;

    @NotEmpty(message = "Name cannot be empty or null")
    @Column(nullable = false)
    @Schema(description = "Name of the dormitory", example = "Lincoln House", required = true)
    private String name;

    @Size(max = 255, message = "Description cannot exceed 255 characters")
    @Schema(description = "Description of the dormitory", example = "Boys dormitory for senior students")
    private String description;

    @Size(max = 255, message = "Address cannot exceed 255 characters")
    @Column(nullable = true)
    @Schema(description = "Physical address of the dormitory", example = "123 Campus Drive")
    private String address;

    @ManyToOne
    @Schema(description = "School this dormitory belongs to")
    private School school;

    public Dorm(long id) {
        this.id = id;
    }
}
