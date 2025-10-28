import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:provider/provider.dart';
import 'package:go_router/go_router.dart';
import 'core/constants/enums.dart';

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
import 'presentation/screens/home/admin_home/admin_home_screen.dart';
import 'presentation/screens/home/teacher_home/teacher_home_screen.dart';
import 'presentation/screens/home/parent_home/parent_home_screen.dart';
import 'presentation/screens/home/staff_home/staff_home_screen.dart';
import 'presentation/screens/home/student_home/student_home_screen.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  late GoRouter _router;

  @override
  void initState() {
    super.initState();
    _router = GoRouter(
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
          builder: (context, state) => const AdminHomeScreen(),
        ),
        GoRoute(
          path: '/teacher-home',
          builder: (context, state) => const TeacherHomeScreen(),
        ),
        GoRoute(
          path: '/parent-home',
          builder: (context, state) => const ParentHomeScreen(),
        ),
        GoRoute(
          path: '/staff-home',
          builder: (context, state) => const StaffHomeScreen(),
        ),
        GoRoute(
          path: '/student-home',
          builder: (context, state) => const StudentHomeScreen(),
        ),
      ],
      redirect: (context, state) {
        // Get the current auth state
        final authBloc = context.read<AuthBloc>();
        final authState = authBloc.state;

        // If user is authenticated, redirect to appropriate home screen based on active role
        if (authState is AuthAuthenticated) {
          final user = authState.user;
          final activeRole = user.activeRole;

          if (activeRole != null) {
            switch (activeRole) {
              case Role.ADMIN:
                return '/admin-home';
              case Role.TEACHER:
                return '/teacher-home';
              case Role.PARENT:
                return '/parent-home';
              case Role.STUDENT:
                return '/student-home';
              case Role.STAFF:
                return '/staff-home';
              case Role.GUEST:
                return '/login'; // Guests shouldn't have access
            }
          } else if (user.roles.length == 1) {
            // Auto-set active role if user has only one role
            final singleRole = user.roles.first;
            switch (singleRole) {
              case Role.ADMIN:
                return '/admin-home';
              case Role.TEACHER:
                return '/teacher-home';
              case Role.PARENT:
                return '/parent-home';
              case Role.STUDENT:
                return '/student-home';
              case Role.STAFF:
                return '/staff-home';
              case Role.GUEST:
                return '/login';
            }
          }
          // If no active role is set and user has multiple roles, stay on current route to show role selection
        }

        // If user is not authenticated and trying to access protected routes, redirect to login
        final protectedRoutes = ['/admin-home', '/teacher-home', '/parent-home', '/student-home', '/staff-home'];
        if (protectedRoutes.contains(state.matchedLocation) && authState is! AuthAuthenticated) {
          return '/login';
        }

        // Allow access to auth routes when not authenticated
        return null;
      },
    );
  }

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

