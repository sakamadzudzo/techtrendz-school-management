package zw.co.techtrendz.school.management.api.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import zw.co.techtrendz.school.management.api.enums.Grade;
import zw.co.techtrendz.school.management.api.enums.Status;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Audited
@Schema(description = "Entity representing student marks for coursework")
public class Mark extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the mark", example = "1")
    private Long id;

    @ManyToOne
    @Schema(description = "Coursework this mark belongs to")
    private Coursework coursework;

    @ManyToOne
    @Schema(description = "Student this mark belongs to")
    private Student student;

    @Schema(description = "Numerical mark obtained", example = "85.5")
    private Float mark;

    public Mark(Long id, Coursework coursework, Student student, Float mark, Status status, LocalDateTime datestamp) {
        this(id, coursework, student, mark);
        this.setStatus(status);
        this.setDatestamp(datestamp);
    }

    public Mark(long id) {
        this.id = id;
    }

    public String getSubjectName() {
        String name = "";
        if (this.coursework != null) {
            name = this.coursework.getSubjectName();
        }

        return name;
    }

    public Grade getGrade() {
        if (mark == null || coursework == null || coursework.getTotal() == null) {
            return Grade.U;
        }
        if (mark / coursework.getTotal() >= 95) {
            return Grade.A_P;
        } else if (mark / coursework.getTotal() >= 90) {
            return Grade.A;
        } else if (mark / coursework.getTotal() >= 85) {
            return Grade.A_M;
        } else if (mark / coursework.getTotal() >= 80) {
            return Grade.B_P;
        } else if (mark / coursework.getTotal() >= 75) {
            return Grade.B;
        } else if (mark / coursework.getTotal() >= 70) {
            return Grade.B_M;
        } else if (mark / coursework.getTotal() >= 65) {
            return Grade.C_P;
        } else if (mark / coursework.getTotal() >= 60) {
            return Grade.C;
        } else if (mark / coursework.getTotal() >= 55) {
            return Grade.C_M;
        } else if (mark / coursework.getTotal() >= 50) {
            return Grade.D_P;
        } else if (mark / coursework.getTotal() >= 40) {
            return Grade.D;
        } else if (mark / coursework.getTotal() >= 35) {
            return Grade.D_M;
        } else if (mark / coursework.getTotal() >= 30) {
            return Grade.E;
        } else if (mark / coursework.getTotal() >= 15) {
            return Grade.F;
        } else {
            return Grade.U;
        }
    }
}
