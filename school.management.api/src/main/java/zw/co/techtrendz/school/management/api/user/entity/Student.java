package zw.co.techtrendz.school.management.api.user.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import zw.co.techtrendz.school.management.api.academic.entity.ClassroomYear;
import zw.co.techtrendz.school.management.api.academic.entity.DormYear;
import zw.co.techtrendz.school.management.api.academic.entity.ExtraCurricularYear;
import zw.co.techtrendz.school.management.api.academic.entity.School;
import zw.co.techtrendz.school.management.api.common.entity.Base;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Audited
@Schema(description = "Entity representing a student enrolled in the school")
public class Student extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the student", example = "1")
    private Long id;

    @ManyToOne
    @Schema(description = "Person details associated with this student")
    private Person person;

    @Schema(description = "Year when the student started at the school", example = "2020")
    private Integer startYear;

    @Schema(description = "Year when the student is expected to graduate", example = "2024")
    private Integer endYear;

    @ManyToOne
    @Schema(description = "Current classroom-year assignment for the student")
    private ClassroomYear classroom;

    @ManyToMany(mappedBy = "students")
    @Schema(description = "Extra-curricular activities the student is enrolled in")
    private List<ExtraCurricularYear> extraCurricularYears;

    @ManyToOne
    @Schema(description = "Dormitory assignment for the student")
    private DormYear dorm;

    @ManyToOne
    @Schema(description = "School the student is enrolled in")
    private School school;

    public Student(long id) {
        this.id = id;
    }

    public Student(Long id, Person person) {
        this.id = id;
        this.person = person;
    }

    public Student(Long id, Person person, ClassroomYear classroom) {
        this.id = id;
        this.person = person;
        this.classroom = classroom;
    }
}
