import 'dart:typed_data';

import 'package:json_annotation/json_annotation.dart';

part 'report_data.g.dart';

@JsonSerializable()
class MarksReportDto {
  final String studentName;
  final String studentId;
  final String classroomName;
  final String subjectName;
  final String courseworkTitle;
  final double mark;
  final double passMark;
  final double total;
  final String grade;
  final int calendarYear;
  final String teacherName;

  const MarksReportDto({
    required this.studentName,
    required this.studentId,
    required this.classroomName,
    required this.subjectName,
    required this.courseworkTitle,
    required this.mark,
    required this.passMark,
    required this.total,
    required this.grade,
    required this.calendarYear,
    required this.teacherName,
  });

  factory MarksReportDto.fromJson(Map<String, dynamic> json) =>
      _$MarksReportDtoFromJson(json);

  Map<String, dynamic> toJson() => _$MarksReportDtoToJson(this);
}

@JsonSerializable()
class ProgressReportDto {
  final String entityName;
  final String entityId;
  final int year;
  final double currentValue;
  final double previousValue;
  final double percentageChange;
  final String trend;
  final List<YearlyData> yearlyData;

  const ProgressReportDto({
    required this.entityName,
    required this.entityId,
    required this.year,
    required this.currentValue,
    required this.previousValue,
    required this.percentageChange,
    required this.trend,
    required this.yearlyData,
  });

  factory ProgressReportDto.fromJson(Map<String, dynamic> json) =>
      _$ProgressReportDtoFromJson(json);

  Map<String, dynamic> toJson() => _$ProgressReportDtoToJson(this);
}

@JsonSerializable()
class YearlyData {
  final int year;
  final double value;
  final String label;

  const YearlyData({
    required this.year,
    required this.value,
    required this.label,
  });

  factory YearlyData.fromJson(Map<String, dynamic> json) =>
      _$YearlyDataFromJson(json);

  Map<String, dynamic> toJson() => _$YearlyDataToJson(this);
}

@JsonSerializable()
class RatingsReportDto {
  final String studentName;
  final String studentId;
  final String extraCurricularActivityName;
  final double rating;
  final double percentage;
  final double passPercentage;
  final String facilitatorName;
  final int calendarYear;

  const RatingsReportDto({
    required this.studentName,
    required this.studentId,
    required this.extraCurricularActivityName,
    required this.rating,
    required this.percentage,
    required this.passPercentage,
    required this.facilitatorName,
    required this.calendarYear,
  });

  factory RatingsReportDto.fromJson(Map<String, dynamic> json) =>
      _$RatingsReportDtoFromJson(json);

  Map<String, dynamic> toJson() => _$RatingsReportDtoToJson(this);
}

@JsonSerializable()
class ReportFilter {
  final int? studentId;
  final int? teacherId;
  final int? courseworkId;
  final int? classroomId;
  final int? subjectId;
  final int? extraCurricularId;
  final int? year;
  final String type;
  final String? fileName;

  const ReportFilter({
    this.studentId,
    this.teacherId,
    this.courseworkId,
    this.classroomId,
    this.subjectId,
    this.extraCurricularId,
    this.year,
    required this.type,
    this.fileName,
  });

  factory ReportFilter.fromJson(Map<String, dynamic> json) =>
      _$ReportFilterFromJson(json);

  Map<String, dynamic> toJson() => _$ReportFilterToJson(this);

  ReportFilter copyWith({
    int? studentId,
    int? teacherId,
    int? courseworkId,
    int? classroomId,
    int? subjectId,
    int? extraCurricularId,
    int? year,
    String? type,
    String? fileName,
  }) {
    return ReportFilter(
      studentId: studentId ?? this.studentId,
      teacherId: teacherId ?? this.teacherId,
      courseworkId: courseworkId ?? this.courseworkId,
      classroomId: classroomId ?? this.classroomId,
      subjectId: subjectId ?? this.subjectId,
      extraCurricularId: extraCurricularId ?? this.extraCurricularId,
      year: year ?? this.year,
      type: type ?? this.type,
      fileName: fileName ?? this.fileName,
    );
  }
}

@JsonSerializable()
class AvailableFilters {
  final bool students;
  final bool teachers;
  final bool courseworks;
  final bool classrooms;
  final bool subjects;
  final bool extraCurriculars;
  final bool calendarYears;

  const AvailableFilters({
    required this.students,
    required this.teachers,
    required this.courseworks,
    required this.classrooms,
    required this.subjects,
    required this.extraCurriculars,
    required this.calendarYears,
  });

  factory AvailableFilters.fromJson(Map<String, dynamic> json) =>
      _$AvailableFiltersFromJson(json);

  Map<String, dynamic> toJson() => _$AvailableFiltersToJson(this);
}

@JsonSerializable()
class ReportType {
  final String title;
  final String endpoint;
  final AvailableFilters availableFilters;
  final int index;

  const ReportType({
    required this.title,
    required this.endpoint,
    required this.availableFilters,
    required this.index,
  });

  factory ReportType.fromJson(Map<String, dynamic> json) =>
      _$ReportTypeFromJson(json);

  Map<String, dynamic> toJson() => _$ReportTypeToJson(this);
}

class ReportResult {
  final bool success;
  final Uint8List? data;
  final String? error;
  final int? statusCode;

  const ReportResult({
    required this.success,
    this.data,
    this.error,
    this.statusCode,
  });
}