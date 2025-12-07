import 'package:dartz/dartz.dart';
import '../../core/errors/failure.dart';
import '../datasources/remote/subject_remote_data_source.dart';
import '../models/subject.dart';
import 'base_repository.dart';

class SubjectRepository implements BaseRepository<Subject> {
  final SubjectRemoteDataSource _remoteDataSource;

  SubjectRepository(this._remoteDataSource);

  @override
  Future<Either<Failure, List<Subject>>> getAll() async {
    return await _remoteDataSource.getAll();
  }

  @override
  Future<Either<Failure, List<Subject>>> getAllByFilter(Map<String, dynamic> filters) async {
    return await _remoteDataSource.getAllByFilter(filters);
  }

  @override
  Future<Either<Failure, Subject>> getById(int id) async {
    return await _remoteDataSource.getById(id);
  }

  @override
  Future<Either<Failure, Subject>> save(Subject subject) async {
    return await _remoteDataSource.save(subject);
  }

  @override
  Future<Either<Failure, List<Subject>>> saveAll(List<Subject> subjects) async {
    return await _remoteDataSource.saveAll(subjects);
  }

  @override
  Future<Either<Failure, void>> delete(int id) async {
    return await _remoteDataSource.delete(id);
  }

  Future<Either<Failure, List<Map<String, dynamic>>>> getAllList() async {
    return await _remoteDataSource.getAllList();
  }
}