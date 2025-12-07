// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'school.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

School _$SchoolFromJson(Map<String, dynamic> json) => School(
  id: (json['id'] as num).toInt(),
  name: json['name'] as String?,
  email: json['email'] as String?,
  telNumber: json['telNumber'] as String?,
  mobileNumber: json['mobileNumber'] as String?,
  mobileNumber2: json['mobileNumber2'] as String?,
  address: json['address'] as String?,
  website: json['website'] as String?,
  status: _statusFromJson(json['status'] as String),
  datestamp: DateTime.parse(json['datestamp'] as String),
);

Map<String, dynamic> _$SchoolToJson(School instance) => <String, dynamic>{
  'id': instance.id,
  'name': instance.name,
  'email': instance.email,
  'telNumber': instance.telNumber,
  'mobileNumber': instance.mobileNumber,
  'mobileNumber2': instance.mobileNumber2,
  'address': instance.address,
  'website': instance.website,
  'status': _statusToJson(instance.status),
  'datestamp': instance.datestamp.toIso8601String(),
};
