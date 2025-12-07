// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'report_data.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

MarksReportDto _$MarksReportDtoFromJson(Map<String, dynamic> json) =>
    MarksReportDto(
      studentName: json['studentName'] as String,
      studentId: json['studentId'] as String,
      classroomName: json['classroomName'] as String,
      subjectName: json['subjectName'] as String,
      courseworkTitle: json['courseworkTitle'] as String,
      mark: (json['mark'] as num).toDouble(),
      passMark: (json['passMark'] as num).toDouble(),
      total: (json['total'] as num).toDouble(),
      grade: json['grade'] as String,
      calendarYear: (json['calendarYear'] as num).toInt(),
      teacherName: json['teacherName'] as String,
    );

Map<String, dynamic> _$MarksReportDtoToJson(MarksReportDto instance) =>
    <String, dynamic>{
      'studentName': instance.studentName,
      'studentId': instance.studentId,
      'classroomName': instance.classroomName,
      'subjectName': instance.subjectName,
      'courseworkTitle': instance.courseworkTitle,
      'mark': instance.mark,
      'passMark': instance.passMark,
      'total': instance.total,
      'grade': instance.grade,
      'calendarYear': instance.calendarYear,
      'teacherName': instance.teacherName,
    };

ProgressReportDto _$ProgressReportDtoFromJson(Map<String, dynamic> json) =>
    ProgressReportDto(
      entityName: json['entityName'] as String,
      entityId: json['entityId'] as String,
      year: (json['year'] as num).toInt(),
      currentValue: (json['currentValue'] as num).toDouble(),
      previousValue: (json['previousValue'] as num).toDouble(),
      percentageChange: (json['percentageChange'] as num).toDouble(),
      trend: json['trend'] as String,
      yearlyData: (json['yearlyData'] as List<dynamic>)
          .map((e) => YearlyData.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$ProgressReportDtoToJson(ProgressReportDto instance) =>
    <String, dynamic>{
      'entityName': instance.entityName,
      'entityId': instance.entityId,
      'year': instance.year,
      'currentValue': instance.currentValue,
      'previousValue': instance.previousValue,
      'percentageChange': instance.percentageChange,
      'trend': instance.trend,
      'yearlyData': instance.yearlyData,
    };

YearlyData _$YearlyDataFromJson(Map<String, dynamic> json) => YearlyData(
  year: (json['year'] as num).toInt(),
  value: (json['value'] as num).toDouble(),
  label: json['label'] as String,
);

Map<String, dynamic> _$YearlyDataToJson(YearlyData instance) =>
    <String, dynamic>{
      'year': instance.year,
      'value': instance.value,
      'label': instance.label,
    };

RatingsReportDto _$RatingsReportDtoFromJson(Map<String, dynamic> json) =>
    RatingsReportDto(
      studentName: json['studentName'] as String,
      studentId: json['studentId'] as String,
      extraCurricularActivityName:
          json['extraCurricularActivityName'] as String,
      rating: (json['rating'] as num).toDouble(),
      percentage: (json['percentage'] as num).toDouble(),
      passPercentage: (json['passPercentage'] as num).toDouble(),
      facilitatorName: json['facilitatorName'] as String,
      calendarYear: (json['calendarYear'] as num).toInt(),
    );

Map<String, dynamic> _$RatingsReportDtoToJson(RatingsReportDto instance) =>
    <String, dynamic>{
      'studentName': instance.studentName,
      'studentId': instance.studentId,
      'extraCurricularActivityName': instance.extraCurricularActivityName,
      'rating': instance.rating,
      'percentage': instance.percentage,
      'passPercentage': instance.passPercentage,
      'facilitatorName': instance.facilitatorName,
      'calendarYear': instance.calendarYear,
    };

ReportFilter _$ReportFilterFromJson(Map<String, dynamic> json) => ReportFilter(
  studentId: (json['studentId'] as num?)?.toInt(),
  teacherId: (json['teacherId'] as num?)?.toInt(),
  courseworkId: (json['courseworkId'] as num?)?.toInt(),
  classroomId: (json['classroomId'] as num?)?.toInt(),
  subjectId: (json['subjectId'] as num?)?.toInt(),
  extraCurricularId: (json['extraCurricularId'] as num?)?.toInt(),
  year: (json['year'] as num?)?.toInt(),
  type: json['type'] as String,
  fileName: json['fileName'] as String?,
);

Map<String, dynamic> _$ReportFilterToJson(ReportFilter instance) =>
    <String, dynamic>{
      'studentId': instance.studentId,
      'teacherId': instance.teacherId,
      'courseworkId': instance.courseworkId,
      'classroomId': instance.classroomId,
      'subjectId': instance.subjectId,
      'extraCurricularId': instance.extraCurricularId,
      'year': instance.year,
      'type': instance.type,
      'fileName': instance.fileName,
    };

AvailableFilters _$AvailableFiltersFromJson(Map<String, dynamic> json) =>
    AvailableFilters(
      students: json['students'] as bool,
      teachers: json['teachers'] as bool,
      courseworks: json['courseworks'] as bool,
      classrooms: json['classrooms'] as bool,
      subjects: json['subjects'] as bool,
      extraCurriculars: json['extraCurriculars'] as bool,
      calendarYears: json['calendarYears'] as bool,
    );

Map<String, dynamic> _$AvailableFiltersToJson(AvailableFilters instance) =>
    <String, dynamic>{
      'students': instance.students,
      'teachers': instance.teachers,
      'courseworks': instance.courseworks,
      'classrooms': instance.classrooms,
      'subjects': instance.subjects,
      'extraCurriculars': instance.extraCurriculars,
      'calendarYears': instance.calendarYears,
    };

ReportType _$ReportTypeFromJson(Map<String, dynamic> json) => ReportType(
  title: json['title'] as String,
  endpoint: json['endpoint'] as String,
  availableFilters: AvailableFilters.fromJson(
    json['availableFilters'] as Map<String, dynamic>,
  ),
  index: (json['index'] as num).toInt(),
);

Map<String, dynamic> _$ReportTypeToJson(ReportType instance) =>
    <String, dynamic>{
      'title': instance.title,
      'endpoint': instance.endpoint,
      'availableFilters': instance.availableFilters,
      'index': instance.index,
    };
