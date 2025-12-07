import 'package:equatable/equatable.dart';
import 'package:json_annotation/json_annotation.dart';
import '../../core/constants/enums.dart';

part 'subject.g.dart';

// Helper functions for enum serialization
Status _statusFromJson(String json) {
  return Status.fromString(json);
}

String _statusToJson(Status status) {
  return status.name.toUpperCase();
}

enum SubjectType {
  CORE,
  ELECTIVE,
  AP,
  PHYSICAL,
  OTHER;

  String get displayName {
    switch (this) {
      case SubjectType.CORE:
        return 'Core';
      case SubjectType.ELECTIVE:
        return 'Elective';
      case SubjectType.AP:
        return 'Advanced Placement';
      case SubjectType.PHYSICAL:
        return 'Physical';
      case SubjectType.OTHER:
        return 'Other';
    }
  }

  static SubjectType fromString(String type) {
    return SubjectType.values.firstWhere(
      (e) => e.name.toLowerCase() == type.toLowerCase(),
      orElse: () => SubjectType.OTHER,
    );
  }
}

SubjectType? _subjectTypeFromJson(String? json) {
  return json == null ? null : SubjectType.fromString(json);
}

String? _subjectTypeToJson(SubjectType? type) {
  return type?.name;
}

@JsonSerializable()
class Subject extends Equatable {
  final int id;
  final String? name;
  final String? description;
  final String? code;
  final String? syllabus;
  final String? curriculum;
  final String? level;
  @JsonKey(fromJson: _subjectTypeFromJson, toJson: _subjectTypeToJson)
  final SubjectType? type;
  final String? category;
  @JsonKey(fromJson: _statusFromJson, toJson: _statusToJson)
  final Status status;
  final DateTime datestamp;

  Subject({
    required this.id,
    this.name,
    this.description,
    this.code,
    this.syllabus,
    this.curriculum,
    this.level,
    this.type,
    this.category,
    required this.status,
    required this.datestamp,
  });

  factory Subject.fromJson(Map<String, dynamic> json) => _$SubjectFromJson(json);

  Map<String, dynamic> toJson() => _$SubjectToJson(this);

  Subject copyWith({
    int? id,
    String? name,
    String? description,
    String? code,
    String? syllabus,
    String? curriculum,
    String? level,
    SubjectType? type,
    String? category,
    Status? status,
    DateTime? datestamp,
  }) {
    return Subject(
      id: id ?? this.id,
      name: name ?? this.name,
      description: description ?? this.description,
      code: code ?? this.code,
      syllabus: syllabus ?? this.syllabus,
      curriculum: curriculum ?? this.curriculum,
      level: level ?? this.level,
      type: type ?? this.type,
      category: category ?? this.category,
      status: status ?? this.status,
      datestamp: datestamp ?? this.datestamp,
    );
  }

  @override
  List<Object?> get props => [
        id,
        name,
        description,
        code,
        syllabus,
        curriculum,
        level,
        type,
        category,
        status,
        datestamp,
      ];
}