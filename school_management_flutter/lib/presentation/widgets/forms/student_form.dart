import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:phosphor_flutter/phosphor_flutter.dart';
import '../../../core/constants/dimensions.dart';
import '../../../core/constants/enums.dart';
import '../../../core/utils/validators.dart';
import '../../../data/models/student.dart';
import '../../../data/models/person.dart';
import '../../../data/models/school.dart';
import '../../../data/models/classroom_year.dart';
import '../../../data/models/dorm_year.dart';
import '../../../presentation/blocs/student/student_bloc.dart';
import '../common/custom_text_field.dart';

class StudentForm extends StatefulWidget {
  final Student? student;
  final List<School> schools;
  final List<ClassroomYear> classroomYears;
  final List<DormYear> dormYears;

  const StudentForm({
    super.key,
    this.student,
    required this.schools,
    required this.classroomYears,
    required this.dormYears,
  });

  @override
  State<StudentForm> createState() => _StudentFormState();
}

class _StudentFormState extends State<StudentForm> {
  final _formKey = GlobalKey<FormState>();
  late final TextEditingController _firstNameController;
  late final TextEditingController _lastNameController;
  late final TextEditingController _emailController;
  late final TextEditingController _mobileNumberController;
  late final TextEditingController _identificationController;
  late final TextEditingController _startYearController;
  late final TextEditingController _endYearController;

  Salutation? _selectedSalutation;
  School? _selectedSchool;
  ClassroomYear? _selectedClassroomYear;
  DormYear? _selectedDormYear;
  DateTime? _selectedDateOfBirth;

  bool get _isEditing => widget.student != null;

  @override
  void initState() {
    super.initState();
    _initializeControllers();
  }

  void _initializeControllers() {
    final person = widget.student?.person;
    _firstNameController = TextEditingController(text: person?.firstName ?? '');
    _lastNameController = TextEditingController(text: person?.lastName ?? '');
    _emailController = TextEditingController(text: person?.email ?? '');
    _mobileNumberController = TextEditingController(text: person?.mobileNumber ?? '');
    _identificationController = TextEditingController(text: person?.identification ?? '');
    _startYearController = TextEditingController(text: widget.student?.startYear?.toString() ?? '');
    _endYearController = TextEditingController(text: widget.student?.endYear?.toString() ?? '');

    _selectedSalutation = person?.salutation;
    _selectedSchool = widget.student?.school;
    _selectedClassroomYear = widget.student?.classroom;
    _selectedDormYear = widget.student?.dorm;
    _selectedDateOfBirth = person?.dateOfBirth;
  }

  @override
  void dispose() {
    _firstNameController.dispose();
    _lastNameController.dispose();
    _emailController.dispose();
    _mobileNumberController.dispose();
    _identificationController.dispose();
    _startYearController.dispose();
    _endYearController.dispose();
    super.dispose();
  }

  void _onSavePressed() {
    if (_formKey.currentState?.validate() ?? false) {
      final person = Person(
        id: widget.student?.person?.id ?? 0, // Will be set by backend
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

      final student = Student(
        id: widget.student?.id ?? 0, // Will be set by backend
        person: person,
        startYear: int.tryParse(_startYearController.text.trim()),
        endYear: int.tryParse(_endYearController.text.trim()),
        school: _selectedSchool,
        classroom: _selectedClassroomYear,
        dorm: _selectedDormYear,
        status: Status.ACTIVE,
        datestamp: DateTime.now(),
      );

      if (_isEditing) {
        context.read<StudentBloc>().add(StudentUpdateRequested(student));
      } else {
        context.read<StudentBloc>().add(StudentCreateRequested(student));
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
              _isEditing ? 'Edit Student' : 'Add Student',
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

            // Academic Information Section
            Text(
              'Academic Information',
              style: Theme.of(context).textTheme.titleMedium?.copyWith(
                fontWeight: FontWeight.bold,
              ),
            ),
            const SizedBox(height: AppDimensions.spacingM),

            CustomTextField(
              controller: _startYearController,
              labelText: 'Start Year',
              hintText: 'Enter start year',
            ),
            const SizedBox(height: AppDimensions.spacingM),

            CustomTextField(
              controller: _endYearController,
              labelText: 'End Year',
              hintText: 'Enter end year',
            ),
            const SizedBox(height: AppDimensions.spacingM),

            // School Dropdown
            DropdownButtonFormField<School>(
              value: _selectedSchool,
              decoration: const InputDecoration(
                labelText: 'School',
                border: OutlineInputBorder(),
              ),
              items: widget.schools.map((school) {
                return DropdownMenuItem(
                  value: school,
                  child: Text(school.name ?? 'Unknown School'),
                );
              }).toList(),
              onChanged: (value) {
                setState(() {
                  _selectedSchool = value;
                });
              },
            ),
            const SizedBox(height: AppDimensions.spacingM),

            // Classroom Year Dropdown
            DropdownButtonFormField<ClassroomYear>(
              value: _selectedClassroomYear,
              decoration: const InputDecoration(
                labelText: 'Classroom',
                border: OutlineInputBorder(),
              ),
              items: widget.classroomYears.map((classroomYear) {
                return DropdownMenuItem(
                  value: classroomYear,
                  child: Text(classroomYear.name),
                );
              }).toList(),
              onChanged: (value) {
                setState(() {
                  _selectedClassroomYear = value;
                });
              },
            ),
            const SizedBox(height: AppDimensions.spacingM),

            // Dorm Year Dropdown
            DropdownButtonFormField<DormYear>(
              value: _selectedDormYear,
              decoration: const InputDecoration(
                labelText: 'Dormitory',
                border: OutlineInputBorder(),
              ),
              items: widget.dormYears.map((dormYear) {
                return DropdownMenuItem(
                  value: dormYear,
                  child: Text(dormYear.name),
                );
              }).toList(),
              onChanged: (value) {
                setState(() {
                  _selectedDormYear = value;
                });
              },
            ),
            const SizedBox(height: AppDimensions.spacingXL),

            // Save Button
            BlocBuilder<StudentBloc, StudentState>(
              builder: (context, state) {
                return ElevatedButton(
                  onPressed: state is StudentLoading ? null : _onSavePressed,
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
                  child: state is StudentLoading
                      ? const SizedBox(
                          height: AppDimensions.iconM,
                          width: AppDimensions.iconM,
                          child: CircularProgressIndicator(
                            strokeWidth: 2,
                            valueColor: AlwaysStoppedAnimation<Color>(Colors.white),
                          ),
                        )
                      : Text(
                          _isEditing ? 'Update Student' : 'Save Student',
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