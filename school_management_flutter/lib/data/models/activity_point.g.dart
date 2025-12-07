// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'activity_point.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ActivityPoint _$ActivityPointFromJson(Map<String, dynamic> json) =>
    ActivityPoint(
      id: (json['id'] as num).toInt(),
      extraCurricularYear: json['extraCurricularYear'] == null
          ? null
          : ExtraCurricularYear.fromJson(
              json['extraCurricularYear'] as Map<String, dynamic>,
            ),
      student: json['student'] == null
          ? null
          : Student.fromJson(json['student'] as Map<String, dynamic>),
      remarks: json['remarks'] as String?,
      ratings: (json['ratings'] as List<dynamic>?)
          ?.map((e) => Rating.fromJson(e as Map<String, dynamic>))
          .toList(),
      status: _statusFromJson(json['status'] as String),
      datestamp: DateTime.parse(json['datestamp'] as String),
    );

Map<String, dynamic> _$ActivityPointToJson(ActivityPoint instance) =>
    <String, dynamic>{
      'id': instance.id,
      'extraCurricularYear': instance.extraCurricularYear,
      'student': instance.student,
      'remarks': instance.remarks,
      'ratings': instance.ratings,
      'status': _statusToJson(instance.status),
      'datestamp': instance.datestamp.toIso8601String(),
    };
