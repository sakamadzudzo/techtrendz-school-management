package zw.co.techtrendz.school.management.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.envers.Audited;
import zw.co.techtrendz.school.management.api.enums.SubjectType;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Audited
public class Subject extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name cannot be empty or null")
    @Column(nullable = false)
    private String name;

    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;

    private String code;

    private String syllabus;

    private String curriculum;

    private Integer level;

    private SubjectType type;

    private String category;

    public Subject(long id) {
        this.id = id;
    }
}
