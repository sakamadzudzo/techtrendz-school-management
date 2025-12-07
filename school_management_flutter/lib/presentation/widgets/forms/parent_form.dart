import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:phosphor_flutter/phosphor_flutter.dart';
import '../../../core/constants/dimensions.dart';
import '../../../core/constants/enums.dart';
import '../../../core/utils/validators.dart';
import '../../../data/models/parent.dart';
import '../../../data/models/person.dart';
import '../../../data/models/student.dart';
import '../../../presentation/blocs/parent/parent_bloc.dart';
import '../../../presentation/blocs/student/student_bloc.dart';
import '../common/custom_text_field.dart';

class ParentForm extends StatefulWidget {
  final Parent? parent;
  final List<Student> students;

  const ParentForm({
    super.key,
    this.parent,
    required this.students,
  });

  @override
  State<ParentForm> createState() => _ParentFormState();
}

class _ParentFormState extends State<ParentForm> {
  final _formKey = GlobalKey<FormState>();
  late final TextEditingController _firstNameController;
  late final TextEditingController _lastNameController;
  late final TextEditingController _emailController;
  late final TextEditingController _mobileNumberController;
  late final TextEditingController _identificationController;

  Salutation? _selectedSalutation;
  DateTime? _selectedDateOfBirth;
  List<Student> _selectedStudents = [];

  bool get _isEditing => widget.parent != null;

  @override
  void initState() {
    super.initState();
    _initializeControllers();
  }

  void _initializeControllers() {
    final person = widget.parent?.person;
    _firstNameController = TextEditingController(text: person?.firstName ?? '');
    _lastNameController = TextEditingController(text: person?.lastName ?? '');
    _emailController = TextEditingController(text: person?.email ?? '');
    _mobileNumberController = TextEditingController(text: person?.mobileNumber ?? '');
    _identificationController = TextEditingController(text: person?.identification ?? '');

    _selectedSalutation = person?.salutation;
    _selectedDateOfBirth = person?.dateOfBirth;
    _selectedStudents = widget.parent?.students ?? [];
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
        id: widget.parent?.person?.id ?? 0, // Will be set by backend
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

      final parent = Parent(
        id: widget.parent?.id ?? 0, // Will be set by backend
        person: person,
        students: _selectedStudents,
        status: Status.ACTIVE,
        datestamp: DateTime.now(),
      );

      if (_isEditing) {
        context.read<ParentBloc>().add(ParentUpdateRequested(parent));
      } else {
        context.read<ParentBloc>().add(ParentCreateRequested(parent));
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
              _isEditing ? 'Edit Parent' : 'Add Parent',
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

            // Associated Students Section
            Text(
              'Associated Students',
              style: Theme.of(context).textTheme.titleMedium?.copyWith(
                fontWeight: FontWeight.bold,
              ),
            ),
            const SizedBox(height: AppDimensions.spacingM),

            // Student Selection
            Container(
              decoration: BoxDecoration(
                border: Border.all(color: Theme.of(context).colorScheme.outline),
                borderRadius: BorderRadius.circular(AppDimensions.radiusM),
              ),
              child: ExpansionTile(
                title: Text(
                  _selectedStudents.isEmpty
                      ? 'Select students'
                      : '${_selectedStudents.length} student${_selectedStudents.length == 1 ? '' : 's'} selected',
                ),
                children: widget.students.map((student) {
                  final isSelected = _selectedStudents.contains(student);
                  return CheckboxListTile(
                    title: Text(student.fullname),
                    subtitle: Text(student.person?.email ?? ''),
                    value: isSelected,
                    onChanged: (selected) {
                      setState(() {
                        if (selected == true) {
                          _selectedStudents.add(student);
                        } else {
                          _selectedStudents.remove(student);
                        }
                      });
                    },
                  );
                }).toList(),
              ),
            ),
            const SizedBox(height: AppDimensions.spacingXL),

            // Save Button
            BlocBuilder<ParentBloc, ParentState>(
              builder: (context, state) {
                return ElevatedButton(
                  onPressed: state is ParentLoading ? null : _onSavePressed,
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
                  child: state is ParentLoading
                      ? const SizedBox(
                          height: AppDimensions.iconM,
                          width: AppDimensions.iconM,
                          child: CircularProgressIndicator(
                            strokeWidth: 2,
                            valueColor: AlwaysStoppedAnimation<Color>(Colors.white),
                          ),
                        )
                      : Text(
                          _isEditing ? 'Update Parent' : 'Save Parent',
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