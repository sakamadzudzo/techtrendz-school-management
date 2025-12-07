part of 'coursework_bloc.dart';

abstract class CourseworkEvent extends Equatable {
  const CourseworkEvent();

  @override
  List<Object?> get props => [];
}

class CourseworkLoadRequested extends CourseworkEvent {
  const CourseworkLoadRequested();
}

class CourseworkLoadByFilterRequested extends CourseworkEvent {
  final Map<String, dynamic> filters;

  const CourseworkLoadByFilterRequested(this.filters);

  @override
  List<Object?> get props => [filters];
}

class CourseworkLoadByIdRequested extends CourseworkEvent {
  final int id;

  const CourseworkLoadByIdRequested(this.id);

  @override
  List<Object?> get props => [id];
}

class CourseworkCreateRequested extends CourseworkEvent {
  final Coursework coursework;

  const CourseworkCreateRequested(this.coursework);

  @override
  List<Object?> get props => [coursework];
}

class CourseworkUpdateRequested extends CourseworkEvent {
  final Coursework coursework;

  const CourseworkUpdateRequested(this.coursework);

  @override
  List<Object?> get props => [coursework];
}

class CourseworkDeleteRequested extends CourseworkEvent {
  final int id;

  const CourseworkDeleteRequested(this.id);

  @override
  List<Object?> get props => [id];
}

class CourseworkDropdownLoadRequested extends CourseworkEvent {
  const CourseworkDropdownLoadRequested();
}

class CourseworkDropdownForSubjectLoadRequested extends CourseworkEvent {
  final int subjectId;

  const CourseworkDropdownForSubjectLoadRequested(this.subjectId);

  @override
  List<Object?> get props => [subjectId];
}