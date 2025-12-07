import 'dart:typed_data';

import 'package:school_management_flutter/data/models/report_data.dart';

class ReportService {
  // Report types configuration based on backend endpoints
  static const List<ReportType> reportTypes = [
    ReportType(
      title: 'Marks By Classroom Year',
      endpoint: 'marks/classroom-year',
      availableFilters: AvailableFilters(
        students: false,
        teachers: false,
        courseworks: false,
        classrooms: true,
        subjects: false,
        extraCurriculars: false,
        calendarYears: true,
      ),
      index: 1,
    ),
    ReportType(
      title: 'Marks By Student',
      endpoint: 'marks/student',
      availableFilters: AvailableFilters(
        students: true,
        teachers: false,
        courseworks: false,
        classrooms: false,
        subjects: false,
        extraCurriculars: false,
        calendarYears: false,
      ),
      index: 2,
    ),
    ReportType(
      title: 'Marks By Student Classroom Year',
      endpoint: 'marks/student-classroom-year',
      availableFilters: AvailableFilters(
        students: true,
        teachers: false,
        courseworks: false,
        classrooms: true,
        subjects: false,
        extraCurriculars: false,
        calendarYears: true,
      ),
      index: 3,
    ),
    ReportType(
      title: 'Marks By Student Subject Year',
      endpoint: 'marks/student-subject-year',
      availableFilters: AvailableFilters(
        students: true,
        teachers: false,
        courseworks: false,
        classrooms: false,
        subjects: true,
        extraCurriculars: false,
        calendarYears: true,
      ),
      index: 4,
    ),
    ReportType(
      title: 'Marks By Student Year',
      endpoint: 'marks/student-year',
      availableFilters: AvailableFilters(
        students: true,
        teachers: false,
        courseworks: false,
        classrooms: false,
        subjects: false,
        extraCurriculars: false,
        calendarYears: true,
      ),
      index: 5,
    ),
    ReportType(
      title: 'Marks By Year',
      endpoint: 'marks/year',
      availableFilters: AvailableFilters(
        students: false,
        teachers: false,
        courseworks: false,
        classrooms: false,
        subjects: false,
        extraCurriculars: false,
        calendarYears: true,
      ),
      index: 6,
    ),
    ReportType(
      title: 'Marks By Subject Year',
      endpoint: 'marks/subject-year',
      availableFilters: AvailableFilters(
        students: false,
        teachers: false,
        courseworks: false,
        classrooms: false,
        subjects: true,
        extraCurriculars: false,
        calendarYears: true,
      ),
      index: 7,
    ),
    ReportType(
      title: 'Ratings By Student Activity Year',
      endpoint: 'ratings/student-activity-year',
      availableFilters: AvailableFilters(
        students: true,
        teachers: false,
        courseworks: false,
        classrooms: false,
        subjects: false,
        extraCurriculars: true,
        calendarYears: true,
      ),
      index: 8,
    ),
    ReportType(
      title: 'Ratings By Student Year',
      endpoint: 'ratings/student-year',
      availableFilters: AvailableFilters(
        students: true,
        teachers: false,
        courseworks: false,
        classrooms: false,
        subjects: false,
        extraCurriculars: false,
        calendarYears: true,
      ),
      index: 9,
    ),
    ReportType(
      title: 'Ratings By Activity Year',
      endpoint: 'ratings/activity-year',
      availableFilters: AvailableFilters(
        students: false,
        teachers: false,
        courseworks: false,
        classrooms: false,
        subjects: false,
        extraCurriculars: true,
        calendarYears: true,
      ),
      index: 10,
    ),
    ReportType(
      title: 'Ratings By Activity',
      endpoint: 'ratings/activity',
      availableFilters: AvailableFilters(
        students: false,
        teachers: false,
        courseworks: false,
        classrooms: false,
        subjects: false,
        extraCurriculars: true,
        calendarYears: false,
      ),
      index: 11,
    ),
    ReportType(
      title: 'Marks By Teacher Year',
      endpoint: 'marks/teacher-year',
      availableFilters: AvailableFilters(
        students: false,
        teachers: true,
        courseworks: false,
        classrooms: false,
        subjects: false,
        extraCurriculars: false,
        calendarYears: true,
      ),
      index: 12,
    ),
    ReportType(
      title: 'Ratings By Teacher Year',
      endpoint: 'ratings/teacher-year',
      availableFilters: AvailableFilters(
        students: false,
        teachers: true,
        courseworks: false,
        classrooms: false,
        subjects: false,
        extraCurriculars: false,
        calendarYears: true,
      ),
      index: 13,
    ),
    ReportType(
      title: 'Progress By Student',
      endpoint: 'progress/student',
      availableFilters: AvailableFilters(
        students: true,
        teachers: false,
        courseworks: false,
        classrooms: false,
        subjects: false,
        extraCurriculars: false,
        calendarYears: false,
      ),
      index: 14,
    ),
    ReportType(
      title: 'Progress By Teacher',
      endpoint: 'progress/teacher',
      availableFilters: AvailableFilters(
        students: false,
        teachers: true,
        courseworks: false,
        classrooms: false,
        subjects: false,
        extraCurriculars: false,
        calendarYears: false,
      ),
      index: 15,
    ),
    ReportType(
      title: 'Progress By Subject',
      endpoint: 'progress/subject',
      availableFilters: AvailableFilters(
        students: false,
        teachers: false,
        courseworks: false,
        classrooms: false,
        subjects: true,
        extraCurriculars: false,
        calendarYears: false,
      ),
      index: 16,
    ),
    ReportType(
      title: 'Progress By Classroom',
      endpoint: 'progress/classroom',
      availableFilters: AvailableFilters(
        students: false,
        teachers: false,
        courseworks: false,
        classrooms: true,
        subjects: false,
        extraCurriculars: false,
        calendarYears: false,
      ),
      index: 17,
    ),
    ReportType(
      title: 'Progress By Activity',
      endpoint: 'progress/activity',
      availableFilters: AvailableFilters(
        students: false,
        teachers: false,
        courseworks: false,
        classrooms: false,
        subjects: false,
        extraCurriculars: true,
        calendarYears: false,
      ),
      index: 18,
    ),
    ReportType(
      title: 'Progress By Facilitator',
      endpoint: 'progress/facilitator',
      availableFilters: AvailableFilters(
        students: false,
        teachers: true,
        courseworks: false,
        classrooms: false,
        subjects: false,
        extraCurriculars: false,
        calendarYears: false,
      ),
      index: 19,
    ),
  ];

