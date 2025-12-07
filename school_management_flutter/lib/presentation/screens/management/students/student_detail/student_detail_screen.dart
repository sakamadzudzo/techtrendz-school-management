import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:go_router/go_router.dart';
import 'package:phosphor_flutter/phosphor_flutter.dart';
import '../../../../../core/constants/dimensions.dart';
import '../../../../../data/models/student.dart';
import '../../../../../presentation/blocs/student/student_bloc.dart';
import '../../../../../presentation/widgets/common/custom_app_bar.dart';

class StudentDetailScreen extends StatefulWidget {
  final int studentId;

  const StudentDetailScreen({
    super.key,
    required this.studentId,
  });

  @override
  State<StudentDetailScreen> createState() => _StudentDetailScreenState();
}

class _StudentDetailScreenState extends State<StudentDetailScreen> {
  @override
  void initState() {
    super.initState();
    context.read<StudentBloc>().add(StudentLoadByIdRequested(widget.studentId));
  }

  void _onEditPressed() {
    context.go('/students/${widget.studentId}/edit');
  }

  void _onDeletePressed() {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Delete Student'),
        content: const Text('Are you sure you want to delete this student? This action cannot be undone.'),
        actions: [
          TextButton(
            onPressed: () => Navigator.of(context).pop(),
            child: const Text('Cancel'),
          ),
          TextButton(
            onPressed: () {
              Navigator.of(context).pop();
              context.read<StudentBloc>().add(StudentDeleteRequested(widget.studentId));
            },
            style: TextButton.styleFrom(
              foregroundColor: Theme.of(context).colorScheme.error,
            ),
            child: const Text('Delete'),
          ),
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return BlocListener<StudentBloc, StudentState>(
      listener: (context, state) {
        if (state is StudentDeleted) {
          ScaffoldMessenger.of(context).showSnackBar(
            const SnackBar(
              content: Text('Student deleted successfully'),
              backgroundColor: Colors.green,
            ),
          );
          context.go('/students');
        } else if (state is StudentError) {
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(
              content: Text(state.message),
              backgroundColor: Theme.of(context).colorScheme.error,
            ),
          );
        }
      },
      child: Scaffold(
        appBar: CustomAppBar(
          title: 'Student Details',
          actions: [
            IconButton(
              icon: Icon(PhosphorIcons.pencil()),
              onPressed: _onEditPressed,
              tooltip: 'Edit Student',
            ),
            IconButton(
              icon: Icon(PhosphorIcons.trash()),
              onPressed: _onDeletePressed,
              tooltip: 'Delete Student',
            ),
          ],
        ),
        body: BlocBuilder<StudentBloc, StudentState>(
          builder: (context, state) {
            if (state is StudentLoading) {
              return const Center(
                child: CircularProgressIndicator(),
              );
            } else if (state is StudentDetailLoaded) {
              return _StudentDetailView(student: state.student);
            } else if (state is StudentError) {
              return Center(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Icon(
                      PhosphorIcons.warning(),
                      size: 64,
                      color: Theme.of(context).colorScheme.error,
                    ),
                    const SizedBox(height: AppDimensions.spacingM),
                    Text(
                      'Error loading student',
                      style: Theme.of(context).textTheme.titleMedium?.copyWith(
                        color: Theme.of(context).colorScheme.error,
                      ),
                    ),
                    const SizedBox(height: AppDimensions.spacingS),
                    Text(
                      state.message,
                      style: Theme.of(context).textTheme.bodyMedium?.copyWith(
                        color: Theme.of(context).colorScheme.onSurfaceVariant,
                      ),
                      textAlign: TextAlign.center,
                    ),
                    const SizedBox(height: AppDimensions.spacingM),
                    ElevatedButton(
                      onPressed: () {
                        context.read<StudentBloc>().add(StudentLoadByIdRequested(widget.studentId));
                      },
                      child: const Text('Retry'),
                    ),
                  ],
                ),
              );
            }

            return const SizedBox.shrink();
          },
        ),
      ),
    );
  }
}

class _StudentDetailView extends StatelessWidget {
  final Student student;

