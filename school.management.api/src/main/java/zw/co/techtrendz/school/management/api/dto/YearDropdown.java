package zw.co.techtrendz.school.management.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for entities with year information in dropdown selections")
public class YearDropdown {

    @Schema(description = "Unique identifier for the entity", example = "1")
    private Long id;

    @Schema(description = "Display name for the entity", example = "Mathematics Grade 10")
    private String name;

    @Schema(description = "Calendar year associated with the entity", example = "2024")
    private Integer calendarYear;
}
