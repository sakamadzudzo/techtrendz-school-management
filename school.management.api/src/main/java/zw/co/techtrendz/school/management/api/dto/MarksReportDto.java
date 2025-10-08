package zw.co.techtrendz.school.management.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarksReportDto {
    private String studentName;
    private String studentId;
    private String classroomName;
    private String subjectName;
    private String courseworkTitle;
    private BigDecimal mark;
    private BigDecimal passMark;
    private BigDecimal total;
    private String grade;
    private Integer calendarYear;
    private String teacherName;
}