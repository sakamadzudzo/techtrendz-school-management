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
import 'presentation/screens/management/students/student_list/student_list_screen.dart';
import 'presentation/screens/management/students/student_detail/student_detail_screen.dart';
import 'presentation/screens/management/students/student_form/student_form_screen.dart';
import 'presentation/screens/management/teachers/teacher_list/teacher_list_screen.dart';
import 'presentation/screens/management/teachers/teacher_detail/teacher_detail_screen.dart';
import 'presentation/screens/management/teachers/teacher_form/teacher_form_screen.dart';
import 'presentation/screens/management/parents/parent_list/parent_list_screen.dart';
import 'presentation/screens/management/parents/parent_detail/parent_detail_screen.dart';
import 'presentation/screens/management/parents/parent_form/parent_form_screen.dart';
import 'presentation/screens/management/schools/school_list/school_list_screen.dart';
import 'presentation/screens/management/schools/school_detail/school_detail_screen.dart';
import 'presentation/screens/management/schools/school_form/school_form_screen.dart';
import 'presentation/screens/management/classrooms/classroom_list/classroom_list_screen.dart';
import 'presentation/screens/management/classrooms/classroom_detail/classroom_detail_screen.dart';
import 'presentation/screens/management/classrooms/classroom_form/classroom_form_screen.dart';
import 'presentation/screens/reports/report_list_screen.dart';
import 'presentation/screens/reports/report_filters_screen.dart';
import 'presentation/screens/reports/report_viewer_screen.dart';
import 'presentation/screens/profile/profile/profile_screen.dart';
import 'presentation/screens/profile/change_password/change_password_screen.dart';
import 'data/repositories/student_repository.dart';
import 'data/datasources/remote/student_remote_data_source.dart';
import 'presentation/blocs/student/student_bloc.dart';
import 'data/repositories/school_repository.dart';
import 'data/datasources/remote/school_remote_data_source.dart';
import 'presentation/blocs/school/school_bloc.dart';
import 'data/repositories/classroom_repository.dart';
import 'data/datasources/remote/classroom_remote_data_source.dart';
import 'presentation/blocs/classroom/classroom_bloc.dart';
import 'data/repositories/report_repository.dart';
import 'data/repositories/report_repository_impl.dart';
import 'data/datasources/remote/report_remote_data_source.dart';
import 'presentation/blocs/report/report_bloc.dart';

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
        GoRoute(
          path: '/students',
          builder: (context, state) => const StudentListScreen(),
        ),
        GoRoute(
          path: '/students/new',
          builder: (context, state) => const StudentFormScreen(),
        ),
        GoRoute(
          path: '/students/:id',
          builder: (context, state) {
            final id = int.parse(state.pathParameters['id']!);
            return StudentDetailScreen(studentId: id);
          },
        ),
        GoRoute(
          path: '/students/:id/edit',
          builder: (context, state) {
            final id = int.parse(state.pathParameters['id']!);
            return StudentFormScreen(studentId: id);
          },
        ),
        GoRoute(
          path: '/teachers',
          builder: (context, state) => const TeacherListScreen(),
        ),
        GoRoute(
          path: '/teachers/new',
          builder: (context, state) => const TeacherFormScreen(),
        ),
        GoRoute(
          path: '/teachers/:id',
          builder: (context, state) {
            final id = int.parse(state.pathParameters['id']!);
            return TeacherDetailScreen(teacherId: id);
          },
        ),
        GoRoute(
          path: '/teachers/:id/edit',
          builder: (context, state) {
            final id = int.parse(state.pathParameters['id']!);
            return TeacherFormScreen(teacherId: id);
          },
        ),
        GoRoute(
          path: '/parents',
          builder: (context, state) => const ParentListScreen(),
        ),
        GoRoute(
          path: '/parents/new',
          builder: (context, state) => const ParentFormScreen(),
        ),
        GoRoute(
          path: '/parents/:id',
          builder: (context, state) {
            final id = int.parse(state.pathParameters['id']!);
            return ParentDetailScreen(parentId: id);
          },
        ),
        GoRoute(
          path: '/parents/:id/edit',
          builder: (context, state) {
            final id = int.parse(state.pathParameters['id']!);
            return ParentFormScreen(parentId: id);
          },
        ),
        GoRoute(
          path: '/schools',
          builder: (context, state) => const SchoolListScreen(),
        ),
        GoRoute(
          path: '/schools/new',
          builder: (context, state) => const SchoolFormScreen(),
        ),
        GoRoute(
          path: '/schools/:id',
          builder: (context, state) {
            final id = int.parse(state.pathParameters['id']!);
            return SchoolDetailScreen(schoolId: id);
          },
        ),
        GoRoute(
          path: '/schools/:id/edit',
          builder: (context, state) {
            final id = int.parse(state.pathParameters['id']!);
            return SchoolFormScreen(schoolId: id);
          },
        ),
        GoRoute(
          path: '/classrooms',
          builder: (context, state) => const ClassroomListScreen(),
        ),
        GoRoute(
          path: '/classrooms/new',
          builder: (context, state) => const ClassroomFormScreen(),
        ),
        GoRoute(
          path: '/classrooms/:id',
          builder: (context, state) {
            final id = int.parse(state.pathParameters['id']!);
            return ClassroomDetailScreen(classroomId: id);
          },
        ),
        GoRoute(
          path: '/classrooms/:id/edit',
          builder: (context, state) {
            final id = int.parse(state.pathParameters['id']!);
            return ClassroomFormScreen(classroomId: id);
          },
        ),
        GoRoute(
          path: '/reports',
          builder: (context, state) => const ReportListScreen(),
        ),
        GoRoute(
          path: '/reports/filters',
          builder: (context, state) => const ReportFiltersScreen(),
        ),
        GoRoute(
          path: '/reports/viewer',
          builder: (context, state) => const ReportViewerScreen(),
        ),
        GoRoute(
          path: '/profile',
          builder: (context, state) => const ProfileScreen(),
        ),
        GoRoute(
          path: '/change-password',
          builder: (context, state) => const ChangePasswordScreen(),
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
        final protectedRoutes = ['/admin-home', '/teacher-home', '/parent-home', '/student-home', '/staff-home', '/students', '/teachers', '/parents', '/schools'];
        if (protectedRoutes.any((route) => state.matchedLocation.startsWith(route)) && authState is! AuthAuthenticated) {
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
        BlocProvider(
          create: (context) => StudentBloc(
            StudentRepository(StudentRemoteDataSource()),
          ),
        ),
        BlocProvider(
          create: (context) => SchoolBloc(
            SchoolRepository(SchoolRemoteDataSource()),
          ),
        ),
        BlocProvider(
          create: (context) => ClassroomBloc(
            ClassroomRepository(ClassroomRemoteDataSource()),
          ),
        ),
        BlocProvider(
          create: (context) => ReportBloc(
            ReportRepositoryImpl(ReportRemoteDataSourceImpl()),
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

