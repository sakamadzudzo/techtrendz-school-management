import 'package:dartz/dartz.dart';
import '../../core/errors/failure.dart';
import '../datasources/remote/school_remote_data_source.dart';
import '../models/school.dart';
import 'base_repository.dart';

class SchoolRepository implements BaseRepository<School> {
  final SchoolRemoteDataSource _remoteDataSource;

  SchoolRepository(this._remoteDataSource);

  @override
  Future<Either<Failure, List<School>>> getAll() async {
    return await _remoteDataSource.getAll();
  }

  @override
  Future<Either<Failure, List<School>>> getAllByFilter(Map<String, dynamic> filters) async {
    return await _remoteDataSource.getAllByFilter(filters);
  }

  @override
  Future<Either<Failure, School>> getById(int id) async {
    return await _remoteDataSource.getById(id);
  }

  @override
  Future<Either<Failure, School>> save(School school) async {
    return await _remoteDataSource.save(school);
  }

  @override
  Future<Either<Failure, List<School>>> saveAll(List<School> schools) async {
    return await _remoteDataSource.saveAll(schools);
  }

  @override
  Future<Either<Failure, void>> delete(int id) async {
    return await _remoteDataSource.delete(id);
  }

  Future<Either<Failure, List<Map<String, dynamic>>>> getAllList() async {
    return await _remoteDataSource.getAllList();
  }
}