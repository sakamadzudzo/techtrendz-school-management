part of 'school_bloc.dart';

abstract class SchoolState extends Equatable {
  const SchoolState();

  @override
  List<Object?> get props => [];
}

class SchoolInitial extends SchoolState {
  const SchoolInitial();
}

class SchoolLoading extends SchoolState {
  const SchoolLoading();
}

class SchoolLoaded extends SchoolState {
  final List<School> schools;

  const SchoolLoaded(this.schools);

  @override
  List<Object?> get props => [schools];
}

class SchoolDetailLoaded extends SchoolState {
  final School school;

  const SchoolDetailLoaded(this.school);

  @override
  List<Object?> get props => [school];
}

class SchoolListLoaded extends SchoolState {
  final List<Map<String, dynamic>> schools;

  const SchoolListLoaded(this.schools);

  @override
  List<Object?> get props => [schools];
}

class SchoolCreated extends SchoolState {
  final School school;

  const SchoolCreated(this.school);

  @override
  List<Object?> get props => [school];
}

class SchoolUpdated extends SchoolState {
  final School school;

  const SchoolUpdated(this.school);

  @override
  List<Object?> get props => [school];
}

class SchoolDeleted extends SchoolState {
  const SchoolDeleted();
}

class SchoolError extends SchoolState {
  final String message;

  const SchoolError(this.message);

  @override
  List<Object?> get props => [message];
}