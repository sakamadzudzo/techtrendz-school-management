import 'package:equatable/equatable.dart';
import 'package:json_annotation/json_annotation.dart';
import '../../core/constants/enums.dart';
import 'classroom.dart';
import 'subject_year.dart';
import 'person.dart';

part 'classroom_year.g.dart';

// Helper functions for enum serialization
Status _statusFromJson(String json) {
  return Status.fromString(json);
}

String _statusToJson(Status status) {
  return status.name.toUpperCase();
}

@JsonSerializable()
class ClassroomYear extends Equatable {
  final int id;
  final Classroom? classroom;
  final int? calendarYear;
  final List<SubjectYear>? subjects;
  final Person? homeroomTeacher;
  @JsonKey(fromJson: _statusFromJson, toJson: _statusToJson)
  final Status status;
  final DateTime datestamp;

  ClassroomYear({
    required this.id,
    this.classroom,
    this.calendarYear,
    this.subjects,
    this.homeroomTeacher,
    required this.status,
    required this.datestamp,
  });

  String get name => classroom?.name ?? '';

  String get description => classroom?.description ?? '';

  String get homeroomTeacherName => homeroomTeacher?.fullname ?? '';

  int get subjectCount => subjects?.length ?? 0;

  factory ClassroomYear.fromJson(Map<String, dynamic> json) => _$ClassroomYearFromJson(json);

  Map<String, dynamic> toJson() => _$ClassroomYearToJson(this);

  ClassroomYear copyWith({
    int? id,
    Classroom? classroom,
    int? calendarYear,
    List<SubjectYear>? subjects,
    Person? homeroomTeacher,
    Status? status,
    DateTime? datestamp,
  }) {
    return ClassroomYear(
      id: id ?? this.id,
      classroom: classroom ?? this.classroom,
      calendarYear: calendarYear ?? this.calendarYear,
      subjects: subjects ?? this.subjects,
      homeroomTeacher: homeroomTeacher ?? this.homeroomTeacher,
      status: status ?? this.status,
      datestamp: datestamp ?? this.datestamp,
    );
  }

  @override
  List<Object?> get props => [
        id,
        classroom,
        calendarYear,
        subjects,
        homeroomTeacher,
        status,
        datestamp,
      ];
}