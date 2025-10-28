part of 'auth_bloc.dart';

abstract class AuthEvent extends Equatable {
  const AuthEvent();

  @override
  List<Object?> get props => [];
}

class AuthCheckRequested extends AuthEvent {
  const AuthCheckRequested();
}

class AuthLoginRequested extends AuthEvent {
  final String username;
  final String password;

  const AuthLoginRequested({
    required this.username,
    required this.password,
  });

  @override
  List<Object?> get props => [username, password];
}

class AuthRegisterRequested extends AuthEvent {
  final String email;
  final String username;
  final String password;
  final String firstName;
  final String lastName;
  final String role;
  final String? phone;
  final String? address;

  const AuthRegisterRequested({
    required this.email,
    required this.username,
    required this.password,
    required this.firstName,
    required this.lastName,
    required this.role,
    this.phone,
    this.address,
  });

  @override
  List<Object?> get props => [
        email,
        username,
        password,
        firstName,
        lastName,
        role,
        phone,
        address,
      ];
}

class AuthLogoutRequested extends AuthEvent {
  const AuthLogoutRequested();
}

class AuthTokenRefreshRequested extends AuthEvent {
  const AuthTokenRefreshRequested();
}