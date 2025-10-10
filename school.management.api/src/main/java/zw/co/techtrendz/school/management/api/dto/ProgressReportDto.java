package zw.co.techtrendz.school.management.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO for progress report data with trend analysis")
public class ProgressReportDto {
    @Schema(description = "Name of the entity being reported on", example = "Mathematics")
    private String entityName; // student, teacher, subject, classroom, etc.

    @Schema(description = "Unique identifier for the entity", example = "SUBJ001")
    private String entityId;

    @Schema(description = "Current year for the report", example = "2024")
    private Integer year;

    @Schema(description = "Current value/metric", example = "85.5")
    private BigDecimal currentValue;

    @Schema(description = "Previous period value/metric", example = "82.0")
    private BigDecimal previousValue;

    @Schema(description = "Percentage change from previous period", example = "4.27")
    private BigDecimal percentageChange;

    @Schema(description = "Trend direction", example = "UP", allowableValues = {"UP", "DOWN", "STABLE"})
    private String trend; // "UP", "DOWN", "STABLE"

    @Schema(description = "Historical data by year")
    private List<YearlyData> yearlyData;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Yearly data point for progress tracking")
    public static class YearlyData {
        @Schema(description = "Year for this data point", example = "2024")
        private Integer year;

        @Schema(description = "Value for this year", example = "85.5")
        private BigDecimal value;

        @Schema(description = "Label for this data point", example = "Mathematics Performance")
        private String label;
    }
}