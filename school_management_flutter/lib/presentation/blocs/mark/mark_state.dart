part of 'mark_bloc.dart';

abstract class MarkState extends Equatable {
  const MarkState();

  @override
  List<Object?> get props => [];
}

class MarkInitial extends MarkState {
  const MarkInitial();
}

class MarkLoading extends MarkState {
  const MarkLoading();
}

class MarkLoaded extends MarkState {
  final List<Mark> marks;

  const MarkLoaded(this.marks);

  @override
  List<Object?> get props => [marks];
}

class MarkDetailLoaded extends MarkState {
  final Mark mark;

  const MarkDetailLoaded(this.mark);

  @override
  List<Object?> get props => [mark];
}

class MarkCreated extends MarkState {
  final Mark mark;

  const MarkCreated(this.mark);

  @override
  List<Object?> get props => [mark];
}

class MarkUpdated extends MarkState {
  final Mark mark;

  const MarkUpdated(this.mark);

  @override
  List<Object?> get props => [mark];
}

class MarkDeleted extends MarkState {
  const MarkDeleted();
}

class MarkDropdownLoaded extends MarkState {
  final List<Mark> marks;

  const MarkDropdownLoaded(this.marks);

  @override
  List<Object?> get props => [marks];
}

class MarkError extends MarkState {
  final String message;

  const MarkError(this.message);

  @override
  List<Object?> get props => [message];
}