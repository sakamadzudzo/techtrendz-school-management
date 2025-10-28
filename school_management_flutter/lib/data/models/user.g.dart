// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'user.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

User _$UserFromJson(Map<String, dynamic> json) => User(
  id: (json['id'] as num).toInt(),
  personId: (json['personId'] as num?)?.toInt(),
  salutation: _salutationFromJson(json['salutation'] as String?),
  email: json['email'] as String,
  firstName: json['firstName'] as String?,
  otherNames: json['otherNames'] as String?,
  lastName: json['lastName'] as String?,
  roles: _rolesFromJson(json['roles'] as List),
  status: _statusFromJson(json['status'] as String),
  mobileNumber: json['mobileNumber'] as String?,
  mobileNumber2: json['mobileNumber2'] as String?,
  telNumber: json['telNumber'] as String?,
  whatsapp: json['whatsapp'] as String?,
  facebook: json['facebook'] as String?,
  linkedin: json['linkedin'] as String?,
  username: json['username'] as String,
  password: json['password'] as String?,
  verified: json['verified'] as bool?,
  enabled: json['enabled'] as bool?,
  changePassword: json['changePassword'] as bool?,
  dateOfBirth: json['dateOfBirth'] == null
      ? null
      : DateTime.parse(json['dateOfBirth'] as String),
  datestamp: DateTime.parse(json['datestamp'] as String),
);

Map<String, dynamic> _$UserToJson(User instance) => <String, dynamic>{
  'id': instance.id,
  'personId': instance.personId,
  'salutation': _salutationToJson(instance.salutation),
  'email': instance.email,
  'firstName': instance.firstName,
  'otherNames': instance.otherNames,
  'lastName': instance.lastName,
  'roles': _rolesToJson(instance.roles),
  'status': _statusToJson(instance.status),
  'mobileNumber': instance.mobileNumber,
  'mobileNumber2': instance.mobileNumber2,
  'telNumber': instance.telNumber,
  'whatsapp': instance.whatsapp,
  'facebook': instance.facebook,
  'linkedin': instance.linkedin,
  'username': instance.username,
  'password': instance.password,
  'verified': instance.verified,
  'enabled': instance.enabled,
  'changePassword': instance.changePassword,
  'dateOfBirth': instance.dateOfBirth?.toIso8601String(),
  'datestamp': instance.datestamp.toIso8601String(),
};
