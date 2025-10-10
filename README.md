# techtrendz-school-management
A basic school and student management system

## 📊 Jasper Reports System

The system includes a comprehensive reporting feature using Jasper Reports to generate professional PDF reports for various school management data.

### 🚀 Features

- **Marks Reports**: Generate reports for student marks by various criteria
- **Ratings Reports**: Generate reports for extra-curricular activity ratings
- **Progress Reports**: Year-on-year progress tracking with visual charts
- **Teacher Reports**: Performance reports for teachers and facilitators
- **Professional PDF Output**: Clean, formatted reports with proper headers and grouping

### 📋 Available Reports

#### Marks Reports
- **By Student and Classroom (Year)**: `marks_by_student_classroom_year`
- **By Student and Subject (Year)**: `marks_by_student_subject_year`
- **By Student (Year)**: `marks_by_student_year`
- **By Student (All Years)**: `marks_by_student`
- **By Year**: `marks_by_year`
- **By Classroom (Year)**: `marks_by_classroom_year`
- **By Subject (Year)**: `marks_by_subject_year`

#### Ratings Reports
- **By Student and Activity (Year)**: `ratings_by_student_activity_year`
- **By Student (Year)**: `ratings_by_student_year`
- **By Activity (Year)**: `ratings_by_activity_year`
- **By Activity (All Years)**: `ratings_by_activity`

#### Teacher Reports
- **Marks by Teacher (Year)**: `marks_by_teacher_year`
- **Ratings by Teacher/Facilitator (Year)**: `ratings_by_teacher_year`

#### Progress Reports
- **By Student**: `progress_report_by_student`
- **By Teacher**: `progress_report_by_teacher`
- **By Subject**: `progress_report_by_subject`
- **By Classroom**: `progress_report_by_classroom`
- **By Activity**: `progress_report_by_activity`
- **By Facilitator**: `progress_report_by_facilitator`

### 🔌 API Endpoints

All reports are accessible via POST endpoints under `/api/pdf-reports/`. Each endpoint accepts:
- JSON data array in request body
- Query parameters for filtering (year, IDs, etc.)
- Returns PDF file as response

#### Example Usage

```bash
# Generate marks report for student in classroom for year 2024
curl -X POST "http://localhost:8080/api/pdf-reports/marks/student-classroom-year?year=2024&classroomId=1&studentId=1" \
  -H "Content-Type: application/json" \
  -d '[
    {
      "studentName": "John Doe",
      "subjectName": "Mathematics",
      "courseworkTitle": "Final Exam",
      "mark": 85,
      "total": 100,
      "grade": "A",
      "teacherName": "Mr. Smith"
    }
  ]' \
  -o student_marks_report.pdf
```

### 📁 File Structure

```
src/main/resources/reports/
├── jrxml/          # Report templates (XML format)
│   ├── marks_by_student_classroom_year.jrxml
│   ├── marks_by_student_subject_year.jrxml
│   ├── marks_by_year.jrxml
│   ├── ratings_by_student_activity_year.jrxml
│   ├── ratings_by_activity.jrxml
│   └── progress_report_by_student.jrxml
└── compiled/       # Auto-compiled Jasper files

src/main/java/.../api/
├── dto/
│   ├── MarksReportDto.java
│   ├── RatingsReportDto.java
│   └── ProgressReportDto.java
├── utility/
│   └── JasperReportCompiler.java
├── service/impl/
│   └── PdfReportServiceImpl.java
└── controller/
    └── PdfReportController.java
```

### 🏗️ Architecture

- **DTOs**: Data Transfer Objects for report data structures
- **JasperReportCompiler**: Utility for compiling JRXML to Jasper format
- **PdfReportServiceImpl**: Business logic for report generation
- **PdfReportController**: REST endpoints for report access

### 📊 Report Data Structure

#### MarksReportDto
```java
{
  "studentName": "John Doe",
  "studentId": "12345",
  "classroomName": "Grade 10A",
  "subjectName": "Mathematics",
  "courseworkTitle": "Final Exam",
  "mark": 85,
  "passMark": 50,
  "total": 100,
  "grade": "A",
  "calendarYear": 2024,
  "teacherName": "Mr. Smith"
}
```

#### RatingsReportDto
```java
{
  "studentName": "John Doe",
  "studentId": "12345",
  "extraCurricularActivityName": "Basketball",
  "rating": 88,
  "percentage": 88.0,
  "passPercentage": 75.0,
  "facilitatorName": "Coach Johnson",
  "calendarYear": 2024
}
```

### 🔧 Configuration

The system automatically:
- Compiles JRXML templates to Jasper format on-demand
- Creates necessary directory structure
- Handles report generation and PDF export
- Manages temporary file cleanup

### 📈 Progress Reports with Charts

Progress reports include visual chart representations showing:
- Year-on-year performance trends
- Current vs previous year comparisons
- Percentage change calculations
- Visual indicators for improvement/decline

### 🚀 Getting Started

1. Ensure Jasper dependencies are included (already added to `pom.xml`)
2. Start the application - reports will be auto-compiled
3. Use the API endpoints to generate reports
4. Reports are returned as PDF files for download