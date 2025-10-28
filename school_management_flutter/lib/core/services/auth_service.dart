import 'dart:convert';
import 'package:dartz/dartz.dart';
import '../../core/errors/failure.dart';
import '../../core/network/api_client.dart';
import '../../core/services/storage_service.dart';
import '../../data/models/user.dart';

class AuthService {
  final ApiClient _apiClient;
  final StorageService _storageService;

  AuthService(this._apiClient, this._storageService);

  Future<Either<Failure, User>> login(String username, String password) async {
    try {
      final result = await _apiClient.post<Map<String, dynamic>>(
        '/auth/login',
        data: {
          'username': username,
          'password': password,
        },
      );

      return result.fold(
        (failure) => Left(failure),
        (data) async {
          final user = User.fromJson(data['user']);
          final token = data['token'] as String;

          // Store token and user data
          await _storageService.saveToken(token);
          await _storageService.saveUser(user);

          return Right(user);
        },
      );
    } catch (e) {
      return Left(AuthenticationFailure('Login failed: ${e.toString()}'));
    }
  }

  Future<Either<Failure, User>> register({
    required String email,
    required String username,
    required String password,
    required String firstName,
    required String lastName,
    required String role,
    String? phone,
    String? address,
  }) async {
    try {
      final result = await _apiClient.post<Map<String, dynamic>>(
        '/auth/register',
        data: {
          'email': email,
          'username': username,
          'password': password,
          'firstName': firstName,
          'lastName': lastName,
          'role': role,
          'phone': phone,
          'address': address,
        },
      );

      return result.fold(
        (failure) => Left(failure),
        (data) async {
          final user = User.fromJson(data['user']);
          final token = data['token'] as String;

          // Store token and user data
          await _storageService.saveToken(token);
          await _storageService.saveUser(user);

          return Right(user);
        },
      );
    } catch (e) {
      return Left(AuthenticationFailure('Registration failed: ${e.toString()}'));
    }
  }

  Future<Either<Failure, void>> logout() async {
    try {
      // Call logout endpoint if needed
      await _apiClient.post('/auth/logout');

      // Clear local storage
      await _storageService.clearToken();
      await _storageService.clearUser();

      return const Right(null);
    } catch (e) {
      // Even if API call fails, clear local data
      await _storageService.clearToken();
      await _storageService.clearUser();
      return const Right(null);
    }
  }

  Future<Either<Failure, User>> refreshToken() async {
    try {
      final refreshToken = await _storageService.getRefreshToken();
      if (refreshToken == null) {
        return Left(AuthenticationFailure('No refresh token available'));
      }

      final result = await _apiClient.post<Map<String, dynamic>>(
        '/auth/refresh',
        data: {'refreshToken': refreshToken},
      );

      return result.fold(
        (failure) => Left(failure),
        (data) async {
          final user = User.fromJson(data['user']);
          final newToken = data['token'] as String;

          await _storageService.saveToken(newToken);
          await _storageService.saveUser(user);

          return Right(user);
        },
      );
    } catch (e) {
      return Left(AuthenticationFailure('Token refresh failed: ${e.toString()}'));
    }
  }

  Future<Either<Failure, void>> forgotPassword(String email) async {
    try {
      final result = await _apiClient.post(
        '/auth/forgot-password',
        data: {'email': email},
      );

      return result.fold(
        (failure) => Left(failure),
        (_) => const Right(null),
      );
    } catch (e) {
      return Left(AuthenticationFailure('Forgot password failed: ${e.toString()}'));
    }
  }

  Future<Either<Failure, void>> resetPassword({
    required String token,
    required String newPassword,
  }) async {
    try {
      final result = await _apiClient.post(
        '/auth/reset-password',
        data: {
          'token': token,
          'password': newPassword,
        },
      );

      return result.fold(
        (failure) => Left(failure),
        (_) => const Right(null),
      );
    } catch (e) {
      return Left(AuthenticationFailure('Password reset failed: ${e.toString()}'));
    }
  }

  Future<Either<Failure, User>> changePassword({
    required String currentPassword,
    required String newPassword,
  }) async {
    try {
      final result = await _apiClient.post<Map<String, dynamic>>(
        '/auth/change-password',
        data: {
          'currentPassword': currentPassword,
          'newPassword': newPassword,
        },
      );

      return result.fold(
        (failure) => Left(failure),
        (data) async {
          final user = User.fromJson(data['user']);
          await _storageService.saveUser(user);
          return Right(user);
        },
      );
    } catch (e) {
      return Left(AuthenticationFailure('Password change failed: ${e.toString()}'));
    }
  }

  Future<bool> isLoggedIn() async {
    final token = await _storageService.getToken();
    final user = await _storageService.getUser();
    return token != null && user != null;
  }

  Future<User?> getCurrentUser() async {
    return await _storageService.getUser();
  }

  Future<String?> getCurrentToken() async {
    return await _storageService.getToken();
  }
}