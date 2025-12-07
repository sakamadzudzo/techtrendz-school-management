part of 'rating_bloc.dart';

abstract class RatingState extends Equatable {
  const RatingState();

  @override
  List<Object?> get props => [];
}

class RatingInitial extends RatingState {
  const RatingInitial();
}

class RatingLoading extends RatingState {
  const RatingLoading();
}

class RatingLoaded extends RatingState {
  final List<Rating> ratings;

  const RatingLoaded(this.ratings);

  @override
  List<Object?> get props => [ratings];
}

class RatingDetailLoaded extends RatingState {
  final Rating rating;

  const RatingDetailLoaded(this.rating);

  @override
  List<Object?> get props => [rating];
}

class RatingCreated extends RatingState {
  final Rating rating;

  const RatingCreated(this.rating);

  @override
  List<Object?> get props => [rating];
}

class RatingUpdated extends RatingState {
  final Rating rating;

  const RatingUpdated(this.rating);

  @override
  List<Object?> get props => [rating];
}

class RatingDeleted extends RatingState {
  const RatingDeleted();
}

class RatingDropdownLoaded extends RatingState {
  final List<Rating> ratings;

  const RatingDropdownLoaded(this.ratings);

  @override
  List<Object?> get props => [ratings];
}

class RatingError extends RatingState {
  final String message;

  const RatingError(this.message);

  @override
  List<Object?> get props => [message];
}