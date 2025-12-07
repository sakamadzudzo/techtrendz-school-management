// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'person.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Person _$PersonFromJson(Map<String, dynamic> json) => Person(
  id: (json['id'] as num).toInt(),
  salutation: _salutationFromJson(json['salutation'] as String?),
  firstName: json['firstName'] as String?,
  otherNames: json['otherNames'] as String?,
  lastName: json['lastName'] as String?,
  email: json['email'] as String?,
  mobileNumber: json['mobileNumber'] as String?,
  mobileNumber2: json['mobileNumber2'] as String?,
  telNumber: json['telNumber'] as String?,
  whatsapp: json['whatsapp'] as String?,
  facebook: json['facebook'] as String?,
  linkedin: json['linkedin'] as String?,
  dateOfBirth: json['dateOfBirth'] == null
      ? null
      : DateTime.parse(json['dateOfBirth'] as String),
  identification: json['identification'] as String,
  status: _statusFromJson(json['status'] as String),
  datestamp: DateTime.parse(json['datestamp'] as String),
  avatarUrl: json['avatarUrl'] as String?,
  imagesUrls: (json['imagesUrls'] as List<dynamic>?)
      ?.map((e) => e as String)
      .toList(),
);

Map<String, dynamic> _$PersonToJson(Person instance) => <String, dynamic>{
  'id': instance.id,
  'salutation': _salutationToJson(instance.salutation),
  'firstName': instance.firstName,
  'otherNames': instance.otherNames,
  'lastName': instance.lastName,
  'email': instance.email,
  'mobileNumber': instance.mobileNumber,
  'mobileNumber2': instance.mobileNumber2,
  'telNumber': instance.telNumber,
  'whatsapp': instance.whatsapp,
  'facebook': instance.facebook,
  'linkedin': instance.linkedin,
  'dateOfBirth': instance.dateOfBirth?.toIso8601String(),
  'identification': instance.identification,
  'status': _statusToJson(instance.status),
  'datestamp': instance.datestamp.toIso8601String(),
  'avatarUrl': instance.avatarUrl,
  'imagesUrls': instance.imagesUrls,
};
