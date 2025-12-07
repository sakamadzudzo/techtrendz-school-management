import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:go_router/go_router.dart';
import 'package:phosphor_flutter/phosphor_flutter.dart';
import '../../../../../core/constants/dimensions.dart';
import '../../../../../core/constants/enums.dart';
import '../../../../../presentation/blocs/classroom/classroom_bloc.dart';
import '../../../../../presentation/widgets/common/custom_app_bar.dart';
import '../../../../../data/models/classroom.dart';

class ClassroomFormScreen extends StatefulWidget {
  final int? classroomId;

  const ClassroomFormScreen({super.key, this.classroomId});

  @override
  State<ClassroomFormScreen> createState() => _ClassroomFormScreenState();
}

class _ClassroomFormScreenState extends State<ClassroomFormScreen> {
  final _formKey = GlobalKey<FormState>();
  final _nameController = TextEditingController();
  final _descriptionController = TextEditingController();
  Status _selectedStatus = Status.ACTIVE;

  bool get _isEditing => widget.classroomId != null;

  @override
  void initState() {
    super.initState();
    if (_isEditing) {
      context.read<ClassroomBloc>().add(ClassroomLoadByIdRequested(widget.classroomId!));
    }
  }

  @override
  void dispose() {
    _nameController.dispose();
    _descriptionController.dispose();
    super.dispose();
  }

  void _onSavePressed() {
    if (_formKey.currentState!.validate()) {
      final classroom = Classroom(
        id: _isEditing ? widget.classroomId! : 0,
        name: _nameController.text.trim().isEmpty ? null : _nameController.text.trim(),
        description: _descriptionController.text.trim().isEmpty ? null : _descriptionController.text.trim(),
        status: _selectedStatus,
        datestamp: DateTime.now(),
      );

      if (_isEditing) {
        context.read<ClassroomBloc>().add(ClassroomUpdateRequested(classroom));
      } else {
        context.read<ClassroomBloc>().add(ClassroomCreateRequested(classroom));
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: CustomAppBar(
        title: _isEditing ? 'Edit Classroom' : 'Add Classroom',
        actions: [
          IconButton(
            icon: Icon(PhosphorIcons.check()),
            onPressed: _onSavePressed,
            tooltip: 'Save Classroom',
          ),
        ],
      ),
      body: BlocListener<ClassroomBloc, ClassroomState>(
        listener: (context, state) {
          if (state is ClassroomCreated || state is ClassroomUpdated) {
            context.go('/classrooms');
          } else if (state is ClassroomError) {
            ScaffoldMessenger.of(context).showSnackBar(
              SnackBar(content: Text(state.message)),
            );
          }
        },
        child: BlocBuilder<ClassroomBloc, ClassroomState>(
          builder: (context, state) {
            if (_isEditing && state is ClassroomDetailLoaded) {
              final classroom = state.classroom;
              _nameController.text = classroom.name ?? '';
              _descriptionController.text = classroom.description ?? '';
              _selectedStatus = classroom.status;
            }

            return SingleChildScrollView(
              padding: const EdgeInsets.all(AppDimensions.paddingM),
              child: Form(
                key: _formKey,
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    // Name Field
                    TextFormField(
                      controller: _nameController,
                      decoration: const InputDecoration(
                        labelText: 'Classroom Name',
                        hintText: 'Enter classroom name',
                        border: OutlineInputBorder(),
                      ),
                      validator: (value) {
                        if (value == null || value.trim().isEmpty) {
                          return 'Please enter a classroom name';
                        }
                        return null;
                      },
                    ),
                    const SizedBox(height: AppDimensions.spacingM),

                    // Description Field
                    TextFormField(
                      controller: _descriptionController,
                      decoration: const InputDecoration(
                        labelText: 'Description (Optional)',
                        hintText: 'Enter classroom description',
                        border: OutlineInputBorder(),
                      ),
                      maxLines: 3,
                    ),
                    const SizedBox(height: AppDimensions.spacingM),

                    // Status Dropdown
                    DropdownButtonFormField<Status>(
                      value: _selectedStatus,
                      decoration: const InputDecoration(
                        labelText: 'Status',
                        border: OutlineInputBorder(),
                      ),
                      items: Status.values.map((status) {
                        return DropdownMenuItem(
                          value: status,
                          child: Text(status.name.toUpperCase()),
                        );
                      }).toList(),
                      onChanged: (value) {
                        if (value != null) {
                          setState(() {
                            _selectedStatus = value;
                          });
                        }
                      },
                    ),
                    const SizedBox(height: AppDimensions.spacingXL),

                    // Save Button
                    SizedBox(
                      width: double.infinity,
                      child: ElevatedButton.icon(
                        onPressed: state is ClassroomLoading ? null : _onSavePressed,
                        icon: state is ClassroomLoading
                            ? const SizedBox(
                                width: 20,
                                height: 20,
                                child: CircularProgressIndicator(strokeWidth: 2),
                              )
                            : Icon(PhosphorIcons.check()),
                        label: Text(_isEditing ? 'Update Classroom' : 'Create Classroom'),
                      ),
                    ),
                  ],
                ),
              ),
            );
          },
        ),
      ),
    );
  }
}