import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:phosphor_flutter/phosphor_flutter.dart';
import '../../../core/constants/dimensions.dart';
import '../../../core/constants/strings.dart';
import '../../../core/constants/enums.dart';
import '../../../core/utils/validators.dart';
import '../../../presentation/blocs/auth/auth_bloc.dart';

class RegisterForm extends StatefulWidget {
  const RegisterForm({super.key});

  @override
  State<RegisterForm> createState() => _RegisterFormState();
}

class _RegisterFormState extends State<RegisterForm> {
  final _formKey = GlobalKey<FormState>();
  final _emailController = TextEditingController();
  final _usernameController = TextEditingController();
  final _passwordController = TextEditingController();
  final _confirmPasswordController = TextEditingController();
  final _firstNameController = TextEditingController();
  final _lastNameController = TextEditingController();
  final _phoneController = TextEditingController();
  final _addressController = TextEditingController();

  Role _selectedRole = Role.TEACHER;
  bool _obscurePassword = true;
  bool _obscureConfirmPassword = true;

  final List<Map<String, String>> _roles = [
    {'value': 'TEACHER', 'label': 'Teacher'},
    {'value': 'PARENT', 'label': 'Parent'},
    {'value': 'STAFF', 'label': 'Staff'},
  ];

  @override
  void dispose() {
    _emailController.dispose();
    _usernameController.dispose();
    _passwordController.dispose();
    _confirmPasswordController.dispose();
    _firstNameController.dispose();
    _lastNameController.dispose();
    _phoneController.dispose();
    _addressController.dispose();
    super.dispose();
  }

  void _togglePasswordVisibility() {
    setState(() {
      _obscurePassword = !_obscurePassword;
    });
  }

  void _toggleConfirmPasswordVisibility() {
    setState(() {
      _obscureConfirmPassword = !_obscureConfirmPassword;
    });
  }

  void _onRegisterPressed() {
    if (_formKey.currentState?.validate() ?? false) {
      context.read<AuthBloc>().add(
        AuthRegisterRequested(
          email: _emailController.text.trim(),
          username: _usernameController.text.trim(),
          password: _passwordController.text,
          firstName: _firstNameController.text.trim(),
          lastName: _lastNameController.text.trim(),
          role: _selectedRole.name,
          phone: _phoneController.text.trim().isNotEmpty ? _phoneController.text.trim() : null,
          address: _addressController.text.trim().isNotEmpty ? _addressController.text.trim() : null,
        ),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return BlocListener<AuthBloc, AuthState>(
      listener: (context, state) {
        if (state is AuthError) {
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(
              content: Text(state.message),
              backgroundColor: Theme.of(context).colorScheme.error,
            ),
          );
        }
      },
      child: Form(
        key: _formKey,
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            // Name Fields
            Row(
              children: [
                Expanded(
                  child: TextFormField(
                    controller: _firstNameController,
                    decoration: InputDecoration(
                      labelText: AppStrings.firstName,
                      hintText: AppStrings.enterName,
                      prefixIcon: Icon(
                        PhosphorIcons.user(),
                        size: AppDimensions.iconM,
                      ),
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(AppDimensions.radiusM),
                      ),
                      contentPadding: const EdgeInsets.symmetric(
                        horizontal: AppDimensions.paddingM,
                        vertical: AppDimensions.paddingM,
                      ),
                    ),
                    validator: (value) => Validators.validateName(value, AppStrings.firstName),
                  ),
                ),
                const SizedBox(width: AppDimensions.spacingM),
                Expanded(
                  child: TextFormField(
                    controller: _lastNameController,
                    decoration: InputDecoration(
                      labelText: AppStrings.lastName,
                      hintText: AppStrings.enterName,
                      prefixIcon: Icon(
                        PhosphorIcons.user(),
                        size: AppDimensions.iconM,
                      ),
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(AppDimensions.radiusM),
                      ),
                      contentPadding: const EdgeInsets.symmetric(
                        horizontal: AppDimensions.paddingM,
                        vertical: AppDimensions.paddingM,
                      ),
                    ),
                    validator: (value) => Validators.validateName(value, AppStrings.lastName),
                  ),
                ),
              ],
            ),
            const SizedBox(height: AppDimensions.spacingL),

            // Email Field
            TextFormField(
              controller: _emailController,
              keyboardType: TextInputType.emailAddress,
              decoration: InputDecoration(
                labelText: AppStrings.email,
                hintText: AppStrings.enterEmail,
                prefixIcon: Icon(
                  PhosphorIcons.envelope(),
                  size: AppDimensions.iconM,
                ),
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(AppDimensions.radiusM),
                ),
                contentPadding: const EdgeInsets.symmetric(
                  horizontal: AppDimensions.paddingM,
                  vertical: AppDimensions.paddingM,
                ),
              ),
              validator: Validators.validateEmail,
            ),
            const SizedBox(height: AppDimensions.spacingL),

            // Username Field
            TextFormField(
              controller: _usernameController,
              decoration: InputDecoration(
                labelText: 'Username',
                hintText: 'Enter your username',
                prefixIcon: Icon(
                  PhosphorIcons.user(),
                  size: AppDimensions.iconM,
                ),
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(AppDimensions.radiusM),
                ),
                contentPadding: const EdgeInsets.symmetric(
                  horizontal: AppDimensions.paddingM,
                  vertical: AppDimensions.paddingM,
                ),
              ),
              validator: (value) {
                if (value == null || value.trim().isEmpty) {
                  return 'Username is required';
                }
                if (value.length < 3) {
                  return 'Username must be at least 3 characters long';
                }
                return null;
              },
            ),
            const SizedBox(height: AppDimensions.spacingL),

