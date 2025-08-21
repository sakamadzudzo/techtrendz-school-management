package zw.co.techtrendz.school.management.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YearDropdown {

    private Long id;

    private String name;

    private Integer calendarYear;
}
