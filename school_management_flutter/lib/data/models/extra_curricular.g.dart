// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'extra_curricular.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ExtraCurricular _$ExtraCurricularFromJson(Map<String, dynamic> json) =>
    ExtraCurricular(
      id: (json['id'] as num).toInt(),
      name: json['name'] as String?,
      description: json['description'] as String?,
      status: _statusFromJson(json['status'] as String),
      datestamp: DateTime.parse(json['datestamp'] as String),
    );

Map<String, dynamic> _$ExtraCurricularToJson(ExtraCurricular instance) =>
    <String, dynamic>{
      'id': instance.id,
      'name': instance.name,
      'description': instance.description,
      'status': _statusToJson(instance.status),
      'datestamp': instance.datestamp.toIso8601String(),
    };
