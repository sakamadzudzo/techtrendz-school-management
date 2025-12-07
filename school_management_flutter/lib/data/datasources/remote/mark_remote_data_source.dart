import '../../../core/network/api_client.dart';
import '../../models/mark.dart';

abstract class MarkRemoteDataSource {
  Future<List<Mark>> getMarks({Map<String, dynamic>? filters});
  Future<Mark> getMarkById(int id);
  Future<Mark> createMark(Mark mark);
  Future<Mark> updateMark(Mark mark);
  Future<void> deleteMark(int id);
  Future<List<Mark>> getMarksByStudent(int studentId);
  Future<List<Mark>> getMarksByCoursework(int courseworkId);
}

class MarkRemoteDataSourceImpl implements MarkRemoteDataSource {
  final ApiClient apiClient;

  MarkRemoteDataSourceImpl(this.apiClient);

  @override
  Future<List<Mark>> getMarks({Map<String, dynamic>? filters}) async {
    final queryParams = filters ?? {};
    final result = await apiClient.get<List<Mark>>(
      '/marks',
      queryParams: queryParams,
      fromJson: (data) {
        final List<dynamic> list = data as List<dynamic>;
        return list.map((json) => Mark.fromJson(json)).toList();
      },
    );

    return result.fold(
      (failure) => throw Exception(failure.message),
      (marks) => marks,
    );
  }

  @override
  Future<Mark> getMarkById(int id) async {
    final result = await apiClient.get<Mark>(
      '/marks/$id',
      fromJson: (data) => Mark.fromJson(data),
    );

    return result.fold(
      (failure) => throw Exception(failure.message),
      (mark) => mark,
    );
  }

  @override
  Future<Mark> createMark(Mark mark) async {
    final result = await apiClient.post<Mark>(
      '/marks',
      data: mark.toJson(),
      fromJson: (data) => Mark.fromJson(data),
    );

    return result.fold(
      (failure) => throw Exception(failure.message),
      (createdMark) => createdMark,
    );
  }

  @override
  Future<Mark> updateMark(Mark mark) async {
    final result = await apiClient.put<Mark>(
      '/marks/${mark.id}',
      data: mark.toJson(),
      fromJson: (data) => Mark.fromJson(data),
    );

    return result.fold(
      (failure) => throw Exception(failure.message),
      (updatedMark) => updatedMark,
    );
  }

  @override
  Future<void> deleteMark(int id) async {
    final result = await apiClient.delete<void>('/marks/$id');

    return result.fold(
      (failure) => throw Exception(failure.message),
      (_) => null,
    );
  }

  @override
  Future<List<Mark>> getMarksByStudent(int studentId) async {
    final result = await apiClient.get<List<Mark>>(
      '/marks/student/$studentId',
      fromJson: (data) {
        final List<dynamic> list = data as List<dynamic>;
        return list.map((json) => Mark.fromJson(json)).toList();
      },
    );

    return result.fold(
      (failure) => throw Exception(failure.message),
      (marks) => marks,
    );
  }

  @override
  Future<List<Mark>> getMarksByCoursework(int courseworkId) async {
    final result = await apiClient.get<List<Mark>>(
      '/marks/coursework/$courseworkId',
      fromJson: (data) {
        final List<dynamic> list = data as List<dynamic>;
        return list.map((json) => Mark.fromJson(json)).toList();
      },
    );

    return result.fold(
      (failure) => throw Exception(failure.message),
      (marks) => marks,
    );
  }
}