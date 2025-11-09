package zw.co.techtrendz.school.management.api.academic.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.envers.Audited;
import zw.co.techtrendz.school.management.api.common.entity.Base;
import zw.co.techtrendz.school.management.api.academic.enums.SubjectType;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Audited
@Schema(description = "Entity representing an academic subject")
public class Subject extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the subject", example = "1")
    private Long id;

    @NotEmpty(message = "Name cannot be empty or null")
    @Column(nullable = false)
    @Schema(description = "Name of the subject", example = "Mathematics", required = true)
    private String name;

    @Size(max = 255, message = "Description cannot exceed 255 characters")
    @Schema(description = "Description of the subject", example = "Advanced mathematics covering algebra and calculus")
    private String description;

    @Schema(description = "Subject code for identification", example = "MATH101")
    private String code;

    @Schema(description = "Syllabus information", example = "Cambridge IGCSE Mathematics")
    private String syllabus;

    @Schema(description = "Curriculum details", example = "National Curriculum")
    private String curriculum;

    @Schema(description = "Grade level for the subject", example = "10")
    private Integer level;

    @Schema(description = "Type of subject", example = "CORE")
    private SubjectType type;

    @Schema(description = "Subject category", example = "STEM")
    private String category;

    public Subject(long id) {
        this.id = id;
    }

    public Subject(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
