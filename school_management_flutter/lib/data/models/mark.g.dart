// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'mark.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Mark _$MarkFromJson(Map<String, dynamic> json) => Mark(
  id: (json['id'] as num).toInt(),
  coursework: json['coursework'] == null
      ? null
      : Coursework.fromJson(json['coursework'] as Map<String, dynamic>),
  student: json['student'] == null
      ? null
      : Student.fromJson(json['student'] as Map<String, dynamic>),
  mark: (json['mark'] as num?)?.toDouble(),
  subjectName: json['subjectName'] as String?,
  status: _statusFromJson(json['status'] as String),
  datestamp: DateTime.parse(json['datestamp'] as String),
);

Map<String, dynamic> _$MarkToJson(Mark instance) => <String, dynamic>{
  'id': instance.id,
  'coursework': instance.coursework,
  'student': instance.student,
  'mark': instance.mark,
  'subjectName': instance.subjectName,
  'status': _statusToJson(instance.status),
  'datestamp': instance.datestamp.toIso8601String(),
};
