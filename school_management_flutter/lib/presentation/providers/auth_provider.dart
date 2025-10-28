import 'package:flutter/material.dart';
import '../../data/repositories/auth_repository.dart';

class AuthProvider extends ChangeNotifier {
  final AuthRepository _authRepository;

  AuthProvider(this._authRepository);

  AuthRepository get authRepository => _authRepository;

  // Add any additional auth-related state management here
  // For example: current user, login status, etc.
}