part of 'teacher_bloc.dart';

abstract class TeacherEvent extends Equatable {
  const TeacherEvent();

  @override
  List<Object?> get props => [];
}

class TeacherLoadRequested extends TeacherEvent {
  const TeacherLoadRequested();
}

class TeacherLoadByFilterRequested extends TeacherEvent {
  final Map<String, dynamic> filters;

  const TeacherLoadByFilterRequested(this.filters);

  @override
  List<Object?> get props => [filters];
}

class TeacherLoadByIdRequested extends TeacherEvent {
  final int id;

  const TeacherLoadByIdRequested(this.id);

  @override
  List<Object?> get props => [id];
}

class TeacherCreateRequested extends TeacherEvent {
  final Teacher teacher;

  const TeacherCreateRequested(this.teacher);

  @override
  List<Object?> get props => [teacher];
}

class TeacherUpdateRequested extends TeacherEvent {
  final Teacher teacher;

  const TeacherUpdateRequested(this.teacher);

  @override
  List<Object?> get props => [teacher];
}

class TeacherDeleteRequested extends TeacherEvent {
  final int id;

  const TeacherDeleteRequested(this.id);

  @override
  List<Object?> get props => [id];
}

class TeacherDropdownLoadRequested extends TeacherEvent {
  const TeacherDropdownLoadRequested();
}