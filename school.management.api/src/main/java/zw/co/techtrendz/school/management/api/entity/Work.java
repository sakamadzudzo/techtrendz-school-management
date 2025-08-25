package zw.co.techtrendz.school.management.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.envers.Audited;
import zw.co.techtrendz.school.management.api.enums.WorkType;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Audited
public class Work extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Work type is required")
    private WorkType type;

    @ManyToOne
    @NotNull(message = "Subject is required")
    private SubjectYear subject;
}
