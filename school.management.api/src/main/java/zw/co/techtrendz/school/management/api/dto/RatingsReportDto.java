package zw.co.techtrendz.school.management.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO for ratings report data")
public class RatingsReportDto {
    @Schema(description = "Name of the student", example = "John Doe")
    private String studentName;

    @Schema(description = "Unique identifier for the student", example = "STU001")
    private String studentId;

    @Schema(description = "Name of the extra-curricular activity", example = "Basketball Team")
    private String extraCurricularActivityName;

    @Schema(description = "Rating achieved", example = "4.5")
    private BigDecimal rating;

    @Schema(description = "Percentage score", example = "90.0")
    private BigDecimal percentage;

    @Schema(description = "Minimum pass percentage", example = "60.0")
    private BigDecimal passPercentage;

    @Schema(description = "Name of the facilitator/teacher", example = "Jane Smith")
    private String facilitatorName;

    @Schema(description = "Calendar year of the rating", example = "2024")
    private Integer calendarYear;
}