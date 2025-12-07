import 'package:dio/dio.dart';

class AuthInterceptor extends Interceptor {
  final Future<String?> Function() getToken;
  final Future<String?> Function() getRefreshToken;
  final Future<void> Function(String) saveToken;
  final Future<void> Function() clearTokens;

  AuthInterceptor({
    required this.getToken,
    required this.getRefreshToken,
    required this.saveToken,
    required this.clearTokens,
  });

  @override
  void onRequest(RequestOptions options, RequestInterceptorHandler handler) async {
    final token = await getToken();
    if (token != null) {
      options.headers['Authorization'] = 'Bearer $token';
    }
    super.onRequest(options, handler);
  }

  @override
  void onError(DioException err, ErrorInterceptorHandler handler) async {
    if (err.response?.statusCode == 401) {
      final refreshToken = await getRefreshToken();
      if (refreshToken != null) {
        try {
          final dio = Dio();
          final response = await dio.post(
            '${err.requestOptions.baseUrl}/auth/refresh',
            data: {'refreshToken': refreshToken},
          );
          
          if (response.statusCode == 200) {
            final newToken = response.data['token'] as String;
            await saveToken(newToken);
            
            err.requestOptions.headers['Authorization'] = 'Bearer $newToken';
            final retryResponse = await Dio().fetch(err.requestOptions);
            return handler.resolve(retryResponse);
          }
        } catch (e) {
          await clearTokens();
        }
      } else {
        await clearTokens();
      }
    }
    super.onError(err, handler);
  }
}

class LoggingInterceptor extends Interceptor {
  @override
  void onRequest(RequestOptions options, RequestInterceptorHandler handler) {
    print('REQUEST[${options.method}] => PATH: ${options.path}');
    print('REQUEST HEADERS: ${options.headers}');
    if (options.data != null) {
      print('REQUEST DATA: ${options.data}');
    }
    super.onRequest(options, handler);
  }

  @override
  void onResponse(Response response, ResponseInterceptorHandler handler) {
    print('RESPONSE[${response.statusCode}] => PATH: ${response.requestOptions.path}');
    print('RESPONSE DATA: ${response.data}');
    super.onResponse(response, handler);
  }

  @override
  void onError(DioException err, ErrorInterceptorHandler handler) {
    print('ERROR[${err.response?.statusCode}] => PATH: ${err.requestOptions.path}');
    print('ERROR MESSAGE: ${err.message}');
    if (err.response?.data != null) {
      print('ERROR DATA: ${err.response?.data}');
    }
    super.onError(err, handler);
  }
}

class RetryInterceptor extends Interceptor {
  final int maxRetries;
  final Duration retryDelay;

  RetryInterceptor({
    this.maxRetries = 3,
    this.retryDelay = const Duration(seconds: 1),
  });

  @override
  void onError(DioException err, ErrorInterceptorHandler handler) async {
    if (_shouldRetry(err)) {
      final requestOptions = err.requestOptions;

      if (requestOptions.extra['retry_count'] == null) {
        requestOptions.extra['retry_count'] = 0;
      }

      final retryCount = requestOptions.extra['retry_count'] as int;

      if (retryCount < maxRetries) {
        requestOptions.extra['retry_count'] = retryCount + 1;

        await Future.delayed(retryDelay * (retryCount + 1));

        // Retry the request
        try {
          final dio = Dio();
          final response = await dio.fetch(requestOptions);
          return handler.resolve(response);
        } catch (e) {
          return handler.next(err);
        }
      }
    }

    super.onError(err, handler);
  }

  bool _shouldRetry(DioException err) {
    return err.type == DioExceptionType.connectionTimeout ||
           err.type == DioExceptionType.sendTimeout ||
           err.type == DioExceptionType.receiveTimeout ||
           err.type == DioExceptionType.connectionError ||
           (err.response?.statusCode != null &&
            err.response!.statusCode! >= 500);
  }
}

class CacheInterceptor extends Interceptor {
  final Map<String, Response> _cache = {};

  @override
  void onRequest(RequestOptions options, RequestInterceptorHandler handler) {
    if (options.method == 'GET' && _shouldCache(options)) {
      final cacheKey = _generateCacheKey(options);
      final cachedResponse = _cache[cacheKey];

      if (cachedResponse != null) {
        // Return cached response
        return handler.resolve(cachedResponse);
      }
    }

    super.onRequest(options, handler);
  }

  @override
  void onResponse(Response response, ResponseInterceptorHandler handler) {
    if (response.requestOptions.method == 'GET' &&
        _shouldCache(response.requestOptions)) {
      final cacheKey = _generateCacheKey(response.requestOptions);
      _cache[cacheKey] = response;
    }

    super.onResponse(response, handler);
  }

  bool _shouldCache(RequestOptions options) {
    // Cache GET requests for certain endpoints
    final cacheableEndpoints = [
      '/students',
      '/teachers',
      '/classrooms',
      '/subjects',
      '/schools',
    ];

    return cacheableEndpoints.any((endpoint) =>
      options.path.contains(endpoint));
  }

  String _generateCacheKey(RequestOptions options) {
    return '${options.method}:${options.path}:${options.queryParameters}';
  }

  void clearCache() {
    _cache.clear();
  }

  void removeFromCache(String path) {
    _cache.removeWhere((key, _) => key.contains(path));
  }
}