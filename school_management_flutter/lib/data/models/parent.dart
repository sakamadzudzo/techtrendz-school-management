import 'package:equatable/equatable.dart';
import 'package:json_annotation/json_annotation.dart';
import '../../core/constants/enums.dart';
import 'person.dart';
import 'student.dart';

part 'parent.g.dart';

// Helper functions for enum serialization
Status _statusFromJson(String json) {
  return Status.fromString(json);
}

String _statusToJson(Status status) {
  return status.name.toUpperCase();
}

@JsonSerializable()
class Parent extends Equatable {
  final int id;
  final Person? person;
  final List<Student>? students;
  @JsonKey(fromJson: _statusFromJson, toJson: _statusToJson)
  final Status status;
  final DateTime datestamp;

  Parent({
    required this.id,
    this.person,
    this.students,
    required this.status,
    required this.datestamp,
  });

  String get name => person?.name ?? '';

  String get fullname => person?.fullname ?? '';

  String get avatarUrl => person?.avatarUrl ?? '';

  List<String> get imagesUrls => person?.imagesUrls ?? [];

  int get studentCount => students?.length ?? 0;

  factory Parent.fromJson(Map<String, dynamic> json) => _$ParentFromJson(json);

  Map<String, dynamic> toJson() => _$ParentToJson(this);

  Parent copyWith({
    int? id,
    Person? person,
    List<Student>? students,
    Status? status,
    DateTime? datestamp,
  }) {
    return Parent(
      id: id ?? this.id,
      person: person ?? this.person,
      students: students ?? this.students,
      status: status ?? this.status,
      datestamp: datestamp ?? this.datestamp,
    );
  }

  @override
  List<Object?> get props => [
        id,
        person,
        students,
        status,
        datestamp,
      ];
}