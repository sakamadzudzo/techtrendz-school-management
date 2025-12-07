import 'package:equatable/equatable.dart';
import 'package:json_annotation/json_annotation.dart';
import '../../core/constants/enums.dart';

part 'person.g.dart';

// Helper functions for enum serialization
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
class Person extends Equatable {
  final int id;
  @JsonKey(fromJson: _salutationFromJson, toJson: _salutationToJson)
  final Salutation? salutation;
  final String? firstName;
  final String? otherNames;
  final String? lastName;
  final String? email;
  final String? mobileNumber;
  final String? mobileNumber2;
  final String? telNumber;
  final String? whatsapp;
  final String? facebook;
  final String? linkedin;
  final DateTime? dateOfBirth;
  final String identification;
  @JsonKey(fromJson: _statusFromJson, toJson: _statusToJson)
  final Status status;
  final DateTime datestamp;
  final String? avatarUrl;
  final List<String>? imagesUrls;

  Person({
    required this.id,
    this.salutation,
    this.firstName,
    this.otherNames,
    this.lastName,
    this.email,
    this.mobileNumber,
    this.mobileNumber2,
    this.telNumber,
    this.whatsapp,
    this.facebook,
    this.linkedin,
    this.dateOfBirth,
    required this.identification,
    required this.status,
    required this.datestamp,
    this.avatarUrl,
    this.imagesUrls,
  });

  String get name => '${firstName ?? ''} ${lastName ?? ''}'.trim();

  String get fullname => '${salutation?.displayName ?? ''} ${firstName ?? ''} ${otherNames ?? ''} ${lastName ?? ''}'.trim();

  factory Person.fromJson(Map<String, dynamic> json) => _$PersonFromJson(json);

  Map<String, dynamic> toJson() => _$PersonToJson(this);

  Person copyWith({
    int? id,
    Salutation? salutation,
    String? firstName,
    String? otherNames,
    String? lastName,
    String? email,
    String? mobileNumber,
    String? mobileNumber2,
    String? telNumber,
    String? whatsapp,
    String? facebook,
    String? linkedin,
    DateTime? dateOfBirth,
    String? identification,
    Status? status,
    DateTime? datestamp,
    String? avatarUrl,
    List<String>? imagesUrls,
  }) {
    return Person(
      id: id ?? this.id,
      salutation: salutation ?? this.salutation,
      firstName: firstName ?? this.firstName,
      otherNames: otherNames ?? this.otherNames,
      lastName: lastName ?? this.lastName,
      email: email ?? this.email,
      mobileNumber: mobileNumber ?? this.mobileNumber,
      mobileNumber2: mobileNumber2 ?? this.mobileNumber2,
      telNumber: telNumber ?? this.telNumber,
      whatsapp: whatsapp ?? this.whatsapp,
      facebook: facebook ?? this.facebook,
      linkedin: linkedin ?? this.linkedin,
      dateOfBirth: dateOfBirth ?? this.dateOfBirth,
      identification: identification ?? this.identification,
      status: status ?? this.status,
      datestamp: datestamp ?? this.datestamp,
      avatarUrl: avatarUrl ?? this.avatarUrl,
      imagesUrls: imagesUrls ?? this.imagesUrls,
    );
  }

  @override
  List<Object?> get props => [
        id,
        salutation,
        firstName,
        otherNames,
        lastName,
        email,
        mobileNumber,
        mobileNumber2,
        telNumber,
        whatsapp,
        facebook,
        linkedin,
        dateOfBirth,
        status,
        datestamp,
        avatarUrl,
        imagesUrls,
      ];
}