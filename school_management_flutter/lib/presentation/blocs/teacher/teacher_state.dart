part of 'teacher_bloc.dart';

abstract class TeacherState extends Equatable {
  const TeacherState();

  @override
  List<Object?> get props => [];
}

class TeacherInitial extends TeacherState {
  const TeacherInitial();
}

class TeacherLoading extends TeacherState {
  const TeacherLoading();
}

class TeacherLoaded extends TeacherState {
  final List<Teacher> teachers;

  const TeacherLoaded(this.teachers);

  @override
  List<Object?> get props => [teachers];
}

class TeacherDetailLoaded extends TeacherState {
  final Teacher teacher;

  const TeacherDetailLoaded(this.teacher);

  @override
  List<Object?> get props => [teacher];
}

class TeacherDropdownLoaded extends TeacherState {
  final List<Map<String, dynamic>> dropdowns;

  const TeacherDropdownLoaded(this.dropdowns);

  @override
  List<Object?> get props => [dropdowns];
}

class TeacherCreated extends TeacherState {
  final Teacher teacher;

  const TeacherCreated(this.teacher);

  @override
  List<Object?> get props => [teacher];
}

class TeacherUpdated extends TeacherState {
  final Teacher teacher;

  const TeacherUpdated(this.teacher);

  @override
  List<Object?> get props => [teacher];
}

class TeacherDeleted extends TeacherState {
  const TeacherDeleted();
}

class TeacherError extends TeacherState {
  final String message;

  const TeacherError(this.message);

  @override
  List<Object?> get props => [message];
}