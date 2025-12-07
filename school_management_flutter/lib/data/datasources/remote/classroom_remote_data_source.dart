import 'package:dartz/dartz.dart';
import 'package:dio/dio.dart';
import '../../../core/config/api_endpoints.dart';
import '../../../core/errors/failure.dart';
import '../../../core/network/dio_client.dart';
import '../../models/classroom.dart';
import 'base_remote_data_source.dart';

class ClassroomRemoteDataSource implements BaseRemoteDataSource<Classroom> {
  final Dio _dio = DioClient.instance;

  @override
  Future<Either<Failure, List<Classroom>>> getAll() async {
    try {
      final response = await _dio.get(ApiEndpoints.classroomGetAll);
      final List<dynamic> data = response.data;
      final classrooms = data.map((json) => Classroom.fromJson(json)).toList();
      return Right(classrooms);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  @override
  Future<Either<Failure, List<Classroom>>> getAllByFilter(Map<String, dynamic> filters) async {
    try {
      final response = await _dio.get(
        ApiEndpoints.classroomGetAllByFilter,
        queryParameters: filters,
      );
      final List<dynamic> data = response.data;
      final classrooms = data.map((json) => Classroom.fromJson(json)).toList();
      return Right(classrooms);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  @override
  Future<Either<Failure, Classroom>> getById(int id) async {
    try {
      final response = await _dio.get(
        ApiEndpoints.classroomGetOne,
        queryParameters: {'id': id},
      );
      final classroom = Classroom.fromJson(response.data);
      return Right(classroom);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  @override
  Future<Either<Failure, Classroom>> save(Classroom classroom) async {
    try {
      final response = await _dio.post(
        ApiEndpoints.classroomSave,
        data: classroom.toJson(),
      );
      final savedClassroom = Classroom.fromJson(response.data);
      return Right(savedClassroom);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  @override
  Future<Either<Failure, List<Classroom>>> saveAll(List<Classroom> classrooms) async {
    try {
      final data = classrooms.map((classroom) => classroom.toJson()).toList();
      final response = await _dio.post(
        ApiEndpoints.classroomSave,
        data: data,
      );
      final List<dynamic> responseData = response.data;
      final savedClassrooms = responseData.map((json) => Classroom.fromJson(json)).toList();
      return Right(savedClassrooms);
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
        ApiEndpoints.classroomSave,
        queryParameters: {'id': id},
      );
      return const Right(null);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }
}