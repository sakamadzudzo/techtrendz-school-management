package zw.co.techtrendz.school.management.api.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.envers.Audited;
import zw.co.techtrendz.school.management.api.enums.CourseworkType;
import zw.co.techtrendz.school.management.api.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Audited
@Schema(description = "Entity representing academic coursework and assessments")
public class Coursework extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the coursework", example = "1")
    private Long id;

    @Schema(description = "Title of the coursework", example = "Mid-term Mathematics Exam")
    private String title;

    @NotNull(message = "Coursework type is required")
    @Schema(description = "Type of coursework", example = "EXAM", required = true)
    private CourseworkType type;

    @ManyToOne
    @NotNull(message = "Subject is required")
    @Schema(description = "Subject-year this coursework belongs to", required = true)
    private SubjectYear subject;

    @NotNull(message = "Coursework total is required")
    @Schema(description = "Total marks for the coursework", example = "100.0", required = true)
    private Float total;

    @NotNull(message = "Coursework pass mark is required")
    @Schema(description = "Minimum pass mark for the coursework", example = "50.0", required = true)
    private Float passMark;

    @Transient
    @Schema(description = "List of marks associated with this coursework")
    private List<Mark> marks;

    {
        if (this.title == null || this.title.isBlank()) {
            if (this.type != null) {
                this.title = this.type.name();
            }
        }
    }

    public Coursework(long id) {
        this.id = id;
    }

    public void setType(CourseworkType type) {
        this.type = type;
        if (this.title == null || this.title.isBlank()) {
            this.title = type.name();
        }
    }

    public Coursework(Long id, String title, CourseworkType type, SubjectYear subject, Float total, Float passMark) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.subject = subject;
        this.total = total;
        this.passMark = passMark;
    }

    public Coursework(Long id, String title, CourseworkType type, SubjectYear subject, Float total, Float passMark, Status status, LocalDateTime datestamp) {
        this(id,title,type,subject,total,passMark);
        this.setStatus(status);
        this.setDatestamp(datestamp);
    }

    public String getSubjectName() {
        String name = "";
        if (this.subject != null) {
            if (this.subject.getSubject() != null) {
                name = this.subject.getSubject().getName();
            }
        }

        return name;
    }
}
