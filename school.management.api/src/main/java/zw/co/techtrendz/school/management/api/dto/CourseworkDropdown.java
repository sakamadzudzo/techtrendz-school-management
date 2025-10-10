package zw.co.techtrendz.school.management.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.techtrendz.school.management.api.entity.Subject;
import zw.co.techtrendz.school.management.api.enums.CourseworkType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseworkDropdown {

    private Long id;

    private CourseworkType type;

    private Integer calendarYear;

    private Subject subject;
}
