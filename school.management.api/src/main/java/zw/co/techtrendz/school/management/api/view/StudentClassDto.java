package zw.co.techtrendz.school.management.api.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentClassDto {

    private Long id;

    private String name;

    private String description;

    private Integer calendarYear;

    private List<SubjectYearDto> subjects;

    public StudentClassDto(
            Long id,
            String name,
            String description,
            Integer calendarYear
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.calendarYear = calendarYear;
    }
}
