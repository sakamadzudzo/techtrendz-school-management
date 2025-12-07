part of 'classroom_bloc.dart';

abstract class ClassroomEvent extends Equatable {
  const ClassroomEvent();

  @override
  List<Object?> get props => [];
}

class ClassroomLoadRequested extends ClassroomEvent {
  const ClassroomLoadRequested();
}

class ClassroomLoadByFilterRequested extends ClassroomEvent {
  final Map<String, dynamic> filters;

  const ClassroomLoadByFilterRequested(this.filters);

  @override
  List<Object?> get props => [filters];
}

class ClassroomLoadByIdRequested extends ClassroomEvent {
  final int id;

  const ClassroomLoadByIdRequested(this.id);

  @override
  List<Object?> get props => [id];
}

class ClassroomCreateRequested extends ClassroomEvent {
  final Classroom classroom;

  const ClassroomCreateRequested(this.classroom);

  @override
  List<Object?> get props => [classroom];
}

class ClassroomUpdateRequested extends ClassroomEvent {
  final Classroom classroom;

  const ClassroomUpdateRequested(this.classroom);

  @override
  List<Object?> get props => [classroom];
}

class ClassroomDeleteRequested extends ClassroomEvent {
  final int id;

  const ClassroomDeleteRequested(this.id);

  @override
  List<Object?> get props => [id];
}
