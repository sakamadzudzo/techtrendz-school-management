package zw.co.techtrendz.school.management.api.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.techtrendz.school.management.api.enums.Status;

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

    private LocalDateTime datestamp;

    private Status status;

    public ClassroomYearDto(
            Long id,
            String name,
            String description,
            Integer calendarYear,
            Long homeroomTeacher
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.calendarYear = calendarYear;
        this.homeroomTeacher = homeroomTeacher;
    }

    public ClassroomYearDto(
            Long id,
            String name,
            String description,
            Integer calendarYear,
            Long homeroomTeacher,
            LocalDateTime datestamp,
            Status status
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.calendarYear = calendarYear;
        this.homeroomTeacher = homeroomTeacher;
        this.datestamp = datestamp;
        this.status = status;
    }
}