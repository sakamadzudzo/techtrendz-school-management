import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:phosphor_flutter/phosphor_flutter.dart';
import 'package:go_router/go_router.dart';
import 'package:school_management_flutter/core/constants/enums.dart';
import 'package:school_management_flutter/data/models/user.dart';
import 'package:school_management_flutter/presentation/blocs/auth/auth_bloc.dart';
import 'package:school_management_flutter/presentation/widgets/common/custom_app_bar.dart';
import 'package:school_management_flutter/presentation/widgets/common/custom_button.dart';

class ProfileScreen extends StatefulWidget {
  const ProfileScreen({super.key});

  @override
  State<ProfileScreen> createState() => _ProfileScreenState();
}

class _ProfileScreenState extends State<ProfileScreen> {
  @override
  Widget build(BuildContext context) {
    return BlocBuilder<AuthBloc, AuthState>(
      builder: (context, state) {
        if (state is AuthAuthenticated) {
          final user = state.user;
          return Scaffold(
            appBar: const CustomAppBar(
              title: 'Profile',
              showBackButton: true,
            ),
            body: SingleChildScrollView(
              padding: const EdgeInsets.all(16.0),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  // Profile Header
                  _buildProfileHeader(user),
                  const SizedBox(height: 24),

                  // Personal Information
                  _buildSectionTitle('Personal Information'),
                  _buildInfoCard([
                    _buildInfoRow('Full Name', user.fullName),
                    if (user.firstName != null) _buildInfoRow('First Name', user.firstName!),
                    if (user.otherNames != null) _buildInfoRow('Other Names', user.otherNames!),
                    if (user.lastName != null) _buildInfoRow('Last Name', user.lastName!),
                    if (user.salutation != null) _buildInfoRow('Salutation', user.salutation!.name),
                    if (user.dateOfBirth != null) _buildInfoRow('Date of Birth', _formatDate(user.dateOfBirth!)),
                  ]),

                  const SizedBox(height: 24),

                  // Account Information
                  _buildSectionTitle('Account Information'),
                  _buildInfoCard([
                    _buildInfoRow('Username', user.username),
                    _buildInfoRow('Email', user.email),
                    _buildInfoRow('Status', user.status.name),
                    _buildInfoRow('Verified', user.verified == true ? 'Yes' : 'No'),
                    _buildInfoRow('Enabled', user.enabled == true ? 'Yes' : 'No'),
                  ]),

                  const SizedBox(height: 24),

                  // Roles
                  _buildSectionTitle('Roles'),
                  _buildInfoCard([
                    _buildInfoRow('Roles', user.roles.map((role) => role.name).join(', ')),
                    if (user.activeRole != null) _buildInfoRow('Active Role', user.activeRole!.name),
                  ]),

                  const SizedBox(height: 24),

                  // Contact Information
                  if (_hasContactInfo(user)) ...[
                    _buildSectionTitle('Contact Information'),
                    _buildInfoCard([
                      if (user.mobileNumber != null) _buildInfoRow('Mobile', user.mobileNumber!),
                      if (user.mobileNumber2 != null) _buildInfoRow('Mobile 2', user.mobileNumber2!),
                      if (user.telNumber != null) _buildInfoRow('Telephone', user.telNumber!),
                      if (user.whatsapp != null) _buildInfoRow('WhatsApp', user.whatsapp!),
                    ]),
                    const SizedBox(height: 24),
                  ],

                  // Social Media
                  if (_hasSocialMedia(user)) ...[
                    _buildSectionTitle('Social Media'),
                    _buildInfoCard([
                      if (user.facebook != null) _buildInfoRow('Facebook', user.facebook!),
                      if (user.linkedin != null) _buildInfoRow('LinkedIn', user.linkedin!),
                    ]),
                    const SizedBox(height: 24),
                  ],

                  // Action Buttons
                  _buildActionButtons(),
                ],
              ),
            ),
          );
        }

        return const Scaffold(
          body: Center(
            child: CircularProgressIndicator(),
          ),
        );
      },
    );
  }

  Widget _buildProfileHeader(User user) {
    return Container(
      padding: const EdgeInsets.all(20),
      decoration: BoxDecoration(
        color: Theme.of(context).primaryColor.withOpacity(0.1),
        borderRadius: BorderRadius.circular(12),
      ),
      child: Row(
        children: [
          CircleAvatar(
            radius: 40,
            backgroundColor: Theme.of(context).primaryColor,
            child: Text(
              user.firstName?.isNotEmpty == true ? user.firstName![0].toUpperCase() : '?',
              style: const TextStyle(
                fontSize: 32,
                fontWeight: FontWeight.bold,
                color: Colors.white,
              ),
            ),
          ),
          const SizedBox(width: 16),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  user.fullName,
                  style: Theme.of(context).textTheme.headlineSmall?.copyWith(
                        fontWeight: FontWeight.bold,
                      ),
                ),
                const SizedBox(height: 4),
                Text(
                  user.email,
                  style: Theme.of(context).textTheme.bodyMedium,
                ),
                const SizedBox(height: 4),
                Container(
                  padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 4),
                  decoration: BoxDecoration(
                    color: user.isActive ? Colors.green : Colors.red,
                    borderRadius: BorderRadius.circular(12),
                  ),
                  child: Text(
                    user.isActive ? 'Active' : 'Inactive',
                    style: const TextStyle(
                      color: Colors.white,
                      fontSize: 12,
                      fontWeight: FontWeight.w500,
                    ),
                  ),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildSectionTitle(String title) {
    return Text(
      title,
      style: Theme.of(context).textTheme.titleLarge?.copyWith(
            fontWeight: FontWeight.bold,
          ),
    );
  }

  Widget _buildInfoCard(List<Widget> children) {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          children: children,
        ),
      ),
    );
  }

  Widget _buildInfoRow(String label, String value) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 12),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          SizedBox(
            width: 120,
            child: Text(
              label,
              style: Theme.of(context).textTheme.bodyMedium?.copyWith(
                    fontWeight: FontWeight.w500,
                    color: Theme.of(context).textTheme.bodySmall?.color,
                  ),
            ),
          ),
          const SizedBox(width: 16),
          Expanded(
            child: Text(
              value,
              style: Theme.of(context).textTheme.bodyMedium,
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildActionButtons() {
    return Column(
      children: [
        CustomButton(
          text: 'Edit Profile',
          onPressed: () {
            // TODO: Navigate to edit profile screen
            ScaffoldMessenger.of(context).showSnackBar(
              const SnackBar(content: Text('Edit profile functionality coming soon')),
            );
          },
          leadingIcon: Icon(PhosphorIconsRegular.pencil),
        ),
        const SizedBox(height: 12),
        CustomButton(
          text: 'Change Password',
          onPressed: () => context.go('/change-password'),
          leadingIcon: Icon(PhosphorIconsRegular.lock),
          variant: ButtonVariant.secondary,
        ),
      ],
    );
  }

  String _formatDate(DateTime date) {
    return '${date.day}/${date.month}/${date.year}';
  }

  bool _hasContactInfo(User user) {
    return user.mobileNumber != null ||
           user.mobileNumber2 != null ||
           user.telNumber != null ||
           user.whatsapp != null;
  }

  bool _hasSocialMedia(User user) {
    return user.facebook != null || user.linkedin != null;
  }
}