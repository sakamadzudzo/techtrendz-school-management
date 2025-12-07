import '../../../core/network/api_client.dart';
import '../../models/activity_point.dart';

abstract class ActivityPointRemoteDataSource {
  Future<List<ActivityPoint>> getActivityPoints({Map<String, dynamic>? filters});
  Future<ActivityPoint> getActivityPointById(int id);
  Future<ActivityPoint> createActivityPoint(ActivityPoint activityPoint);
  Future<ActivityPoint> updateActivityPoint(ActivityPoint activityPoint);
  Future<void> deleteActivityPoint(int id);
  Future<List<ActivityPoint>> getActivityPointsByStudent(int studentId);
  Future<List<ActivityPoint>> getActivityPointsByActivity(int activityId);
}

class ActivityPointRemoteDataSourceImpl implements ActivityPointRemoteDataSource {
  final ApiClient apiClient;

  ActivityPointRemoteDataSourceImpl(this.apiClient);

  @override
  Future<List<ActivityPoint>> getActivityPoints({Map<String, dynamic>? filters}) async {
    final queryParams = filters ?? {};
    final result = await apiClient.get<List<ActivityPoint>>(
      '/activity-points',
      queryParams: queryParams,
      fromJson: (data) {
        final List<dynamic> list = data as List<dynamic>;
        return list.map((json) => ActivityPoint.fromJson(json)).toList();
      },
    );

    return result.fold(
      (failure) => throw Exception(failure.message),
      (activityPoints) => activityPoints,
    );
  }

  @override
  Future<ActivityPoint> getActivityPointById(int id) async {
    final result = await apiClient.get<ActivityPoint>(
      '/activity-points/$id',
      fromJson: (data) => ActivityPoint.fromJson(data),
    );

    return result.fold(
      (failure) => throw Exception(failure.message),
      (activityPoint) => activityPoint,
    );
  }

  @override
  Future<ActivityPoint> createActivityPoint(ActivityPoint activityPoint) async {
    final result = await apiClient.post<ActivityPoint>(
      '/activity-points',
      data: activityPoint.toJson(),
      fromJson: (data) => ActivityPoint.fromJson(data),
    );

    return result.fold(
      (failure) => throw Exception(failure.message),
      (createdActivityPoint) => createdActivityPoint,
    );
  }

  @override
  Future<ActivityPoint> updateActivityPoint(ActivityPoint activityPoint) async {
    final result = await apiClient.put<ActivityPoint>(
      '/activity-points/${activityPoint.id}',
      data: activityPoint.toJson(),
      fromJson: (data) => ActivityPoint.fromJson(data),
    );

    return result.fold(
      (failure) => throw Exception(failure.message),
      (updatedActivityPoint) => updatedActivityPoint,
    );
  }

  @override
  Future<void> deleteActivityPoint(int id) async {
    final result = await apiClient.delete<void>('/activity-points/$id');

    return result.fold(
      (failure) => throw Exception(failure.message),
      (_) => null,
    );
  }

  @override
  Future<List<ActivityPoint>> getActivityPointsByStudent(int studentId) async {
    final result = await apiClient.get<List<ActivityPoint>>(
      '/activity-points/student/$studentId',
      fromJson: (data) {
        final List<dynamic> list = data as List<dynamic>;
        return list.map((json) => ActivityPoint.fromJson(json)).toList();
      },
    );

    return result.fold(
      (failure) => throw Exception(failure.message),
      (activityPoints) => activityPoints,
    );
  }

  @override
  Future<List<ActivityPoint>> getActivityPointsByActivity(int activityId) async {
    final result = await apiClient.get<List<ActivityPoint>>(
      '/activity-points/activity/$activityId',
      fromJson: (data) {
        final List<dynamic> list = data as List<dynamic>;
        return list.map((json) => ActivityPoint.fromJson(json)).toList();
      },
    );

    return result.fold(
      (failure) => throw Exception(failure.message),
      (activityPoints) => activityPoints,
    );
  }
}