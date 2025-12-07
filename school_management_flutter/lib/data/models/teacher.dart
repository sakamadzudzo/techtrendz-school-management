import 'package:equatable/equatable.dart';
import 'package:json_annotation/json_annotation.dart';
import '../../core/constants/enums.dart';
import 'person.dart';

part 'teacher.g.dart';

// Helper functions for enum serialization
Status _statusFromJson(String json) {
  return Status.fromString(json);
}

String _statusToJson(Status status) {
  return status.name.toUpperCase();
}

@JsonSerializable()
class Teacher extends Equatable {
  final int id;
  final Person? person;
  @JsonKey(fromJson: _statusFromJson, toJson: _statusToJson)
  final Status status;
  final DateTime datestamp;

  Teacher({
    required this.id,
    this.person,
    required this.status,
    required this.datestamp,
  });

  String get name => person?.name ?? '';

  String get fullname => person?.fullname ?? '';

  String get avatarUrl => person?.avatarUrl ?? '';

  List<String> get imagesUrls => person?.imagesUrls ?? [];

  factory Teacher.fromJson(Map<String, dynamic> json) => _$TeacherFromJson(json);

  Map<String, dynamic> toJson() => _$TeacherToJson(this);

  Teacher copyWith({
    int? id,
    Person? person,
    Status? status,
    DateTime? datestamp,
  }) {
    return Teacher(
      id: id ?? this.id,
      person: person ?? this.person,
      status: status ?? this.status,
      datestamp: datestamp ?? this.datestamp,
    );
  }

  @override
  List<Object?> get props => [
        id,
        person,
        status,
        datestamp,
      ];
}