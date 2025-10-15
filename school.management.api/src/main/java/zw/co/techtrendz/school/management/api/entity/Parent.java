package zw.co.techtrendz.school.management.api.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Audited
@Schema(description = "Entity representing parent and which students they relate to")
public class Parent extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for parent", example = "1")
    private Long id;

    @ManyToOne
    @Schema(description = "Person details associated with this parent")
    private Person person;

    @ManyToMany
    @Schema(description = "Students related to this parent record")
    private List<Student> students = new ArrayList<>();

    public Parent(Long id) {
        this.id = id;
    }

    public Parent(Long id, Person person) {
        this.id = id;
        this.person = person;
    }
}
