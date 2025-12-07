import 'package:equatable/equatable.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import '../../../data/models/teacher.dart';
import '../../../data/repositories/teacher_repository.dart';

part 'teacher_event.dart';
part 'teacher_state.dart';

class TeacherBloc extends Bloc<TeacherEvent, TeacherState> {
  final TeacherRepository _teacherRepository;

  TeacherBloc(this._teacherRepository) : super(const TeacherInitial()) {
    on<TeacherLoadRequested>(_onTeacherLoadRequested);
    on<TeacherLoadByFilterRequested>(_onTeacherLoadByFilterRequested);
    on<TeacherLoadByIdRequested>(_onTeacherLoadByIdRequested);
    on<TeacherCreateRequested>(_onTeacherCreateRequested);
    on<TeacherUpdateRequested>(_onTeacherUpdateRequested);
    on<TeacherDeleteRequested>(_onTeacherDeleteRequested);
    on<TeacherDropdownLoadRequested>(_onTeacherDropdownLoadRequested);
  }

  Future<void> _onTeacherLoadRequested(
    TeacherLoadRequested event,
    Emitter<TeacherState> emit,
  ) async {
    emit(const TeacherLoading());
    final result = await _teacherRepository.getAll();
    result.fold(
      (failure) => emit(TeacherError(failure.message)),
      (teachers) => emit(TeacherLoaded(teachers)),
    );
  }

  Future<void> _onTeacherLoadByFilterRequested(
    TeacherLoadByFilterRequested event,
    Emitter<TeacherState> emit,
  ) async {
    emit(const TeacherLoading());
    final result = await _teacherRepository.getAllByFilter(event.filters);
    result.fold(
      (failure) => emit(TeacherError(failure.message)),
      (teachers) => emit(TeacherLoaded(teachers)),
    );
  }

  Future<void> _onTeacherLoadByIdRequested(
    TeacherLoadByIdRequested event,
    Emitter<TeacherState> emit,
  ) async {
    emit(const TeacherLoading());
    final result = await _teacherRepository.getById(event.id);
    result.fold(
      (failure) => emit(TeacherError(failure.message)),
      (teacher) => emit(TeacherDetailLoaded(teacher)),
    );
  }

  Future<void> _onTeacherCreateRequested(
    TeacherCreateRequested event,
    Emitter<TeacherState> emit,
  ) async {
    emit(const TeacherLoading());
    final result = await _teacherRepository.save(event.teacher);
    result.fold(
      (failure) => emit(TeacherError(failure.message)),
      (teacher) => emit(TeacherCreated(teacher)),
    );
  }

  Future<void> _onTeacherUpdateRequested(
    TeacherUpdateRequested event,
    Emitter<TeacherState> emit,
  ) async {
    emit(const TeacherLoading());
    final result = await _teacherRepository.save(event.teacher);
    result.fold(
      (failure) => emit(TeacherError(failure.message)),
      (teacher) => emit(TeacherUpdated(teacher)),
    );
  }

  Future<void> _onTeacherDeleteRequested(
    TeacherDeleteRequested event,
    Emitter<TeacherState> emit,
  ) async {
    emit(const TeacherLoading());
    final result = await _teacherRepository.delete(event.id);
    result.fold(
      (failure) => emit(TeacherError(failure.message)),
      (_) => emit(const TeacherDeleted()),
    );
  }

  Future<void> _onTeacherDropdownLoadRequested(
    TeacherDropdownLoadRequested event,
    Emitter<TeacherState> emit,
  ) async {
    emit(const TeacherLoading());
    final result = await _teacherRepository.getDropdownAll();
    result.fold(
      (failure) => emit(TeacherError(failure.message)),
      (dropdowns) => emit(TeacherDropdownLoaded(dropdowns)),
    );
  }
}