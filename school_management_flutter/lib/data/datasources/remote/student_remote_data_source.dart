import 'package:dartz/dartz.dart';
import 'package:dio/dio.dart';
import '../../../core/config/api_endpoints.dart';
import '../../../core/errors/exceptions.dart';
import '../../../core/errors/failure.dart';
import '../../../core/network/dio_client.dart';
import '../../models/student.dart';
import 'base_remote_data_source.dart';

class StudentRemoteDataSource implements BaseRemoteDataSource<Student> {
  final Dio _dio = DioClient.instance;

  @override
  Future<Either<Failure, List<Student>>> getAll() async {
    try {
      final response = await _dio.get(ApiEndpoints.studentViewGetAll);
      final List<dynamic> data = response.data;
      final students = data.map((json) => Student.fromJson(json)).toList();
      return Right(students);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  @override
  Future<Either<Failure, List<Student>>> getAllByFilter(Map<String, dynamic> filters) async {
    try {
      final response = await _dio.get(
        ApiEndpoints.studentViewGetAllByFilter,
        queryParameters: filters,
      );
      final List<dynamic> data = response.data;
      final students = data.map((json) => Student.fromJson(json)).toList();
      return Right(students);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  @override
  Future<Either<Failure, Student>> getById(int id) async {
    try {
      final response = await _dio.get(
        ApiEndpoints.studentViewGetOne,
        queryParameters: {'id': id},
      );
      final student = Student.fromJson(response.data);
      return Right(student);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  @override
  Future<Either<Failure, Student>> save(Student student) async {
    try {
      final response = await _dio.post(
        ApiEndpoints.studentSave,
        data: student.toJson(),
      );
      final savedStudent = Student.fromJson(response.data);
      return Right(savedStudent);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  @override
  Future<Either<Failure, List<Student>>> saveAll(List<Student> students) async {
    try {
      final data = students.map((student) => student.toJson()).toList();
      final response = await _dio.post(
        ApiEndpoints.studentSave,
        data: data,
      );
      final List<dynamic> responseData = response.data;
      final savedStudents = responseData.map((json) => Student.fromJson(json)).toList();
      return Right(savedStudents);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  @override
  Future<Either<Failure, void>> delete(int id) async {
    try {
      await _dio.delete(
        ApiEndpoints.studentSave,
        queryParameters: {'id': id},
      );
      return const Right(null);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  // Additional methods specific to Student
  Future<Either<Failure, List<Map<String, dynamic>>>> getDropdownAll() async {
    try {
      final response = await _dio.get(ApiEndpoints.studentDropdownGetAll);
      final List<dynamic> data = response.data;
      final dropdowns = data.map((item) => item as Map<String, dynamic>).toList();
      return Right(dropdowns);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  Future<Either<Failure, List<Map<String, dynamic>>>> getDropdownByExample(Map<String, dynamic> example) async {
    try {
      final response = await _dio.get(
        ApiEndpoints.studentDropdownGetAllByExample,
        queryParameters: example,
      );
      final List<dynamic> data = response.data;
      final dropdowns = data.map((item) => item as Map<String, dynamic>).toList();
      return Right(dropdowns);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  Future<Either<Failure, List<Map<String, dynamic>>>> getDropdownForSubject(int subjectId) async {
    try {
      final response = await _dio.get(
        ApiEndpoints.studentDropdownGetAllForSubject,
        queryParameters: {'subjectId': subjectId},
      );
      final List<dynamic> data = response.data;
      final dropdowns = data.map((item) => item as Map<String, dynamic>).toList();
      return Right(dropdowns);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  Future<Either<Failure, List<Map<String, dynamic>>>> getDropdownForClassroom(int classroomId) async {
    try {
      final response = await _dio.get(
        ApiEndpoints.studentDropdownGetAllForClassroom,
        queryParameters: {'classroomId': classroomId},
      );
      final List<dynamic> data = response.data;
      final dropdowns = data.map((item) => item as Map<String, dynamic>).toList();
      return Right(dropdowns);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }
}