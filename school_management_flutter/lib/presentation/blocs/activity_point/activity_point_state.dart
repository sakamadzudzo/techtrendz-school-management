part of 'activity_point_bloc.dart';

abstract class ActivityPointState extends Equatable {
  const ActivityPointState();

  @override
  List<Object?> get props => [];
}

class ActivityPointInitial extends ActivityPointState {
  const ActivityPointInitial();
}

class ActivityPointLoading extends ActivityPointState {
  const ActivityPointLoading();
}

class ActivityPointLoaded extends ActivityPointState {
  final List<ActivityPoint> activityPoints;

  const ActivityPointLoaded(this.activityPoints);

  @override
  List<Object?> get props => [activityPoints];
}

class ActivityPointDetailLoaded extends ActivityPointState {
  final ActivityPoint activityPoint;

  const ActivityPointDetailLoaded(this.activityPoint);

  @override
  List<Object?> get props => [activityPoint];
}

class ActivityPointCreated extends ActivityPointState {
  final ActivityPoint activityPoint;

  const ActivityPointCreated(this.activityPoint);

  @override
  List<Object?> get props => [activityPoint];
}

class ActivityPointUpdated extends ActivityPointState {
  final ActivityPoint activityPoint;

  const ActivityPointUpdated(this.activityPoint);

  @override
  List<Object?> get props => [activityPoint];
}

class ActivityPointDeleted extends ActivityPointState {
  const ActivityPointDeleted();
}

class ActivityPointDropdownLoaded extends ActivityPointState {
  final List<ActivityPoint> activityPoints;

  const ActivityPointDropdownLoaded(this.activityPoints);

  @override
  List<Object?> get props => [activityPoints];
}

class ActivityPointError extends ActivityPointState {
  final String message;

  const ActivityPointError(this.message);

  @override
  List<Object?> get props => [message];
}