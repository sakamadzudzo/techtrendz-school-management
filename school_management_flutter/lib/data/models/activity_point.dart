import 'package:equatable/equatable.dart';
import 'package:json_annotation/json_annotation.dart';
import '../../core/constants/enums.dart';
import 'extra_curricular_year.dart';
import 'student.dart';
import 'rating.dart';

part 'activity_point.g.dart';

// Helper functions for enum serialization
Status _statusFromJson(String json) {
  return Status.fromString(json);
}

String _statusToJson(Status status) {
  return status.name.toUpperCase();
}

@JsonSerializable()
class ActivityPoint extends Equatable {
  final int id;
  final ExtraCurricularYear? extraCurricularYear;
  final Student? student;
  final String? remarks;
  final List<Rating>? ratings;
  @JsonKey(fromJson: _statusFromJson, toJson: _statusToJson)
  final Status status;
  final DateTime datestamp;

  ActivityPoint({
    required this.id,
    this.extraCurricularYear,
    this.student,
    this.remarks,
    this.ratings,
    required this.status,
    required this.datestamp,
  });

  String get studentName => student?.fullname ?? 'Unknown Student';

  String get activityName => extraCurricularYear?.extraCurricular?.name ?? 'Unknown Activity';

  double get averageRating {
    if (ratings == null || ratings!.isEmpty) return 0.0;
    final validRatings = ratings!.where((rating) => rating.rating != null);
    if (validRatings.isEmpty) return 0.0;
    return validRatings.map((rating) => rating.rating!).reduce((a, b) => a + b) / validRatings.length;
  }

  int get ratingsCount => ratings?.length ?? 0;

  // Performance level calculation is handled in the backend
  // This getter is kept for backward compatibility but should be removed
  // in favor of getting performance level from the API response
  String get performanceLevel => 'Unknown'; // Placeholder until backend provides performance level

  factory ActivityPoint.fromJson(Map<String, dynamic> json) => _$ActivityPointFromJson(json);

  Map<String, dynamic> toJson() => _$ActivityPointToJson(this);

  ActivityPoint copyWith({
    int? id,
    ExtraCurricularYear? extraCurricularYear,
    Student? student,
    String? remarks,
    List<Rating>? ratings,
    Status? status,
    DateTime? datestamp,
  }) {
    return ActivityPoint(
      id: id ?? this.id,
      extraCurricularYear: extraCurricularYear ?? this.extraCurricularYear,
      student: student ?? this.student,
      remarks: remarks ?? this.remarks,
      ratings: ratings ?? this.ratings,
      status: status ?? this.status,
      datestamp: datestamp ?? this.datestamp,
    );
  }

  @override
  List<Object?> get props => [
        id,
        extraCurricularYear,
        student,
        remarks,
        ratings,
        status,
        datestamp,
      ];
}