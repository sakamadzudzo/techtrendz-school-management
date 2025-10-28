import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:phosphor_flutter/phosphor_flutter.dart';
import '../../../core/constants/dimensions.dart';
import '../../../core/constants/strings.dart';
import '../../../core/utils/validators.dart';
import '../../../presentation/blocs/auth/auth_bloc.dart';
import '../common/custom_text_field.dart';

class LoginForm extends StatefulWidget {
  const LoginForm({super.key});

  @override
  State<LoginForm> createState() => _LoginFormState();
}

class _LoginFormState extends State<LoginForm> {
  final _formKey = GlobalKey<FormState>();
  final _usernameController = TextEditingController();
  final _passwordController = TextEditingController();
  bool _obscurePassword = true;

  @override
  void dispose() {
    _usernameController.dispose();
    _passwordController.dispose();
    super.dispose();
  }

  void _togglePasswordVisibility() {
    setState(() {
      _obscurePassword = !_obscurePassword;
    });
  }

  void _onLoginPressed() {
    if (_formKey.currentState?.validate() ?? false) {
      context.read<AuthBloc>().add(
        AuthLoginRequested(
          username: _usernameController.text.trim(),
          password: _passwordController.text,
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
            CustomTextField(
              controller: _usernameController,
              labelText: 'Email or Username',
              hintText: 'Enter your email or username',
              keyboardType: TextInputType.emailAddress,
              prefixIcon: Icon(
                PhosphorIcons.envelope(),
                size: AppDimensions.iconM,
              ),
              validator: (value) {
                if (value == null || value.trim().isEmpty) {
                  return 'Email or username is required';
                }
                return null;
              },
            ),
            const SizedBox(height: AppDimensions.spacingL),
            CustomTextField(
              controller: _passwordController,
              labelText: AppStrings.password,
              hintText: AppStrings.enterPassword,
              obscureText: _obscurePassword,
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
              validator: (value) => Validators.validateIsPresent(value, 'Password'),
            ),
            const SizedBox(height: AppDimensions.spacingXL),
            BlocBuilder<AuthBloc, AuthState>(
              builder: (context, state) {
                return ElevatedButton(
                  onPressed: state is AuthLoading ? null : _onLoginPressed,
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
                          AppStrings.login,
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