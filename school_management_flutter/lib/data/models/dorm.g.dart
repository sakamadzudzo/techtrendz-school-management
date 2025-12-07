// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'dorm.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Dorm _$DormFromJson(Map<String, dynamic> json) => Dorm(
  id: (json['id'] as num).toInt(),
  name: json['name'] as String?,
  description: json['description'] as String?,
  address: json['address'] as String?,
  school: json['school'] == null
      ? null
      : School.fromJson(json['school'] as Map<String, dynamic>),
  status: _statusFromJson(json['status'] as String),
  datestamp: DateTime.parse(json['datestamp'] as String),
);

Map<String, dynamic> _$DormToJson(Dorm instance) => <String, dynamic>{
  'id': instance.id,
  'name': instance.name,
  'description': instance.description,
  'address': instance.address,
  'school': instance.school,
  'status': _statusToJson(instance.status),
  'datestamp': instance.datestamp.toIso8601String(),
};
