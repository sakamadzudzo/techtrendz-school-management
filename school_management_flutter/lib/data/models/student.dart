import 'package:equatable/equatable.dart';
import 'package:json_annotation/json_annotation.dart';
import '../../core/constants/enums.dart';
import 'person.dart';
import 'classroom_year.dart';
import 'dorm_year.dart';
import 'school.dart';

part 'student.g.dart';

// Helper functions for enum serialization
Status _statusFromJson(String json) {
  return Status.fromString(json);
}

String _statusToJson(Status status) {
  return status.name.toUpperCase();
}

@JsonSerializable()
class Student extends Equatable {
  final int id;
  final Person? person;
  final int? startYear;
  final int? endYear;
  final ClassroomYear? classroom;
  final DormYear? dorm;
  final School? school;
  @JsonKey(fromJson: _statusFromJson, toJson: _statusToJson)
  final Status status;
  final DateTime datestamp;

  Student({
    required this.id,
    this.person,
    this.startYear,
    this.endYear,
    this.classroom,
    this.dorm,
    this.school,
    required this.status,
    required this.datestamp,
  });

  String get name => person?.name ?? '';

  String get fullname => person?.fullname ?? '';

  String get avatarUrl => person?.avatarUrl ?? '';

  List<String> get imagesUrls => person?.imagesUrls ?? [];

  factory Student.fromJson(Map<String, dynamic> json) => _$StudentFromJson(json);

  Map<String, dynamic> toJson() => _$StudentToJson(this);

  Student copyWith({
    int? id,
    Person? person,
    int? startYear,
    int? endYear,
    ClassroomYear? classroom,
    DormYear? dorm,
    School? school,
    Status? status,
    DateTime? datestamp,
  }) {
    return Student(
      id: id ?? this.id,
      person: person ?? this.person,
      startYear: startYear ?? this.startYear,
      endYear: endYear ?? this.endYear,
      classroom: classroom ?? this.classroom,
      dorm: dorm ?? this.dorm,
      school: school ?? this.school,
      status: status ?? this.status,
      datestamp: datestamp ?? this.datestamp,
    );
  }

  @override
  List<Object?> get props => [
        id,
        person,
        startYear,
        endYear,
        classroom,
        dorm,
        school,
        status,
        datestamp,
      ];
}