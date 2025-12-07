part of 'coursework_bloc.dart';

abstract class CourseworkState extends Equatable {
  const CourseworkState();

  @override
  List<Object?> get props => [];
}

class CourseworkInitial extends CourseworkState {
  const CourseworkInitial();
}

class CourseworkLoading extends CourseworkState {
  const CourseworkLoading();
}

class CourseworkLoaded extends CourseworkState {
  final List<Coursework> courseworks;

  const CourseworkLoaded(this.courseworks);

  @override
  List<Object?> get props => [courseworks];
}

class CourseworkDetailLoaded extends CourseworkState {
  final Coursework coursework;

  const CourseworkDetailLoaded(this.coursework);

  @override
  List<Object?> get props => [coursework];
}

class CourseworkCreated extends CourseworkState {
  final Coursework coursework;

  const CourseworkCreated(this.coursework);

  @override
  List<Object?> get props => [coursework];
}

class CourseworkUpdated extends CourseworkState {
  final Coursework coursework;

  const CourseworkUpdated(this.coursework);

  @override
  List<Object?> get props => [coursework];
}

class CourseworkDeleted extends CourseworkState {
  const CourseworkDeleted();
}

class CourseworkDropdownLoaded extends CourseworkState {
  final List<Coursework> courseworks;

  const CourseworkDropdownLoaded(this.courseworks);

  @override
  List<Object?> get props => [courseworks];
}

class CourseworkError extends CourseworkState {
  final String message;

  const CourseworkError(this.message);

  @override
  List<Object?> get props => [message];
}