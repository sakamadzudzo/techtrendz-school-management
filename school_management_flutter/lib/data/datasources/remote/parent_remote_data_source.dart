import 'package:dartz/dartz.dart';
import 'package:dio/dio.dart';
import '../../../core/config/api_endpoints.dart';
import '../../../core/errors/failure.dart';
import '../../../core/network/dio_client.dart';
import '../../models/parent.dart';
import 'base_remote_data_source.dart';

class ParentRemoteDataSource implements BaseRemoteDataSource<Parent> {
  final Dio _dio = DioClient.instance;

  @override
  Future<Either<Failure, List<Parent>>> getAll() async {
    try {
      final response = await _dio.get(ApiEndpoints.parentViewGetAll);
      final List<dynamic> data = response.data;
      final parents = data.map((json) => Parent.fromJson(json)).toList();
      return Right(parents);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  @override
  Future<Either<Failure, List<Parent>>> getAllByFilter(Map<String, dynamic> filters) async {
    try {
      final response = await _dio.get(
        ApiEndpoints.parentViewGetAllByFilter,
        queryParameters: filters,
      );
      final List<dynamic> data = response.data;
      final parents = data.map((json) => Parent.fromJson(json)).toList();
      return Right(parents);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  @override
  Future<Either<Failure, Parent>> getById(int id) async {
    try {
      final response = await _dio.get(
        ApiEndpoints.parentViewGetOne,
        queryParameters: {'id': id},
      );
      final parent = Parent.fromJson(response.data);
      return Right(parent);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  @override
  Future<Either<Failure, Parent>> save(Parent parent) async {
    try {
      final response = await _dio.post(
        ApiEndpoints.parentSave,
        data: parent.toJson(),
      );
      final savedParent = Parent.fromJson(response.data);
      return Right(savedParent);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  @override
  Future<Either<Failure, List<Parent>>> saveAll(List<Parent> parents) async {
    try {
      final data = parents.map((parent) => parent.toJson()).toList();
      final response = await _dio.post(
        ApiEndpoints.parentSaveAll,
        data: data,
      );
      final List<dynamic> responseData = response.data;
      final savedParents = responseData.map((json) => Parent.fromJson(json)).toList();
      return Right(savedParents);
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
        ApiEndpoints.parentSave,
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
      final response = await _dio.get(ApiEndpoints.parentDropdownGetAll);
      final List<dynamic> data = response.data;
      final dropdowns = data.map((item) => item as Map<String, dynamic>).toList();
      return Right(dropdowns);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  Future<Either<Failure, Parent>> getByPersonId(int personId) async {
    try {
      final response = await _dio.get(
        ApiEndpoints.parentViewGetOneByPerson,
        queryParameters: {'personId': personId},
      );
      final parent = Parent.fromJson(response.data);
      return Right(parent);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  Future<Either<Failure, void>> manageStudents(int parentId, List<int> studentIds) async {
    try {
      await _dio.post(
        ApiEndpoints.parentManageStudents,
        data: {
          'parentId': parentId,
          'studentIds': studentIds,
        },
      );
      return const Right(null);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }
}