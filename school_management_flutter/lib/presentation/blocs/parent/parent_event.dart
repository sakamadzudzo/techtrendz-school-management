part of 'parent_bloc.dart';

abstract class ParentEvent extends Equatable {
  const ParentEvent();

  @override
  List<Object?> get props => [];
}

class ParentLoadRequested extends ParentEvent {
  const ParentLoadRequested();
}

class ParentLoadByFilterRequested extends ParentEvent {
  final Map<String, dynamic> filters;

  const ParentLoadByFilterRequested(this.filters);

  @override
  List<Object?> get props => [filters];
}

class ParentLoadByIdRequested extends ParentEvent {
  final int id;

  const ParentLoadByIdRequested(this.id);

  @override
  List<Object?> get props => [id];
}

class ParentCreateRequested extends ParentEvent {
  final Parent parent;

  const ParentCreateRequested(this.parent);

  @override
  List<Object?> get props => [parent];
}

class ParentUpdateRequested extends ParentEvent {
  final Parent parent;

  const ParentUpdateRequested(this.parent);

  @override
  List<Object?> get props => [parent];
}

class ParentDeleteRequested extends ParentEvent {
  final int id;

  const ParentDeleteRequested(this.id);

  @override
  List<Object?> get props => [id];
}

class ParentDropdownLoadRequested extends ParentEvent {
  const ParentDropdownLoadRequested();
}

class ParentByPersonIdLoadRequested extends ParentEvent {
  final int personId;

  const ParentByPersonIdLoadRequested(this.personId);

  @override
  List<Object?> get props => [personId];
}

class ParentManageStudentsRequested extends ParentEvent {
  final int parentId;
  final List<int> studentIds;

  const ParentManageStudentsRequested(this.parentId, this.studentIds);

  @override
  List<Object?> get props => [parentId, studentIds];
}