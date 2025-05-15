package zw.co.techtrendz.school.management.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Audited
public class Student extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Person person;

    private Integer startYear;

    private Integer endYear;

    @ManyToOne
    private ClassroomYear classroom;

    @ManyToMany
    private List<SubjectYear> subjects;

    @ManyToOne
    private Dorm dorm;

    @ManyToOne
    private School school;

    @ManyToMany
    private List<Student> students;
}
