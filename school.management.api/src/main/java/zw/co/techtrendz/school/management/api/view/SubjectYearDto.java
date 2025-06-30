package zw.co.techtrendz.school.management.api.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;
import zw.co.techtrendz.school.management.api.enums.Status;
import zw.co.techtrendz.school.management.api.enums.SubjectType;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectYearDto {

    private Long id;

    private Integer calendarYear;

    private String name;

    private String description;

    private String code;

    private SubjectType type;

    private String syllabus;

    private String curriculum;

    private Integer level;

    private String category;

    private Long teacherId;

    private TeacherDto teacher;

    private Long classroomId;

    private LocalDateTime datestamp;

    private Status status;
}