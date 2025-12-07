// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'dorm_year.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

DormYear _$DormYearFromJson(Map<String, dynamic> json) => DormYear(
  id: (json['id'] as num).toInt(),
  dorm: json['dorm'] == null
      ? null
      : Dorm.fromJson(json['dorm'] as Map<String, dynamic>),
  calendarYear: (json['calendarYear'] as num?)?.toInt(),
  status: _statusFromJson(json['status'] as String),
  datestamp: DateTime.parse(json['datestamp'] as String),
);

Map<String, dynamic> _$DormYearToJson(DormYear instance) => <String, dynamic>{
  'id': instance.id,
  'dorm': instance.dorm,
  'calendarYear': instance.calendarYear,
  'status': _statusToJson(instance.status),
  'datestamp': instance.datestamp.toIso8601String(),
};
