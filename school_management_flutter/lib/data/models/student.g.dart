// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'student.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Student _$StudentFromJson(Map<String, dynamic> json) => Student(
  id: (json['id'] as num).toInt(),
  person: json['person'] == null
      ? null
      : Person.fromJson(json['person'] as Map<String, dynamic>),
  startYear: (json['startYear'] as num?)?.toInt(),
  endYear: (json['endYear'] as num?)?.toInt(),
  classroom: json['classroom'] == null
      ? null
      : ClassroomYear.fromJson(json['classroom'] as Map<String, dynamic>),
  dorm: json['dorm'] == null
      ? null
      : DormYear.fromJson(json['dorm'] as Map<String, dynamic>),
  school: json['school'] == null
      ? null
      : School.fromJson(json['school'] as Map<String, dynamic>),
  status: _statusFromJson(json['status'] as String),
  datestamp: DateTime.parse(json['datestamp'] as String),
);

Map<String, dynamic> _$StudentToJson(Student instance) => <String, dynamic>{
  'id': instance.id,
  'person': instance.person,
  'startYear': instance.startYear,
  'endYear': instance.endYear,
  'classroom': instance.classroom,
  'dorm': instance.dorm,
  'school': instance.school,
  'status': _statusToJson(instance.status),
  'datestamp': instance.datestamp.toIso8601String(),
};
