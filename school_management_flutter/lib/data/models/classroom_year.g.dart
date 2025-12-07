// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'classroom_year.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ClassroomYear _$ClassroomYearFromJson(Map<String, dynamic> json) =>
    ClassroomYear(
      id: (json['id'] as num).toInt(),
      classroom: json['classroom'] == null
          ? null
          : Classroom.fromJson(json['classroom'] as Map<String, dynamic>),
      calendarYear: (json['calendarYear'] as num?)?.toInt(),
      subjects: (json['subjects'] as List<dynamic>?)
          ?.map((e) => SubjectYear.fromJson(e as Map<String, dynamic>))
          .toList(),
      homeroomTeacher: json['homeroomTeacher'] == null
          ? null
          : Person.fromJson(json['homeroomTeacher'] as Map<String, dynamic>),
      status: _statusFromJson(json['status'] as String),
      datestamp: DateTime.parse(json['datestamp'] as String),
    );

Map<String, dynamic> _$ClassroomYearToJson(ClassroomYear instance) =>
    <String, dynamic>{
      'id': instance.id,
      'classroom': instance.classroom,
      'calendarYear': instance.calendarYear,
      'subjects': instance.subjects,
      'homeroomTeacher': instance.homeroomTeacher,
      'status': _statusToJson(instance.status),
      'datestamp': instance.datestamp.toIso8601String(),
    };
