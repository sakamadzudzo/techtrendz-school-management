import 'package:equatable/equatable.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import '../../../data/models/mark.dart';
import '../../../data/repositories/mark_repository.dart';

part 'mark_event.dart';
part 'mark_state.dart';

class MarkBloc extends Bloc<MarkEvent, MarkState> {
  final MarkRepository _markRepository;

  MarkBloc(this._markRepository) : super(const MarkInitial()) {
    on<MarkLoadRequested>(_onMarkLoadRequested);
    on<MarkLoadByFilterRequested>(_onMarkLoadByFilterRequested);
    on<MarkLoadByIdRequested>(_onMarkLoadByIdRequested);
    on<MarkCreateRequested>(_onMarkCreateRequested);
    on<MarkUpdateRequested>(_onMarkUpdateRequested);
    on<MarkDeleteRequested>(_onMarkDeleteRequested);
    on<MarkDropdownLoadRequested>(_onMarkDropdownLoadRequested);
    on<MarkDropdownForStudentLoadRequested>(_onMarkDropdownForStudentLoadRequested);
    on<MarkDropdownForCourseworkLoadRequested>(_onMarkDropdownForCourseworkLoadRequested);
    on<MarkByStudentLoadRequested>(_onMarkByStudentLoadRequested);
    on<MarkByCourseworkLoadRequested>(_onMarkByCourseworkLoadRequested);
  }

  Future<void> _onMarkLoadRequested(
    MarkLoadRequested event,
    Emitter<MarkState> emit,
  ) async {
    emit(const MarkLoading());
    final result = await _markRepository.getAll();
    result.fold(
      (failure) => emit(MarkError(failure.message)),
      (marks) => emit(MarkLoaded(marks)),
    );
  }

  Future<void> _onMarkLoadByFilterRequested(
    MarkLoadByFilterRequested event,
    Emitter<MarkState> emit,
  ) async {
    emit(const MarkLoading());
    final result = await _markRepository.getAll(filters: event.filters);
    result.fold(
      (failure) => emit(MarkError(failure.message)),
      (marks) => emit(MarkLoaded(marks)),
    );
  }

  Future<void> _onMarkLoadByIdRequested(
    MarkLoadByIdRequested event,
    Emitter<MarkState> emit,
  ) async {
    emit(const MarkLoading());
    final result = await _markRepository.getById(event.id);
    result.fold(
      (failure) => emit(MarkError(failure.message)),
      (mark) => emit(MarkDetailLoaded(mark)),
    );
  }

  Future<void> _onMarkCreateRequested(
    MarkCreateRequested event,
    Emitter<MarkState> emit,
  ) async {
    emit(const MarkLoading());
    final result = await _markRepository.save(event.mark);
    result.fold(
      (failure) => emit(MarkError(failure.message)),
      (mark) => emit(MarkCreated(mark)),
    );
  }

  Future<void> _onMarkUpdateRequested(
    MarkUpdateRequested event,
    Emitter<MarkState> emit,
  ) async {
    emit(const MarkLoading());
    final result = await _markRepository.save(event.mark);
    result.fold(
      (failure) => emit(MarkError(failure.message)),
      (mark) => emit(MarkUpdated(mark)),
    );
  }

  Future<void> _onMarkDeleteRequested(
    MarkDeleteRequested event,
    Emitter<MarkState> emit,
  ) async {
    emit(const MarkLoading());
    final result = await _markRepository.delete(event.id);
    result.fold(
      (failure) => emit(MarkError(failure.message)),
      (_) => emit(const MarkDeleted()),
    );
  }

  Future<void> _onMarkDropdownLoadRequested(
    MarkDropdownLoadRequested event,
    Emitter<MarkState> emit,
  ) async {
    emit(const MarkLoading());
    final result = await _markRepository.getAll();
    result.fold(
      (failure) => emit(MarkError(failure.message)),
      (marks) => emit(MarkDropdownLoaded(marks)),
    );
  }

  Future<void> _onMarkDropdownForStudentLoadRequested(
    MarkDropdownForStudentLoadRequested event,
    Emitter<MarkState> emit,
  ) async {
    emit(const MarkLoading());
    final result = await _markRepository.getByStudent(event.studentId);
    result.fold(
      (failure) => emit(MarkError(failure.message)),
      (marks) => emit(MarkDropdownLoaded(marks)),
    );
  }

  Future<void> _onMarkDropdownForCourseworkLoadRequested(
    MarkDropdownForCourseworkLoadRequested event,
    Emitter<MarkState> emit,
  ) async {
    emit(const MarkLoading());
    final result = await _markRepository.getByCoursework(event.courseworkId);
    result.fold(
      (failure) => emit(MarkError(failure.message)),
      (marks) => emit(MarkDropdownLoaded(marks)),
    );
  }

  Future<void> _onMarkByStudentLoadRequested(
    MarkByStudentLoadRequested event,
    Emitter<MarkState> emit,
  ) async {
    emit(const MarkLoading());
    final result = await _markRepository.getByStudent(event.studentId);
    result.fold(
      (failure) => emit(MarkError(failure.message)),
      (marks) => emit(MarkLoaded(marks)),
    );
  }

  Future<void> _onMarkByCourseworkLoadRequested(
    MarkByCourseworkLoadRequested event,
    Emitter<MarkState> emit,
  ) async {
    emit(const MarkLoading());
    final result = await _markRepository.getByCoursework(event.courseworkId);
    result.fold(
      (failure) => emit(MarkError(failure.message)),
      (marks) => emit(MarkLoaded(marks)),
    );
  }
}