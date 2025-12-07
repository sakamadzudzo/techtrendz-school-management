import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:phosphor_flutter/phosphor_flutter.dart';
import '../../../core/constants/dimensions.dart';
import '../../../core/constants/enums.dart';
import '../../../core/utils/validators.dart';
import '../../../data/models/school.dart';
import '../../../presentation/blocs/school/school_bloc.dart';
import '../common/custom_text_field.dart';

class SchoolForm extends StatefulWidget {
  final School? school;

  const SchoolForm({
    super.key,
    this.school,
  });

  @override
  State<SchoolForm> createState() => _SchoolFormState();
}

class _SchoolFormState extends State<SchoolForm> {
  final _formKey = GlobalKey<FormState>();
  late final TextEditingController _nameController;
  late final TextEditingController _emailController;
  late final TextEditingController _telNumberController;
  late final TextEditingController _mobileNumberController;
  late final TextEditingController _mobileNumber2Controller;
  late final TextEditingController _addressController;
  late final TextEditingController _websiteController;

  bool get _isEditing => widget.school != null;

  @override
  void initState() {
    super.initState();
    _initializeControllers();
  }

  void _initializeControllers() {
    _nameController = TextEditingController(text: widget.school?.name ?? '');
    _emailController = TextEditingController(text: widget.school?.email ?? '');
    _telNumberController = TextEditingController(text: widget.school?.telNumber ?? '');
    _mobileNumberController = TextEditingController(text: widget.school?.mobileNumber ?? '');
    _mobileNumber2Controller = TextEditingController(text: widget.school?.mobileNumber2 ?? '');
    _addressController = TextEditingController(text: widget.school?.address ?? '');
    _websiteController = TextEditingController(text: widget.school?.website ?? '');
  }

  @override
  void dispose() {
    _nameController.dispose();
    _emailController.dispose();
    _telNumberController.dispose();
    _mobileNumberController.dispose();
    _mobileNumber2Controller.dispose();
    _addressController.dispose();
    _websiteController.dispose();
    super.dispose();
  }

  void _onSavePressed() {
    if (_formKey.currentState?.validate() ?? false) {
      final school = School(
        id: widget.school?.id ?? 0, // Will be set by backend
        name: _nameController.text.trim(),
        email: _emailController.text.trim(),
        telNumber: _telNumberController.text.trim(),
        mobileNumber: _mobileNumberController.text.trim(),
        mobileNumber2: _mobileNumber2Controller.text.trim(),
        address: _addressController.text.trim(),
        website: _websiteController.text.trim(),
        status: Status.ACTIVE,
        datestamp: DateTime.now(),
      );

      if (_isEditing) {
        context.read<SchoolBloc>().add(SchoolUpdateRequested(school));
      } else {
        context.read<SchoolBloc>().add(SchoolCreateRequested(school));
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Form(
      key: _formKey,
      child: SingleChildScrollView(
        padding: const EdgeInsets.all(AppDimensions.paddingL),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            Text(
              _isEditing ? 'Edit School' : 'Add School',
              style: Theme.of(context).textTheme.headlineSmall,
              textAlign: TextAlign.center,
            ),
            const SizedBox(height: AppDimensions.spacingXL),

            // Basic Information Section
            Text(
              'Basic Information',
              style: Theme.of(context).textTheme.titleMedium?.copyWith(
                fontWeight: FontWeight.bold,
              ),
            ),
            const SizedBox(height: AppDimensions.spacingM),

            CustomTextField(
              controller: _nameController,
              labelText: 'School Name',
              hintText: 'Enter school name',
              validator: (value) => Validators.validateIsPresent(value, 'School name'),
            ),
            const SizedBox(height: AppDimensions.spacingM),

            CustomTextField(
              controller: _emailController,
              labelText: 'Email',
              hintText: 'Enter school email address',
              keyboardType: TextInputType.emailAddress,
              validator: Validators.validateEmail,
            ),
            const SizedBox(height: AppDimensions.spacingM),

            CustomTextField(
              controller: _websiteController,
              labelText: 'Website',
              hintText: 'Enter school website (optional)',
              keyboardType: TextInputType.url,
            ),
            const SizedBox(height: AppDimensions.spacingXL),

            // Contact Information Section
            Text(
              'Contact Information',
              style: Theme.of(context).textTheme.titleMedium?.copyWith(
                fontWeight: FontWeight.bold,
              ),
            ),
            const SizedBox(height: AppDimensions.spacingM),

            CustomTextField(
              controller: _telNumberController,
              labelText: 'Telephone Number',
              hintText: 'Enter telephone number',
              keyboardType: TextInputType.phone,
            ),
            const SizedBox(height: AppDimensions.spacingM),

            CustomTextField(
              controller: _mobileNumberController,
              labelText: 'Mobile Number',
              hintText: 'Enter primary mobile number',
              keyboardType: TextInputType.phone,
            ),
            const SizedBox(height: AppDimensions.spacingM),

            CustomTextField(
              controller: _mobileNumber2Controller,
              labelText: 'Secondary Mobile Number',
              hintText: 'Enter secondary mobile number (optional)',
              keyboardType: TextInputType.phone,
            ),
            const SizedBox(height: AppDimensions.spacingM),

            CustomTextField(
              controller: _addressController,
              labelText: 'Address',
              hintText: 'Enter school address',
              maxLines: 3,
            ),
            const SizedBox(height: AppDimensions.spacingXL),

            // Save Button
            BlocBuilder<SchoolBloc, SchoolState>(
              builder: (context, state) {
                return ElevatedButton(
                  onPressed: state is SchoolLoading ? null : _onSavePressed,
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
                  child: state is SchoolLoading
                      ? const SizedBox(
                          height: AppDimensions.iconM,
                          width: AppDimensions.iconM,
                          child: CircularProgressIndicator(
                            strokeWidth: 2,
                            valueColor: AlwaysStoppedAnimation<Color>(Colors.white),
                          ),
                        )
                      : Text(
                          _isEditing ? 'Update School' : 'Save School',
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