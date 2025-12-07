import 'package:dartz/dartz.dart';
import 'package:dio/dio.dart';
import '../../../core/config/api_endpoints.dart';
import '../../../core/errors/failure.dart';
import '../../../core/network/dio_client.dart';
import '../../models/dorm.dart';
import 'base_remote_data_source.dart';

class DormRemoteDataSource implements BaseRemoteDataSource<Dorm> {
  final Dio _dio = DioClient.instance;

  @override
  Future<Either<Failure, List<Dorm>>> getAll() async {
    try {
      final response = await _dio.get(ApiEndpoints.dormGetAll);
      final List<dynamic> data = response.data;
      final dorms = data.map((json) => Dorm.fromJson(json)).toList();
      return Right(dorms);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  @override
  Future<Either<Failure, List<Dorm>>> getAllByFilter(Map<String, dynamic> filters) async {
    try {
      final response = await _dio.get(
        ApiEndpoints.dormGetAllByFilter,
        queryParameters: filters,
      );
      final List<dynamic> data = response.data;
      final dorms = data.map((json) => Dorm.fromJson(json)).toList();
      return Right(dorms);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  @override
  Future<Either<Failure, Dorm>> getById(int id) async {
    try {
      final response = await _dio.get(
        ApiEndpoints.dormGetOne,
        queryParameters: {'id': id},
      );
      final dorm = Dorm.fromJson(response.data);
      return Right(dorm);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  @override
  Future<Either<Failure, Dorm>> save(Dorm dorm) async {
    try {
      final response = await _dio.post(
        ApiEndpoints.dormSave,
        data: dorm.toJson(),
      );
      final savedDorm = Dorm.fromJson(response.data);
      return Right(savedDorm);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  @override
  Future<Either<Failure, List<Dorm>>> saveAll(List<Dorm> dorms) async {
    try {
      final data = dorms.map((dorm) => dorm.toJson()).toList();
      final response = await _dio.post(
        ApiEndpoints.dormSave,
        data: data,
      );
      final List<dynamic> responseData = response.data;
      final savedDorms = responseData.map((json) => Dorm.fromJson(json)).toList();
      return Right(savedDorms);
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
        ApiEndpoints.dormSave,
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
      final response = await _dio.get(ApiEndpoints.dormGetAllList);
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