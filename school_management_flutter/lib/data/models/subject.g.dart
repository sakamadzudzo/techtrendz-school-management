// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'subject.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Subject _$SubjectFromJson(Map<String, dynamic> json) => Subject(
  id: (json['id'] as num).toInt(),
  name: json['name'] as String?,
  description: json['description'] as String?,
  code: json['code'] as String?,
  syllabus: json['syllabus'] as String?,
  curriculum: json['curriculum'] as String?,
  level: json['level'] as String?,
  type: _subjectTypeFromJson(json['type'] as String?),
  category: json['category'] as String?,
  status: _statusFromJson(json['status'] as String),
  datestamp: DateTime.parse(json['datestamp'] as String),
);

Map<String, dynamic> _$SubjectToJson(Subject instance) => <String, dynamic>{
  'id': instance.id,
  'name': instance.name,
  'description': instance.description,
  'code': instance.code,
  'syllabus': instance.syllabus,
  'curriculum': instance.curriculum,
  'level': instance.level,
  'type': _subjectTypeToJson(instance.type),
  'category': instance.category,
  'status': _statusToJson(instance.status),
  'datestamp': instance.datestamp.toIso8601String(),
};
