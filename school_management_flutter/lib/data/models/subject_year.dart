import 'package:equatable/equatable.dart';
import 'package:json_annotation/json_annotation.dart';
import '../../core/constants/enums.dart';
import 'subject.dart';
import 'person.dart';

part 'subject_year.g.dart';

// Helper functions for enum serialization
Status _statusFromJson(String json) {
  return Status.fromString(json);
}

String _statusToJson(Status status) {
  return status.name.toUpperCase();
}

@JsonSerializable()
class SubjectYear extends Equatable {
  final int id;
  final Subject? subject;
  final int? calendarYear;
  final Person? teacher;
  @JsonKey(fromJson: _statusFromJson, toJson: _statusToJson)
  final Status status;
  final DateTime datestamp;

  SubjectYear({
    required this.id,
    this.subject,
    this.calendarYear,
    this.teacher,
    required this.status,
    required this.datestamp,
  });

  String get name => subject?.name ?? '';

  String get description => subject?.description ?? '';

  String get code => subject?.code ?? '';

  String get teacherName => teacher?.fullname ?? '';

  factory SubjectYear.fromJson(Map<String, dynamic> json) => _$SubjectYearFromJson(json);

  Map<String, dynamic> toJson() => _$SubjectYearToJson(this);

  SubjectYear copyWith({
    int? id,
    Subject? subject,
    int? calendarYear,
    Person? teacher,
    Status? status,
    DateTime? datestamp,
  }) {
    return SubjectYear(
      id: id ?? this.id,
      subject: subject ?? this.subject,
      calendarYear: calendarYear ?? this.calendarYear,
      teacher: teacher ?? this.teacher,
      status: status ?? this.status,
      datestamp: datestamp ?? this.datestamp,
    );
  }

  @override
  List<Object?> get props => [
        id,
        subject,
        calendarYear,
        teacher,
        status,
        datestamp,
      ];
}