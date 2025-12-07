import 'package:equatable/equatable.dart';
import 'package:json_annotation/json_annotation.dart';
import '../../core/constants/enums.dart';

part 'school.g.dart';

// Helper functions for enum serialization
Status _statusFromJson(String json) {
  return Status.fromString(json);
}

String _statusToJson(Status status) {
  return status.name.toUpperCase();
}

@JsonSerializable()
class School extends Equatable {
  final int id;
  final String? name;
  final String? email;
  final String? telNumber;
  final String? mobileNumber;
  final String? mobileNumber2;
  final String? address;
  final String? website;
  @JsonKey(fromJson: _statusFromJson, toJson: _statusToJson)
  final Status status;
  final DateTime datestamp;

  School({
    required this.id,
    this.name,
    this.email,
    this.telNumber,
    this.mobileNumber,
    this.mobileNumber2,
    this.address,
    this.website,
    required this.status,
    required this.datestamp,
  });

  factory School.fromJson(Map<String, dynamic> json) => _$SchoolFromJson(json);

  Map<String, dynamic> toJson() => _$SchoolToJson(this);

  School copyWith({
    int? id,
    String? name,
    String? email,
    String? telNumber,
    String? mobileNumber,
    String? mobileNumber2,
    String? address,
    String? website,
    Status? status,
    DateTime? datestamp,
  }) {
    return School(
      id: id ?? this.id,
      name: name ?? this.name,
      email: email ?? this.email,
      telNumber: telNumber ?? this.telNumber,
      mobileNumber: mobileNumber ?? this.mobileNumber,
      mobileNumber2: mobileNumber2 ?? this.mobileNumber2,
      address: address ?? this.address,
      website: website ?? this.website,
      status: status ?? this.status,
      datestamp: datestamp ?? this.datestamp,
    );
  }

  @override
  List<Object?> get props => [
        id,
        name,
        email,
        telNumber,
        mobileNumber,
        mobileNumber2,
        address,
        website,
        status,
        datestamp,
      ];
}