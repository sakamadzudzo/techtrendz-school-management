import 'package:dartz/dartz.dart';
import '../../core/errors/failure.dart';
import '../../core/services/auth_service.dart';
import '../../core/services/storage_service.dart';
import '../models/user.dart';

abstract class AuthRepository {
  Future<Either<Failure, User>> login(String username, String password);
  Future<Either<Failure, User>> register({
    required String email,
    required String username,
    required String password,
    required String firstName,
    required String lastName,
    required String role,
    String? phone,
    String? address,
  });
  Future<Either<Failure, void>> logout();
  Future<Either<Failure, User>> refreshToken();
  Future<Either<Failure, void>> forgotPassword(String email);
  Future<Either<Failure, void>> resetPassword({
    required String token,
    required String newPassword,
  });
  Future<Either<Failure, User>> changePassword({
    required String currentPassword,
    required String newPassword,
  });
  Future<bool> isLoggedIn();
  Future<Either<Failure, User?>> getCurrentUser();
  Future<Either<Failure, String?>> getCurrentToken();
}

class AuthRepositoryImpl implements AuthRepository {
  final AuthService _authService;
  final StorageService _storageService;

  AuthRepositoryImpl(this._authService, this._storageService);

  @override
  Future<Either<Failure, User>> login(String username, String password) async {
    return await _authService.login(username, password);
  }

  @override
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
    return await _authService.register(
      email: email,
      username: username,
      password: password,
      firstName: firstName,
      lastName: lastName,
      role: role,
      phone: phone,
      address: address,
    );
  }

  @override
  Future<Either<Failure, void>> logout() async {
    return await _authService.logout();
  }

  @override
  Future<Either<Failure, User>> refreshToken() async {
    return await _authService.refreshToken();
  }

  @override
  Future<Either<Failure, void>> forgotPassword(String email) async {
    return await _authService.forgotPassword(email);
  }

  @override
  Future<Either<Failure, void>> resetPassword({
    required String token,
    required String newPassword,
  }) async {
    return await _authService.resetPassword(
      token: token,
      newPassword: newPassword,
    );
  }

  @override
  Future<Either<Failure, User>> changePassword({
    required String currentPassword,
    required String newPassword,
  }) async {
    return await _authService.changePassword(
      currentPassword: currentPassword,
      newPassword: newPassword,
    );
  }

  @override
  Future<bool> isLoggedIn() async {
    return await _authService.isLoggedIn();
  }

  @override
  Future<Either<Failure, User?>> getCurrentUser() async {
    try {
      final user = await _authService.getCurrentUser();
      return Right(user);
    } catch (e) {
      return Left(CacheFailure('Failed to get current user: ${e.toString()}'));
    }
  }

  @override
  Future<Either<Failure, String?>> getCurrentToken() async {
    try {
      final token = await _authService.getCurrentToken();
      return Right(token);
    } catch (e) {
      return Left(CacheFailure('Failed to get current token: ${e.toString()}'));
    }
  }
}