import 'package:flutter/material.dart';
import 'package:flutter_form_builder/flutter_form_builder.dart';
import 'package:form_builder_validators/form_builder_validators.dart';
import 'package:phosphor_flutter/phosphor_flutter.dart';
import 'package:provider/provider.dart';
import 'package:school_management_flutter/presentation/widgets/common/custom_app_bar.dart';
import 'package:school_management_flutter/presentation/widgets/common/custom_button.dart';
import 'package:school_management_flutter/presentation/providers/auth_provider.dart';

class ChangePasswordScreen extends StatefulWidget {
  const ChangePasswordScreen({super.key});

  @override
  State<ChangePasswordScreen> createState() => _ChangePasswordScreenState();
}

class _ChangePasswordScreenState extends State<ChangePasswordScreen> {
  final _formKey = GlobalKey<FormBuilderState>();
  bool _isLoading = false;
  bool _obscureCurrentPassword = true;
  bool _obscureNewPassword = true;
  bool _obscureConfirmPassword = true;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: const CustomAppBar(
        title: 'Change Password',
        showBackButton: true,
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16.0),
        child: FormBuilder(
          key: _formKey,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(
                'Change Your Password',
                style: Theme.of(context).textTheme.headlineMedium?.copyWith(
                      fontWeight: FontWeight.bold,
                    ),
              ),
              const SizedBox(height: 8),
              Text(
                'Enter your current password and choose a new secure password.',
                style: Theme.of(context).textTheme.bodyMedium,
              ),
              const SizedBox(height: 32),

              // Current Password
              FormBuilderTextField(
                name: 'currentPassword',
                decoration: InputDecoration(
                  labelText: 'Current Password',
                  suffixIcon: IconButton(
                    icon: Icon(
                      _obscureCurrentPassword
                          ? PhosphorIconsRegular.eye
                          : PhosphorIconsRegular.eyeSlash,
                    ),
                    onPressed: () {
                      setState(() {
                        _obscureCurrentPassword = !_obscureCurrentPassword;
                      });
                    },
                  ),
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(8),
                  ),
                ),
                obscureText: _obscureCurrentPassword,
                validator: FormBuilderValidators.compose([
                  FormBuilderValidators.required(
                    errorText: 'Current password is required',
                  ),
                  FormBuilderValidators.minLength(
                    6,
                    errorText: 'Password must be at least 6 characters',
                  ),
                ]),
              ),
              const SizedBox(height: 16),

              // New Password
              FormBuilderTextField(
                name: 'newPassword',
                decoration: InputDecoration(
                  labelText: 'New Password',
                  suffixIcon: IconButton(
                    icon: Icon(
                      _obscureNewPassword
                          ? PhosphorIconsRegular.eye
                          : PhosphorIconsRegular.eyeSlash,
                    ),
                    onPressed: () {
                      setState(() {
                        _obscureNewPassword = !_obscureNewPassword;
                      });
                    },
                  ),
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(8),
                  ),
                ),
                obscureText: _obscureNewPassword,
                validator: FormBuilderValidators.compose([
                  FormBuilderValidators.required(
                    errorText: 'New password is required',
                  ),
                  FormBuilderValidators.minLength(
                    8,
                    errorText: 'Password must be at least 8 characters',
                  ),
                  FormBuilderValidators.match(
                    RegExp(r'^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)'),
                    errorText: 'Password must contain at least one uppercase letter, one lowercase letter, and one number',
                  ),
                ]),
              ),
              const SizedBox(height: 16),

              // Confirm New Password
              FormBuilderTextField(
                name: 'confirmPassword',
                decoration: InputDecoration(
                  labelText: 'Confirm New Password',
                  suffixIcon: IconButton(
                    icon: Icon(
                      _obscureConfirmPassword
                          ? PhosphorIconsRegular.eye
                          : PhosphorIconsRegular.eyeSlash,
                    ),
                    onPressed: () {
                      setState(() {
                        _obscureConfirmPassword = !_obscureConfirmPassword;
                      });
                    },
                  ),
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(8),
                  ),
                ),
                obscureText: _obscureConfirmPassword,
                validator: FormBuilderValidators.compose([
                  FormBuilderValidators.required(
                    errorText: 'Please confirm your new password',
                  ),
                  (value) {
                    final newPassword = _formKey.currentState?.fields['newPassword']?.value;
                    if (value != newPassword) {
                      return 'Passwords do not match';
                    }
                    return null;
                  },
                ]),
              ),
              const SizedBox(height: 32),

              // Password Requirements
              Container(
                padding: const EdgeInsets.all(16),
                decoration: BoxDecoration(
                  color: Theme.of(context).primaryColor.withOpacity(0.1),
                  borderRadius: BorderRadius.circular(8),
                ),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      'Password Requirements:',
                      style: Theme.of(context).textTheme.titleSmall?.copyWith(
                            fontWeight: FontWeight.bold,
                          ),
                    ),
                    const SizedBox(height: 8),
                    _buildRequirement('At least 8 characters long'),
                    _buildRequirement('At least one uppercase letter'),
                    _buildRequirement('At least one lowercase letter'),
                    _buildRequirement('At least one number'),
                  ],
                ),
              ),
              const SizedBox(height: 32),

              // Action Buttons
              CustomButton(
                text: 'Change Password',
                onPressed: _isLoading ? null : _changePassword,
                isLoading: _isLoading,
                leadingIcon: Icon(PhosphorIconsRegular.lock),
              ),
              const SizedBox(height: 16),
              CustomButton(
                text: 'Cancel',
                onPressed: () {
                  Navigator.of(context).pop();
                },
                variant: ButtonVariant.secondary,
              ),
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildRequirement(String text) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 4),
      child: Row(
        children: [
          Icon(
            PhosphorIconsRegular.check,
            size: 16,
            color: Theme.of(context).primaryColor,
          ),
          const SizedBox(width: 8),
          Text(
            text,
            style: Theme.of(context).textTheme.bodySmall,
          ),
        ],
      ),
    );
  }

  Future<void> _changePassword() async {
    if (!_formKey.currentState!.saveAndValidate()) {
      return;
    }

    setState(() {
      _isLoading = true;
    });

    try {
      final formData = _formKey.currentState!.value;
      final currentPassword = formData['currentPassword'] as String;
      final newPassword = formData['newPassword'] as String;

      final authRepository = context.read<AuthProvider>().authRepository;
      final result = await authRepository.changePassword(
        currentPassword: currentPassword,
        newPassword: newPassword,
      );

      if (mounted) {
        result.fold(
          (failure) {
            ScaffoldMessenger.of(context).showSnackBar(
              SnackBar(
                content: Text(failure.message),
                backgroundColor: Colors.red,
              ),
            );
          },
          (user) {
            ScaffoldMessenger.of(context).showSnackBar(
              const SnackBar(
                content: Text('Password changed successfully!'),
                backgroundColor: Colors.green,
              ),
            );
            Navigator.of(context).pop();
          },
        );
      }
    } catch (e) {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('Failed to change password: $e'),
            backgroundColor: Colors.red,
          ),
        );
      }
    } finally {
      if (mounted) {
        setState(() {
          _isLoading = false;
        });
      }
    }
  }
}