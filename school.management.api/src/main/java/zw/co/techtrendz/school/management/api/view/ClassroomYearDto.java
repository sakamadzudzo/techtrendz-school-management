package zw.co.techtrendz.school.management.api.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomYearDto {

    private Integer calendarYear;

    private String name;

    private String description;

    private Long homeroomTeacher;
}