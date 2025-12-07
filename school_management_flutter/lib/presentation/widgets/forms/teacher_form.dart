import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:phosphor_flutter/phosphor_flutter.dart';
import '../../../core/constants/dimensions.dart';
import '../../../core/constants/enums.dart';
import '../../../core/utils/validators.dart';
import '../../../data/models/teacher.dart';
import '../../../data/models/person.dart';
import '../../../presentation/blocs/teacher/teacher_bloc.dart';
import '../common/custom_text_field.dart';

class TeacherForm extends StatefulWidget {
  final Teacher? teacher;

  const TeacherForm({
    super.key,
    this.teacher,
  });

  @override
  State<TeacherForm> createState() => _TeacherFormState();
}

class _TeacherFormState extends State<TeacherForm> {
  final _formKey = GlobalKey<FormState>();
  late final TextEditingController _firstNameController;
  late final TextEditingController _lastNameController;
  late final TextEditingController _emailController;
  late final TextEditingController _mobileNumberController;
  late final TextEditingController _identificationController;

  Salutation? _selectedSalutation;
  DateTime? _selectedDateOfBirth;

  bool get _isEditing => widget.teacher != null;

  @override
  void initState() {
    super.initState();
    _initializeControllers();
  }

  void _initializeControllers() {
    final person = widget.teacher?.person;
    _firstNameController = TextEditingController(text: person?.firstName ?? '');
    _lastNameController = TextEditingController(text: person?.lastName ?? '');
    _emailController = TextEditingController(text: person?.email ?? '');
    _mobileNumberController = TextEditingController(text: person?.mobileNumber ?? '');
    _identificationController = TextEditingController(text: person?.identification ?? '');

    _selectedSalutation = person?.salutation;
    _selectedDateOfBirth = person?.dateOfBirth;
  }

  @override
  void dispose() {
    _firstNameController.dispose();
    _lastNameController.dispose();
    _emailController.dispose();
    _mobileNumberController.dispose();
    _identificationController.dispose();
    super.dispose();
  }

  void _onSavePressed() {
    if (_formKey.currentState?.validate() ?? false) {
      final person = Person(
        id: widget.teacher?.person?.id ?? 0, // Will be set by backend
        salutation: _selectedSalutation,
        firstName: _firstNameController.text.trim(),
        lastName: _lastNameController.text.trim(),
        email: _emailController.text.trim(),
        mobileNumber: _mobileNumberController.text.trim(),
        dateOfBirth: _selectedDateOfBirth,
        identification: _identificationController.text.trim(),
        status: Status.ACTIVE,
        datestamp: DateTime.now(),
      );

      final teacher = Teacher(
        id: widget.teacher?.id ?? 0, // Will be set by backend
        person: person,
        status: Status.ACTIVE,
        datestamp: DateTime.now(),
      );

      if (_isEditing) {
        context.read<TeacherBloc>().add(TeacherUpdateRequested(teacher));
      } else {
        context.read<TeacherBloc>().add(TeacherCreateRequested(teacher));
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
              _isEditing ? 'Edit Teacher' : 'Add Teacher',
              style: Theme.of(context).textTheme.headlineSmall,
              textAlign: TextAlign.center,
            ),
            const SizedBox(height: AppDimensions.spacingXL),

            // Personal Information Section
            Text(
              'Personal Information',
              style: Theme.of(context).textTheme.titleMedium?.copyWith(
                fontWeight: FontWeight.bold,
              ),
            ),
            const SizedBox(height: AppDimensions.spacingM),

            // Salutation Dropdown
            DropdownButtonFormField<Salutation>(
              value: _selectedSalutation,
              decoration: const InputDecoration(
                labelText: 'Salutation',
                border: OutlineInputBorder(),
              ),
              items: Salutation.values.map((salutation) {
                return DropdownMenuItem(
                  value: salutation,
                  child: Text(salutation.displayName),
                );
              }).toList(),
              onChanged: (value) {
                setState(() {
                  _selectedSalutation = value;
                });
              },
            ),
            const SizedBox(height: AppDimensions.spacingM),

            CustomTextField(
              controller: _firstNameController,
              labelText: 'First Name',
              hintText: 'Enter first name',
              validator: (value) => Validators.validateIsPresent(value, 'First name'),
            ),
            const SizedBox(height: AppDimensions.spacingM),

            CustomTextField(
              controller: _lastNameController,
              labelText: 'Last Name',
              hintText: 'Enter last name',
              validator: (value) => Validators.validateIsPresent(value, 'Last name'),
            ),
            const SizedBox(height: AppDimensions.spacingM),

            CustomTextField(
              controller: _emailController,
              labelText: 'Email',
              hintText: 'Enter email address',
              keyboardType: TextInputType.emailAddress,
              validator: Validators.validateEmail,
            ),
            const SizedBox(height: AppDimensions.spacingM),

            CustomTextField(
              controller: _mobileNumberController,
              labelText: 'Mobile Number',
              hintText: 'Enter mobile number',
              keyboardType: TextInputType.phone,
            ),
            const SizedBox(height: AppDimensions.spacingM),

            CustomTextField(
              controller: _identificationController,
              labelText: 'Identification',
              hintText: 'Enter identification number',
              validator: (value) => Validators.validateIsPresent(value, 'Identification'),
            ),
            const SizedBox(height: AppDimensions.spacingM),

            // Date of Birth Picker
            InkWell(
              onTap: () async {
                final date = await showDatePicker(
                  context: context,
                  initialDate: _selectedDateOfBirth ?? DateTime.now(),
                  firstDate: DateTime(1950),
                  lastDate: DateTime.now(),
                );
                if (date != null) {
                  setState(() {
                    _selectedDateOfBirth = date;
                  });
                }
              },
              child: InputDecorator(
                decoration: InputDecoration(
                  labelText: 'Date of Birth',
                  border: const OutlineInputBorder(),
                  suffixIcon: Icon(PhosphorIcons.calendar()),
                ),
                child: Text(
                  _selectedDateOfBirth != null
                      ? '${_selectedDateOfBirth!.day}/${_selectedDateOfBirth!.month}/${_selectedDateOfBirth!.year}'
                      : 'Select date of birth',
                ),
              ),
            ),
            const SizedBox(height: AppDimensions.spacingXL),

            // Save Button
            BlocBuilder<TeacherBloc, TeacherState>(
              builder: (context, state) {
                return ElevatedButton(
                  onPressed: state is TeacherLoading ? null : _onSavePressed,
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
                  child: state is TeacherLoading
                      ? const SizedBox(
                          height: AppDimensions.iconM,
                          width: AppDimensions.iconM,
                          child: CircularProgressIndicator(
                            strokeWidth: 2,
                            valueColor: AlwaysStoppedAnimation<Color>(Colors.white),
                          ),
                        )
                      : Text(
                          _isEditing ? 'Update Teacher' : 'Save Teacher',
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