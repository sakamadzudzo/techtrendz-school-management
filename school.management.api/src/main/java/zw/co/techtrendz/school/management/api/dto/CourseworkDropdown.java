package zw.co.techtrendz.school.management.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.techtrendz.school.management.api.entity.Subject;
import zw.co.techtrendz.school.management.api.enums.CourseworkType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for coursework information in dropdown selections")
public class CourseworkDropdown {

    @Schema(description = "Unique identifier for the coursework", example = "1")
    private Long id;

    @Schema(description = "Type of coursework", example = "TEST")
    private CourseworkType type;

    @Schema(description = "Calendar year when the coursework occurs", example = "2024")
    private Integer calendarYear;

    @Schema(description = "Subject associated with the coursework")
    private Subject subject;
}
