import 'package:dartz/dartz.dart';
import '../../core/errors/failure.dart';
import '../datasources/remote/coursework_remote_data_source.dart';
import '../models/coursework.dart';

class CourseworkRepository {
  final CourseworkRemoteDataSource remoteDataSource;

  CourseworkRepository(this.remoteDataSource);

  Future<Either<Failure, List<Coursework>>> getCourseworks({
    Map<String, dynamic>? filters,
  }) async {
    try {
      final courseworks = await remoteDataSource.getCourseworks(filters: filters);
      return Right(courseworks);
    } catch (e) {
      return Left(ServerFailure('Failed to load courseworks: ${e.toString()}'));
    }
  }

  Future<Either<Failure, Coursework>> getCourseworkById(int id) async {
    try {
      final coursework = await remoteDataSource.getCourseworkById(id);
      return Right(coursework);
    } catch (e) {
      return Left(ServerFailure('Failed to load coursework: ${e.toString()}'));
    }
  }

  Future<Either<Failure, Coursework>> createCoursework(Coursework coursework) async {
    try {
      final createdCoursework = await remoteDataSource.createCoursework(coursework);
      return Right(createdCoursework);
    } catch (e) {
      return Left(ServerFailure('Failed to create coursework: ${e.toString()}'));
    }
  }

  Future<Either<Failure, Coursework>> updateCoursework(Coursework coursework) async {
    try {
      final updatedCoursework = await remoteDataSource.updateCoursework(coursework);
      return Right(updatedCoursework);
    } catch (e) {
      return Left(ServerFailure('Failed to update coursework: ${e.toString()}'));
    }
  }

  Future<Either<Failure, void>> deleteCoursework(int id) async {
    try {
      await remoteDataSource.deleteCoursework(id);
      return const Right(null);
    } catch (e) {
      return Left(ServerFailure('Failed to delete coursework: ${e.toString()}'));
    }
  }
}