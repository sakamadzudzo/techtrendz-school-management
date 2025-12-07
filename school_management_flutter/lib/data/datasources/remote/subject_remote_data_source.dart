import 'package:dartz/dartz.dart';
import 'package:dio/dio.dart';
import '../../../core/config/api_endpoints.dart';
import '../../../core/errors/failure.dart';
import '../../../core/network/dio_client.dart';
import '../../models/subject.dart';
import 'base_remote_data_source.dart';

class SubjectRemoteDataSource implements BaseRemoteDataSource<Subject> {
  final Dio _dio = DioClient.instance;

  @override
  Future<Either<Failure, List<Subject>>> getAll() async {
    try {
      final response = await _dio.get(ApiEndpoints.subjectGetAll);
      final List<dynamic> data = response.data;
      final subjects = data.map((json) => Subject.fromJson(json)).toList();
      return Right(subjects);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  @override
  Future<Either<Failure, List<Subject>>> getAllByFilter(Map<String, dynamic> filters) async {
    try {
      final response = await _dio.get(
        ApiEndpoints.subjectGetAllByFilter,
        queryParameters: filters,
      );
      final List<dynamic> data = response.data;
      final subjects = data.map((json) => Subject.fromJson(json)).toList();
      return Right(subjects);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  @override
  Future<Either<Failure, Subject>> getById(int id) async {
    try {
      final response = await _dio.get(
        ApiEndpoints.subjectGetOne,
        queryParameters: {'id': id},
      );
      final subject = Subject.fromJson(response.data);
      return Right(subject);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  @override
  Future<Either<Failure, Subject>> save(Subject subject) async {
    try {
      final response = await _dio.post(
        ApiEndpoints.subjectSave,
        data: subject.toJson(),
      );
      final savedSubject = Subject.fromJson(response.data);
      return Right(savedSubject);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  @override
  Future<Either<Failure, List<Subject>>> saveAll(List<Subject> subjects) async {
    try {
      final data = subjects.map((subject) => subject.toJson()).toList();
      final response = await _dio.post(
        ApiEndpoints.subjectSave,
        data: data,
      );
      final List<dynamic> responseData = response.data;
      final savedSubjects = responseData.map((json) => Subject.fromJson(json)).toList();
      return Right(savedSubjects);
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
        ApiEndpoints.subjectSave,
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
      final response = await _dio.get(ApiEndpoints.subjectGetAllList);
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