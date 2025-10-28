import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:provider/provider.dart';
import 'package:go_router/go_router.dart';

import 'core/config/app_config.dart';
import 'core/theme/app_theme.dart';
import 'core/theme/theme_provider.dart';
import 'core/network/api_client.dart';
import 'core/network/dio_client.dart';
import 'core/services/auth_service.dart';
import 'core/services/storage_service.dart';
import 'data/repositories/auth_repository.dart';
import 'presentation/providers/auth_provider.dart';
import 'presentation/blocs/auth/auth_bloc.dart';
import 'presentation/screens/auth/login_screen.dart';
import 'presentation/screens/auth/register_screen.dart';
import 'presentation/screens/auth/forgot_password_screen.dart';
// import 'presentation/screens/home/admin_home_screen.dart';
// import 'presentation/screens/home/teacher_home_screen.dart';
// import 'presentation/screens/home/parent_home_screen.dart';
// import 'presentation/screens/home/student_home_screen.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MultiProvider(
      providers: [
        ChangeNotifierProvider(create: (_) => ThemeProvider()),
        ChangeNotifierProvider(
          create: (_) => AuthProvider(AuthRepositoryImpl(
            AuthService(ApiClientImpl(DioClient.instance), StorageService()),
            StorageService(),
          )),
        ),
        BlocProvider(
          create: (context) => AuthBloc(
            context.read<AuthProvider>().authRepository,
          ),
        ),
      ],
      child: Consumer<ThemeProvider>(
        builder: (context, themeProvider, child) {
          return MaterialApp.router(
            title: AppConfig.appName,
            theme: AppTheme.lightTheme,
            darkTheme: AppTheme.darkTheme,
            themeMode: themeProvider.themeMode,
            routerConfig: _router,
            debugShowCheckedModeBanner: false,
          );
        },
      ),
    );
  }
}

// GoRouter configuration
final GoRouter _router = GoRouter(
  initialLocation: '/login',
  routes: [
    GoRoute(
      path: '/login',
      builder: (context, state) => const LoginScreen(),
    ),
    GoRoute(
      path: '/register',
      builder: (context, state) => const RegisterScreen(),
    ),
    GoRoute(
      path: '/forgot-password',
      builder: (context, state) => const ForgotPasswordScreen(),
    ),
    GoRoute(
      path: '/admin-home',
      builder: (context, state) => const Scaffold(
        body: Center(
          child: Text('Admin Home Screen - To be implemented'),
        ),
      ),
    ),
    GoRoute(
      path: '/teacher-home',
      builder: (context, state) => const Scaffold(
        body: Center(
          child: Text('Teacher Home Screen - To be implemented'),
        ),
      ),
    ),
    GoRoute(
      path: '/parent-home',
      builder: (context, state) => const Scaffold(
        body: Center(
          child: Text('Parent Home Screen - To be implemented'),
        ),
      ),
    ),
    GoRoute(
      path: '/staff-home',
      builder: (context, state) => const Scaffold(
        body: Center(
          child: Text('Staff Home Screen - To be implemented'),
        ),
      ),
    ),
  ],
  redirect: (context, state) {
    // Add authentication-based redirects here
    // For now, allow all routes
    return null;
  },
);
