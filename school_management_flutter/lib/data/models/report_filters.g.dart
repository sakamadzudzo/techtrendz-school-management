// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'report_filters.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ReportFilters _$ReportFiltersFromJson(Map<String, dynamic> json) =>
    ReportFilters(
      students: (json['students'] as List<dynamic>?)
          ?.map((e) => YearDropdown.fromJson(e as Map<String, dynamic>))
          .toList(),
      teachers: (json['teachers'] as List<dynamic>?)
          ?.map((e) => YearDropdown.fromJson(e as Map<String, dynamic>))
          .toList(),
      courseworks: (json['courseworks'] as List<dynamic>?)
          ?.map((e) => CourseworkDropdown.fromJson(e as Map<String, dynamic>))
          .toList(),
      classrooms: (json['classrooms'] as List<dynamic>?)
          ?.map((e) => YearDropdown.fromJson(e as Map<String, dynamic>))
          .toList(),
      subjects: (json['subjects'] as List<dynamic>?)
          ?.map((e) => YearDropdown.fromJson(e as Map<String, dynamic>))
          .toList(),
      extraCurriculars: (json['extraCurriculars'] as List<dynamic>?)
          ?.map((e) => YearDropdown.fromJson(e as Map<String, dynamic>))
          .toList(),
      calendarYears: (json['calendarYears'] as List<dynamic>?)
          ?.map((e) => (e as num).toInt())
          .toList(),
    );

Map<String, dynamic> _$ReportFiltersToJson(ReportFilters instance) =>
    <String, dynamic>{
      'students': instance.students,
      'teachers': instance.teachers,
      'courseworks': instance.courseworks,
      'classrooms': instance.classrooms,
      'subjects': instance.subjects,
      'extraCurriculars': instance.extraCurriculars,
      'calendarYears': instance.calendarYears,
    };

YearDropdown _$YearDropdownFromJson(Map<String, dynamic> json) => YearDropdown(
  id: (json['id'] as num).toInt(),
  name: json['name'] as String,
  calendarYear: (json['calendarYear'] as num?)?.toInt(),
);

Map<String, dynamic> _$YearDropdownToJson(YearDropdown instance) =>
    <String, dynamic>{
      'id': instance.id,
      'name': instance.name,
      'calendarYear': instance.calendarYear,
    };

CourseworkDropdown _$CourseworkDropdownFromJson(Map<String, dynamic> json) =>
    CourseworkDropdown(
      id: (json['id'] as num).toInt(),
      type: json['type'] as String,
      calendarYear: (json['calendarYear'] as num).toInt(),
      subject: Subject.fromJson(json['subject'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$CourseworkDropdownToJson(CourseworkDropdown instance) =>
    <String, dynamic>{
      'id': instance.id,
      'type': instance.type,
      'calendarYear': instance.calendarYear,
      'subject': instance.subject,
    };

Subject _$SubjectFromJson(Map<String, dynamic> json) => Subject(
  id: (json['id'] as num).toInt(),
  name: json['name'] as String,
  description: json['description'] as String,
  code: json['code'] as String,
  syllabus: json['syllabus'] as String,
  curriculum: json['curriculum'] as String,
  level: json['level'] as String,
  type: json['type'] as String,
  category: json['category'] as String,
);

Map<String, dynamic> _$SubjectToJson(Subject instance) => <String, dynamic>{
  'id': instance.id,
  'name': instance.name,
  'description': instance.description,
  'code': instance.code,
  'syllabus': instance.syllabus,
  'curriculum': instance.curriculum,
  'level': instance.level,
  'type': instance.type,
  'category': instance.category,
};
