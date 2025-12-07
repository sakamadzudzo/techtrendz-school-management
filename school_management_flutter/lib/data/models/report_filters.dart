import 'package:json_annotation/json_annotation.dart';

part 'report_filters.g.dart';

@JsonSerializable()
class ReportFilters {
  final List<YearDropdown>? students;
  final List<YearDropdown>? teachers;
  final List<CourseworkDropdown>? courseworks;
  final List<YearDropdown>? classrooms;
  final List<YearDropdown>? subjects;
  final List<YearDropdown>? extraCurriculars;
  final List<int>? calendarYears;

  const ReportFilters({
    this.students,
    this.teachers,
    this.courseworks,
    this.classrooms,
    this.subjects,
    this.extraCurriculars,
    this.calendarYears,
  });

  factory ReportFilters.fromJson(Map<String, dynamic> json) =>
      _$ReportFiltersFromJson(json);

  Map<String, dynamic> toJson() => _$ReportFiltersToJson(this);
}

@JsonSerializable()
class YearDropdown {
  final int id;
  final String name;
  final int? calendarYear;

  const YearDropdown({
    required this.id,
    required this.name,
    this.calendarYear,
  });

  factory YearDropdown.fromJson(Map<String, dynamic> json) =>
      _$YearDropdownFromJson(json);

  Map<String, dynamic> toJson() => _$YearDropdownToJson(this);
}

@JsonSerializable()
class CourseworkDropdown {
  final int id;
  final String type;
  final int calendarYear;
  final Subject subject;

  const CourseworkDropdown({
    required this.id,
    required this.type,
    required this.calendarYear,
    required this.subject,
  });

  factory CourseworkDropdown.fromJson(Map<String, dynamic> json) =>
      _$CourseworkDropdownFromJson(json);

  Map<String, dynamic> toJson() => _$CourseworkDropdownToJson(this);
}

@JsonSerializable()
class Subject {
  final int id;
  final String name;
  final String description;
  final String code;
  final String syllabus;
  final String curriculum;
  final String level;
  final String type;
  final String category;

  const Subject({
    required this.id,
    required this.name,
    required this.description,
    required this.code,
    required this.syllabus,
    required this.curriculum,
    required this.level,
    required this.type,
    required this.category,
  });

  factory Subject.fromJson(Map<String, dynamic> json) =>
      _$SubjectFromJson(json);

  Map<String, dynamic> toJson() => _$SubjectToJson(this);
}