import 'package:dartz/dartz.dart';
import 'package:dio/dio.dart';
import '../../../core/config/api_endpoints.dart';
import '../../../core/errors/failure.dart';
import '../../../core/network/dio_client.dart';
import '../../models/extra_curricular.dart';
import 'base_remote_data_source.dart';

class ExtraCurricularRemoteDataSource implements BaseRemoteDataSource<ExtraCurricular> {
  final Dio _dio = DioClient.instance;

  @override
  Future<Either<Failure, List<ExtraCurricular>>> getAll() async {
    try {
      final response = await _dio.get(ApiEndpoints.extraCurricularGetAll);
      final List<dynamic> data = response.data;
      final extraCurriculars = data.map((json) => ExtraCurricular.fromJson(json)).toList();
      return Right(extraCurriculars);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  @override
  Future<Either<Failure, List<ExtraCurricular>>> getAllByFilter(Map<String, dynamic> filters) async {
    try {
      final response = await _dio.get(
        ApiEndpoints.extraCurricularGetAllByFilter,
        queryParameters: filters,
      );
      final List<dynamic> data = response.data;
      final extraCurriculars = data.map((json) => ExtraCurricular.fromJson(json)).toList();
      return Right(extraCurriculars);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  @override
  Future<Either<Failure, ExtraCurricular>> getById(int id) async {
    try {
      final response = await _dio.get(
        ApiEndpoints.extraCurricularGetOne,
        queryParameters: {'id': id},
      );
      final extraCurricular = ExtraCurricular.fromJson(response.data);
      return Right(extraCurricular);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  @override
  Future<Either<Failure, ExtraCurricular>> save(ExtraCurricular extraCurricular) async {
    try {
      final response = await _dio.post(
        ApiEndpoints.extraCurricularSave,
        data: extraCurricular.toJson(),
      );
      final savedExtraCurricular = ExtraCurricular.fromJson(response.data);
      return Right(savedExtraCurricular);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  @override
  Future<Either<Failure, List<ExtraCurricular>>> saveAll(List<ExtraCurricular> extraCurriculars) async {
    try {
      final data = extraCurriculars.map((extraCurricular) => extraCurricular.toJson()).toList();
      final response = await _dio.post(
        ApiEndpoints.extraCurricularSave,
        data: data,
      );
      final List<dynamic> responseData = response.data;
      final savedExtraCurriculars = responseData.map((json) => ExtraCurricular.fromJson(json)).toList();
      return Right(savedExtraCurriculars);
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
        ApiEndpoints.extraCurricularSave,
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
      final response = await _dio.get(ApiEndpoints.extraCurricularGetAllList);
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