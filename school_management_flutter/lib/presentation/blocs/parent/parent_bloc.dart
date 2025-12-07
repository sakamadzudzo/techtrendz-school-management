import 'package:equatable/equatable.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import '../../../data/models/parent.dart';
import '../../../data/repositories/parent_repository.dart';

part 'parent_event.dart';
part 'parent_state.dart';

class ParentBloc extends Bloc<ParentEvent, ParentState> {
  final ParentRepository _parentRepository;

  ParentBloc(this._parentRepository) : super(const ParentInitial()) {
    on<ParentLoadRequested>(_onParentLoadRequested);
    on<ParentLoadByFilterRequested>(_onParentLoadByFilterRequested);
    on<ParentLoadByIdRequested>(_onParentLoadByIdRequested);
    on<ParentCreateRequested>(_onParentCreateRequested);
    on<ParentUpdateRequested>(_onParentUpdateRequested);
    on<ParentDeleteRequested>(_onParentDeleteRequested);
    on<ParentDropdownLoadRequested>(_onParentDropdownLoadRequested);
    on<ParentByPersonIdLoadRequested>(_onParentByPersonIdLoadRequested);
    on<ParentManageStudentsRequested>(_onParentManageStudentsRequested);
  }

  Future<void> _onParentLoadRequested(
    ParentLoadRequested event,
    Emitter<ParentState> emit,
  ) async {
    emit(const ParentLoading());
    final result = await _parentRepository.getAll();
    result.fold(
      (failure) => emit(ParentError(failure.message)),
      (parents) => emit(ParentLoaded(parents)),
    );
  }

  Future<void> _onParentLoadByFilterRequested(
    ParentLoadByFilterRequested event,
    Emitter<ParentState> emit,
  ) async {
    emit(const ParentLoading());
    final result = await _parentRepository.getAllByFilter(event.filters);
    result.fold(
      (failure) => emit(ParentError(failure.message)),
      (parents) => emit(ParentLoaded(parents)),
    );
  }

  Future<void> _onParentLoadByIdRequested(
    ParentLoadByIdRequested event,
    Emitter<ParentState> emit,
  ) async {
    emit(const ParentLoading());
    final result = await _parentRepository.getById(event.id);
    result.fold(
      (failure) => emit(ParentError(failure.message)),
      (parent) => emit(ParentDetailLoaded(parent)),
    );
  }

  Future<void> _onParentCreateRequested(
    ParentCreateRequested event,
    Emitter<ParentState> emit,
  ) async {
    emit(const ParentLoading());
    final result = await _parentRepository.save(event.parent);
    result.fold(
      (failure) => emit(ParentError(failure.message)),
      (parent) => emit(ParentCreated(parent)),
    );
  }

  Future<void> _onParentUpdateRequested(
    ParentUpdateRequested event,
    Emitter<ParentState> emit,
  ) async {
    emit(const ParentLoading());
    final result = await _parentRepository.save(event.parent);
    result.fold(
      (failure) => emit(ParentError(failure.message)),
      (parent) => emit(ParentUpdated(parent)),
    );
  }

  Future<void> _onParentDeleteRequested(
    ParentDeleteRequested event,
    Emitter<ParentState> emit,
  ) async {
    emit(const ParentLoading());
    final result = await _parentRepository.delete(event.id);
    result.fold(
      (failure) => emit(ParentError(failure.message)),
      (_) => emit(const ParentDeleted()),
    );
  }

  Future<void> _onParentDropdownLoadRequested(
    ParentDropdownLoadRequested event,
    Emitter<ParentState> emit,
  ) async {
    emit(const ParentLoading());
    final result = await _parentRepository.getDropdownAll();
    result.fold(
      (failure) => emit(ParentError(failure.message)),
      (dropdowns) => emit(ParentDropdownLoaded(dropdowns)),
    );
  }

  Future<void> _onParentByPersonIdLoadRequested(
    ParentByPersonIdLoadRequested event,
    Emitter<ParentState> emit,
  ) async {
    emit(const ParentLoading());
    final result = await _parentRepository.getByPersonId(event.personId);
    result.fold(
      (failure) => emit(ParentError(failure.message)),
      (parent) => emit(ParentDetailLoaded(parent)),
    );
  }

  Future<void> _onParentManageStudentsRequested(
    ParentManageStudentsRequested event,
    Emitter<ParentState> emit,
  ) async {
    emit(const ParentLoading());
    final result = await _parentRepository.manageStudents(event.parentId, event.studentIds);
    result.fold(
      (failure) => emit(ParentError(failure.message)),
      (_) => emit(const ParentDeleted()), // Using deleted as success state for now
    );
  }
}