part of 'school_bloc.dart';

abstract class SchoolEvent extends Equatable {
  const SchoolEvent();

  @override
  List<Object?> get props => [];
}

class SchoolLoadRequested extends SchoolEvent {
  const SchoolLoadRequested();
}

class SchoolLoadByFilterRequested extends SchoolEvent {
  final Map<String, dynamic> filters;

  const SchoolLoadByFilterRequested(this.filters);

  @override
  List<Object?> get props => [filters];
}

class SchoolLoadByIdRequested extends SchoolEvent {
  final int id;

  const SchoolLoadByIdRequested(this.id);

  @override
  List<Object?> get props => [id];
}

class SchoolCreateRequested extends SchoolEvent {
  final School school;

  const SchoolCreateRequested(this.school);

  @override
  List<Object?> get props => [school];
}

class SchoolUpdateRequested extends SchoolEvent {
  final School school;

  const SchoolUpdateRequested(this.school);

  @override
  List<Object?> get props => [school];
}

class SchoolDeleteRequested extends SchoolEvent {
  final int id;

  const SchoolDeleteRequested(this.id);

  @override
  List<Object?> get props => [id];
}

class SchoolListLoadRequested extends SchoolEvent {
  const SchoolListLoadRequested();
}