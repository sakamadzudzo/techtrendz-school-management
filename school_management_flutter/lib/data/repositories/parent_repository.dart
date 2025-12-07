import 'package:dartz/dartz.dart';
import '../../core/errors/failure.dart';
import '../datasources/remote/parent_remote_data_source.dart';
import '../models/parent.dart';
import 'base_repository.dart';

class ParentRepository implements BaseRepository<Parent> {
  final ParentRemoteDataSource _remoteDataSource;

  ParentRepository(this._remoteDataSource);

  @override
  Future<Either<Failure, List<Parent>>> getAll() async {
    return await _remoteDataSource.getAll();
  }

  @override
  Future<Either<Failure, List<Parent>>> getAllByFilter(Map<String, dynamic> filters) async {
    return await _remoteDataSource.getAllByFilter(filters);
  }

  @override
  Future<Either<Failure, Parent>> getById(int id) async {
    return await _remoteDataSource.getById(id);
  }

  @override
  Future<Either<Failure, Parent>> save(Parent parent) async {
    return await _remoteDataSource.save(parent);
  }

  @override
  Future<Either<Failure, List<Parent>>> saveAll(List<Parent> parents) async {
    return await _remoteDataSource.saveAll(parents);
  }

  @override
  Future<Either<Failure, void>> delete(int id) async {
    return await _remoteDataSource.delete(id);
  }

  Future<Either<Failure, List<Map<String, dynamic>>>> getDropdownAll() async {
    return await _remoteDataSource.getDropdownAll();
  }

  Future<Either<Failure, Parent>> getByPersonId(int personId) async {
    return await _remoteDataSource.getByPersonId(personId);
  }

  Future<Either<Failure, void>> manageStudents(int parentId, List<int> studentIds) async {
    return await _remoteDataSource.manageStudents(parentId, studentIds);
  }
}