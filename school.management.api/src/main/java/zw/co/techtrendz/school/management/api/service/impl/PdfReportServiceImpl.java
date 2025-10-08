package zw.co.techtrendz.school.management.api.service.impl;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import zw.co.techtrendz.school.management.api.dto.MarksReportDto;
import zw.co.techtrendz.school.management.api.dto.ProgressReportDto;
import zw.co.techtrendz.school.management.api.dto.RatingsReportDto;
import zw.co.techtrendz.school.management.api.entity.ActivityPoint;
import zw.co.techtrendz.school.management.api.entity.Mark;
import zw.co.techtrendz.school.management.api.entity.Rating;
import zw.co.techtrendz.school.management.api.service.ActivityPointService;
import zw.co.techtrendz.school.management.api.service.MarkService;
import zw.co.techtrendz.school.management.api.service.PdfReportService;
import zw.co.techtrendz.school.management.api.service.RatingService;
import zw.co.techtrendz.school.management.api.service.ReportDataAggregationService;
import zw.co.techtrendz.school.management.api.utility.JasperReportCompiler;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class PdfReportServiceImpl implements PdfReportService {

    @Autowired
    private JasperReportCompiler jasperReportCompiler;

    @Autowired
    private MarkService markService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private ActivityPointService activityPointService;

    @Autowired
    private ReportDataAggregationService reportDataAggregationService;

    // Marks Reports
    public byte[] generateMarksByStudentClassroomYearReport(Integer year, Long classroomId, Long studentId) {
        try {
            // Validate required parameters
            validateYearParameter(year);
            validateEntityId(classroomId, "Classroom");
            validateEntityId(studentId, "Student");

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("year", year);
            parameters.put("classroomId", classroomId);
            parameters.put("studentId", studentId);

            // Retrieve data from database
            List<Mark> marks = markService.getAllByStudentIdByClassroomIdByYear(studentId, classroomId, year);
            List<MarksReportDto> data = reportDataAggregationService.aggregateMarksDataByStudentClassroomYear(marks, studentId, classroomId, year);

            return generateReport("marks_by_student_classroom_year", parameters, data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate marks by student classroom year report", e);
        }
    }

    public byte[] generateMarksByStudentSubjectYearReport(Integer year, Long subjectId, Long studentId) {
        try {
            // Validate required parameters
            validateYearParameter(year);
            validateEntityId(subjectId, "Subject");
            validateEntityId(studentId, "Student");

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("year", year);
            parameters.put("subjectId", subjectId);
            parameters.put("studentId", studentId);

            // Retrieve data from database
            List<Mark> marks = markService.getAllByStudentIdBySubjectIdByYear(studentId, subjectId, year);
            List<MarksReportDto> data = reportDataAggregationService.aggregateMarksDataByStudentSubjectYear(marks, studentId, subjectId, year);

            return generateReport("marks_by_student_subject_year", parameters, data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate marks by student subject year report", e);
        }
    }

    public byte[] generateMarksByStudentYearReport(Integer year, Long studentId) {
        try {
            // Validate required parameters
            validateYearParameter(year);
            validateEntityId(studentId, "Student");

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("year", year);
            parameters.put("studentId", studentId);

            // Retrieve data from database
            List<Mark> marks = markService.getAllByStudentIdByYear(studentId, year);
            List<MarksReportDto> data = reportDataAggregationService.aggregateMarksDataByStudentYear(marks, studentId, year);

            return generateReport("marks_by_student_year", parameters, data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate marks by student year report", e);
        }
    }

    public byte[] generateMarksByStudentReport(Long studentId) {
        try {
            // Validate required parameters
            validateEntityId(studentId, "Student");

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("studentId", studentId);

            // Retrieve data from database
            List<Mark> marks = markService.getAllByStudentId(studentId);
            List<MarksReportDto> data = reportDataAggregationService.aggregateMarksData(marks);

            return generateReport("marks_by_student", parameters, data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate marks by student report", e);
        }
    }

    public byte[] generateMarksByYearReport(Integer year) {
        try {
            // Validate required parameters
            validateYearParameter(year);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("year", year);

            // Retrieve data from database
            List<Mark> marks = markService.getAllByYear(year);
            List<MarksReportDto> data = reportDataAggregationService.aggregateMarksDataByYear(marks, year);

            return generateReport("marks_by_year", parameters, data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate marks by year report", e);
        }
    }

    // Async versions for large reports
    @Async
    public CompletableFuture<byte[]> generateMarksByYearReportAsync(Integer year) {
        try {
            validateYearParameter(year);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("year", year);

            // Retrieve data from database
            List<Mark> marks = markService.getAllByYear(year);
            List<MarksReportDto> data = reportDataAggregationService.aggregateMarksDataByYear(marks, year);

            // Check if data is large enough to warrant async processing
            if (data.size() > 100) {
                byte[] report = generateReport("marks_by_year", parameters, data);
                return CompletableFuture.completedFuture(report);
            } else {
                // For smaller datasets, generate synchronously for better performance
                byte[] report = generateReport("marks_by_year", parameters, data);
                return CompletableFuture.completedFuture(report);
            }
        } catch (Exception e) {
            CompletableFuture<byte[]> future = new CompletableFuture<>();
            future.completeExceptionally(new RuntimeException("Failed to generate async marks by year report", e));
            return future;
        }
    }

    @Async
    public CompletableFuture<byte[]> generateMarksByClassroomYearReportAsync(Integer year, Long classroomId) {
        try {
            validateYearParameter(year);
            validateEntityId(classroomId, "Classroom");

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("year", year);
            parameters.put("classroomId", classroomId);

            // Retrieve data from database
            List<Mark> marks = markService.getAllByClassroomIdByYear(classroomId, year);
            List<MarksReportDto> data = reportDataAggregationService.aggregateMarksDataByClassroomYear(marks, classroomId, year);

            // Check if data is large enough to warrant async processing
            if (data.size() > 50) {
                byte[] report = generateReport("marks_by_classroom_year", parameters, data);
                return CompletableFuture.completedFuture(report);
            } else {
                byte[] report = generateReport("marks_by_classroom_year", parameters, data);
                return CompletableFuture.completedFuture(report);
            }
        } catch (Exception e) {
            CompletableFuture<byte[]> future = new CompletableFuture<>();
            future.completeExceptionally(new RuntimeException("Failed to generate async marks by classroom year report", e));
            return future;
        }
    }

    public byte[] generateMarksByClassroomYearReport(Integer year, Long classroomId) {
        try {
            // Validate required parameters
            validateYearParameter(year);
            validateEntityId(classroomId, "Classroom");

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("year", year);
            parameters.put("classroomId", classroomId);

            // Retrieve data from database
            List<Mark> marks = markService.getAllByClassroomIdByYear(classroomId, year);
            List<MarksReportDto> data = reportDataAggregationService.aggregateMarksDataByClassroomYear(marks, classroomId, year);

            return generateReport("marks_by_classroom_year", parameters, data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate marks by classroom year report", e);
        }
    }

    public byte[] generateMarksBySubjectYearReport(Integer year, Long subjectId) {
        try {
            // Validate required parameters
            validateYearParameter(year);
            validateEntityId(subjectId, "Subject");

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("year", year);
            parameters.put("subjectId", subjectId);

            // Retrieve data from database
            List<Mark> marks = markService.getAllBySubjectIdByYear(subjectId, year);
            List<MarksReportDto> data = reportDataAggregationService.aggregateMarksDataBySubjectYear(marks, subjectId, year);

            return generateReport("marks_by_subject_year", parameters, data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate marks by subject year report", e);
        }
    }

    // Ratings Reports
    public byte[] generateRatingsByStudentActivityYearReport(Integer year, Long activityId, Long studentId) {
        try {
            // Validate required parameters
            validateYearParameter(year);
            validateEntityId(activityId, "Activity");
            validateEntityId(studentId, "Student");

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("year", year);
            parameters.put("activityId", activityId);
            parameters.put("studentId", studentId);

            // Retrieve data from database
            List<Rating> ratings = ratingService.getAllByStudentIdByActivityIdByYear(studentId, activityId, year);
            List<RatingsReportDto> data = reportDataAggregationService.aggregateRatingsDataByStudentActivityYear(ratings, studentId, activityId, year);

            return generateReport("ratings_by_student_activity_year", parameters, data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate ratings by student activity year report", e);
        }
    }

    public byte[] generateRatingsByStudentYearReport(Integer year, Long studentId) {
        try {
            // Validate required parameters
            validateYearParameter(year);
            validateEntityId(studentId, "Student");

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("year", year);
            parameters.put("studentId", studentId);

            // Retrieve data from database
            List<Rating> ratings = ratingService.getAllByStudentIdByYear(studentId, year);
            List<RatingsReportDto> data = reportDataAggregationService.aggregateRatingsDataByStudentYear(ratings, studentId, year);

            return generateReport("ratings_by_student_year", parameters, data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate ratings by student year report", e);
        }
    }

    public byte[] generateRatingsByActivityYearReport(Integer year, Long activityId) {
        try {
            // Validate required parameters
            validateYearParameter(year);
            validateEntityId(activityId, "Activity");

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("year", year);
            parameters.put("activityId", activityId);

            // Retrieve data from database
            List<Rating> ratings = ratingService.getAllByActivityIdByYear(activityId, year);
            List<RatingsReportDto> data = reportDataAggregationService.aggregateRatingsDataByActivityYear(ratings, activityId, year);

            return generateReport("ratings_by_activity_year", parameters, data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate ratings by activity year report", e);
        }
    }

    public byte[] generateRatingsByActivityReport(Long activityId) {
        try {
            // Validate required parameters
            validateEntityId(activityId, "Activity");

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("activityId", activityId);

            // Retrieve data from database
            List<Rating> ratings = ratingService.getAllByActivityId(activityId);
            List<RatingsReportDto> data = reportDataAggregationService.aggregateRatingsData(ratings);

            return generateReport("ratings_by_activity", parameters, data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate ratings by activity report", e);
        }
    }

    // Async version for large ratings reports
    @Async
    public CompletableFuture<byte[]> generateRatingsByActivityReportAsync(Long activityId) {
        try {
            validateEntityId(activityId, "Activity");

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("activityId", activityId);

            // Retrieve data from database
            List<Rating> ratings = ratingService.getAllByActivityId(activityId);
            List<RatingsReportDto> data = reportDataAggregationService.aggregateRatingsData(ratings);

            // Check if data is large enough to warrant async processing
            if (data.size() > 30) {
                byte[] report = generateReport("ratings_by_activity", parameters, data);
                return CompletableFuture.completedFuture(report);
            } else {
                byte[] report = generateReport("ratings_by_activity", parameters, data);
                return CompletableFuture.completedFuture(report);
            }
        } catch (Exception e) {
            CompletableFuture<byte[]> future = new CompletableFuture<>();
            future.completeExceptionally(new RuntimeException("Failed to generate async ratings by activity report", e));
            return future;
        }
    }

    // Teacher Reports
    public byte[] generateMarksByTeacherYearReport(Integer year, Long teacherId) {
        try {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("year", year);
            parameters.put("teacherId", teacherId);

            // Retrieve data from database
            List<Mark> marks = markService.getAllByTeacherIdByYear(teacherId, year);
            List<MarksReportDto> data = reportDataAggregationService.aggregateMarksDataByTeacherYear(marks, teacherId, year);

            return generateReport("marks_by_teacher_year", parameters, data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate marks by teacher year report", e);
        }
    }

    public byte[] generateRatingsByTeacherYearReport(Integer year, Long teacherId) {
        try {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("year", year);
            parameters.put("teacherId", teacherId);

            // Retrieve data from database
            List<Rating> ratings = ratingService.getAllByTeacherIdByYear(teacherId, year);
            List<RatingsReportDto> data = reportDataAggregationService.aggregateRatingsDataByTeacherYear(ratings, teacherId, year);

            return generateReport("ratings_by_teacher_year", parameters, data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate ratings by teacher year report", e);
        }
    }

    // Progress Reports
    public byte[] generateProgressReportByStudent(Long studentId, String entityType) {
        try {
            // Validate required parameters
            validateEntityId(studentId, "Student");
            validateRequiredParameters(Map.of("entityType", entityType), "entityType");

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("studentId", studentId);
            parameters.put("entityType", entityType);

            // Retrieve data from database
            List<ActivityPoint> activityPoints = activityPointService.getAllByStudentId(studentId);
            List<ProgressReportDto> data = reportDataAggregationService.aggregateProgressDataByStudent(activityPoints, studentId, entityType);

            return generateReport("progress_report_by_student", parameters, data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate progress report by student", e);
        }
    }

    public byte[] generateProgressReportByTeacher(Long teacherId, String entityType) {
        try {
            // Validate required parameters
            validateEntityId(teacherId, "Teacher");
            validateRequiredParameters(Map.of("entityType", entityType), "entityType");

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("teacherId", teacherId);
            parameters.put("entityType", entityType);

            // Retrieve data from database
            List<ActivityPoint> activityPoints = activityPointService.getAllByTeacherId(teacherId);
            List<ProgressReportDto> data = reportDataAggregationService.aggregateProgressDataByTeacher(activityPoints, teacherId, entityType);

            return generateReport("progress_report_by_teacher", parameters, data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate progress report by teacher", e);
        }
    }

    public byte[] generateProgressReportBySubject(Long subjectId, String entityType) {
        try {
            // Validate required parameters
            validateEntityId(subjectId, "Subject");
            validateRequiredParameters(Map.of("entityType", entityType), "entityType");

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("subjectId", subjectId);
            parameters.put("entityType", entityType);

            // Retrieve data from database - using Mark instead of ActivityPoint
            List<Mark> marks = markService.getAllBySubjectId(subjectId);
            List<ProgressReportDto> data = reportDataAggregationService.aggregateProgressDataBySubjectFromMarks(marks, subjectId, entityType);

            return generateReport("progress_report_by_subject", parameters, data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate progress report by subject", e);
        }
    }

    public byte[] generateProgressReportByClassroom(Long classroomId, String entityType) {
        try {
            // Validate required parameters
            validateEntityId(classroomId, "Classroom");
            validateRequiredParameters(Map.of("entityType", entityType), "entityType");

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("classroomId", classroomId);
            parameters.put("entityType", entityType);

            // Retrieve data from database
            List<ActivityPoint> activityPoints = activityPointService.getAllByClassroomId(classroomId);
            List<ProgressReportDto> data = reportDataAggregationService.aggregateProgressDataByClassroom(activityPoints, classroomId, entityType);

            return generateReport("progress_report_by_classroom", parameters, data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate progress report by classroom", e);
        }
    }

    public byte[] generateProgressReportByActivity(Long activityId, String entityType) {
        try {
            // Validate required parameters
            validateEntityId(activityId, "Activity");
            validateRequiredParameters(Map.of("entityType", entityType), "entityType");

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("activityId", activityId);
            parameters.put("entityType", entityType);

            // Retrieve data from database
            List<ActivityPoint> activityPoints = activityPointService.getAllByActivityId(activityId);
            List<ProgressReportDto> data = reportDataAggregationService.aggregateProgressDataByActivity(activityPoints, activityId, entityType);

            return generateReport("progress_report_by_activity", parameters, data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate progress report by activity", e);
        }
    }

    public byte[] generateProgressReportByFacilitator(Long facilitatorId, String entityType) {
        try {
            // Validate required parameters
            validateEntityId(facilitatorId, "Facilitator");
            validateRequiredParameters(Map.of("entityType", entityType), "entityType");

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("facilitatorId", facilitatorId);
            parameters.put("entityType", entityType);

            // Retrieve data from database
            List<ActivityPoint> activityPoints = activityPointService.getAllByFacilitatorId(facilitatorId);
            List<ProgressReportDto> data = reportDataAggregationService.aggregateProgressDataByFacilitator(activityPoints, facilitatorId, entityType);

            return generateReport("progress_report_by_facilitator", parameters, data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate progress report by facilitator", e);
        }
    }

    // Async version for large progress reports
    @Async
    public CompletableFuture<byte[]> generateProgressReportByClassroomAsync(Long classroomId, String entityType) {
        try {
            validateEntityId(classroomId, "Classroom");
            validateRequiredParameters(Map.of("entityType", entityType), "entityType");

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("classroomId", classroomId);
            parameters.put("entityType", entityType);

            // Retrieve data from database
            List<ActivityPoint> activityPoints = activityPointService.getAllByClassroomId(classroomId);
            List<ProgressReportDto> data = reportDataAggregationService.aggregateProgressDataByClassroom(activityPoints, classroomId, entityType);

            // Check if data is large enough to warrant async processing
            if (data.size() > 25) {
                byte[] report = generateReport("progress_report_by_classroom", parameters, data);
                return CompletableFuture.completedFuture(report);
            } else {
                byte[] report = generateReport("progress_report_by_classroom", parameters, data);
                return CompletableFuture.completedFuture(report);
            }
        } catch (Exception e) {
            CompletableFuture<byte[]> future = new CompletableFuture<>();
            future.completeExceptionally(new RuntimeException("Failed to generate async progress report by classroom", e));
            return future;
        }
    }

    // Generic report generation method with validation and caching
    @Cacheable(value = "reportCache", key = "#reportName + '_' + #parameters.hashCode() + '_' + (#data != null ? #data.size() : 0)")
    private byte[] generateReport(String reportName, Map<String, Object> parameters, List<?> data) throws JRException {
        // Validate data is not empty
        validateReportData(reportName, data);

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

    // Async version for large reports
    @Async
    public CompletableFuture<byte[]> generateLargeReportAsync(String reportName, Map<String, Object> parameters, List<?> data) {
        try {
            byte[] report = generateReport(reportName, parameters, data);
            return CompletableFuture.completedFuture(report);
        } catch (JRException e) {
            throw new RuntimeException("Failed to generate async report: " + reportName, e);
        }
    }

    // Validation methods
    private void validateReportData(String reportName, List<?> data) {
        if (data == null || data.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT,
                String.format("No data found for report: %s. Please check your parameters and ensure data exists.", reportName));
        }
    }

    private void validateRequiredParameters(Map<String, Object> parameters, String... requiredParams) {
        if (parameters == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Report parameters cannot be null");
        }

        for (String param : requiredParams) {
            if (!parameters.containsKey(param) || parameters.get(param) == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Required parameter '%s' is missing or null", param));
            }
        }
    }

    private void validateYearParameter(Integer year) {
        if (year == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Year parameter is required");
        }

        int currentYear = java.time.Year.now().getValue();
        if (year < 2000 || year > currentYear + 10) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                String.format("Year parameter must be between 2000 and %d", currentYear + 10));
        }
    }

    private void validateEntityId(Long entityId, String entityType) {
        if (entityId == null || entityId <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                String.format("%s ID is required and must be a positive number", entityType));
        }
    }
}
