import 'package:equatable/equatable.dart';
import 'package:json_annotation/json_annotation.dart';
import '../../core/constants/enums.dart';
import 'dorm.dart';

part 'dorm_year.g.dart';

// Helper functions for enum serialization
Status _statusFromJson(String json) {
  return Status.fromString(json);
}

String _statusToJson(Status status) {
  return status.name.toUpperCase();
}

@JsonSerializable()
class DormYear extends Equatable {
  final int id;
  final Dorm? dorm;
  final int? calendarYear;
  @JsonKey(fromJson: _statusFromJson, toJson: _statusToJson)
  final Status status;
  final DateTime datestamp;

  DormYear({
    required this.id,
    this.dorm,
    this.calendarYear,
    required this.status,
    required this.datestamp,
  });

  String get name => dorm?.name ?? '';

  String get description => dorm?.description ?? '';

  String get address => dorm?.address ?? '';

  factory DormYear.fromJson(Map<String, dynamic> json) => _$DormYearFromJson(json);

  Map<String, dynamic> toJson() => _$DormYearToJson(this);

  DormYear copyWith({
    int? id,
    Dorm? dorm,
    int? calendarYear,
    Status? status,
    DateTime? datestamp,
  }) {
    return DormYear(
      id: id ?? this.id,
      dorm: dorm ?? this.dorm,
      calendarYear: calendarYear ?? this.calendarYear,
      status: status ?? this.status,
      datestamp: datestamp ?? this.datestamp,
    );
  }

  @override
  List<Object?> get props => [
        id,
        dorm,
        calendarYear,
        status,
        datestamp,
      ];
}