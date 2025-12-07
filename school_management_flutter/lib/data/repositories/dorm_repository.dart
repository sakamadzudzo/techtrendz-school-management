import 'package:dartz/dartz.dart';
import '../../core/errors/failure.dart';
import '../datasources/remote/dorm_remote_data_source.dart';
import '../models/dorm.dart';
import 'base_repository.dart';

class DormRepository implements BaseRepository<Dorm> {
  final DormRemoteDataSource _remoteDataSource;

  DormRepository(this._remoteDataSource);

  @override
  Future<Either<Failure, List<Dorm>>> getAll() async {
    return await _remoteDataSource.getAll();
  }

  @override
  Future<Either<Failure, List<Dorm>>> getAllByFilter(Map<String, dynamic> filters) async {
    return await _remoteDataSource.getAllByFilter(filters);
  }

  @override
  Future<Either<Failure, Dorm>> getById(int id) async {
    return await _remoteDataSource.getById(id);
  }

  @override
  Future<Either<Failure, Dorm>> save(Dorm dorm) async {
    return await _remoteDataSource.save(dorm);
  }

  @override
  Future<Either<Failure, List<Dorm>>> saveAll(List<Dorm> dorms) async {
    return await _remoteDataSource.saveAll(dorms);
  }

  @override
  Future<Either<Failure, void>> delete(int id) async {
    return await _remoteDataSource.delete(id);
  }

  Future<Either<Failure, List<Map<String, dynamic>>>> getAllList() async {
    return await _remoteDataSource.getAllList();
  }
}