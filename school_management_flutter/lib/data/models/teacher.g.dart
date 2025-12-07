// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'teacher.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Teacher _$TeacherFromJson(Map<String, dynamic> json) => Teacher(
  id: (json['id'] as num).toInt(),
  person: json['person'] == null
      ? null
      : Person.fromJson(json['person'] as Map<String, dynamic>),
  status: _statusFromJson(json['status'] as String),
  datestamp: DateTime.parse(json['datestamp'] as String),
);

Map<String, dynamic> _$TeacherToJson(Teacher instance) => <String, dynamic>{
  'id': instance.id,
  'person': instance.person,
  'status': _statusToJson(instance.status),
  'datestamp': instance.datestamp.toIso8601String(),
};
