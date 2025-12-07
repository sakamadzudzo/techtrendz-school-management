import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:phosphor_flutter/phosphor_flutter.dart';
import 'package:go_router/go_router.dart';
import '../../../blocs/auth/auth_bloc.dart';

class ParentHomeScreen extends StatelessWidget {
  const ParentHomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Parent Dashboard'),
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
              'My Children',
              PhosphorIconsRegular.users,
              () => context.go('/students'),
            ),
            _buildMenuCard(
              context,
              'Academic Progress',
              PhosphorIconsRegular.chartLine,
              () => context.go('/reports'),
            ),
            _buildMenuCard(
              context,
              'Reports',
              PhosphorIconsRegular.chartBar,
              () => context.go('/reports'),
            ),
            _buildMenuCard(
              context,
              'Activities',
              PhosphorIconsRegular.calendar,
              () => ScaffoldMessenger.of(context).showSnackBar(
                const SnackBar(content: Text('Activities coming soon')),
              ),
            ),
            _buildMenuCard(
              context,
              'Messages',
              PhosphorIconsRegular.chat,
              () => ScaffoldMessenger.of(context).showSnackBar(
                const SnackBar(content: Text('Messages coming soon')),
              ),
            ),
            _buildMenuCard(
              context,
              'Profile',
              PhosphorIconsRegular.user,
              () => context.go('/profile'),
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