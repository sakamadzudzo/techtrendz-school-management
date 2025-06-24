package zw.co.techtrendz.school.management.api.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DormDto {

    private Long id;

    private String name;

    private String description;

    private String address;

    private Integer calendarYear;

    private String linkedSchool;
}
