import 'package:dartz/dartz.dart';
import '../../core/errors/failure.dart';
import '../datasources/remote/mark_remote_data_source.dart';
import '../models/mark.dart';

class MarkRepository {
  final MarkRemoteDataSource remoteDataSource;

  MarkRepository(this.remoteDataSource);

  Future<Either<Failure, List<Mark>>> getAll({
    Map<String, dynamic>? filters,
  }) async {
    try {
      final marks = await remoteDataSource.getMarks(filters: filters);
      return Right(marks);
    } catch (e) {
      return Left(ServerFailure('Failed to load marks: ${e.toString()}'));
    }
  }

  Future<Either<Failure, Mark>> getById(int id) async {
    try {
      final mark = await remoteDataSource.getMarkById(id);
      return Right(mark);
    } catch (e) {
      return Left(ServerFailure('Failed to load mark: ${e.toString()}'));
    }
  }

  Future<Either<Failure, Mark>> save(Mark mark) async {
    try {
      final savedMark = mark.id == 0
          ? await remoteDataSource.createMark(mark)
          : await remoteDataSource.updateMark(mark);
      return Right(savedMark);
    } catch (e) {
      return Left(ServerFailure('Failed to save mark: ${e.toString()}'));
    }
  }

  Future<Either<Failure, void>> delete(int id) async {
    try {
      await remoteDataSource.deleteMark(id);
      return const Right(null);
    } catch (e) {
      return Left(ServerFailure('Failed to delete mark: ${e.toString()}'));
    }
  }

  Future<Either<Failure, List<Mark>>> getByStudent(int studentId) async {
    try {
      final marks = await remoteDataSource.getMarksByStudent(studentId);
      return Right(marks);
    } catch (e) {
      return Left(ServerFailure('Failed to load student marks: ${e.toString()}'));
    }
  }

  Future<Either<Failure, List<Mark>>> getByCoursework(int courseworkId) async {
    try {
      final marks = await remoteDataSource.getMarksByCoursework(courseworkId);
      return Right(marks);
    } catch (e) {
      return Left(ServerFailure('Failed to load coursework marks: ${e.toString()}'));
    }
  }
}