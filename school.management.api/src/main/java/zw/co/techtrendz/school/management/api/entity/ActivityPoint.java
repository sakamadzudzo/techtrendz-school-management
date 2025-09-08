package zw.co.techtrendz.school.management.api.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import zw.co.techtrendz.school.management.api.enums.Grade;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Audited
public class ActivityPoint extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ExtraCurricularYear extraCurricularYear;

    @ManyToOne
    private Student student;

    private Long passPercentage;

    private Long percentage;

    public ActivityPoint(long id) {
        this.id = id;
    }

    public Grade getGrade() {
        if (percentage == null) {
            return Grade.U;
        }
        if (percentage >= 95) {
            return Grade.A_P;
        } else if (percentage >= 90) {
            return Grade.A;
        } else if (percentage >= 85) {
            return Grade.A_M;
        } else if (percentage >= 80) {
            return Grade.B_P;
        } else if (percentage >= 75) {
            return Grade.B;
        } else if (percentage >= 70) {
            return Grade.B_M;
        } else if (percentage >= 65) {
            return Grade.C_P;
        } else if (percentage >= 60) {
            return Grade.C;
        } else if (percentage >= 55) {
            return Grade.C_M;
        } else if (percentage >= 50) {
            return Grade.D_P;
        } else if (percentage >= 40) {
            return Grade.D;
        } else if (percentage >= 35) {
            return Grade.D_M;
        } else if (percentage >= 30) {
            return Grade.E;
        } else if (percentage >= 15) {
            return Grade.F;
        } else {
            return Grade.U;
        }
    }
}
