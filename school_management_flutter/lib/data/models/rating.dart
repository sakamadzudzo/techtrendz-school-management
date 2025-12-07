import 'package:equatable/equatable.dart';
import 'package:json_annotation/json_annotation.dart';
import 'activity_point.dart';

part 'rating.g.dart';

@JsonSerializable()
class Rating extends Equatable {
  final int id;
  final ActivityPoint? activityPoint;
  final double? rating;

  Rating({
    required this.id,
    this.activityPoint,
    this.rating,
  });

  String get ratingDescription {
    if (rating == null) return 'Not Rated';
    if (rating! >= 5) return 'Outstanding';
    if (rating! >= 4) return 'Excellent';
    if (rating! >= 3) return 'Good';
    if (rating! >= 2) return 'Fair';
    if (rating! >= 1) return 'Poor';
    return 'Very Poor';
  }

  factory Rating.fromJson(Map<String, dynamic> json) => _$RatingFromJson(json);

  Map<String, dynamic> toJson() => _$RatingToJson(this);

  Rating copyWith({
    int? id,
    ActivityPoint? activityPoint,
    double? rating,
  }) {
    return Rating(
      id: id ?? this.id,
      activityPoint: activityPoint ?? this.activityPoint,
      rating: rating ?? this.rating,
    );
  }

  @override
  List<Object?> get props => [
        id,
        activityPoint,
        rating,
      ];
}