            // Role Dropdown
            DropdownButtonFormField<Role>(
              value: _selectedRole,
              decoration: InputDecoration(
                labelText: AppStrings.selectRole,
                prefixIcon: Icon(
                  PhosphorIcons.userCircle(),
                  size: AppDimensions.iconM,
                ),
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(AppDimensions.radiusM),
                ),
                contentPadding: const EdgeInsets.symmetric(
                  horizontal: AppDimensions.paddingM,
                  vertical: AppDimensions.paddingM,
                ),
              ),
              items: Role.values.map((role) {
                return DropdownMenuItem<Role>(
                  value: role,
                  child: Text(role.displayName),
                );
              }).toList(),
              onChanged: (value) {
                setState(() {
                  _selectedRole = value!;
                });
              },
              validator: (value) {
                if (value == null) {
                  return 'Please select a role';
                }
                return null;
              },
            ),
            const SizedBox(height: AppDimensions.spacingL),

            // Phone Field
            TextFormField(
              controller: _phoneController,
              keyboardType: TextInputType.phone,
              decoration: InputDecoration(
                labelText: AppStrings.phone,
                hintText: 'Enter phone number',
                prefixIcon: Icon(
                  PhosphorIcons.phone(),
                  size: AppDimensions.iconM,
                ),
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(AppDimensions.radiusM),
                ),
                contentPadding: const EdgeInsets.symmetric(
                  horizontal: AppDimensions.paddingM,
                  vertical: AppDimensions.paddingM,
                ),
              ),
              validator: Validators.validatePhone,
            ),
            const SizedBox(height: AppDimensions.spacingL),

            // Address Field
            TextFormField(
              controller: _addressController,
              maxLines: 2,
              decoration: InputDecoration(
                labelText: AppStrings.address,
                hintText: AppStrings.enterDescription,
                prefixIcon: Icon(
                  PhosphorIcons.mapPin(),
                  size: AppDimensions.iconM,
                ),
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(AppDimensions.radiusM),
                ),
                contentPadding: const EdgeInsets.symmetric(
                  horizontal: AppDimensions.paddingM,
                  vertical: AppDimensions.paddingM,
                ),
              ),
            ),
            const SizedBox(height: AppDimensions.spacingL),

            // Password Fields
            TextFormField(
              controller: _passwordController,
              obscureText: _obscurePassword,
              decoration: InputDecoration(
                labelText: AppStrings.password,
                hintText: AppStrings.enterPassword,
                prefixIcon: Icon(
                  PhosphorIcons.lock(),
                  size: AppDimensions.iconM,
                ),
                suffixIcon: IconButton(
                  icon: Icon(
                    _obscurePassword
                        ? PhosphorIcons.eye()
                        : PhosphorIcons.eyeSlash(),
                    size: AppDimensions.iconM,
                  ),
                  onPressed: _togglePasswordVisibility,
                ),
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(AppDimensions.radiusM),
                ),
                contentPadding: const EdgeInsets.symmetric(
                  horizontal: AppDimensions.paddingM,
                  vertical: AppDimensions.paddingM,
                ),
              ),
              validator: Validators.validatePassword,
            ),
            const SizedBox(height: AppDimensions.spacingL),

            TextFormField(
              controller: _confirmPasswordController,
              obscureText: _obscureConfirmPassword,
              decoration: InputDecoration(
                labelText: AppStrings.confirmPassword,
                hintText: 'Re-enter your password',
                prefixIcon: Icon(
                  PhosphorIcons.lock(),
                  size: AppDimensions.iconM,
                ),
                suffixIcon: IconButton(
                  icon: Icon(
                    _obscureConfirmPassword
                        ? PhosphorIcons.eye()
                        : PhosphorIcons.eyeSlash(),
                    size: AppDimensions.iconM,
                  ),
                  onPressed: _toggleConfirmPasswordVisibility,
                ),
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(AppDimensions.radiusM),
                ),
                contentPadding: const EdgeInsets.symmetric(
                  horizontal: AppDimensions.paddingM,
                  vertical: AppDimensions.paddingM,
                ),
              ),
              validator: (value) => Validators.validateConfirmPassword(value, _passwordController.text),
            ),
            const SizedBox(height: AppDimensions.spacingXL),

            // Register Button
            BlocBuilder<AuthBloc, AuthState>(
              builder: (context, state) {
                return ElevatedButton(
                  onPressed: state is AuthLoading ? null : _onRegisterPressed,
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Theme.of(context).colorScheme.primary,
                    foregroundColor: Theme.of(context).colorScheme.onPrimary,
                    padding: const EdgeInsets.symmetric(
                      vertical: AppDimensions.paddingL,
                    ),
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(AppDimensions.radiusM),
                    ),
                  ),
                  child: state is AuthLoading
                      ? const SizedBox(
                          height: AppDimensions.iconM,
                          width: AppDimensions.iconM,
                          child: CircularProgressIndicator(
                            strokeWidth: 2,
                            valueColor: AlwaysStoppedAnimation<Color>(Colors.white),
                          ),
                        )
                      : Text(
                          AppStrings.register,
                          style: Theme.of(context).textTheme.labelLarge?.copyWith(
                            color: Theme.of(context).colorScheme.onPrimary,
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                );
              },
            ),
          ],
        ),
      ),
    );
  }
}