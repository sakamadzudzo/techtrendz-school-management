package zw.co.techtrendz.school.management.api.academic.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.techtrendz.school.management.api.academic.entity.Coursework;
import zw.co.techtrendz.school.management.api.common.enums.Status;
import zw.co.techtrendz.school.management.api.academic.enums.SubjectType;
import zw.co.techtrendz.school.management.api.user.view.TeacherDto;

import java.time.LocalDateTime;
import java.util.List;

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

    private Long subjectId;

    private List<Coursework> courseworks;
}