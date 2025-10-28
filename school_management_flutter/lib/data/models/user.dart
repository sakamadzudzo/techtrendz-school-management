import 'package:equatable/equatable.dart';
import 'package:json_annotation/json_annotation.dart';
import '../../core/constants/enums.dart';

part 'user.g.dart';

// Helper functions for enum serialization
List<Role> _rolesFromJson(List<dynamic> json) {
  return json.map((e) => Role.fromString(e as String)).toList();
}

List<String> _rolesToJson(List<Role> roles) {
  return roles.map((e) => e.name.toUpperCase()).toList();
}

Status _statusFromJson(String json) {
  return Status.fromString(json);
}

String _statusToJson(Status status) {
  return status.name.toUpperCase();
}

Salutation? _salutationFromJson(String? json) {
  return json == null ? null : Salutation.fromString(json);
}

String? _salutationToJson(Salutation? salutation) {
  return salutation?.name.toUpperCase();
}

@JsonSerializable()
class User extends Equatable {
  final int id;
  final int? personId;
  @JsonKey(fromJson: _salutationFromJson, toJson: _salutationToJson)
  final Salutation? salutation;
  final String email;
  final String? firstName;
  final String? otherNames;
  final String? lastName;
  @JsonKey(fromJson: _rolesFromJson, toJson: _rolesToJson)
  final List<Role> roles;
  @JsonKey(fromJson: _statusFromJson, toJson: _statusToJson)
  final Status status;
  final String? mobileNumber;
  final String? mobileNumber2;
  final String? telNumber;
  final String? whatsapp;
  final String? facebook;
  final String? linkedin;
  final String username;
  final String? password;
  final bool? verified;
  final bool? enabled;
  final bool? changePassword;
  final DateTime? dateOfBirth;
  final DateTime datestamp;

  const User({
    required this.id,
    this.personId,
    this.salutation,
    required this.email,
    required this.firstName,
    this.otherNames,
    required this.lastName,
    required this.roles,
    required this.status,
    this.mobileNumber,
    this.mobileNumber2,
    this.telNumber,
    this.whatsapp,
    this.facebook,
    this.linkedin,
    required this.username,
    this.password,
    required this.verified,
    required this.enabled,
    required this.changePassword,
    this.dateOfBirth,
    required this.datestamp,
  });

  String get fullName => '$firstName $lastName';

  bool get isActive => status == Status.ACTIVE;

  bool get isAdmin => roles.contains(Role.ADMIN);

  bool get isTeacher => roles.contains(Role.TEACHER);

  bool get isStaff => roles.contains(Role.STAFF);

  bool get isParent => roles.contains(Role.PARENT);

  factory User.fromJson(Map<String, dynamic> json) => _$UserFromJson(json);

  Map<String, dynamic> toJson() => _$UserToJson(this);

  User copyWith({
    int? id,
    int? personId,
    Salutation? salutation,
    String? email,
    String? firstName,
    String? otherNames,
    String? lastName,
    List<Role>? roles,
    Status? status,
    String? mobileNumber,
    String? mobileNumber2,
    String? telNumber,
    String? whatsapp,
    String? facebook,
    String? linkedin,
    String? username,
    String? password,
    bool? verified,
    bool? enabled,
    bool? changePassword,
    DateTime? dateOfBirth,
    DateTime? datestamp,
  }) {
    return User(
      id: id ?? this.id,
      personId: personId ?? this.personId,
      salutation: salutation ?? this.salutation,
      email: email ?? this.email,
      firstName: firstName ?? this.firstName,
      otherNames: otherNames ?? this.otherNames,
      lastName: lastName ?? this.lastName,
      roles: roles ?? this.roles,
      status: status ?? this.status,
      mobileNumber: mobileNumber ?? this.mobileNumber,
      mobileNumber2: mobileNumber2 ?? this.mobileNumber2,
      telNumber: telNumber ?? this.telNumber,
      whatsapp: whatsapp ?? this.whatsapp,
      facebook: facebook ?? this.facebook,
      linkedin: linkedin ?? this.linkedin,
      username: username ?? this.username,
      password: password ?? this.password,
      verified: verified ?? this.verified,
      enabled: enabled ?? this.enabled,
      changePassword: changePassword ?? this.changePassword,
      dateOfBirth: dateOfBirth ?? this.dateOfBirth,
      datestamp: datestamp ?? this.datestamp,
    );
  }

  @override
  List<Object?> get props => [
        id,
        personId,
        salutation,
        email,
        firstName,
        otherNames,
        lastName,
        roles,
        status,
        mobileNumber,
        mobileNumber2,
        telNumber,
        whatsapp,
        facebook,
        linkedin,
        username,
        password,
        verified,
        enabled,
        changePassword,
        dateOfBirth,
        datestamp,
      ];
}