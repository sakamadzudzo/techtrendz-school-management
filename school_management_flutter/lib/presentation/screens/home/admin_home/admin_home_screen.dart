import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:phosphor_flutter/phosphor_flutter.dart';
import 'package:go_router/go_router.dart';
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
              () => context.go('/students'),
            ),
            _buildMenuCard(
              context,
              'Teachers',
              PhosphorIconsRegular.chalkboardTeacher,
              () => context.go('/teachers'),
            ),
            _buildMenuCard(
              context,
              'Classrooms',
              PhosphorIconsRegular.chalkboard,
              () => context.go('/classrooms'),
            ),
            _buildMenuCard(
              context,
              'Subjects',
              PhosphorIconsRegular.bookOpen,
              () => ScaffoldMessenger.of(context).showSnackBar(
                const SnackBar(content: Text('Subject management coming soon')),
              ),
            ),
            _buildMenuCard(
              context,
              'Reports',
              PhosphorIconsRegular.chartBar,
              () => context.go('/reports'),
            ),
            _buildMenuCard(
              context,
              'Settings',
              PhosphorIconsRegular.gear,
              () => ScaffoldMessenger.of(context).showSnackBar(
                const SnackBar(content: Text('Settings coming soon')),
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildMenuCard(BuildContext context, String title, IconData icon, VoidCallback onTap) {
    return Card(
      elevation: 2.0,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12.0)),
      child: InkWell(
        onTap: onTap,
        borderRadius: BorderRadius.circular(12.0),
        child: Container(
          padding: const EdgeInsets.all(20.0),
          decoration: BoxDecoration(
            gradient: LinearGradient(
              begin: Alignment.topLeft,
              end: Alignment.bottomRight,
              colors: [
                Theme.of(context).primaryColor.withOpacity(0.05),
                Theme.of(context).primaryColor.withOpacity(0.02),
              ],
            ),
            borderRadius: BorderRadius.circular(12.0),
          ),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Container(
                padding: const EdgeInsets.all(12.0),
                decoration: BoxDecoration(
                  color: Theme.of(context).primaryColor.withOpacity(0.1),
                  shape: BoxShape.circle,
                ),
                child: Icon(icon, size: 40.0, color: Theme.of(context).primaryColor),
              ),
              const SizedBox(height: 12.0),
              Text(
                title,
                style: Theme.of(context).textTheme.titleMedium?.copyWith(fontWeight: FontWeight.w600),
                textAlign: TextAlign.center,
              ),
            ],
          ),
        ),
      ),
    );
  }
}