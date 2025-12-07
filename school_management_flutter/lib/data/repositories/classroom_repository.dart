import 'package:dartz/dartz.dart';
import '../../core/errors/failure.dart';
import '../datasources/remote/classroom_remote_data_source.dart';
import '../models/classroom.dart';
import 'base_repository.dart';

class ClassroomRepository implements BaseRepository<Classroom> {
  final ClassroomRemoteDataSource _remoteDataSource;

  ClassroomRepository(this._remoteDataSource);

  @override
  Future<Either<Failure, List<Classroom>>> getAll() async {
    return await _remoteDataSource.getAll();
  }

  @override
  Future<Either<Failure, List<Classroom>>> getAllByFilter(Map<String, dynamic> filters) async {
    return await _remoteDataSource.getAllByFilter(filters);
  }

  @override
  Future<Either<Failure, Classroom>> getById(int id) async {
    return await _remoteDataSource.getById(id);
  }

  @override
  Future<Either<Failure, Classroom>> save(Classroom classroom) async {
    return await _remoteDataSource.save(classroom);
  }

  @override
  Future<Either<Failure, List<Classroom>>> saveAll(List<Classroom> classrooms) async {
    return await _remoteDataSource.saveAll(classrooms);
  }

  @override
  Future<Either<Failure, void>> delete(int id) async {
    return await _remoteDataSource.delete(id);
  }
}