import 'package:dartz/dartz.dart';
import 'package:dio/dio.dart';
import '../../core/config/api_endpoints.dart';
import '../../core/config/environment.dart';
import '../../core/errors/exceptions.dart';
import '../../core/errors/failure.dart';

abstract class ApiClient {
  Future<Either<Failure, T>> get<T>(
    String endpoint, {
    Map<String, dynamic>? queryParams,
    Map<String, String>? headers,
    T Function(Map<String, dynamic>)? fromJson,
  });

  Future<Either<Failure, T>> post<T>(
    String endpoint, {
    dynamic data,
    Map<String, String>? headers,
    T Function(Map<String, dynamic>)? fromJson,
  });

  Future<Either<Failure, T>> put<T>(
    String endpoint, {
    dynamic data,
    Map<String, String>? headers,
    T Function(Map<String, dynamic>)? fromJson,
  });

  Future<Either<Failure, T>> delete<T>(
    String endpoint, {
    Map<String, String>? headers,
    T Function(Map<String, dynamic>)? fromJson,
  });

  Future<Either<Failure, T>> patch<T>(
    String endpoint, {
    dynamic data,
    Map<String, String>? headers,
    T Function(Map<String, dynamic>)? fromJson,
  });
}

class ApiClientImpl implements ApiClient {
  final Dio _dio;

  ApiClientImpl(this._dio) {
    _setupInterceptors();
  }

  void _setupInterceptors() {
    _dio.interceptors.addAll([
      // Add auth interceptor
      InterceptorsWrapper(
        onRequest: (options, handler) async {
          // Add authorization header if token exists
          final token = await _getAuthToken();
          if (token != null) {
            options.headers['Authorization'] = 'Bearer $token';
          }

          // Add common headers
          options.headers.addAll({
            'Content-Type': 'application/json',
            'Accept': 'application/json',
          });

          return handler.next(options);
        },
        onError: (error, handler) async {
          if (error.response?.statusCode == 401) {
            // Token expired, try refresh
            final refreshResult = await _refreshToken();
            if (refreshResult.isRight()) {
              // Retry the request with new token
              final token = await _getAuthToken();
              if (token != null) {
                error.requestOptions.headers['Authorization'] = 'Bearer $token';
                final response = await _dio.fetch(error.requestOptions);
                return handler.resolve(response);
              }
            }
          }
          return handler.next(error);
        },
      ),
    ]);
  }

  Future<String?> _getAuthToken() async {
    // Implementation will be in storage service
    return null;
  }

  Future<Either<Failure, String>> _refreshToken() async {
    // Implementation will be in auth service
    return Left(AuthenticationFailure('Token refresh failed'));
  }

  @override
  Future<Either<Failure, T>> get<T>(
    String endpoint, {
    Map<String, dynamic>? queryParams,
    Map<String, String>? headers,
    T Function(Map<String, dynamic>)? fromJson,
  }) async {
    try {
      final response = await _dio.get(
        endpoint,
        queryParameters: queryParams,
        options: headers != null ? Options(headers: headers) : null,
      );

      return _handleResponse<T>(response, fromJson);
    } on DioException catch (e) {
      return Left(_handleDioError(e));
    } catch (e) {
      return Left(UnknownFailure(e.toString()));
    }
  }

  @override
  Future<Either<Failure, T>> post<T>(
    String endpoint, {
    dynamic data,
    Map<String, String>? headers,
    T Function(Map<String, dynamic>)? fromJson,
  }) async {
    try {
      final response = await _dio.post(
        endpoint,
        data: data,
        options: headers != null ? Options(headers: headers) : null,
      );

      return _handleResponse<T>(response, fromJson);
    } on DioException catch (e) {
      return Left(_handleDioError(e));
    } catch (e) {
      return Left(UnknownFailure(e.toString()));
    }
  }

  @override
  Future<Either<Failure, T>> put<T>(
    String endpoint, {
    dynamic data,
    Map<String, String>? headers,
    T Function(Map<String, dynamic>)? fromJson,
  }) async {
    try {
      final response = await _dio.put(
        endpoint,
        data: data,
        options: headers != null ? Options(headers: headers) : null,
      );

      return _handleResponse<T>(response, fromJson);
    } on DioException catch (e) {
      return Left(_handleDioError(e));
    } catch (e) {
      return Left(UnknownFailure(e.toString()));
    }
  }

  @override
  Future<Either<Failure, T>> delete<T>(
    String endpoint, {
    Map<String, String>? headers,
    T Function(Map<String, dynamic>)? fromJson,
  }) async {
    try {
      final response = await _dio.delete(
        endpoint,
        options: headers != null ? Options(headers: headers) : null,
      );

      return _handleResponse<T>(response, fromJson);
    } on DioException catch (e) {
      return Left(_handleDioError(e));
    } catch (e) {
      return Left(UnknownFailure(e.toString()));
    }
  }

  @override
  Future<Either<Failure, T>> patch<T>(
    String endpoint, {
    dynamic data,
    Map<String, String>? headers,
    T Function(Map<String, dynamic>)? fromJson,
  }) async {
    try {
      final response = await _dio.patch(
        endpoint,
        data: data,
        options: headers != null ? Options(headers: headers) : null,
      );

      return _handleResponse<T>(response, fromJson);
    } on DioException catch (e) {
      return Left(_handleDioError(e));
    } catch (e) {
      return Left(UnknownFailure(e.toString()));
    }
  }

  Either<Failure, T> _handleResponse<T>(
    Response response,
    T Function(Map<String, dynamic>)? fromJson,
  ) {
    try {
      if (response.statusCode != null &&
          response.statusCode! >= 200 &&
          response.statusCode! < 300) {
        if (fromJson != null && response.data != null) {
          final result = fromJson(response.data);
          return Right(result);
        } else {
          return Right(response.data as T);
        }
      } else {
        return Left(ServerFailure(
          response.data?['message'] ?? 'Server error',
          response.statusCode,
        ));
      }
    } catch (e) {
      return Left(UnknownFailure('Failed to parse response: ${e.toString()}'));
    }
  }

  Failure _handleDioError(DioException error) {
    switch (error.type) {
      case DioExceptionType.connectionTimeout:
      case DioExceptionType.sendTimeout:
      case DioExceptionType.receiveTimeout:
        return TimeoutFailure('Request timeout');
      case DioExceptionType.connectionError:
        return NetworkFailure('No internet connection');
      case DioExceptionType.badResponse:
        final statusCode = error.response?.statusCode;
        final message = error.response?.data?['message'] ?? error.message;

        if (statusCode == 401) {
          return AuthenticationFailure(message ?? 'Unauthorized');
        } else if (statusCode == 403) {
          return PermissionFailure(message ?? 'Forbidden');
        } else if (statusCode == 422) {
          return ValidationFailure(
            message ?? 'Validation error',
            error.response?.data?['errors'],
          );
        } else {
          return ServerFailure(message ?? 'Server error', statusCode);
        }
      default:
        return NetworkFailure(error.message ?? 'Network error');
    }
  }
}