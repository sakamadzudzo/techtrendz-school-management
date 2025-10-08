package zw.co.techtrendz.school.management.api.service.impl;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.dto.MarksReportDto;
import zw.co.techtrendz.school.management.api.dto.ProgressReportDto;
import zw.co.techtrendz.school.management.api.dto.RatingsReportDto;
import zw.co.techtrendz.school.management.api.service.PdfReportService;
import zw.co.techtrendz.school.management.api.utility.JasperReportCompiler;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PdfReportServiceImpl implements PdfReportService {

    @Autowired
    private JasperReportCompiler jasperReportCompiler;

    // Marks Reports
    public byte[] generateMarksByStudentClassroomYearReport(List<MarksReportDto> data, Integer year, Long classroomId, Long studentId) {
        try {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("year", year);
            parameters.put("classroomId", classroomId);
            parameters.put("studentId", studentId);

            return generateReport("marks_by_student_classroom_year", parameters, data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate marks by student classroom year report", e);
        }
    }

    public byte[] generateMarksByStudentSubjectYearReport(List<MarksReportDto> data, Integer year, Long subjectId, Long studentId) {
        try {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("year", year);
            parameters.put("subjectId", subjectId);
            parameters.put("studentId", studentId);

            return generateReport("marks_by_student_subject_year", parameters, data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate marks by student subject year report", e);
        }
    }

    public byte[] generateMarksByStudentYearReport(List<MarksReportDto> data, Integer year, Long studentId) {
        try {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("year", year);
            parameters.put("studentId", studentId);

            return generateReport("marks_by_student_year", parameters, data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate marks by student year report", e);
        }
    }

    public byte[] generateMarksByStudentReport(List<MarksReportDto> data, Long studentId) {
        try {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("studentId", studentId);

            return generateReport("marks_by_student", parameters, data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate marks by student report", e);
        }
    }

    public byte[] generateMarksByYearReport(List<MarksReportDto> data, Integer year) {
        try {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("year", year);

            return generateReport("marks_by_year", parameters, data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate marks by year report", e);
        }
    }

    public byte[] generateMarksByClassroomYearReport(List<MarksReportDto> data, Integer year, Long classroomId) {
        try {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("year", year);
            parameters.put("classroomId", classroomId);

            return generateReport("marks_by_classroom_year", parameters, data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate marks by classroom year report", e);
        }
    }

    public byte[] generateMarksBySubjectYearReport(List<MarksReportDto> data, Integer year, Long subjectId) {
        try {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("year", year);
            parameters.put("subjectId", subjectId);

            return generateReport("marks_by_subject_year", parameters, data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate marks by subject year report", e);
        }
    }

    // Ratings Reports
    public byte[] generateRatingsByStudentActivityYearReport(List<RatingsReportDto> data, Integer year, Long activityId, Long studentId) {
        try {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("year", year);
            parameters.put("activityId", activityId);
            parameters.put("studentId", studentId);

            return generateReport("ratings_by_student_activity_year", parameters, data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate ratings by student activity year report", e);
        }
    }

    public byte[] generateRatingsByStudentYearReport(List<RatingsReportDto> data, Integer year, Long studentId) {
        try {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("year", year);
            parameters.put("studentId", studentId);

            return generateReport("ratings_by_student_year", parameters, data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate ratings by student year report", e);
        }
    }

    public byte[] generateRatingsByActivityYearReport(List<RatingsReportDto> data, Integer year, Long activityId) {
        try {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("year", year);
            parameters.put("activityId", activityId);

            return generateReport("ratings_by_activity_year", parameters, data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate ratings by activity year report", e);
        }
    }

    public byte[] generateRatingsByActivityReport(List<RatingsReportDto> data, Long activityId) {
        try {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("activityId", activityId);

            return generateReport("ratings_by_activity", parameters, data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate ratings by activity report", e);
        }
    }

    // Teacher Reports
    public byte[] generateMarksByTeacherYearReport(List<MarksReportDto> data, Integer year, Long teacherId) {
        try {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("year", year);
            parameters.put("teacherId", teacherId);

            return generateReport("marks_by_teacher_year", parameters, data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate marks by teacher year report", e);
        }
    }

    public byte[] generateRatingsByTeacherYearReport(List<RatingsReportDto> data, Integer year, Long teacherId) {
        try {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("year", year);
            parameters.put("teacherId", teacherId);

            return generateReport("ratings_by_teacher_year", parameters, data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate ratings by teacher year report", e);
        }
    }

    // Progress Reports
    public byte[] generateProgressReportByStudent(List<ProgressReportDto> data, Long studentId, String entityType) {
        try {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("studentId", studentId);
            parameters.put("entityType", entityType);

            return generateReport("progress_report_by_student", parameters, data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate progress report by student", e);
        }
    }

    public byte[] generateProgressReportByTeacher(List<ProgressReportDto> data, Long teacherId, String entityType) {
        try {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("teacherId", teacherId);
            parameters.put("entityType", entityType);

            return generateReport("progress_report_by_teacher", parameters, data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate progress report by teacher", e);
        }
    }

    public byte[] generateProgressReportBySubject(List<ProgressReportDto> data, Long subjectId, String entityType) {
        try {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("subjectId", subjectId);
            parameters.put("entityType", entityType);

            return generateReport("progress_report_by_subject", parameters, data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate progress report by subject", e);
        }
    }

    public byte[] generateProgressReportByClassroom(List<ProgressReportDto> data, Long classroomId, String entityType) {
        try {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("classroomId", classroomId);
            parameters.put("entityType", entityType);

            return generateReport("progress_report_by_classroom", parameters, data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate progress report by classroom", e);
        }
    }

    public byte[] generateProgressReportByActivity(List<ProgressReportDto> data, Long activityId, String entityType) {
        try {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("activityId", activityId);
            parameters.put("entityType", entityType);

            return generateReport("progress_report_by_activity", parameters, data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate progress report by activity", e);
        }
    }

    public byte[] generateProgressReportByFacilitator(List<ProgressReportDto> data, Long facilitatorId, String entityType) {
        try {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("facilitatorId", facilitatorId);
            parameters.put("entityType", entityType);

            return generateReport("progress_report_by_facilitator", parameters, data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate progress report by facilitator", e);
        }
    }

    // Generic report generation method
    private byte[] generateReport(String reportName, Map<String, Object> parameters, List<?> data) throws JRException {
        // Compile report if needed
        jasperReportCompiler.compileReport(reportName + ".jrxml", reportName + ".jasper");

        // Load compiled report
        InputStream reportStream = getClass().getClassLoader()
                .getResourceAsStream("reports/compiled/" + reportName + ".jasper");

        if (reportStream == null) {
            throw new RuntimeException("Compiled report not found: " + reportName);
        }

        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        // Create data source
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);

        // Fill report
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // Export to PDF
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        return outputStream.toByteArray();
    }
}
