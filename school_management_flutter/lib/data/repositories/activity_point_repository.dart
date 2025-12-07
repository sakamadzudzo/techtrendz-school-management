import 'package:dartz/dartz.dart';
import '../../core/errors/failure.dart';
import '../datasources/remote/activity_point_remote_data_source.dart';
import '../models/activity_point.dart';

class ActivityPointRepository {
  final ActivityPointRemoteDataSource remoteDataSource;

  ActivityPointRepository(this.remoteDataSource);

  Future<Either<Failure, List<ActivityPoint>>> getAll({
    Map<String, dynamic>? filters,
  }) async {
    try {
      final activityPoints = await remoteDataSource.getActivityPoints(filters: filters);
      return Right(activityPoints);
    } catch (e) {
      return Left(ServerFailure('Failed to load activity points: ${e.toString()}'));
    }
  }

  Future<Either<Failure, ActivityPoint>> getById(int id) async {
    try {
      final activityPoint = await remoteDataSource.getActivityPointById(id);
      return Right(activityPoint);
    } catch (e) {
      return Left(ServerFailure('Failed to load activity point: ${e.toString()}'));
    }
  }

  Future<Either<Failure, ActivityPoint>> save(ActivityPoint activityPoint) async {
    try {
      final savedActivityPoint = activityPoint.id == 0
          ? await remoteDataSource.createActivityPoint(activityPoint)
          : await remoteDataSource.updateActivityPoint(activityPoint);
      return Right(savedActivityPoint);
    } catch (e) {
      return Left(ServerFailure('Failed to save activity point: ${e.toString()}'));
    }
  }

  Future<Either<Failure, void>> delete(int id) async {
    try {
      await remoteDataSource.deleteActivityPoint(id);
      return const Right(null);
    } catch (e) {
      return Left(ServerFailure('Failed to delete activity point: ${e.toString()}'));
    }
  }

  Future<Either<Failure, List<ActivityPoint>>> getByStudent(int studentId) async {
    try {
      final activityPoints = await remoteDataSource.getActivityPointsByStudent(studentId);
      return Right(activityPoints);
    } catch (e) {
      return Left(ServerFailure('Failed to load student activity points: ${e.toString()}'));
    }
  }

  Future<Either<Failure, List<ActivityPoint>>> getByActivity(int activityId) async {
    try {
      final activityPoints = await remoteDataSource.getActivityPointsByActivity(activityId);
      return Right(activityPoints);
    } catch (e) {
      return Left(ServerFailure('Failed to load activity points for activity: ${e.toString()}'));
    }
  }
}