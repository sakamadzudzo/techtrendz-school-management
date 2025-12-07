import 'package:equatable/equatable.dart';
import 'package:json_annotation/json_annotation.dart';
import '../../core/constants/enums.dart';
import 'school.dart';

part 'dorm.g.dart';

// Helper functions for enum serialization
Status _statusFromJson(String json) {
  return Status.fromString(json);
}

String _statusToJson(Status status) {
  return status.name.toUpperCase();
}

@JsonSerializable()
class Dorm extends Equatable {
  final int id;
  final String? name;
  final String? description;
  final String? address;
  final School? school;
  @JsonKey(fromJson: _statusFromJson, toJson: _statusToJson)
  final Status status;
  final DateTime datestamp;

  Dorm({
    required this.id,
    this.name,
    this.description,
    this.address,
    this.school,
    required this.status,
    required this.datestamp,
  });

  factory Dorm.fromJson(Map<String, dynamic> json) => _$DormFromJson(json);

  Map<String, dynamic> toJson() => _$DormToJson(this);

  Dorm copyWith({
    int? id,
    String? name,
    String? description,
    String? address,
    School? school,
    Status? status,
    DateTime? datestamp,
  }) {
    return Dorm(
      id: id ?? this.id,
      name: name ?? this.name,
      description: description ?? this.description,
      address: address ?? this.address,
      school: school ?? this.school,
      status: status ?? this.status,
      datestamp: datestamp ?? this.datestamp,
    );
  }

  @override
  List<Object?> get props => [
        id,
        name,
        description,
        address,
        school,
        status,
        datestamp,
      ];
}