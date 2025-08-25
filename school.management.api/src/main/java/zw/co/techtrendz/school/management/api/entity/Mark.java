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
public class Mark extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Work work;

    @ManyToOne
    private Student student;

    private Float total;

    private Float passMark;

    private Float mark;

    public Grade getGrade() {
        if (mark == null) {
            return Grade.U;
        }
        if (mark >= 95) {
            return Grade.A_P;
        } else if (mark >= 90) {
            return Grade.A;
        } else if (mark >= 85) {
            return Grade.A_M;
        } else if (mark >= 80) {
            return Grade.B_P;
        } else if (mark >= 75) {
            return Grade.B;
        } else if (mark >= 70) {
            return Grade.B_M;
        } else if (mark >= 65) {
            return Grade.C_P;
        } else if (mark >= 60) {
            return Grade.C;
        } else if (mark >= 55) {
            return Grade.C_M;
        } else if (mark >= 50) {
            return Grade.D_P;
        } else if (mark >= 40) {
            return Grade.D;
        } else if (mark >= 35) {
            return Grade.D_M;
        } else if (mark >= 30) {
            return Grade.E;
        } else if (mark >= 15) {
            return Grade.F;
        } else {
            return Grade.U;
        }
    }
}
