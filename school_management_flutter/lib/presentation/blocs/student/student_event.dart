part of 'student_bloc.dart';

abstract class StudentEvent extends Equatable {
  const StudentEvent();

  @override
  List<Object?> get props => [];
}

class StudentLoadRequested extends StudentEvent {
  const StudentLoadRequested();
}

class StudentLoadByFilterRequested extends StudentEvent {
  final Map<String, dynamic> filters;

  const StudentLoadByFilterRequested(this.filters);

  @override
  List<Object?> get props => [filters];
}

class StudentLoadByIdRequested extends StudentEvent {
  final int id;

  const StudentLoadByIdRequested(this.id);

  @override
  List<Object?> get props => [id];
}

class StudentCreateRequested extends StudentEvent {
  final Student student;

  const StudentCreateRequested(this.student);

  @override
  List<Object?> get props => [student];
}

class StudentUpdateRequested extends StudentEvent {
  final Student student;

  const StudentUpdateRequested(this.student);

  @override
  List<Object?> get props => [student];
}

class StudentDeleteRequested extends StudentEvent {
  final int id;

  const StudentDeleteRequested(this.id);

  @override
  List<Object?> get props => [id];
}

class StudentDropdownLoadRequested extends StudentEvent {
  const StudentDropdownLoadRequested();
}

class StudentDropdownForSubjectLoadRequested extends StudentEvent {
  final int subjectId;

  const StudentDropdownForSubjectLoadRequested(this.subjectId);

  @override
  List<Object?> get props => [subjectId];
}

class StudentDropdownForClassroomLoadRequested extends StudentEvent {
  final int classroomId;

  const StudentDropdownForClassroomLoadRequested(this.classroomId);

  @override
  List<Object?> get props => [classroomId];
}