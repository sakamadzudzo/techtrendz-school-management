import 'package:dartz/dartz.dart';
import '../../core/errors/failure.dart';
import '../datasources/remote/student_remote_data_source.dart';
import '../models/student.dart';
import 'base_repository.dart';

class StudentRepository implements BaseRepository<Student> {
  final StudentRemoteDataSource _remoteDataSource;

  StudentRepository(this._remoteDataSource);

  @override
  Future<Either<Failure, List<Student>>> getAll() async {
    return await _remoteDataSource.getAll();
  }

  @override
  Future<Either<Failure, List<Student>>> getAllByFilter(Map<String, dynamic> filters) async {
    return await _remoteDataSource.getAllByFilter(filters);
  }

  @override
  Future<Either<Failure, Student>> getById(int id) async {
    return await _remoteDataSource.getById(id);
  }

  @override
  Future<Either<Failure, Student>> save(Student student) async {
    return await _remoteDataSource.save(student);
  }

  @override
  Future<Either<Failure, List<Student>>> saveAll(List<Student> students) async {
    return await _remoteDataSource.saveAll(students);
  }

  @override
  Future<Either<Failure, void>> delete(int id) async {
    return await _remoteDataSource.delete(id);
  }

  // Additional methods specific to Student
  Future<Either<Failure, List<Map<String, dynamic>>>> getDropdownAll() async {
    return await _remoteDataSource.getDropdownAll();
  }

  Future<Either<Failure, List<Map<String, dynamic>>>> getDropdownByExample(Map<String, dynamic> example) async {
    return await _remoteDataSource.getDropdownByExample(example);
  }

  Future<Either<Failure, List<Map<String, dynamic>>>> getDropdownForSubject(int subjectId) async {
    return await _remoteDataSource.getDropdownForSubject(subjectId);
  }

  Future<Either<Failure, List<Map<String, dynamic>>>> getDropdownForClassroom(int classroomId) async {
    return await _remoteDataSource.getDropdownForClassroom(classroomId);
  }
}