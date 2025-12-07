import 'package:equatable/equatable.dart';
import 'package:json_annotation/json_annotation.dart';
import '../../core/constants/enums.dart';
import 'extra_curricular.dart';
import 'person.dart';
import 'student.dart';

part 'extra_curricular_year.g.dart';

// Helper functions for enum serialization
Status _statusFromJson(String json) {
  return Status.fromString(json);
}

String _statusToJson(Status status) {
  return status.name.toUpperCase();
}

@JsonSerializable()
class ExtraCurricularYear extends Equatable {
  final int id;
  final int? calendarYear;
  final ExtraCurricular? extraCurricular;
  final List<Person>? facilitators;
  final List<Student>? students;
  final Person? leadFacilitator;
  @JsonKey(fromJson: _statusFromJson, toJson: _statusToJson)
  final Status status;
  final DateTime datestamp;

  ExtraCurricularYear({
    required this.id,
    this.calendarYear,
    this.extraCurricular,
    this.facilitators,
    this.students,
    this.leadFacilitator,
    required this.status,
    required this.datestamp,
  });

  String get name => extraCurricular?.name ?? '';

  String get description => extraCurricular?.description ?? '';

  String get leadFacilitatorName => leadFacilitator?.fullname ?? '';

  int get facilitatorCount => facilitators?.length ?? 0;

  int get studentCount => students?.length ?? 0;

  factory ExtraCurricularYear.fromJson(Map<String, dynamic> json) => _$ExtraCurricularYearFromJson(json);

  Map<String, dynamic> toJson() => _$ExtraCurricularYearToJson(this);

  ExtraCurricularYear copyWith({
    int? id,
    int? calendarYear,
    ExtraCurricular? extraCurricular,
    List<Person>? facilitators,
    List<Student>? students,
    Person? leadFacilitator,
    Status? status,
    DateTime? datestamp,
  }) {
    return ExtraCurricularYear(
      id: id ?? this.id,
      calendarYear: calendarYear ?? this.calendarYear,
      extraCurricular: extraCurricular ?? this.extraCurricular,
      facilitators: facilitators ?? this.facilitators,
      students: students ?? this.students,
      leadFacilitator: leadFacilitator ?? this.leadFacilitator,
      status: status ?? this.status,
      datestamp: datestamp ?? this.datestamp,
    );
  }

  @override
  List<Object?> get props => [
        id,
        calendarYear,
        extraCurricular,
        facilitators,
        students,
        leadFacilitator,
        status,
        datestamp,
      ];
}