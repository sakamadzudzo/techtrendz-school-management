package zw.co.techtrendz.school.management.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgressReportDto {
    private String entityName; // student, teacher, subject, classroom, etc.
    private String entityId;
    private Integer year;
    private BigDecimal currentValue;
    private BigDecimal previousValue;
    private BigDecimal percentageChange;
    private String trend; // "UP", "DOWN", "STABLE"
    private List<YearlyData> yearlyData;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class YearlyData {
        private Integer year;
        private BigDecimal value;
        private String label;
    }
}