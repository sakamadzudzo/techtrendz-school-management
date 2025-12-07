import 'package:dartz/dartz.dart';
import 'package:dio/dio.dart';
import '../../../core/config/api_endpoints.dart';
import '../../../core/errors/failure.dart';
import '../../../core/network/dio_client.dart';
import '../../models/school.dart';
import 'base_remote_data_source.dart';

class SchoolRemoteDataSource implements BaseRemoteDataSource<School> {
  final Dio _dio = DioClient.instance;

  @override
  Future<Either<Failure, List<School>>> getAll() async {
    try {
      final response = await _dio.get(ApiEndpoints.schoolGetAll);
      final List<dynamic> data = response.data;
      final schools = data.map((json) => School.fromJson(json)).toList();
      return Right(schools);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  @override
  Future<Either<Failure, List<School>>> getAllByFilter(Map<String, dynamic> filters) async {
    try {
      final response = await _dio.get(
        ApiEndpoints.schoolGetAllByFilter,
        queryParameters: filters,
      );
      final List<dynamic> data = response.data;
      final schools = data.map((json) => School.fromJson(json)).toList();
      return Right(schools);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  @override
  Future<Either<Failure, School>> getById(int id) async {
    try {
      final response = await _dio.get(
        ApiEndpoints.schoolGetOne,
        queryParameters: {'id': id},
      );
      final school = School.fromJson(response.data);
      return Right(school);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  @override
  Future<Either<Failure, School>> save(School school) async {
    try {
      final response = await _dio.post(
        ApiEndpoints.schoolSave,
        data: school.toJson(),
      );
      final savedSchool = School.fromJson(response.data);
      return Right(savedSchool);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  @override
  Future<Either<Failure, List<School>>> saveAll(List<School> schools) async {
    try {
      final data = schools.map((school) => school.toJson()).toList();
      final response = await _dio.post(
        ApiEndpoints.schoolSave,
        data: data,
      );
      final List<dynamic> responseData = response.data;
      final savedSchools = responseData.map((json) => School.fromJson(json)).toList();
      return Right(savedSchools);
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
        ApiEndpoints.schoolSave,
        queryParameters: {'id': id},
      );
      return const Right(null);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  Future<Either<Failure, List<Map<String, dynamic>>>> getAllList() async {
    try {
      final response = await _dio.get(ApiEndpoints.schoolGetAllList);
      final List<dynamic> data = response.data;
      final list = data.map((item) => item as Map<String, dynamic>).toList();
      return Right(list);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }
}