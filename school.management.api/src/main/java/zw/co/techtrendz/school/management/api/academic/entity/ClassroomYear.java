package zw.co.techtrendz.school.management.api.academic.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import zw.co.techtrendz.school.management.api.common.entity.Base;
import zw.co.techtrendz.school.management.api.user.entity.Teacher;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Audited
@Schema(description = "Entity representing a classroom for a specific academic year")
public class ClassroomYear extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the classroom-year", example = "1")
    private Long id;

    @ManyToOne
    @Schema(description = "Classroom this year assignment belongs to")
    private Classroom classroom;

    @Schema(description = "Academic year for this classroom assignment", example = "2024")
    private Integer calendarYear;

    @ManyToMany
    @Schema(description = "Subjects taught in this classroom during this year")
    private List<SubjectYear> subjects;

    @ManyToOne
    @Schema(description = "Homeroom teacher for this classroom")
    private Teacher homeroomTeacher;

    public ClassroomYear(long id) {
        this.id = id;
    }

    public ClassroomYear(long id, Classroom classroom) {
        this.id = id;
        this.classroom = classroom;
    }
}
