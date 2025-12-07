// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'subject_year.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

SubjectYear _$SubjectYearFromJson(Map<String, dynamic> json) => SubjectYear(
  id: (json['id'] as num).toInt(),
  subject: json['subject'] == null
      ? null
      : Subject.fromJson(json['subject'] as Map<String, dynamic>),
  calendarYear: (json['calendarYear'] as num?)?.toInt(),
  teacher: json['teacher'] == null
      ? null
      : Person.fromJson(json['teacher'] as Map<String, dynamic>),
  status: _statusFromJson(json['status'] as String),
  datestamp: DateTime.parse(json['datestamp'] as String),
);

Map<String, dynamic> _$SubjectYearToJson(SubjectYear instance) =>
    <String, dynamic>{
      'id': instance.id,
      'subject': instance.subject,
      'calendarYear': instance.calendarYear,
      'teacher': instance.teacher,
      'status': _statusToJson(instance.status),
      'datestamp': instance.datestamp.toIso8601String(),
    };
