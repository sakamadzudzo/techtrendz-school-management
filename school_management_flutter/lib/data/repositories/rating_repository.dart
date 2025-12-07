import 'package:dartz/dartz.dart';
import '../../core/errors/failure.dart';
import '../datasources/remote/rating_remote_data_source.dart';
import '../models/rating.dart';

class RatingRepository {
  final RatingRemoteDataSource remoteDataSource;

  RatingRepository(this.remoteDataSource);

  Future<Either<Failure, List<Rating>>> getAll({
    Map<String, dynamic>? filters,
  }) async {
    try {
      final ratings = await remoteDataSource.getRatings(filters: filters);
      return Right(ratings);
    } catch (e) {
      return Left(ServerFailure('Failed to load ratings: ${e.toString()}'));
    }
  }

  Future<Either<Failure, Rating>> getById(int id) async {
    try {
      final rating = await remoteDataSource.getRatingById(id);
      return Right(rating);
    } catch (e) {
      return Left(ServerFailure('Failed to load rating: ${e.toString()}'));
    }
  }

  Future<Either<Failure, Rating>> save(Rating rating) async {
    try {
      final savedRating = rating.id == 0
          ? await remoteDataSource.createRating(rating)
          : await remoteDataSource.updateRating(rating);
      return Right(savedRating);
    } catch (e) {
      return Left(ServerFailure('Failed to save rating: ${e.toString()}'));
    }
  }

  Future<Either<Failure, void>> delete(int id) async {
    try {
      await remoteDataSource.deleteRating(id);
      return const Right(null);
    } catch (e) {
      return Left(ServerFailure('Failed to delete rating: ${e.toString()}'));
    }
  }

  Future<Either<Failure, List<Rating>>> getByActivityPoint(int activityPointId) async {
    try {
      final ratings = await remoteDataSource.getRatingsByActivityPoint(activityPointId);
      return Right(ratings);
    } catch (e) {
      return Left(ServerFailure('Failed to load activity point ratings: ${e.toString()}'));
    }
  }
}