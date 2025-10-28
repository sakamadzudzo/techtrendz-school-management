import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:phosphor_flutter/phosphor_flutter.dart';
import '../../../blocs/auth/auth_bloc.dart';

class TeacherHomeScreen extends StatelessWidget {
  const TeacherHomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Teacher Dashboard'),
        actions: [
          IconButton(
            icon: const Icon(PhosphorIconsRegular.signOut),
            onPressed: () {
              context.read<AuthBloc>().add(AuthLogoutRequested());
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
              'My Classes',
              PhosphorIconsRegular.chalkboard,
              () {
                // TODO: Navigate to my classes
              },
            ),
            _buildMenuCard(
              context,
              'Students',
              PhosphorIconsRegular.graduationCap,
              () {
                // TODO: Navigate to student list
              },
            ),
            _buildMenuCard(
              context,
              'Coursework',
              PhosphorIconsRegular.bookOpen,
              () {
                // TODO: Navigate to coursework management
              },
            ),
            _buildMenuCard(
              context,
              'Marks',
              PhosphorIconsRegular.clipboardText,
              () {
                // TODO: Navigate to marks entry
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
              'Profile',
              PhosphorIconsRegular.user,
              () {
                // TODO: Navigate to profile
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