import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:phosphor_flutter/phosphor_flutter.dart';
import '../../../blocs/auth/auth_bloc.dart';
import '../../../widgets/role_selection_dialog.dart';
import '../../../../core/constants/enums.dart';

class AdminHomeScreen extends StatelessWidget {
  const AdminHomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Admin Dashboard'),
        actions: [
          BlocBuilder<AuthBloc, AuthState>(
            builder: (context, state) {
              if (state is AuthAuthenticated && state.user.roles.length > 1) {
                return PopupMenuButton<String>(
                  onSelected: (value) {
                    if (value == 'switch_role') {
                      showDialog(
                        context: context,
                        builder: (context) => RoleSelectionDialog(
                          roles: state.user.roles,
                          onRoleSelected: (role) {
                            context.read<AuthBloc>().add(AuthRoleSelected(role));
                          },
                        ),
                      );
                    } else if (value == 'logout') {
                      context.read<AuthBloc>().add(AuthLogoutRequested());
                    }
                  },
                  itemBuilder: (context) => [
                    const PopupMenuItem(
                      value: 'switch_role',
                      child: Text('Switch Role'),
                    ),
                    const PopupMenuItem(
                      value: 'logout',
                      child: Text('Logout'),
                    ),
                  ],
                );
              } else {
                return IconButton(
                  icon: const Icon(PhosphorIconsRegular.signOut),
                  onPressed: () {
                    context.read<AuthBloc>().add(AuthLogoutRequested());
                  },
                );
              }
            },
          ),
        ],
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: GridView.count(
          crossAxisCount: 2,
          crossAxisSpacing: 16.0,
          mainAxisSpacing: 16.0,
          children: [
            _buildMenuCard(
              context,
              'Students',
              PhosphorIconsRegular.graduationCap,
              () {
                // TODO: Navigate to student management
              },
            ),
            _buildMenuCard(
              context,
              'Teachers',
              PhosphorIconsRegular.chalkboardTeacher,
              () {
                // TODO: Navigate to teacher management
              },
            ),
            _buildMenuCard(
              context,
              'Classrooms',
              PhosphorIconsRegular.chalkboard,
              () {
                // TODO: Navigate to classroom management
              },
            ),
            _buildMenuCard(
              context,
              'Subjects',
              PhosphorIconsRegular.bookOpen,
              () {
                // TODO: Navigate to subject management
              },
            ),
            _buildMenuCard(
              context,
              'Reports',
              PhosphorIconsRegular.chartBar,
              () {
                // TODO: Navigate to reports
              },
            ),
            _buildMenuCard(
              context,
              'Settings',
              PhosphorIconsRegular.gear,
              () {
                // TODO: Navigate to settings
              },
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildMenuCard(BuildContext context, String title, IconData icon, VoidCallback onTap) {
    return Card(
      elevation: 4.0,
      child: InkWell(
        onTap: onTap,
        borderRadius: BorderRadius.circular(8.0),
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Icon(icon, size: 48.0, color: Theme.of(context).primaryColor),
              const SizedBox(height: 8.0),
              Text(
                title,
                style: Theme.of(context).textTheme.titleMedium,
                textAlign: TextAlign.center,
              ),
            ],
          ),
        ),
      ),
    );
  }
}