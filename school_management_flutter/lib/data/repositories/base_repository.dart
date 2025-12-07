import 'package:dartz/dartz.dart';
import '../../core/errors/failure.dart';

abstract class BaseRepository<T> {
  Future<Either<Failure, List<T>>> getAll();
  Future<Either<Failure, List<T>>> getAllByFilter(Map<String, dynamic> filters);
  Future<Either<Failure, T>> getById(int id);
  Future<Either<Failure, T>> save(T entity);
  Future<Either<Failure, List<T>>> saveAll(List<T> entities);
  Future<Either<Failure, void>> delete(int id);
}