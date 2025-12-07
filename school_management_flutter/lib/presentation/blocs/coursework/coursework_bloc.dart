import 'package:equatable/equatable.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import '../../../data/models/coursework.dart';
import '../../../data/repositories/coursework_repository.dart';

part 'coursework_event.dart';
part 'coursework_state.dart';

class CourseworkBloc extends Bloc<CourseworkEvent, CourseworkState> {
  final CourseworkRepository _courseworkRepository;

  CourseworkBloc(this._courseworkRepository) : super(const CourseworkInitial()) {
    on<CourseworkLoadRequested>(_onCourseworkLoadRequested);
    on<CourseworkLoadByFilterRequested>(_onCourseworkLoadByFilterRequested);
    on<CourseworkLoadByIdRequested>(_onCourseworkLoadByIdRequested);
    on<CourseworkCreateRequested>(_onCourseworkCreateRequested);
    on<CourseworkUpdateRequested>(_onCourseworkUpdateRequested);
    on<CourseworkDeleteRequested>(_onCourseworkDeleteRequested);
    on<CourseworkDropdownLoadRequested>(_onCourseworkDropdownLoadRequested);
    on<CourseworkDropdownForSubjectLoadRequested>(_onCourseworkDropdownForSubjectLoadRequested);
  }

  Future<void> _onCourseworkLoadRequested(
    CourseworkLoadRequested event,
    Emitter<CourseworkState> emit,
  ) async {
    emit(const CourseworkLoading());
    final result = await _courseworkRepository.getCourseworks();
    result.fold(
      (failure) => emit(CourseworkError(failure.message)),
      (courseworks) => emit(CourseworkLoaded(courseworks)),
    );
  }

  Future<void> _onCourseworkLoadByFilterRequested(
    CourseworkLoadByFilterRequested event,
    Emitter<CourseworkState> emit,
  ) async {
    emit(const CourseworkLoading());
    final result = await _courseworkRepository.getCourseworks(filters: event.filters);
    result.fold(
      (failure) => emit(CourseworkError(failure.message)),
      (courseworks) => emit(CourseworkLoaded(courseworks)),
    );
  }

  Future<void> _onCourseworkLoadByIdRequested(
    CourseworkLoadByIdRequested event,
    Emitter<CourseworkState> emit,
  ) async {
    emit(const CourseworkLoading());
    final result = await _courseworkRepository.getCourseworkById(event.id);
    result.fold(
      (failure) => emit(CourseworkError(failure.message)),
      (coursework) => emit(CourseworkDetailLoaded(coursework)),
    );
  }

  Future<void> _onCourseworkCreateRequested(
    CourseworkCreateRequested event,
    Emitter<CourseworkState> emit,
  ) async {
    emit(const CourseworkLoading());
    final result = await _courseworkRepository.createCoursework(event.coursework);
    result.fold(
      (failure) => emit(CourseworkError(failure.message)),
      (coursework) => emit(CourseworkCreated(coursework)),
    );
  }

  Future<void> _onCourseworkUpdateRequested(
    CourseworkUpdateRequested event,
    Emitter<CourseworkState> emit,
  ) async {
    emit(const CourseworkLoading());
    final result = await _courseworkRepository.updateCoursework(event.coursework);
    result.fold(
      (failure) => emit(CourseworkError(failure.message)),
      (coursework) => emit(CourseworkUpdated(coursework)),
    );
  }

  Future<void> _onCourseworkDeleteRequested(
    CourseworkDeleteRequested event,
    Emitter<CourseworkState> emit,
  ) async {
    emit(const CourseworkLoading());
    final result = await _courseworkRepository.deleteCoursework(event.id);
    result.fold(
      (failure) => emit(CourseworkError(failure.message)),
      (_) => emit(const CourseworkDeleted()),
    );
  }

  Future<void> _onCourseworkDropdownLoadRequested(
    CourseworkDropdownLoadRequested event,
    Emitter<CourseworkState> emit,
  ) async {
    emit(const CourseworkLoading());
    final result = await _courseworkRepository.getCourseworks();
    result.fold(
      (failure) => emit(CourseworkError(failure.message)),
      (courseworks) => emit(CourseworkDropdownLoaded(courseworks)),
    );
  }

  Future<void> _onCourseworkDropdownForSubjectLoadRequested(
    CourseworkDropdownForSubjectLoadRequested event,
    Emitter<CourseworkState> emit,
  ) async {
    emit(const CourseworkLoading());
    final result = await _courseworkRepository.getCourseworks(
      filters: {'subjectId': event.subjectId},
    );
    result.fold(
      (failure) => emit(CourseworkError(failure.message)),
      (courseworks) => emit(CourseworkDropdownLoaded(courseworks)),
    );
  }
}