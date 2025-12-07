import 'package:equatable/equatable.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import '../../../data/models/school.dart';
import '../../../data/repositories/school_repository.dart';

part 'school_event.dart';
part 'school_state.dart';

class SchoolBloc extends Bloc<SchoolEvent, SchoolState> {
  final SchoolRepository _schoolRepository;

  SchoolBloc(this._schoolRepository) : super(const SchoolInitial()) {
    on<SchoolLoadRequested>(_onSchoolLoadRequested);
    on<SchoolLoadByFilterRequested>(_onSchoolLoadByFilterRequested);
    on<SchoolLoadByIdRequested>(_onSchoolLoadByIdRequested);
    on<SchoolCreateRequested>(_onSchoolCreateRequested);
    on<SchoolUpdateRequested>(_onSchoolUpdateRequested);
    on<SchoolDeleteRequested>(_onSchoolDeleteRequested);
    on<SchoolListLoadRequested>(_onSchoolListLoadRequested);
  }

  Future<void> _onSchoolLoadRequested(
    SchoolLoadRequested event,
    Emitter<SchoolState> emit,
  ) async {
    emit(const SchoolLoading());
    final result = await _schoolRepository.getAll();
    result.fold(
      (failure) => emit(SchoolError(failure.message)),
      (schools) => emit(SchoolLoaded(schools)),
    );
  }

  Future<void> _onSchoolLoadByFilterRequested(
    SchoolLoadByFilterRequested event,
    Emitter<SchoolState> emit,
  ) async {
    emit(const SchoolLoading());
    final result = await _schoolRepository.getAllByFilter(event.filters);
    result.fold(
      (failure) => emit(SchoolError(failure.message)),
      (schools) => emit(SchoolLoaded(schools)),
    );
  }

  Future<void> _onSchoolLoadByIdRequested(
    SchoolLoadByIdRequested event,
    Emitter<SchoolState> emit,
  ) async {
    emit(const SchoolLoading());
    final result = await _schoolRepository.getById(event.id);
    result.fold(
      (failure) => emit(SchoolError(failure.message)),
      (school) => emit(SchoolDetailLoaded(school)),
    );
  }

  Future<void> _onSchoolCreateRequested(
    SchoolCreateRequested event,
    Emitter<SchoolState> emit,
  ) async {
    emit(const SchoolLoading());
    final result = await _schoolRepository.save(event.school);
    result.fold(
      (failure) => emit(SchoolError(failure.message)),
      (school) => emit(SchoolCreated(school)),
    );
  }

  Future<void> _onSchoolUpdateRequested(
    SchoolUpdateRequested event,
    Emitter<SchoolState> emit,
  ) async {
    emit(const SchoolLoading());
    final result = await _schoolRepository.save(event.school);
    result.fold(
      (failure) => emit(SchoolError(failure.message)),
      (school) => emit(SchoolUpdated(school)),
    );
  }

  Future<void> _onSchoolDeleteRequested(
    SchoolDeleteRequested event,
    Emitter<SchoolState> emit,
  ) async {
    emit(const SchoolLoading());
    final result = await _schoolRepository.delete(event.id);
    result.fold(
      (failure) => emit(SchoolError(failure.message)),
      (_) => emit(const SchoolDeleted()),
    );
  }

  Future<void> _onSchoolListLoadRequested(
    SchoolListLoadRequested event,
    Emitter<SchoolState> emit,
  ) async {
    emit(const SchoolLoading());
    final result = await _schoolRepository.getAllList();
    result.fold(
      (failure) => emit(SchoolError(failure.message)),
      (schools) => emit(SchoolListLoaded(schools)),
    );
  }
}