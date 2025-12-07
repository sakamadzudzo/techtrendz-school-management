import 'package:dartz/dartz.dart';
import '../../core/errors/failure.dart';
import '../datasources/remote/teacher_remote_data_source.dart';
import '../models/teacher.dart';
import 'base_repository.dart';

class TeacherRepository implements BaseRepository<Teacher> {
  final TeacherRemoteDataSource _remoteDataSource;

  TeacherRepository(this._remoteDataSource);

  @override
  Future<Either<Failure, List<Teacher>>> getAll() async {
    return await _remoteDataSource.getAll();
  }

  @override
  Future<Either<Failure, List<Teacher>>> getAllByFilter(Map<String, dynamic> filters) async {
    return await _remoteDataSource.getAllByFilter(filters);
  }

  @override
  Future<Either<Failure, Teacher>> getById(int id) async {
    return await _remoteDataSource.getById(id);
  }

  @override
  Future<Either<Failure, Teacher>> save(Teacher teacher) async {
    return await _remoteDataSource.save(teacher);
  }

  @override
  Future<Either<Failure, List<Teacher>>> saveAll(List<Teacher> teachers) async {
    return await _remoteDataSource.saveAll(teachers);
  }

  @override
  Future<Either<Failure, void>> delete(int id) async {
    return await _remoteDataSource.delete(id);
  }

  Future<Either<Failure, List<Map<String, dynamic>>>> getDropdownAll() async {
    return await _remoteDataSource.getDropdownAll();
  }
}