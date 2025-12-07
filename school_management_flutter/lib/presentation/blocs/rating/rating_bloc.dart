import 'package:equatable/equatable.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import '../../../data/models/rating.dart';
import '../../../data/repositories/rating_repository.dart';

part 'rating_event.dart';
part 'rating_state.dart';

class RatingBloc extends Bloc<RatingEvent, RatingState> {
  final RatingRepository _ratingRepository;

  RatingBloc(this._ratingRepository) : super(const RatingInitial()) {
    on<RatingLoadRequested>(_onRatingLoadRequested);
    on<RatingLoadByFilterRequested>(_onRatingLoadByFilterRequested);
    on<RatingLoadByIdRequested>(_onRatingLoadByIdRequested);
    on<RatingCreateRequested>(_onRatingCreateRequested);
    on<RatingUpdateRequested>(_onRatingUpdateRequested);
    on<RatingDeleteRequested>(_onRatingDeleteRequested);
    on<RatingDropdownLoadRequested>(_onRatingDropdownLoadRequested);
    on<RatingByActivityPointLoadRequested>(_onRatingByActivityPointLoadRequested);
  }

  Future<void> _onRatingLoadRequested(
    RatingLoadRequested event,
    Emitter<RatingState> emit,
  ) async {
    emit(const RatingLoading());
    final result = await _ratingRepository.getAll();
    result.fold(
      (failure) => emit(RatingError(failure.message)),
      (ratings) => emit(RatingLoaded(ratings)),
    );
  }

  Future<void> _onRatingLoadByFilterRequested(
    RatingLoadByFilterRequested event,
    Emitter<RatingState> emit,
  ) async {
    emit(const RatingLoading());
    final result = await _ratingRepository.getAll(filters: event.filters);
    result.fold(
      (failure) => emit(RatingError(failure.message)),
      (ratings) => emit(RatingLoaded(ratings)),
    );
  }

  Future<void> _onRatingLoadByIdRequested(
    RatingLoadByIdRequested event,
    Emitter<RatingState> emit,
  ) async {
    emit(const RatingLoading());
    final result = await _ratingRepository.getById(event.id);
    result.fold(
      (failure) => emit(RatingError(failure.message)),
      (rating) => emit(RatingDetailLoaded(rating)),
    );
  }

  Future<void> _onRatingCreateRequested(
    RatingCreateRequested event,
    Emitter<RatingState> emit,
  ) async {
    emit(const RatingLoading());
    final result = await _ratingRepository.save(event.rating);
    result.fold(
      (failure) => emit(RatingError(failure.message)),
      (rating) => emit(RatingCreated(rating)),
    );
  }

  Future<void> _onRatingUpdateRequested(
    RatingUpdateRequested event,
    Emitter<RatingState> emit,
  ) async {
    emit(const RatingLoading());
    final result = await _ratingRepository.save(event.rating);
    result.fold(
      (failure) => emit(RatingError(failure.message)),
      (rating) => emit(RatingUpdated(rating)),
    );
  }

  Future<void> _onRatingDeleteRequested(
    RatingDeleteRequested event,
    Emitter<RatingState> emit,
  ) async {
    emit(const RatingLoading());
    final result = await _ratingRepository.delete(event.id);
    result.fold(
      (failure) => emit(RatingError(failure.message)),
      (_) => emit(const RatingDeleted()),
    );
  }

  Future<void> _onRatingDropdownLoadRequested(
    RatingDropdownLoadRequested event,
    Emitter<RatingState> emit,
  ) async {
    emit(const RatingLoading());
    final result = await _ratingRepository.getAll();
    result.fold(
      (failure) => emit(RatingError(failure.message)),
      (ratings) => emit(RatingDropdownLoaded(ratings)),
    );
  }

  Future<void> _onRatingByActivityPointLoadRequested(
    RatingByActivityPointLoadRequested event,
    Emitter<RatingState> emit,
  ) async {
    emit(const RatingLoading());
    final result = await _ratingRepository.getByActivityPoint(event.activityPointId);
    result.fold(
      (failure) => emit(RatingError(failure.message)),
      (ratings) => emit(RatingLoaded(ratings)),
    );
  }
}