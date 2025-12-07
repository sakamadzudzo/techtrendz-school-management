// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'classroom.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Classroom _$ClassroomFromJson(Map<String, dynamic> json) => Classroom(
  id: (json['id'] as num).toInt(),
  name: json['name'] as String?,
  description: json['description'] as String?,
  status: _statusFromJson(json['status'] as String),
  datestamp: DateTime.parse(json['datestamp'] as String),
);

Map<String, dynamic> _$ClassroomToJson(Classroom instance) => <String, dynamic>{
  'id': instance.id,
  'name': instance.name,
  'description': instance.description,
  'status': _statusToJson(instance.status),
  'datestamp': instance.datestamp.toIso8601String(),
};
