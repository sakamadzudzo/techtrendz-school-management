import 'package:equatable/equatable.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import '../../../data/models/classroom.dart';
import '../../../data/repositories/classroom_repository.dart';

part 'classroom_event.dart';
part 'classroom_state.dart';

class ClassroomBloc extends Bloc<ClassroomEvent, ClassroomState> {
  final ClassroomRepository _classroomRepository;

  ClassroomBloc(this._classroomRepository) : super(const ClassroomInitial()) {
    on<ClassroomLoadRequested>(_onClassroomLoadRequested);
    on<ClassroomLoadByFilterRequested>(_onClassroomLoadByFilterRequested);
    on<ClassroomLoadByIdRequested>(_onClassroomLoadByIdRequested);
    on<ClassroomCreateRequested>(_onClassroomCreateRequested);
    on<ClassroomUpdateRequested>(_onClassroomUpdateRequested);
    on<ClassroomDeleteRequested>(_onClassroomDeleteRequested);
  }

  Future<void> _onClassroomLoadRequested(
    ClassroomLoadRequested event,
    Emitter<ClassroomState> emit,
  ) async {
    emit(const ClassroomLoading());
    final result = await _classroomRepository.getAll();
    result.fold(
      (failure) => emit(ClassroomError(failure.message)),
      (classrooms) => emit(ClassroomLoaded(classrooms)),
    );
  }

  Future<void> _onClassroomLoadByFilterRequested(
    ClassroomLoadByFilterRequested event,
    Emitter<ClassroomState> emit,
  ) async {
    emit(const ClassroomLoading());
    final result = await _classroomRepository.getAllByFilter(event.filters);
    result.fold(
      (failure) => emit(ClassroomError(failure.message)),
      (classrooms) => emit(ClassroomLoaded(classrooms)),
    );
  }

  Future<void> _onClassroomLoadByIdRequested(
    ClassroomLoadByIdRequested event,
    Emitter<ClassroomState> emit,
  ) async {
    emit(const ClassroomLoading());
    final result = await _classroomRepository.getById(event.id);
    result.fold(
      (failure) => emit(ClassroomError(failure.message)),
      (classroom) => emit(ClassroomDetailLoaded(classroom)),
    );
  }

  Future<void> _onClassroomCreateRequested(
    ClassroomCreateRequested event,
    Emitter<ClassroomState> emit,
  ) async {
    emit(const ClassroomLoading());
    final result = await _classroomRepository.save(event.classroom);
    result.fold(
      (failure) => emit(ClassroomError(failure.message)),
      (classroom) => emit(ClassroomCreated(classroom)),
    );
  }

  Future<void> _onClassroomUpdateRequested(
    ClassroomUpdateRequested event,
    Emitter<ClassroomState> emit,
  ) async {
    emit(const ClassroomLoading());
    final result = await _classroomRepository.save(event.classroom);
    result.fold(
      (failure) => emit(ClassroomError(failure.message)),
      (classroom) => emit(ClassroomUpdated(classroom)),
    );
  }

  Future<void> _onClassroomDeleteRequested(
    ClassroomDeleteRequested event,
    Emitter<ClassroomState> emit,
  ) async {
    emit(const ClassroomLoading());
    final result = await _classroomRepository.delete(event.id);
    result.fold(
      (failure) => emit(ClassroomError(failure.message)),
      (_) => emit(const ClassroomDeleted()),
    );
  }

}