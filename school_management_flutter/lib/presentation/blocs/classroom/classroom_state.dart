part of 'classroom_bloc.dart';

abstract class ClassroomState extends Equatable {
  const ClassroomState();

  @override
  List<Object?> get props => [];
}

class ClassroomInitial extends ClassroomState {
  const ClassroomInitial();
}

class ClassroomLoading extends ClassroomState {
  const ClassroomLoading();
}

class ClassroomLoaded extends ClassroomState {
  final List<Classroom> classrooms;

  const ClassroomLoaded(this.classrooms);

  @override
  List<Object?> get props => [classrooms];
}

class ClassroomDetailLoaded extends ClassroomState {
  final Classroom classroom;

  const ClassroomDetailLoaded(this.classroom);

  @override
  List<Object?> get props => [classroom];
}

class ClassroomCreated extends ClassroomState {
  final Classroom classroom;

  const ClassroomCreated(this.classroom);

  @override
  List<Object?> get props => [classroom];
}

class ClassroomUpdated extends ClassroomState {
  final Classroom classroom;

  const ClassroomUpdated(this.classroom);

  @override
  List<Object?> get props => [classroom];
}

class ClassroomDeleted extends ClassroomState {
  const ClassroomDeleted();
}


class ClassroomError extends ClassroomState {
  final String message;

  const ClassroomError(this.message);

  @override
  List<Object?> get props => [message];
}