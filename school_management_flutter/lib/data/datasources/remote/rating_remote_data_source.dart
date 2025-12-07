import '../../../core/network/api_client.dart';
import '../../models/rating.dart';

abstract class RatingRemoteDataSource {
  Future<List<Rating>> getRatings({Map<String, dynamic>? filters});
  Future<Rating> getRatingById(int id);
  Future<Rating> createRating(Rating rating);
  Future<Rating> updateRating(Rating rating);
  Future<void> deleteRating(int id);
  Future<List<Rating>> getRatingsByActivityPoint(int activityPointId);
}

class RatingRemoteDataSourceImpl implements RatingRemoteDataSource {
  final ApiClient apiClient;

  RatingRemoteDataSourceImpl(this.apiClient);

  @override
  Future<List<Rating>> getRatings({Map<String, dynamic>? filters}) async {
    final queryParams = filters ?? {};
    final result = await apiClient.get<List<Rating>>(
      '/ratings',
      queryParams: queryParams,
      fromJson: (data) {
        final List<dynamic> list = data as List<dynamic>;
        return list.map((json) => Rating.fromJson(json)).toList();
      },
    );

    return result.fold(
      (failure) => throw Exception(failure.message),
      (ratings) => ratings,
    );
  }

  @override
  Future<Rating> getRatingById(int id) async {
    final result = await apiClient.get<Rating>(
      '/ratings/$id',
      fromJson: (data) => Rating.fromJson(data),
    );

    return result.fold(
      (failure) => throw Exception(failure.message),
      (rating) => rating,
    );
  }

  @override
  Future<Rating> createRating(Rating rating) async {
    final result = await apiClient.post<Rating>(
      '/ratings',
      data: rating.toJson(),
      fromJson: (data) => Rating.fromJson(data),
    );

    return result.fold(
      (failure) => throw Exception(failure.message),
      (createdRating) => createdRating,
    );
  }

  @override
  Future<Rating> updateRating(Rating rating) async {
    final result = await apiClient.put<Rating>(
      '/ratings/${rating.id}',
      data: rating.toJson(),
      fromJson: (data) => Rating.fromJson(data),
    );

    return result.fold(
      (failure) => throw Exception(failure.message),
      (updatedRating) => updatedRating,
    );
  }

  @override
  Future<void> deleteRating(int id) async {
    final result = await apiClient.delete<void>('/ratings/$id');

    return result.fold(
      (failure) => throw Exception(failure.message),
      (_) => null,
    );
  }

  @override
  Future<List<Rating>> getRatingsByActivityPoint(int activityPointId) async {
    final result = await apiClient.get<List<Rating>>(
      '/ratings/activity-point/$activityPointId',
      fromJson: (data) {
        final List<dynamic> list = data as List<dynamic>;
        return list.map((json) => Rating.fromJson(json)).toList();
      },
    );

    return result.fold(
      (failure) => throw Exception(failure.message),
      (ratings) => ratings,
    );
  }
}