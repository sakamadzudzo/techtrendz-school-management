import 'package:equatable/equatable.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import '../../../data/models/activity_point.dart';
import '../../../data/repositories/activity_point_repository.dart';

part 'activity_point_event.dart';
part 'activity_point_state.dart';

class ActivityPointBloc extends Bloc<ActivityPointEvent, ActivityPointState> {
  final ActivityPointRepository _activityPointRepository;

  ActivityPointBloc(this._activityPointRepository) : super(const ActivityPointInitial()) {
    on<ActivityPointLoadRequested>(_onActivityPointLoadRequested);
    on<ActivityPointLoadByFilterRequested>(_onActivityPointLoadByFilterRequested);
    on<ActivityPointLoadByIdRequested>(_onActivityPointLoadByIdRequested);
    on<ActivityPointCreateRequested>(_onActivityPointCreateRequested);
    on<ActivityPointUpdateRequested>(_onActivityPointUpdateRequested);
    on<ActivityPointDeleteRequested>(_onActivityPointDeleteRequested);
    on<ActivityPointDropdownLoadRequested>(_onActivityPointDropdownLoadRequested);
    on<ActivityPointByStudentLoadRequested>(_onActivityPointByStudentLoadRequested);
    on<ActivityPointByActivityLoadRequested>(_onActivityPointByActivityLoadRequested);
  }

  Future<void> _onActivityPointLoadRequested(
    ActivityPointLoadRequested event,
    Emitter<ActivityPointState> emit,
  ) async {
    emit(const ActivityPointLoading());
    final result = await _activityPointRepository.getAll();
    result.fold(
      (failure) => emit(ActivityPointError(failure.message)),
      (activityPoints) => emit(ActivityPointLoaded(activityPoints)),
    );
  }

  Future<void> _onActivityPointLoadByFilterRequested(
    ActivityPointLoadByFilterRequested event,
    Emitter<ActivityPointState> emit,
  ) async {
    emit(const ActivityPointLoading());
    final result = await _activityPointRepository.getAll(filters: event.filters);
    result.fold(
      (failure) => emit(ActivityPointError(failure.message)),
      (activityPoints) => emit(ActivityPointLoaded(activityPoints)),
    );
  }

  Future<void> _onActivityPointLoadByIdRequested(
    ActivityPointLoadByIdRequested event,
    Emitter<ActivityPointState> emit,
  ) async {
    emit(const ActivityPointLoading());
    final result = await _activityPointRepository.getById(event.id);
    result.fold(
      (failure) => emit(ActivityPointError(failure.message)),
      (activityPoint) => emit(ActivityPointDetailLoaded(activityPoint)),
    );
  }

  Future<void> _onActivityPointCreateRequested(
    ActivityPointCreateRequested event,
    Emitter<ActivityPointState> emit,
  ) async {
    emit(const ActivityPointLoading());
    final result = await _activityPointRepository.save(event.activityPoint);
    result.fold(
      (failure) => emit(ActivityPointError(failure.message)),
      (activityPoint) => emit(ActivityPointCreated(activityPoint)),
    );
  }

  Future<void> _onActivityPointUpdateRequested(
    ActivityPointUpdateRequested event,
    Emitter<ActivityPointState> emit,
  ) async {
    emit(const ActivityPointLoading());
    final result = await _activityPointRepository.save(event.activityPoint);
    result.fold(
      (failure) => emit(ActivityPointError(failure.message)),
      (activityPoint) => emit(ActivityPointUpdated(activityPoint)),
    );
  }

  Future<void> _onActivityPointDeleteRequested(
    ActivityPointDeleteRequested event,
    Emitter<ActivityPointState> emit,
  ) async {
    emit(const ActivityPointLoading());
    final result = await _activityPointRepository.delete(event.id);
    result.fold(
      (failure) => emit(ActivityPointError(failure.message)),
      (_) => emit(const ActivityPointDeleted()),
    );
  }

  Future<void> _onActivityPointDropdownLoadRequested(
    ActivityPointDropdownLoadRequested event,
    Emitter<ActivityPointState> emit,
  ) async {
    emit(const ActivityPointLoading());
    final result = await _activityPointRepository.getAll();
    result.fold(
      (failure) => emit(ActivityPointError(failure.message)),
      (activityPoints) => emit(ActivityPointDropdownLoaded(activityPoints)),
    );
  }

  Future<void> _onActivityPointByStudentLoadRequested(
    ActivityPointByStudentLoadRequested event,
    Emitter<ActivityPointState> emit,
  ) async {
    emit(const ActivityPointLoading());
    final result = await _activityPointRepository.getByStudent(event.studentId);
    result.fold(
      (failure) => emit(ActivityPointError(failure.message)),
      (activityPoints) => emit(ActivityPointLoaded(activityPoints)),
    );
  }

  Future<void> _onActivityPointByActivityLoadRequested(
    ActivityPointByActivityLoadRequested event,
    Emitter<ActivityPointState> emit,
  ) async {
    emit(const ActivityPointLoading());
    final result = await _activityPointRepository.getByActivity(event.activityId);
    result.fold(
      (failure) => emit(ActivityPointError(failure.message)),
      (activityPoints) => emit(ActivityPointLoaded(activityPoints)),
    );
  }
}