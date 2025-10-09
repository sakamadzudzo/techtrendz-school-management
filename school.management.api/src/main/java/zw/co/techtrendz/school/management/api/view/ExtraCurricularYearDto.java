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

    private Long extraCurricularId;

    private Long leadFacilitatorId;

    private TeacherDto leadFacilitator;
}
