package zw.co.techtrendz.school.management.api.academic.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.techtrendz.school.management.api.common.enums.Status;
import zw.co.techtrendz.school.management.api.user.view.TeacherDto;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomYearDto {

    private Long id;

    private String name;

    private String description;

    private Integer calendarYear;

    private Long homeroomTeacher;

    private List<SubjectYearDto> subjects;

    private TeacherDto teacher;

    private LocalDateTime datestamp;

    private Status status;

    private Long classroomId;
}