  // Helper method to get report type by index
  static ReportType? getReportTypeByIndex(int index) {
    try {
      return reportTypes.firstWhere((type) => type.index == index);
    } catch (e) {
      return null;
    }
  }

  // Helper method to get report type by title
  static ReportType? getReportTypeByTitle(String title) {
    try {
      return reportTypes.firstWhere((type) => type.title == title);
    } catch (e) {
      return null;
    }
  }

  // Helper method to validate report filter
  static bool validateReportFilter(ReportFilter filter, ReportType reportType) {
    final availableFilters = reportType.availableFilters;

    if (availableFilters.students && filter.studentId == null) return false;
    if (availableFilters.teachers && filter.teacherId == null) return false;
    if (availableFilters.courseworks && filter.courseworkId == null) return false;
    if (availableFilters.classrooms && filter.classroomId == null) return false;
    if (availableFilters.subjects && filter.subjectId == null) return false;
    if (availableFilters.extraCurriculars && filter.extraCurricularId == null) return false;
    if (availableFilters.calendarYears && filter.year == null) return false;

    return true;
  }

  // Helper method to generate filename for report
  static String generateReportFilename(ReportType reportType, ReportFilter filter) {
    final timestamp = DateTime.now().millisecondsSinceEpoch;
    final type = filter.type;
    final extension = type == 'pdf' ? 'pdf' : 'xlsx';

    String filename = '${reportType.endpoint.replaceAll('/', '_')}_$timestamp.$extension';

    // Add filter info to filename if available
    if (filter.studentId != null) {
      filename = 'student_${filter.studentId}_$filename';
    } else if (filter.teacherId != null) {
      filename = 'teacher_${filter.teacherId}_$filename';
    } else if (filter.classroomId != null) {
      filename = 'classroom_${filter.classroomId}_$filename';
    } else if (filter.subjectId != null) {
      filename = 'subject_${filter.subjectId}_$filename';
    } else if (filter.extraCurricularId != null) {
      filename = 'activity_${filter.extraCurricularId}_$filename';
    }

    if (filter.year != null) {
      filename = '${filter.year}_$filename';
    }

    return filename;
  }
}