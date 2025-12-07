import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:go_router/go_router.dart';
import 'package:phosphor_flutter/phosphor_flutter.dart';
import '../../../../../core/constants/dimensions.dart';
import '../../../../../data/models/teacher.dart';
import '../../../../../presentation/blocs/teacher/teacher_bloc.dart';
import '../../../../../presentation/widgets/common/custom_app_bar.dart';

class TeacherDetailScreen extends StatefulWidget {
  final int teacherId;

  const TeacherDetailScreen({
    super.key,
    required this.teacherId,
  });

  @override
  State<TeacherDetailScreen> createState() => _TeacherDetailScreenState();
}

class _TeacherDetailScreenState extends State<TeacherDetailScreen> {
  @override
  void initState() {
    super.initState();
    context.read<TeacherBloc>().add(TeacherLoadByIdRequested(widget.teacherId));
  }

  void _onEditPressed() {
    context.go('/teachers/${widget.teacherId}/edit');
  }

  void _onDeletePressed() {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Delete Teacher'),
        content: const Text('Are you sure you want to delete this teacher? This action cannot be undone.'),
        actions: [
          TextButton(
            onPressed: () => Navigator.of(context).pop(),
            child: const Text('Cancel'),
          ),
          TextButton(
            onPressed: () {
              Navigator.of(context).pop();
              context.read<TeacherBloc>().add(TeacherDeleteRequested(widget.teacherId));
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
    return BlocListener<TeacherBloc, TeacherState>(
      listener: (context, state) {
        if (state is TeacherDeleted) {
          ScaffoldMessenger.of(context).showSnackBar(
            const SnackBar(
              content: Text('Teacher deleted successfully'),
              backgroundColor: Colors.green,
            ),
          );
          context.go('/teachers');
        } else if (state is TeacherError) {
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
          title: 'Teacher Details',
          actions: [
            IconButton(
              icon: Icon(PhosphorIcons.pencil()),
              onPressed: _onEditPressed,
              tooltip: 'Edit Teacher',
            ),
            IconButton(
              icon: Icon(PhosphorIcons.trash()),
              onPressed: _onDeletePressed,
              tooltip: 'Delete Teacher',
            ),
          ],
        ),
        body: BlocBuilder<TeacherBloc, TeacherState>(
          builder: (context, state) {
            if (state is TeacherLoading) {
              return const Center(
                child: CircularProgressIndicator(),
              );
            } else if (state is TeacherDetailLoaded) {
              return _TeacherDetailView(teacher: state.teacher);
            } else if (state is TeacherError) {
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
                      'Error loading teacher',
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
                        context.read<TeacherBloc>().add(TeacherLoadByIdRequested(widget.teacherId));
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

class _TeacherDetailView extends StatelessWidget {
  final Teacher teacher;

  const _TeacherDetailView({required this.teacher});

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
                    teacher.name.isNotEmpty ? teacher.name[0].toUpperCase() : '?',
                    style: Theme.of(context).textTheme.headlineLarge?.copyWith(
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                ),
                const SizedBox(height: AppDimensions.spacingM),
                Text(
                  teacher.fullname,
                  style: Theme.of(context).textTheme.headlineSmall?.copyWith(
                    fontWeight: FontWeight.bold,
                  ),
                  textAlign: TextAlign.center,
                ),
                if (teacher.person?.email != null && teacher.person!.email!.isNotEmpty)
                  Text(
                    teacher.person!.email!,
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
              if (teacher.person?.salutation != null)
                _DetailRow(
                  label: 'Salutation',
                  value: teacher.person!.salutation!.displayName,
                ),
              if (teacher.person?.firstName != null)
                _DetailRow(
                  label: 'First Name',
                  value: teacher.person!.firstName!,
                ),
              if (teacher.person?.lastName != null)
                _DetailRow(
                  label: 'Last Name',
                  value: teacher.person!.lastName!,
                ),
              if (teacher.person?.mobileNumber != null)
                _DetailRow(
                  label: 'Mobile Number',
                  value: teacher.person!.mobileNumber!,
                ),
              if (teacher.person?.dateOfBirth != null)
                _DetailRow(
                  label: 'Date of Birth',
                  value: '${teacher.person!.dateOfBirth!.day}/${teacher.person!.dateOfBirth!.month}/${teacher.person!.dateOfBirth!.year}',
                ),
            ],
          ),

          // Status
          _DetailSection(
            title: 'Status',
            children: [
              _DetailRow(
                label: 'Status',
                value: teacher.status.displayName,
              ),
              _DetailRow(
                label: 'Created',
                value: '${teacher.datestamp.day}/${teacher.datestamp.month}/${teacher.datestamp.year}',
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