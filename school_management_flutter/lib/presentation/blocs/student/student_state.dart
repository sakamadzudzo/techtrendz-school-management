part of 'student_bloc.dart';

abstract class StudentState extends Equatable {
  const StudentState();

  @override
  List<Object?> get props => [];
}

class StudentInitial extends StudentState {
  const StudentInitial();
}

class StudentLoading extends StudentState {
  const StudentLoading();
}

class StudentLoaded extends StudentState {
  final List<Student> students;

  const StudentLoaded(this.students);

  @override
  List<Object?> get props => [students];
}

class StudentDetailLoaded extends StudentState {
  final Student student;

  const StudentDetailLoaded(this.student);

  @override
  List<Object?> get props => [student];
}

class StudentDropdownLoaded extends StudentState {
  final List<Map<String, dynamic>> dropdowns;

  const StudentDropdownLoaded(this.dropdowns);

  @override
  List<Object?> get props => [dropdowns];
}

class StudentCreated extends StudentState {
  final Student student;

  const StudentCreated(this.student);

  @override
  List<Object?> get props => [student];
}

class StudentUpdated extends StudentState {
  final Student student;

  const StudentUpdated(this.student);

  @override
  List<Object?> get props => [student];
}

class StudentDeleted extends StudentState {
  const StudentDeleted();
}

class StudentError extends StudentState {
  final String message;

  const StudentError(this.message);

  @override
  List<Object?> get props => [message];
}