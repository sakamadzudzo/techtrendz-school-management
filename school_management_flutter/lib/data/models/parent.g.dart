// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'parent.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Parent _$ParentFromJson(Map<String, dynamic> json) => Parent(
  id: (json['id'] as num).toInt(),
  person: json['person'] == null
      ? null
      : Person.fromJson(json['person'] as Map<String, dynamic>),
  students: (json['students'] as List<dynamic>?)
      ?.map((e) => Student.fromJson(e as Map<String, dynamic>))
      .toList(),
  status: _statusFromJson(json['status'] as String),
  datestamp: DateTime.parse(json['datestamp'] as String),
);

Map<String, dynamic> _$ParentToJson(Parent instance) => <String, dynamic>{
  'id': instance.id,
  'person': instance.person,
  'students': instance.students,
  'status': _statusToJson(instance.status),
  'datestamp': instance.datestamp.toIso8601String(),
};
