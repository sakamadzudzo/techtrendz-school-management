import 'package:dio/dio.dart';
import 'package:pretty_dio_logger/pretty_dio_logger.dart';
import '../../core/config/app_config.dart';
import '../../core/config/environment.dart';

class DioClient {
  static Dio? _dio;

  static Dio get instance {
    _dio ??= _createDio();
    return _dio!;
  }

  static Dio _createDio() {
    final dio = Dio(
      BaseOptions(
        baseUrl: EnvironmentConfig.fullBaseUrl,
        connectTimeout: Duration(milliseconds: AppConfig.connectTimeout),
        receiveTimeout: Duration(milliseconds: AppConfig.receiveTimeout),
        sendTimeout: Duration(milliseconds: AppConfig.sendTimeout),
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json',
        },
      ),
    );

    // Add interceptors
    dio.interceptors.addAll(_getInterceptors());

    return dio;
  }

  static List<Interceptor> _getInterceptors() {
    final interceptors = <Interceptor>[];

    // Add logging interceptor in development
    if (EnvironmentConfig.enableLogging) {
      interceptors.add(
        PrettyDioLogger(
          requestHeader: true,
          requestBody: true,
          responseBody: true,
          responseHeader: false,
          error: true,
          compact: true,
          maxWidth: 90,
        ),
      );
    }

    // Add custom interceptors
    interceptors.addAll([
      // Request interceptor for adding common headers
      InterceptorsWrapper(
        onRequest: (options, handler) {
          // Add any additional headers here
          options.headers.addAll({
            'X-App-Version': AppConfig.appVersion,
            'X-Platform': 'mobile',
          });
          return handler.next(options);
        },
      ),

      // Response interceptor for common response handling
      InterceptorsWrapper(
        onResponse: (response, handler) {
          // Handle common response logic
          return handler.next(response);
        },
      ),

      // Error interceptor for common error handling
      InterceptorsWrapper(
        onError: (error, handler) {
          // Handle common error logic
          return handler.next(error);
        },
      ),
    ]);

    return interceptors;
  }

  // Utility methods for common operations
  static Future<Response> get(
    String path, {
    Map<String, dynamic>? queryParameters,
    Options? options,
    CancelToken? cancelToken,
    ProgressCallback? onReceiveProgress,
  }) {
    return instance.get(
      path,
      queryParameters: queryParameters,
      options: options,
      cancelToken: cancelToken,
      onReceiveProgress: onReceiveProgress,
    );
  }

  static Future<Response> post(
    String path, {
    dynamic data,
    Map<String, dynamic>? queryParameters,
    Options? options,
    CancelToken? cancelToken,
    ProgressCallback? onSendProgress,
    ProgressCallback? onReceiveProgress,
  }) {
    return instance.post(
      path,
      data: data,
      queryParameters: queryParameters,
      options: options,
      cancelToken: cancelToken,
      onSendProgress: onSendProgress,
      onReceiveProgress: onReceiveProgress,
    );
  }

  static Future<Response> put(
    String path, {
    dynamic data,
    Map<String, dynamic>? queryParameters,
    Options? options,
    CancelToken? cancelToken,
    ProgressCallback? onSendProgress,
    ProgressCallback? onReceiveProgress,
  }) {
    return instance.put(
      path,
      data: data,
      queryParameters: queryParameters,
      options: options,
      cancelToken: cancelToken,
      onSendProgress: onSendProgress,
      onReceiveProgress: onReceiveProgress,
    );
  }

  static Future<Response> delete(
    String path, {
    dynamic data,
    Map<String, dynamic>? queryParameters,
    Options? options,
    CancelToken? cancelToken,
  }) {
    return instance.delete(
      path,
      data: data,
      queryParameters: queryParameters,
      options: options,
      cancelToken: cancelToken,
    );
  }

  static Future<Response> patch(
    String path, {
    dynamic data,
    Map<String, dynamic>? queryParameters,
    Options? options,
    CancelToken? cancelToken,
    ProgressCallback? onSendProgress,
    ProgressCallback? onReceiveProgress,
  }) {
    return instance.patch(
      path,
      data: data,
      queryParameters: queryParameters,
      options: options,
      cancelToken: cancelToken,
      onSendProgress: onSendProgress,
      onReceiveProgress: onReceiveProgress,
    );
  }

  // Method to update base URL (useful for environment switching)
  static void updateBaseUrl(String newBaseUrl) {
    instance.options.baseUrl = newBaseUrl;
  }

  // Method to add authorization header
  static void setAuthToken(String token) {
    instance.options.headers['Authorization'] = 'Bearer $token';
  }

  // Method to remove authorization header
  static void removeAuthToken() {
    instance.options.headers.remove('Authorization');
  }

  // Method to clear all interceptors
  static void clearInterceptors() {
    instance.interceptors.clear();
    instance.interceptors.addAll(_getInterceptors());
  }
}