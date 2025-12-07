part of 'activity_point_bloc.dart';

abstract class ActivityPointEvent {}

class ActivityPointLoadRequested extends ActivityPointEvent {}

class ActivityPointLoadByFilterRequested extends ActivityPointEvent {
  final Map<String, dynamic> filters;

  ActivityPointLoadByFilterRequested(this.filters);
}

class ActivityPointLoadByIdRequested extends ActivityPointEvent {
  final int id;

  ActivityPointLoadByIdRequested(this.id);
}

class ActivityPointCreateRequested extends ActivityPointEvent {
  final ActivityPoint activityPoint;

  ActivityPointCreateRequested(this.activityPoint);
}

class ActivityPointUpdateRequested extends ActivityPointEvent {
  final ActivityPoint activityPoint;

  ActivityPointUpdateRequested(this.activityPoint);
}

class ActivityPointDeleteRequested extends ActivityPointEvent {
  final int id;

  ActivityPointDeleteRequested(this.id);
}

class ActivityPointDropdownLoadRequested extends ActivityPointEvent {}

class ActivityPointByStudentLoadRequested extends ActivityPointEvent {
  final int studentId;

  ActivityPointByStudentLoadRequested(this.studentId);
}

class ActivityPointByActivityLoadRequested extends ActivityPointEvent {
  final int activityId;

  ActivityPointByActivityLoadRequested(this.activityId);
}