import 'package:equatable/equatable.dart';
import 'package:json_annotation/json_annotation.dart';
import '../../core/constants/enums.dart';

part 'classroom.g.dart';

// Helper functions for enum serialization
Status _statusFromJson(String json) {
  return Status.fromString(json);
}

String _statusToJson(Status status) {
  return status.name.toUpperCase();
}

@JsonSerializable()
class Classroom extends Equatable {
  final int id;
  final String? name;
  final String? description;
  @JsonKey(fromJson: _statusFromJson, toJson: _statusToJson)
  final Status status;
  final DateTime datestamp;

  Classroom({
    required this.id,
    this.name,
    this.description,
    required this.status,
    required this.datestamp,
  });

  factory Classroom.fromJson(Map<String, dynamic> json) => _$ClassroomFromJson(json);

  Map<String, dynamic> toJson() => _$ClassroomToJson(this);

  Classroom copyWith({
    int? id,
    String? name,
    String? description,
    Status? status,
    DateTime? datestamp,
  }) {
    return Classroom(
      id: id ?? this.id,
      name: name ?? this.name,
      description: description ?? this.description,
      status: status ?? this.status,
      datestamp: datestamp ?? this.datestamp,
    );
  }

  @override
  List<Object?> get props => [
        id,
        name,
        description,
        status,
        datestamp,
      ];
}