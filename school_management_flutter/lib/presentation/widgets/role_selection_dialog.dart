import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:phosphor_flutter/phosphor_flutter.dart';
import '../../core/constants/enums.dart';
import '../../core/constants/dimensions.dart';

class RoleSelectionDialog extends StatelessWidget {
  final List<Role> roles;
  final Function(Role) onRoleSelected;

  const RoleSelectionDialog({
    super.key,
    required this.roles,
    required this.onRoleSelected,
  });

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: const Text('Select Active Role'),
      content: Column(
        mainAxisSize: MainAxisSize.min,
        children: roles.map((role) => _buildRoleOption(context, role)).toList(),
      ),
      actions: [
        TextButton(
          onPressed: () => Navigator.of(context).pop(),
          child: const Text('Cancel'),
        ),
      ],
    );
  }

  Widget _buildRoleOption(BuildContext context, Role role) {
    return ListTile(
      leading: Icon(_getRoleIcon(role), color: Theme.of(context).primaryColor),
      title: Text(role.displayName),
      onTap: () {
        onRoleSelected(role);
        Navigator.of(context).pop();
        // Navigate to home screen after role selection
        _navigateToHomeScreen(context, role);
      },
    );
  }

  void _navigateToHomeScreen(BuildContext context, Role role) {
    switch (role) {
      case Role.ADMIN:
        context.go('/admin-home');
        break;
      case Role.TEACHER:
        context.go('/teacher-home');
        break;
      case Role.PARENT:
        context.go('/parent-home');
        break;
      case Role.STUDENT:
        context.go('/student-home');
        break;
      case Role.STAFF:
        context.go('/staff-home');
        break;
      case Role.GUEST:
        // Guests shouldn't have access
        break;
    }
  }

  IconData _getRoleIcon(Role role) {
    switch (role) {
      case Role.ADMIN:
        return PhosphorIconsRegular.shield;
      case Role.TEACHER:
        return PhosphorIconsRegular.chalkboardTeacher;
      case Role.STUDENT:
        return PhosphorIconsRegular.graduationCap;
      case Role.PARENT:
        return PhosphorIconsRegular.users;
      case Role.STAFF:
        return PhosphorIconsRegular.briefcase;
      case Role.GUEST:
        return PhosphorIconsRegular.user;
    }
  }
}