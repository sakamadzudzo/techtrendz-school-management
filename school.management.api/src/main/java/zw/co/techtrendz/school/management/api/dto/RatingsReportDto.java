package zw.co.techtrendz.school.management.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingsReportDto {
    private String studentName;
    private String studentId;
    private String extraCurricularActivityName;
    private BigDecimal rating;
    private BigDecimal percentage;
    private BigDecimal passPercentage;
    private String facilitatorName;
    private Integer calendarYear;
}