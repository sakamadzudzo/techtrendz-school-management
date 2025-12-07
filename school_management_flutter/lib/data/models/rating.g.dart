// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'rating.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Rating _$RatingFromJson(Map<String, dynamic> json) => Rating(
  id: (json['id'] as num).toInt(),
  activityPoint: json['activityPoint'] == null
      ? null
      : ActivityPoint.fromJson(json['activityPoint'] as Map<String, dynamic>),
  rating: (json['rating'] as num?)?.toDouble(),
);

Map<String, dynamic> _$RatingToJson(Rating instance) => <String, dynamic>{
  'id': instance.id,
  'activityPoint': instance.activityPoint,
  'rating': instance.rating,
};
