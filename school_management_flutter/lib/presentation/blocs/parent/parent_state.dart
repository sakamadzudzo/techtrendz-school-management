part of 'parent_bloc.dart';

abstract class ParentState extends Equatable {
  const ParentState();

  @override
  List<Object?> get props => [];
}

class ParentInitial extends ParentState {
  const ParentInitial();
}

class ParentLoading extends ParentState {
  const ParentLoading();
}

class ParentLoaded extends ParentState {
  final List<Parent> parents;

  const ParentLoaded(this.parents);

  @override
  List<Object?> get props => [parents];
}

class ParentDetailLoaded extends ParentState {
  final Parent parent;

  const ParentDetailLoaded(this.parent);

  @override
  List<Object?> get props => [parent];
}

class ParentDropdownLoaded extends ParentState {
  final List<Map<String, dynamic>> dropdowns;

  const ParentDropdownLoaded(this.dropdowns);

  @override
  List<Object?> get props => [dropdowns];
}

class ParentCreated extends ParentState {
  final Parent parent;

  const ParentCreated(this.parent);

  @override
  List<Object?> get props => [parent];
}

class ParentUpdated extends ParentState {
  final Parent parent;

  const ParentUpdated(this.parent);

  @override
  List<Object?> get props => [parent];
}

class ParentDeleted extends ParentState {
  const ParentDeleted();
}

class ParentError extends ParentState {
  final String message;

  const ParentError(this.message);

  @override
  List<Object?> get props => [message];
}