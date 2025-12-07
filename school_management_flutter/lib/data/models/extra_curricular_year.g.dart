// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'extra_curricular_year.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ExtraCurricularYear _$ExtraCurricularYearFromJson(Map<String, dynamic> json) =>
    ExtraCurricularYear(
      id: (json['id'] as num).toInt(),
      calendarYear: (json['calendarYear'] as num?)?.toInt(),
      extraCurricular: json['extraCurricular'] == null
          ? null
          : ExtraCurricular.fromJson(
              json['extraCurricular'] as Map<String, dynamic>,
            ),
      facilitators: (json['facilitators'] as List<dynamic>?)
          ?.map((e) => Person.fromJson(e as Map<String, dynamic>))
          .toList(),
      students: (json['students'] as List<dynamic>?)
          ?.map((e) => Student.fromJson(e as Map<String, dynamic>))
          .toList(),
      leadFacilitator: json['leadFacilitator'] == null
          ? null
          : Person.fromJson(json['leadFacilitator'] as Map<String, dynamic>),
      status: _statusFromJson(json['status'] as String),
      datestamp: DateTime.parse(json['datestamp'] as String),
    );

Map<String, dynamic> _$ExtraCurricularYearToJson(
  ExtraCurricularYear instance,
) => <String, dynamic>{
  'id': instance.id,
  'calendarYear': instance.calendarYear,
  'extraCurricular': instance.extraCurricular,
  'facilitators': instance.facilitators,
  'students': instance.students,
  'leadFacilitator': instance.leadFacilitator,
  'status': _statusToJson(instance.status),
  'datestamp': instance.datestamp.toIso8601String(),
};