  const _StudentDetailView({required this.student});

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      padding: const EdgeInsets.all(AppDimensions.paddingM),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          // Header with avatar
          Center(
            child: Column(
              children: [
                CircleAvatar(
                  radius: 50,
                  backgroundColor: Theme.of(context).colorScheme.primaryContainer,
                  foregroundColor: Theme.of(context).colorScheme.onPrimaryContainer,
                  child: Text(
                    student.name.isNotEmpty ? student.name[0].toUpperCase() : '?',
                    style: Theme.of(context).textTheme.headlineLarge?.copyWith(
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                ),
                const SizedBox(height: AppDimensions.spacingM),
                Text(
                  student.fullname,
                  style: Theme.of(context).textTheme.headlineSmall?.copyWith(
                    fontWeight: FontWeight.bold,
                  ),
                  textAlign: TextAlign.center,
                ),
                if (student.person?.email != null && student.person!.email!.isNotEmpty)
                  Text(
                    student.person!.email!,
                    style: Theme.of(context).textTheme.bodyLarge?.copyWith(
                      color: Theme.of(context).colorScheme.onSurfaceVariant,
                    ),
                  ),
              ],
            ),
          ),
          const SizedBox(height: AppDimensions.spacingXL),

          // Personal Information
          _DetailSection(
            title: 'Personal Information',
            children: [
              if (student.person?.salutation != null)
                _DetailRow(
                  label: 'Salutation',
                  value: student.person!.salutation!.displayName,
                ),
              if (student.person?.firstName != null)
                _DetailRow(
                  label: 'First Name',
                  value: student.person!.firstName!,
                ),
              if (student.person?.lastName != null)
                _DetailRow(
                  label: 'Last Name',
                  value: student.person!.lastName!,
                ),
              if (student.person?.mobileNumber != null)
                _DetailRow(
                  label: 'Mobile Number',
                  value: student.person!.mobileNumber!,
                ),
              if (student.person?.dateOfBirth != null)
                _DetailRow(
                  label: 'Date of Birth',
                  value: '${student.person!.dateOfBirth!.day}/${student.person!.dateOfBirth!.month}/${student.person!.dateOfBirth!.year}',
                ),
            ],
          ),

          // Academic Information
          _DetailSection(
            title: 'Academic Information',
            children: [
              if (student.startYear != null)
                _DetailRow(
                  label: 'Start Year',
                  value: student.startYear!.toString(),
                ),
              if (student.endYear != null)
                _DetailRow(
                  label: 'End Year',
                  value: student.endYear!.toString(),
                ),
              if (student.classroom != null)
                _DetailRow(
                  label: 'Classroom',
                  value: student.classroom!.name,
                ),
              if (student.dorm != null)
                _DetailRow(
                  label: 'Dormitory',
                  value: student.dorm!.name,
                ),
              if (student.school != null)
                _DetailRow(
                  label: 'School',
                  value: student.school!.name ?? 'Unknown School',
                ),
            ],
          ),

          // Status
          _DetailSection(
            title: 'Status',
            children: [
              _DetailRow(
                label: 'Status',
                value: student.status.displayName,
              ),
              _DetailRow(
                label: 'Created',
                value: '${student.datestamp.day}/${student.datestamp.month}/${student.datestamp.year}',
              ),
            ],
          ),
        ],
      ),
    );
  }
}

class _DetailSection extends StatelessWidget {
  final String title;
  final List<Widget> children;

  const _DetailSection({
    required this.title,
    required this.children,
  });

  @override
  Widget build(BuildContext context) {
    if (children.isEmpty) return const SizedBox.shrink();

    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(
          title,
          style: Theme.of(context).textTheme.titleMedium?.copyWith(
            fontWeight: FontWeight.bold,
            color: Theme.of(context).colorScheme.primary,
          ),
        ),
        const SizedBox(height: AppDimensions.spacingM),
        Card(
          child: Padding(
            padding: const EdgeInsets.all(AppDimensions.paddingM),
            child: Column(
              children: children,
            ),
          ),
        ),
        const SizedBox(height: AppDimensions.spacingXL),
      ],
    );
  }
}

class _DetailRow extends StatelessWidget {
  final String label;
  final String value;

  const _DetailRow({
    required this.label,
    required this.value,
  });

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.only(bottom: AppDimensions.spacingS),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          SizedBox(
            width: 120,
            child: Text(
              '$label:',
              style: Theme.of(context).textTheme.bodyMedium?.copyWith(
                fontWeight: FontWeight.w500,
                color: Theme.of(context).colorScheme.onSurfaceVariant,
              ),
            ),
          ),
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
}