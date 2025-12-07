part of 'mark_bloc.dart';

abstract class MarkEvent {}

class MarkLoadRequested extends MarkEvent {}

class MarkLoadByFilterRequested extends MarkEvent {
  final Map<String, dynamic> filters;

  MarkLoadByFilterRequested(this.filters);
}

class MarkLoadByIdRequested extends MarkEvent {
  final int id;

  MarkLoadByIdRequested(this.id);
}

class MarkCreateRequested extends MarkEvent {
  final Mark mark;

  MarkCreateRequested(this.mark);
}

class MarkUpdateRequested extends MarkEvent {
  final Mark mark;

  MarkUpdateRequested(this.mark);
}

class MarkDeleteRequested extends MarkEvent {
  final int id;

  MarkDeleteRequested(this.id);
}

class MarkDropdownLoadRequested extends MarkEvent {}

class MarkDropdownForStudentLoadRequested extends MarkEvent {
  final int studentId;

  MarkDropdownForStudentLoadRequested(this.studentId);
}

class MarkDropdownForCourseworkLoadRequested extends MarkEvent {
  final int courseworkId;

  MarkDropdownForCourseworkLoadRequested(this.courseworkId);
}

class MarkByStudentLoadRequested extends MarkEvent {
  final int studentId;

  MarkByStudentLoadRequested(this.studentId);
}

class MarkByCourseworkLoadRequested extends MarkEvent {
  final int courseworkId;

  MarkByCourseworkLoadRequested(this.courseworkId);
}