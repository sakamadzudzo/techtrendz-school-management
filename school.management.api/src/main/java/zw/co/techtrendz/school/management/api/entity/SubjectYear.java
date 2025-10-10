package zw.co.techtrendz.school.management.api.entity;

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
@Schema(description = "Entity representing a subject for a specific academic year")
public class SubjectYear extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the subject-year", example = "1")
    private Long id;

    @ManyToOne
    @Schema(description = "Subject this year assignment belongs to")
    private Subject subject;

    @Schema(description = "Academic year for this subject assignment", example = "2024")
    private Integer calendarYear;

    @ManyToOne
    @Schema(description = "Teacher assigned to teach this subject")
    private Teacher teacher;

    @ManyToMany(mappedBy = "subjects")
    @Schema(description = "Classrooms where this subject is taught")
    private List<ClassroomYear> classrooms;

    public SubjectYear(long id) {
        this.id = id;
    }
}
