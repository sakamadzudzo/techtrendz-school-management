part of 'rating_bloc.dart';

abstract class RatingEvent {}

class RatingLoadRequested extends RatingEvent {}

class RatingLoadByFilterRequested extends RatingEvent {
  final Map<String, dynamic> filters;

  RatingLoadByFilterRequested(this.filters);
}

class RatingLoadByIdRequested extends RatingEvent {
  final int id;

  RatingLoadByIdRequested(this.id);
}

class RatingCreateRequested extends RatingEvent {
  final Rating rating;

  RatingCreateRequested(this.rating);
}

class RatingUpdateRequested extends RatingEvent {
  final Rating rating;

  RatingUpdateRequested(this.rating);
}

class RatingDeleteRequested extends RatingEvent {
  final int id;

  RatingDeleteRequested(this.id);
}

class RatingDropdownLoadRequested extends RatingEvent {}

class RatingByActivityPointLoadRequested extends RatingEvent {
  final int activityPointId;

  RatingByActivityPointLoadRequested(this.activityPointId);
}