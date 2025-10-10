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
public class DormYearDto {

    private Long id;

    private String name;

    private String description;

    private String address;

    private Integer calendarYear;

    private String linkedSchool;

    private List<StudentDto> students;

    private Long dormId;

    private Status status;

    private LocalDateTime datestamp;
}
