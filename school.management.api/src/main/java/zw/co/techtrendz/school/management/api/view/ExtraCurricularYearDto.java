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
public class ExtraCurricularYearDto {

    private Long id;

    private String name;

    private String description;

    private Integer calendarYear;

    private LocalDateTime datestamp;

    private Status status;

    private List<TeacherDto> facilitators;

    private List<StudentDto> students;

    public ExtraCurricularYearDto(
            Long id,
            String name,
            String description,
            Integer calendarYear,
            LocalDateTime datestamp,
            Status status
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.calendarYear = calendarYear;
        this.datestamp = datestamp;
        this.status = status;
    }
}
