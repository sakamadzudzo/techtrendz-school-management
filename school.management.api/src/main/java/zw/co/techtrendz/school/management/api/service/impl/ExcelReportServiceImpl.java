package zw.co.techtrendz.school.management.api.service.impl;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import zw.co.techtrendz.school.management.api.dto.MarksReportDto;
import zw.co.techtrendz.school.management.api.dto.ProgressReportDto;
import zw.co.techtrendz.school.management.api.dto.RatingsReportDto;
import zw.co.techtrendz.school.management.api.entity.ActivityPoint;
import zw.co.techtrendz.school.management.api.entity.Mark;
import zw.co.techtrendz.school.management.api.service.ActivityPointService;
import zw.co.techtrendz.school.management.api.service.ExcelReportService;
import zw.co.techtrendz.school.management.api.service.MarkService;
import zw.co.techtrendz.school.management.api.service.RatingService;
import zw.co.techtrendz.school.management.api.service.ReportDataAggregationService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ExcelReportServiceImpl implements ExcelReportService {

    private final MarkService markService;
    private final RatingService ratingService;
    private final ActivityPointService activityPointService;
    private final ReportDataAggregationService reportDataAggregationService;

    public ExcelReportServiceImpl(MarkService markService,
                                RatingService ratingService,
                                ActivityPointService activityPointService,
                                ReportDataAggregationService reportDataAggregationService) {
        this.markService = markService;
        this.ratingService = ratingService;
        this.activityPointService = activityPointService;
        this.reportDataAggregationService = reportDataAggregationService;
    }

    // Marks Reports
    public byte[] generateMarksByStudentClassroomYearReport(Integer year, Long classroomId, Long studentId) {
        try {
            // Validate required parameters
            validateYearParameter(year);
            validateEntityId(classroomId, "Classroom");
            validateEntityId(studentId, "Student");

            // Retrieve data from database
            List<Mark> marks = markService.getAllByStudentIdByClassroomIdByYear(studentId, classroomId, year);
            List<MarksReportDto> data = reportDataAggregationService.aggregateMarksDataByStudentClassroomYear(marks, studentId, classroomId, year);

            return generateMarksExcelReport("Marks Report - Student, Classroom, Year", data, Map.of(
                "Year", year.toString(),
                "Classroom ID", classroomId.toString(),
                "Student ID", studentId.toString()
            ));
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(String.format(
                "Failed to generate Excel marks report for student ID %s in classroom ID %s for year %d: %s",
                studentId, classroomId, year, e.getMessage()), e);
        }
    }

    public byte[] generateMarksByStudentSubjectYearReport(Integer year, Long subjectId, Long studentId) {
        try {
            validateYearParameter(year);
            validateEntityId(subjectId, "Subject");
            validateEntityId(studentId, "Student");

            List<Mark> marks = markService.getAllByStudentIdBySubjectIdByYear(studentId, subjectId, year);
            List<MarksReportDto> data = reportDataAggregationService.aggregateMarksDataByStudentSubjectYear(marks, studentId, subjectId, year);

            return generateMarksExcelReport("Marks Report - Student, Subject, Year", data, Map.of(
                "Year", year.toString(),
                "Subject ID", subjectId.toString(),
                "Student ID", studentId.toString()
            ));
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(String.format(
                "Failed to generate Excel marks report for student ID %s in subject ID %s for year %d: %s",
                studentId, subjectId, year, e.getMessage()), e);
        }
    }

    public byte[] generateMarksByStudentYearReport(Integer year, Long studentId) {
        try {
            validateYearParameter(year);
            validateEntityId(studentId, "Student");

            List<Mark> marks = markService.getAllByStudentIdByYear(studentId, year);
            List<MarksReportDto> data = reportDataAggregationService.aggregateMarksDataByStudentYear(marks, studentId, year);

            return generateMarksExcelReport("Marks Report - Student, Year", data, Map.of(
                "Year", year.toString(),
                "Student ID", studentId.toString()
            ));
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Excel marks by student year report", e);
        }
    }

    public byte[] generateMarksByStudentReport(Long studentId) {
        try {
            validateEntityId(studentId, "Student");

            List<Mark> marks = markService.getAllByStudentId(studentId);
            List<MarksReportDto> data = reportDataAggregationService.aggregateMarksData(marks);

            return generateMarksExcelReport("Marks Report - Student", data, Map.of(
                "Student ID", studentId.toString()
            ));
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Excel marks by student report", e);
        }
    }

    public byte[] generateMarksByYearReport(Integer year) {
        try {
            validateYearParameter(year);

            List<Mark> marks = markService.getAllByYear(year);
            List<MarksReportDto> data = reportDataAggregationService.aggregateMarksDataByYear(marks, year);

            return generateMarksExcelReport("Marks Report - Year", data, Map.of(
                "Year", year.toString()
            ));
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(String.format(
                "Failed to generate Excel marks report for year %d: %s", year, e.getMessage()), e);
        }
    }

    @Async
    public CompletableFuture<byte[]> generateMarksByYearReportAsync(Integer year) {
        try {
            validateYearParameter(year);

            List<Mark> marks = markService.getAllByYear(year);
            List<MarksReportDto> data = reportDataAggregationService.aggregateMarksDataByYear(marks, year);

            if (data.size() > 100) {
                byte[] report = generateMarksExcelReport("Marks Report - Year", data, Map.of("Year", year.toString()));
                return CompletableFuture.completedFuture(report);
            } else {
                byte[] report = generateMarksExcelReport("Marks Report - Year", data, Map.of("Year", year.toString()));
                return CompletableFuture.completedFuture(report);
            }
        } catch (ResponseStatusException e) {
            CompletableFuture<byte[]> future = new CompletableFuture<>();
            future.completeExceptionally(e);
            return future;
        } catch (Exception e) {
            CompletableFuture<byte[]> future = new CompletableFuture<>();
            future.completeExceptionally(new RuntimeException(String.format(
                "Failed to generate async Excel marks report for year %d: %s", year, e.getMessage()), e));
            return future;
        }
    }

    @Async
    public CompletableFuture<byte[]> generateMarksByClassroomYearReportAsync(Integer year, Long classroomId) {
        try {
            validateYearParameter(year);
            validateEntityId(classroomId, "Classroom");

            List<Mark> marks = markService.getAllByClassroomIdByYear(classroomId, year);
            List<MarksReportDto> data = reportDataAggregationService.aggregateMarksDataByClassroomYear(marks, classroomId, year);

            if (data.size() > 50) {
                byte[] report = generateMarksExcelReport("Marks Report - Classroom, Year", data, Map.of(
                    "Year", year.toString(),
                    "Classroom ID", classroomId.toString()
                ));
                return CompletableFuture.completedFuture(report);
            } else {
                byte[] report = generateMarksExcelReport("Marks Report - Classroom, Year", data, Map.of(
                    "Year", year.toString(),
                    "Classroom ID", classroomId.toString()
                ));
                return CompletableFuture.completedFuture(report);
            }
        } catch (Exception e) {
            CompletableFuture<byte[]> future = new CompletableFuture<>();
            future.completeExceptionally(new RuntimeException("Failed to generate async Excel marks by classroom year report", e));
            return future;
        }
    }

    public byte[] generateMarksByClassroomYearReport(Integer year, Long classroomId) {
        try {
            validateYearParameter(year);
            validateEntityId(classroomId, "Classroom");

            List<Mark> marks = markService.getAllByClassroomIdByYear(classroomId, year);
            List<MarksReportDto> data = reportDataAggregationService.aggregateMarksDataByClassroomYear(marks, classroomId, year);

            return generateMarksExcelReport("Marks Report - Classroom, Year", data, Map.of(
                "Year", year.toString(),
                "Classroom ID", classroomId.toString()
            ));
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Excel marks by classroom year report", e);
        }
    }

    public byte[] generateMarksBySubjectYearReport(Integer year, Long subjectId) {
        try {
            validateYearParameter(year);
            validateEntityId(subjectId, "Subject");

            List<Mark> marks = markService.getAllBySubjectIdByYear(subjectId, year);
            List<MarksReportDto> data = reportDataAggregationService.aggregateMarksDataBySubjectYear(marks, subjectId, year);

            return generateMarksExcelReport("Marks Report - Subject, Year", data, Map.of(
                "Year", year.toString(),
                "Subject ID", subjectId.toString()
            ));
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Excel marks by subject year report", e);
        }
    }

    // Ratings Reports
    public byte[] generateRatingsByStudentActivityYearReport(Integer year, Long activityId, Long studentId) {
        try {
            validateYearParameter(year);
            validateEntityId(activityId, "Activity");
            validateEntityId(studentId, "Student");

            List<ActivityPoint> activityPoints = activityPointService.getAllByStudentIdByActivityIdByYear(studentId, activityId, year);
            List<RatingsReportDto> data = reportDataAggregationService.aggregateRatingsDataFromActivityPointsByStudentActivityYear(activityPoints, studentId, activityId, year);

            return generateRatingsExcelReport("Ratings Report - Student, Activity, Year", data, Map.of(
                "Year", year.toString(),
                "Activity ID", activityId.toString(),
                "Student ID", studentId.toString()
            ));
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Excel ratings by student activity year report", e);
        }
    }

    public byte[] generateRatingsByStudentYearReport(Integer year, Long studentId) {
        try {
            validateYearParameter(year);
            validateEntityId(studentId, "Student");

            List<ActivityPoint> activityPoints = activityPointService.getAllByStudentIdByYear(studentId, year);
            List<RatingsReportDto> data = reportDataAggregationService.aggregateRatingsDataFromActivityPointsByStudentYear(activityPoints, studentId, year);

            return generateRatingsExcelReport("Ratings Report - Student, Year", data, Map.of(
                "Year", year.toString(),
                "Student ID", studentId.toString()
            ));
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            Logger.getLogger(ExcelReportServiceImpl.class.getName()).log(Level.WARNING,
                String.format("Error generating Excel ratings report for student ID %s, year %d: %s",
                studentId, year, e.getMessage()), e);
            throw new RuntimeException(String.format(
                "Failed to generate Excel ratings report for student ID %s for year %d: %s",
                studentId, year, e.getMessage()), e);
        }
    }

    public byte[] generateRatingsByActivityYearReport(Integer year, Long activityId) {
        try {
            validateYearParameter(year);
            validateEntityId(activityId, "Activity");

            List<ActivityPoint> activityPoints = activityPointService.getAllByActivityIdByYear(activityId, year);
            List<RatingsReportDto> data = reportDataAggregationService.aggregateRatingsDataFromActivityPointsByActivityYear(activityPoints, activityId, year);

            return generateRatingsExcelReport("Ratings Report - Activity, Year", data, Map.of(
                "Year", year.toString(),
                "Activity ID", activityId.toString()
            ));
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Excel ratings by activity year report", e);
        }
    }

    public byte[] generateRatingsByActivityReport(Long activityId) {
        try {
            validateEntityId(activityId, "Activity");

            List<ActivityPoint> activityPoints = activityPointService.getAllByActivityIdForReports(activityId);
            List<RatingsReportDto> data = reportDataAggregationService.aggregateRatingsDataFromActivityPoints(activityPoints);

            return generateRatingsExcelReport("Ratings Report - Activity", data, Map.of(
                "Activity ID", activityId.toString()
            ));
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Excel ratings by activity report", e);
        }
    }

    @Async
    public CompletableFuture<byte[]> generateRatingsByActivityReportAsync(Long activityId) {
        try {
            validateEntityId(activityId, "Activity");

            List<ActivityPoint> activityPoints = activityPointService.getAllByActivityIdForReports(activityId);
            List<RatingsReportDto> data = reportDataAggregationService.aggregateRatingsDataFromActivityPoints(activityPoints);

            if (data.size() > 30) {
                byte[] report = generateRatingsExcelReport("Ratings Report - Activity", data, Map.of(
                    "Activity ID", activityId.toString()
                ));
                return CompletableFuture.completedFuture(report);
            } else {
                byte[] report = generateRatingsExcelReport("Ratings Report - Activity", data, Map.of(
                    "Activity ID", activityId.toString()
                ));
                return CompletableFuture.completedFuture(report);
            }
        } catch (ResponseStatusException e) {
            CompletableFuture<byte[]> future = new CompletableFuture<>();
            future.completeExceptionally(e);
            return future;
        } catch (Exception e) {
            CompletableFuture<byte[]> future = new CompletableFuture<>();
            future.completeExceptionally(new RuntimeException(String.format(
                "Failed to generate async Excel ratings report for activity ID %s: %s", activityId, e.getMessage()), e));
            return future;
        }
    }

    // Teacher Reports
    public byte[] generateMarksByTeacherYearReport(Integer year, Long teacherId) {
        try {
            List<Mark> marks = markService.getAllByTeacherIdByYear(teacherId, year);
            List<MarksReportDto> data = reportDataAggregationService.aggregateMarksDataByTeacherYear(marks, teacherId, year);

            return generateMarksExcelReport("Marks Report - Teacher, Year", data, Map.of(
                "Year", year.toString(),
                "Teacher ID", teacherId.toString()
            ));
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Excel marks by teacher year report", e);
        }
    }

    public byte[] generateRatingsByTeacherYearReport(Integer year, Long teacherId) {
        try {
            List<ActivityPoint> activityPoints = activityPointService.getAllByTeacherIdByYear(teacherId, year);
            List<RatingsReportDto> data = reportDataAggregationService.aggregateRatingsDataFromActivityPointsByTeacherYear(activityPoints, teacherId, year);

            return generateRatingsExcelReport("Ratings Report - Teacher, Year", data, Map.of(
                "Year", year.toString(),
                "Teacher ID", teacherId.toString()
            ));
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Excel ratings by teacher year report", e);
        }
    }

    // Progress Reports
    public byte[] generateProgressReportByStudent(Long studentId, String entityType) {
        try {
            validateEntityId(studentId, "Student");
            validateRequiredParameters(Map.of("entityType", entityType), "entityType");

            List<ActivityPoint> activityPoints = activityPointService.getAllByStudentIdForReports(studentId);
            List<ProgressReportDto> data = reportDataAggregationService.aggregateProgressDataByStudent(activityPoints, studentId, entityType);

            return generateProgressExcelReport("Progress Report - Student", data, Map.of(
                "Student ID", studentId.toString(),
                "Entity Type", entityType
            ));
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(String.format(
                "Failed to generate Excel progress report for student ID %s with entity type '%s': %s",
                studentId, entityType, e.getMessage()), e);
        }
    }

    public byte[] generateProgressReportByTeacher(Long teacherId, String entityType) {
        try {
            validateEntityId(teacherId, "Teacher");
            validateRequiredParameters(Map.of("entityType", entityType), "entityType");

            List<ActivityPoint> activityPoints = activityPointService.getAllByTeacherId(teacherId);
            List<ProgressReportDto> data = reportDataAggregationService.aggregateProgressDataByTeacher(activityPoints, teacherId, entityType);

            return generateProgressExcelReport("Progress Report - Teacher", data, Map.of(
                "Teacher ID", teacherId.toString(),
                "Entity Type", entityType
            ));
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Excel progress report by teacher", e);
        }
    }

    public byte[] generateProgressReportBySubject(Long subjectId, String entityType) {
        try {
            validateEntityId(subjectId, "Subject");
            validateRequiredParameters(Map.of("entityType", entityType), "entityType");

            List<Mark> marks = markService.getAllBySubjectId(subjectId);
            List<ProgressReportDto> data = reportDataAggregationService.aggregateProgressDataBySubjectFromMarks(marks, subjectId, entityType);

            return generateProgressExcelReport("Progress Report - Subject", data, Map.of(
                "Subject ID", subjectId.toString(),
                "Entity Type", entityType
            ));
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Excel progress report by subject", e);
        }
    }

    public byte[] generateProgressReportByClassroom(Long classroomId, String entityType) {
        try {
            validateEntityId(classroomId, "Classroom");
            validateRequiredParameters(Map.of("entityType", entityType), "entityType");

            List<ActivityPoint> activityPoints = activityPointService.getAllByClassroomId(classroomId);
            List<ProgressReportDto> data = reportDataAggregationService.aggregateProgressDataByClassroom(activityPoints, classroomId, entityType);

            return generateProgressExcelReport("Progress Report - Classroom", data, Map.of(
                "Classroom ID", classroomId.toString(),
                "Entity Type", entityType
            ));
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Excel progress report by classroom", e);
        }
    }

    public byte[] generateProgressReportByActivity(Long activityId, String entityType) {
        try {
            validateEntityId(activityId, "Activity");
            validateRequiredParameters(Map.of("entityType", entityType), "entityType");

            List<ActivityPoint> activityPoints = activityPointService.getAllByActivityIdForReports(activityId);
            List<ProgressReportDto> data = reportDataAggregationService.aggregateProgressDataByActivity(activityPoints, activityId, entityType);

            return generateProgressExcelReport("Progress Report - Activity", data, Map.of(
                "Activity ID", activityId.toString(),
                "Entity Type", entityType
            ));
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Excel progress report by activity", e);
        }
    }

    public byte[] generateProgressReportByFacilitator(Long facilitatorId, String entityType) {
        try {
            validateEntityId(facilitatorId, "Facilitator");
            validateRequiredParameters(Map.of("entityType", entityType), "entityType");

            List<ActivityPoint> activityPoints = activityPointService.getAllByFacilitatorId(facilitatorId);
            List<ProgressReportDto> data = reportDataAggregationService.aggregateProgressDataByFacilitator(activityPoints, facilitatorId, entityType);

            return generateProgressExcelReport("Progress Report - Facilitator", data, Map.of(
                "Facilitator ID", facilitatorId.toString(),
                "Entity Type", entityType
            ));
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Excel progress report by facilitator", e);
        }
    }

    @Async
    public CompletableFuture<byte[]> generateProgressReportByClassroomAsync(Long classroomId, String entityType) {
        try {
            validateEntityId(classroomId, "Classroom");
            validateRequiredParameters(Map.of("entityType", entityType), "entityType");

            List<ActivityPoint> activityPoints = activityPointService.getAllByClassroomId(classroomId);
            List<ProgressReportDto> data = reportDataAggregationService.aggregateProgressDataByClassroom(activityPoints, classroomId, entityType);

            if (data.size() > 25) {
                byte[] report = generateProgressExcelReport("Progress Report - Classroom", data, Map.of(
                    "Classroom ID", classroomId.toString(),
                    "Entity Type", entityType
                ));
                return CompletableFuture.completedFuture(report);
            } else {
                byte[] report = generateProgressExcelReport("Progress Report - Classroom", data, Map.of(
                    "Classroom ID", classroomId.toString(),
                    "Entity Type", entityType
                ));
                return CompletableFuture.completedFuture(report);
            }
        } catch (ResponseStatusException e) {
            CompletableFuture<byte[]> future = new CompletableFuture<>();
            future.completeExceptionally(e);
            return future;
        } catch (Exception e) {
            CompletableFuture<byte[]> future = new CompletableFuture<>();
            future.completeExceptionally(new RuntimeException(String.format(
                "Failed to generate async Excel progress report for classroom ID %s with entity type '%s': %s",
                classroomId, entityType, e.getMessage()), e));
            return future;
        }
    }

    @Async
    public CompletableFuture<byte[]> generateLargeReportAsync(String reportName, Map<String, Object> parameters, List<?> data) {
        try {
            byte[] report = generateGenericExcelReport(reportName, data, parameters);
            return CompletableFuture.completedFuture(report);
        } catch (Exception e) {
            CompletableFuture<byte[]> future = new CompletableFuture<>();
            future.completeExceptionally(new RuntimeException(String.format(
                "Failed to generate async Excel report '%s': %s", reportName, e.getMessage()), e));
            return future;
        }
    }

    // Excel Generation Helper Methods
    private byte[] generateMarksExcelReport(String title, List<MarksReportDto> data, Map<String, String> parameters) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Marks Report");

        // Create title style
        CellStyle titleStyle = workbook.createCellStyle();
        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 16);
        titleStyle.setFont(titleFont);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);

        // Create header style
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);

        // Create data style
        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setBorderBottom(BorderStyle.THIN);
        dataStyle.setBorderTop(BorderStyle.THIN);
        dataStyle.setBorderRight(BorderStyle.THIN);
        dataStyle.setBorderLeft(BorderStyle.THIN);

        // Title row
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(title);
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 0, 9));

        // Parameters section
        int paramRowStart = 2;
        if (parameters != null && !parameters.isEmpty()) {
            for (Map.Entry<String, String> param : parameters.entrySet()) {
                Row paramRow = sheet.createRow(paramRowStart++);
                paramRow.createCell(0).setCellValue(param.getKey() + ":");
                paramRow.createCell(1).setCellValue(param.getValue());
            }
        }

        // Headers
        int headerRowNum = paramRowStart + 1;
        Row headerRow = sheet.createRow(headerRowNum);
        String[] headers = {"Student Name", "Student ID", "Classroom", "Subject", "Coursework", "Mark", "Pass Mark", "Total", "Grade", "Year", "Teacher"};

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // Data rows
        if (data != null && !data.isEmpty()) {
            int rowNum = headerRowNum + 1;
            for (MarksReportDto dto : data) {
                Row row = sheet.createRow(rowNum++);
                int colNum = 0;

                setCellValue(row.createCell(colNum++), dto.getStudentName(), dataStyle);
                setCellValue(row.createCell(colNum++), dto.getStudentId(), dataStyle);
                setCellValue(row.createCell(colNum++), dto.getClassroomName(), dataStyle);
                setCellValue(row.createCell(colNum++), dto.getSubjectName(), dataStyle);
                setCellValue(row.createCell(colNum++), dto.getCourseworkTitle(), dataStyle);
                setCellValue(row.createCell(colNum++), dto.getMark(), dataStyle);
                setCellValue(row.createCell(colNum++), dto.getPassMark(), dataStyle);
                setCellValue(row.createCell(colNum++), dto.getTotal(), dataStyle);
                setCellValue(row.createCell(colNum++), dto.getGrade(), dataStyle);
                setCellValue(row.createCell(colNum++), dto.getCalendarYear(), dataStyle);
                setCellValue(row.createCell(colNum++), dto.getTeacherName(), dataStyle);
            }
        }

        // Auto-size columns
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        return writeWorkbookToBytes(workbook);
    }

    private byte[] generateRatingsExcelReport(String title, List<RatingsReportDto> data, Map<String, String> parameters) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Ratings Report");

        // Create styles (same as marks report)
        CellStyle titleStyle = workbook.createCellStyle();
        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 16);
        titleStyle.setFont(titleFont);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);

        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);

        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setBorderBottom(BorderStyle.THIN);
        dataStyle.setBorderTop(BorderStyle.THIN);
        dataStyle.setBorderRight(BorderStyle.THIN);
        dataStyle.setBorderLeft(BorderStyle.THIN);

        // Title row
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(title);
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 0, 7));

        // Parameters section
        int paramRowStart = 2;
        if (parameters != null && !parameters.isEmpty()) {
            for (Map.Entry<String, String> param : parameters.entrySet()) {
                Row paramRow = sheet.createRow(paramRowStart++);
                paramRow.createCell(0).setCellValue(param.getKey() + ":");
                paramRow.createCell(1).setCellValue(param.getValue());
            }
        }

        // Headers
        int headerRowNum = paramRowStart + 1;
        Row headerRow = sheet.createRow(headerRowNum);
        String[] headers = {"Student Name", "Student ID", "Activity", "Rating", "Percentage", "Pass Percentage", "Facilitator", "Year"};

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // Data rows
        if (data != null && !data.isEmpty()) {
            int rowNum = headerRowNum + 1;
            for (RatingsReportDto dto : data) {
                Row row = sheet.createRow(rowNum++);
                int colNum = 0;

                setCellValue(row.createCell(colNum++), dto.getStudentName(), dataStyle);
                setCellValue(row.createCell(colNum++), dto.getStudentId(), dataStyle);
                setCellValue(row.createCell(colNum++), dto.getExtraCurricularActivityName(), dataStyle);
                setCellValue(row.createCell(colNum++), dto.getRating(), dataStyle);
                setCellValue(row.createCell(colNum++), dto.getPercentage(), dataStyle);
                setCellValue(row.createCell(colNum++), dto.getPassPercentage(), dataStyle);
                setCellValue(row.createCell(colNum++), dto.getFacilitatorName(), dataStyle);
                setCellValue(row.createCell(colNum++), dto.getCalendarYear(), dataStyle);
            }
        }

        // Auto-size columns
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        return writeWorkbookToBytes(workbook);
    }

    private byte[] generateProgressExcelReport(String title, List<ProgressReportDto> data, Map<String, String> parameters) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Progress Report");

        // Create styles (same as marks report)
        CellStyle titleStyle = workbook.createCellStyle();
        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 16);
        titleStyle.setFont(titleFont);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);

        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);

        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setBorderBottom(BorderStyle.THIN);
        dataStyle.setBorderTop(BorderStyle.THIN);
        dataStyle.setBorderRight(BorderStyle.THIN);
        dataStyle.setBorderLeft(BorderStyle.THIN);

        // Title row
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(title);
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 0, 8));

        // Parameters section
        int paramRowStart = 2;
        if (parameters != null && !parameters.isEmpty()) {
            for (Map.Entry<String, String> param : parameters.entrySet()) {
                Row paramRow = sheet.createRow(paramRowStart++);
                paramRow.createCell(0).setCellValue(param.getKey() + ":");
                paramRow.createCell(1).setCellValue(param.getValue());
            }
        }

        // Headers
        int headerRowNum = paramRowStart + 1;
        Row headerRow = sheet.createRow(headerRowNum);
        String[] headers = {"Entity Name", "Entity ID", "Year", "Current Value", "Previous Value", "Change %", "Trend", "Yearly Data"};

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // Data rows
        if (data != null && !data.isEmpty()) {
            int rowNum = headerRowNum + 1;
            for (ProgressReportDto dto : data) {
                Row row = sheet.createRow(rowNum++);
                int colNum = 0;

                setCellValue(row.createCell(colNum++), dto.getEntityName(), dataStyle);
                setCellValue(row.createCell(colNum++), dto.getEntityId(), dataStyle);
                setCellValue(row.createCell(colNum++), dto.getYear(), dataStyle);
                setCellValue(row.createCell(colNum++), dto.getCurrentValue(), dataStyle);
                setCellValue(row.createCell(colNum++), dto.getPreviousValue(), dataStyle);
                setCellValue(row.createCell(colNum++), dto.getPercentageChange(), dataStyle);
                setCellValue(row.createCell(colNum++), dto.getTrend(), dataStyle);

                // For yearly data, create a summary
                Cell yearlyDataCell = row.createCell(colNum++);
                if (dto.getYearlyData() != null && !dto.getYearlyData().isEmpty()) {
                    StringBuilder yearlyDataStr = new StringBuilder();
                    for (ProgressReportDto.YearlyData yearlyData : dto.getYearlyData()) {
                        if (yearlyDataStr.length() > 0) yearlyDataStr.append("; ");
                        yearlyDataStr.append(yearlyData.getYear()).append(": ").append(yearlyData.getValue());
                    }
                    yearlyDataCell.setCellValue(yearlyDataStr.toString());
                }
                yearlyDataCell.setCellStyle(dataStyle);
            }
        }

        // Auto-size columns
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        return writeWorkbookToBytes(workbook);
    }

    private byte[] generateGenericExcelReport(String title, List<?> data, Map<String, Object> parameters) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Report");

        // Create styles
        CellStyle titleStyle = workbook.createCellStyle();
        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 16);
        titleStyle.setFont(titleFont);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);

        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // Title
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(title);
        titleCell.setCellStyle(titleStyle);

        // For generic reports, just return a basic structure
        // This can be enhanced based on specific requirements
        return writeWorkbookToBytes(workbook);
    }

    private void setCellValue(Cell cell, Object value, CellStyle style) {
        if (value == null) {
            cell.setCellValue("");
        } else if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof BigDecimal) {
            cell.setCellValue(((BigDecimal) value).doubleValue());
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else {
            cell.setCellValue(value.toString());
        }
        cell.setCellStyle(style);
    }

    private byte[] writeWorkbookToBytes(Workbook workbook) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream.toByteArray();
    }

    // Validation methods
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
