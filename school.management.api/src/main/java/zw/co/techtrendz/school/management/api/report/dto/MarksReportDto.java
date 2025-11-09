package zw.co.techtrendz.school.management.api.report.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO for marks report data")
public class MarksReportDto {
    @Schema(description = "Name of the student", example = "John Doe")
    private String studentName;

    @Schema(description = "Unique identifier for the student", example = "STU001")
    private String studentId;

    @Schema(description = "Name of the classroom", example = "Grade 10A")
    private String classroomName;

    @Schema(description = "Name of the subject", example = "Mathematics")
    private String subjectName;

    @Schema(description = "Title of the coursework", example = "Mid-term Exam")
    private String courseworkTitle;

    @Schema(description = "Mark obtained by the student", example = "85.5")
    private BigDecimal mark;

    @Schema(description = "Minimum pass mark", example = "50.0")
    private BigDecimal passMark;

    @Schema(description = "Total possible marks", example = "100.0")
    private BigDecimal total;

    @Schema(description = "Grade achieved", example = "A")
    private String grade;

    @Schema(description = "Calendar year of the marks", example = "2024")
    private Integer calendarYear;

    @Schema(description = "Name of the teacher", example = "Jane Smith")
    private String teacherName;
}