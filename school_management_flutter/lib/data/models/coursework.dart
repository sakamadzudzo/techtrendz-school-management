import 'package:equatable/equatable.dart';
import 'package:json_annotation/json_annotation.dart';
import '../../core/constants/enums.dart';
import 'subject_year.dart';
import 'mark.dart';

part 'coursework.g.dart';

// Helper functions for enum serialization
Status _statusFromJson(String json) {
  return Status.fromString(json);
}

String _statusToJson(Status status) {
  return status.name.toUpperCase();
}

enum CourseworkType {
  ASSIGNMENT,
  QUIZ,
  TEST,
  PRACTICAL,
  EXTRA;

  String get displayName {
    switch (this) {
      case CourseworkType.ASSIGNMENT:
        return 'Assignment';
      case CourseworkType.QUIZ:
        return 'Quiz';
      case CourseworkType.TEST:
        return 'Test';
      case CourseworkType.PRACTICAL:
        return 'Practical';
      case CourseworkType.EXTRA:
        return 'Extra';
    }
  }

  static CourseworkType fromString(String type) {
    return CourseworkType.values.firstWhere(
      (e) => e.name.toLowerCase() == type.toLowerCase(),
      orElse: () => CourseworkType.ASSIGNMENT,
    );
  }
}

CourseworkType? _courseworkTypeFromJson(String? json) {
  return json == null ? null : CourseworkType.fromString(json);
}

String? _courseworkTypeToJson(CourseworkType? type) {
  return type?.name;
}

@JsonSerializable()
class Coursework extends Equatable {
  final int id;
  final String? title;
  @JsonKey(fromJson: _courseworkTypeFromJson, toJson: _courseworkTypeToJson)
  final CourseworkType? type;
  final SubjectYear? subject;
  @JsonKey(fromJson: _statusFromJson, toJson: _statusToJson)
  final Status status;
  final DateTime datestamp;
  final String? subjectName;
  final double total;
  final double passMark;
  final List<Mark>? marks;

  Coursework({
    required this.id,
    this.title,
    this.type,
    this.subject,
    required this.status,
    required this.datestamp,
    this.subjectName,
    required this.total,
    required this.passMark,
    this.marks,
  });

  String get displayTitle => title ?? 'Untitled Coursework';

  String get typeDisplay => type?.displayName ?? 'Unknown';

  int get marksCount => marks?.length ?? 0;

  double get averageMark {
    if (marks == null || marks!.isEmpty) return 0.0;
    final validMarks = marks!.where((mark) => mark.mark != null);
    if (validMarks.isEmpty) return 0.0;
    return validMarks.map((mark) => mark.mark!).reduce((a, b) => a + b) / validMarks.length;
  }

  int get passCount {
    if (marks == null || passMark == null) return 0;
    return marks!.where((mark) => mark.mark != null && mark.mark! >= passMark!).length;
  }

  double get passRate {
    if (marks == null || marks!.isEmpty) return 0.0;
    return (passCount / marks!.length) * 100;
  }

  factory Coursework.fromJson(Map<String, dynamic> json) => _$CourseworkFromJson(json);

  Map<String, dynamic> toJson() => _$CourseworkToJson(this);

  Coursework copyWith({
    int? id,
    String? title,
    CourseworkType? type,
    SubjectYear? subject,
    Status? status,
    DateTime? datestamp,
    String? subjectName,
    double? total,
    double? passMark,
    List<Mark>? marks,
  }) {
    return Coursework(
      id: id ?? this.id,
      title: title ?? this.title,
      type: type ?? this.type,
      subject: subject ?? this.subject,
      status: status ?? this.status,
      datestamp: datestamp ?? this.datestamp,
      subjectName: subjectName ?? this.subjectName,
      total: total ?? this.total,
      passMark: passMark ?? this.passMark,
      marks: marks ?? this.marks,
    );
  }

  @override
  List<Object?> get props => [
        id,
        title,
        type,
        subject,
        status,
        datestamp,
        subjectName,
        total,
        passMark,
        marks,
      ];
}