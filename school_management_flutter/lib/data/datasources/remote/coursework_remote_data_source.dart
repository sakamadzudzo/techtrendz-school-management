import '../../../core/network/api_client.dart';
import '../../models/coursework.dart';

abstract class CourseworkRemoteDataSource {
  Future<List<Coursework>> getCourseworks({Map<String, dynamic>? filters});
  Future<Coursework> getCourseworkById(int id);
  Future<Coursework> createCoursework(Coursework coursework);
  Future<Coursework> updateCoursework(Coursework coursework);
  Future<void> deleteCoursework(int id);
}

class CourseworkRemoteDataSourceImpl implements CourseworkRemoteDataSource {
  final ApiClient apiClient;

  CourseworkRemoteDataSourceImpl(this.apiClient);

  @override
  Future<List<Coursework>> getCourseworks({Map<String, dynamic>? filters}) async {
    final queryParams = filters ?? {};
    final result = await apiClient.get<List<Coursework>>(
      '/courseworks',
      queryParams: queryParams,
      fromJson: (data) {
        final List<dynamic> list = data as List<dynamic>;
        return list.map((json) => Coursework.fromJson(json)).toList();
      },
    );

    return result.fold(
      (failure) => throw Exception(failure.message),
      (courseworks) => courseworks,
    );
  }

  @override
  Future<Coursework> getCourseworkById(int id) async {
    final result = await apiClient.get<Coursework>(
      '/courseworks/$id',
      fromJson: (data) => Coursework.fromJson(data),
    );

    return result.fold(
      (failure) => throw Exception(failure.message),
      (coursework) => coursework,
    );
  }

  @override
  Future<Coursework> createCoursework(Coursework coursework) async {
    final result = await apiClient.post<Coursework>(
      '/courseworks',
      data: coursework.toJson(),
      fromJson: (data) => Coursework.fromJson(data),
    );

    return result.fold(
      (failure) => throw Exception(failure.message),
      (createdCoursework) => createdCoursework,
    );
  }

  @override
  Future<Coursework> updateCoursework(Coursework coursework) async {
    final result = await apiClient.put<Coursework>(
      '/courseworks/${coursework.id}',
      data: coursework.toJson(),
      fromJson: (data) => Coursework.fromJson(data),
    );

    return result.fold(
      (failure) => throw Exception(failure.message),
      (updatedCoursework) => updatedCoursework,
    );
  }

  @override
  Future<void> deleteCoursework(int id) async {
    final result = await apiClient.delete<void>('/courseworks/$id');

    return result.fold(
      (failure) => throw Exception(failure.message),
      (_) => null,
    );
  }
}