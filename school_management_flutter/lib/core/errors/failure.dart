import 'package:equatable/equatable.dart';

abstract class Failure extends Equatable {
  final String message;

  const Failure(this.message);

  @override
  List<Object?> get props => [message];
}

class ServerFailure extends Failure {
  final int? statusCode;

  const ServerFailure(String message, [this.statusCode]) : super(message);

  @override
  List<Object?> get props => [message, statusCode];
}

class NetworkFailure extends Failure {
  const NetworkFailure(String message) : super(message);
}

class CacheFailure extends Failure {
  const CacheFailure(String message) : super(message);
}

class AuthenticationFailure extends Failure {
  const AuthenticationFailure(String message) : super(message);
}

class ValidationFailure extends Failure {
  final Map<String, dynamic>? errors;

  const ValidationFailure(String message, [this.errors]) : super(message);

  @override
  List<Object?> get props => [message, errors];
}

class PermissionFailure extends Failure {
  const PermissionFailure(String message) : super(message);
}

class TimeoutFailure extends Failure {
  const TimeoutFailure(String message) : super(message);
}

class UnknownFailure extends Failure {
  const UnknownFailure(String message) : super(message);
}