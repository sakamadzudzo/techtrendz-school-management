// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'coursework.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Coursework _$CourseworkFromJson(Map<String, dynamic> json) => Coursework(
  id: (json['id'] as num).toInt(),
  title: json['title'] as String?,
  type: _courseworkTypeFromJson(json['type'] as String?),
  subject: json['subject'] == null
      ? null
      : SubjectYear.fromJson(json['subject'] as Map<String, dynamic>),
  status: _statusFromJson(json['status'] as String),
  datestamp: DateTime.parse(json['datestamp'] as String),
  subjectName: json['subjectName'] as String?,
  total: (json['total'] as num).toDouble(),
  passMark: (json['passMark'] as num).toDouble(),
  marks: (json['marks'] as List<dynamic>?)
      ?.map((e) => Mark.fromJson(e as Map<String, dynamic>))
      .toList(),
);

Map<String, dynamic> _$CourseworkToJson(Coursework instance) =>
    <String, dynamic>{
      'id': instance.id,
      'title': instance.title,
      'type': _courseworkTypeToJson(instance.type),
      'subject': instance.subject,
      'status': _statusToJson(instance.status),
      'datestamp': instance.datestamp.toIso8601String(),
      'subjectName': instance.subjectName,
      'total': instance.total,
      'passMark': instance.passMark,
      'marks': instance.marks,
    };
