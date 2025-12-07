import 'package:equatable/equatable.dart';
import 'package:json_annotation/json_annotation.dart';
import '../../core/constants/enums.dart';
import 'coursework.dart';
import 'student.dart';

part 'mark.g.dart';

// Helper functions for enum serialization
Status _statusFromJson(String json) {
  return Status.fromString(json);
}

String _statusToJson(Status status) {
  return status.name.toUpperCase();
}

@JsonSerializable()
class Mark extends Equatable {
  final int id;
  final Coursework? coursework;
  final Student? student;
  final double? mark;
  final String? subjectName;
  @JsonKey(fromJson: _statusFromJson, toJson: _statusToJson)
  final Status status;
  final DateTime datestamp;

  Mark({
    required this.id,
    this.coursework,
    this.student,
    this.mark,
    this.subjectName,
    required this.status,
    required this.datestamp,
  });

  String get studentName => student?.fullname ?? 'Unknown Student';

  String get courseworkTitle => coursework?.displayTitle ?? 'Unknown Coursework';

  bool get isPassed {
    if (mark == null || coursework?.passMark == null) return false;
    return mark! >= coursework!.passMark!;
  }

  // Grade calculation is handled in the backend
  // This getter is kept for backward compatibility but should be removed
  // in favor of getting grade from the API response
  String get grade => 'N/A'; // Placeholder until backend provides grade

  factory Mark.fromJson(Map<String, dynamic> json) => _$MarkFromJson(json);

  Map<String, dynamic> toJson() => _$MarkToJson(this);

  Mark copyWith({
    int? id,
    Coursework? coursework,
    Student? student,
    double? mark,
    String? subjectName,
    Status? status,
    DateTime? datestamp,
  }) {
    return Mark(
      id: id ?? this.id,
      coursework: coursework ?? this.coursework,
      student: student ?? this.student,
      mark: mark ?? this.mark,
      subjectName: subjectName ?? this.subjectName,
      status: status ?? this.status,
      datestamp: datestamp ?? this.datestamp,
    );
  }

  @override
  List<Object?> get props => [
        id,
        coursework,
        student,
        mark,
        subjectName,
        status,
        datestamp,
      ];
}