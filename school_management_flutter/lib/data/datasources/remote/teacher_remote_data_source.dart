import 'package:dartz/dartz.dart';
import 'package:dio/dio.dart';
import '../../../core/config/api_endpoints.dart';
import '../../../core/errors/failure.dart';
import '../../../core/network/dio_client.dart';
import '../../models/teacher.dart';
import 'base_remote_data_source.dart';

class TeacherRemoteDataSource implements BaseRemoteDataSource<Teacher> {
  final Dio _dio = DioClient.instance;

  @override
  Future<Either<Failure, List<Teacher>>> getAll() async {
    try {
      final response = await _dio.get(ApiEndpoints.teacherViewGetAll);
      final List<dynamic> data = response.data;
      final teachers = data.map((json) => Teacher.fromJson(json)).toList();
      return Right(teachers);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  @override
  Future<Either<Failure, List<Teacher>>> getAllByFilter(Map<String, dynamic> filters) async {
    try {
      final response = await _dio.get(
        ApiEndpoints.teacherViewGetAllByFilter,
        queryParameters: filters,
      );
      final List<dynamic> data = response.data;
      final teachers = data.map((json) => Teacher.fromJson(json)).toList();
      return Right(teachers);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  @override
  Future<Either<Failure, Teacher>> getById(int id) async {
    try {
      final response = await _dio.get(
        ApiEndpoints.teacherViewGetOne,
        queryParameters: {'id': id},
      );
      final teacher = Teacher.fromJson(response.data);
      return Right(teacher);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  @override
  Future<Either<Failure, Teacher>> save(Teacher teacher) async {
    try {
      final response = await _dio.post(
        ApiEndpoints.teacherSave,
        data: teacher.toJson(),
      );
      final savedTeacher = Teacher.fromJson(response.data);
      return Right(savedTeacher);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  @override
  Future<Either<Failure, List<Teacher>>> saveAll(List<Teacher> teachers) async {
    try {
      final data = teachers.map((teacher) => teacher.toJson()).toList();
      final response = await _dio.post(
        ApiEndpoints.teacherSave,
        data: data,
      );
      final List<dynamic> responseData = response.data;
      final savedTeachers = responseData.map((json) => Teacher.fromJson(json)).toList();
      return Right(savedTeachers);
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
        ApiEndpoints.teacherSave,
        queryParameters: {'id': id},
      );
      return const Right(null);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  Future<Either<Failure, List<Map<String, dynamic>>>> getDropdownAll() async {
    try {
      final response = await _dio.get(ApiEndpoints.teacherDropdownGetAll);
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