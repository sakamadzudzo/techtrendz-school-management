import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:go_router/go_router.dart';
import 'package:phosphor_flutter/phosphor_flutter.dart';
import '../../../../../core/constants/dimensions.dart';
import '../../../../../data/models/school.dart';
import '../../../../../presentation/blocs/school/school_bloc.dart';
import '../../../../../presentation/widgets/common/custom_app_bar.dart';

class SchoolDetailScreen extends StatefulWidget {
  final int schoolId;

  const SchoolDetailScreen({
    super.key,
    required this.schoolId,
  });

  @override
  State<SchoolDetailScreen> createState() => _SchoolDetailScreenState();
}

class _SchoolDetailScreenState extends State<SchoolDetailScreen> {
  @override
  void initState() {
    super.initState();
    context.read<SchoolBloc>().add(SchoolLoadByIdRequested(widget.schoolId));
  }

  void _onEditPressed() {
    context.go('/schools/${widget.schoolId}/edit');
  }

  void _onDeletePressed() {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Delete School'),
        content: const Text('Are you sure you want to delete this school? This action cannot be undone.'),
        actions: [
          TextButton(
            onPressed: () => Navigator.of(context).pop(),
            child: const Text('Cancel'),
          ),
          TextButton(
            onPressed: () {
              Navigator.of(context).pop();
              context.read<SchoolBloc>().add(SchoolDeleteRequested(widget.schoolId));
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
    return BlocListener<SchoolBloc, SchoolState>(
      listener: (context, state) {
        if (state is SchoolDeleted) {
          ScaffoldMessenger.of(context).showSnackBar(
            const SnackBar(
              content: Text('School deleted successfully'),
              backgroundColor: Colors.green,
            ),
          );
          context.go('/schools');
        } else if (state is SchoolError) {
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
          title: 'School Details',
          actions: [
            IconButton(
              icon: Icon(PhosphorIcons.pencil()),
              onPressed: _onEditPressed,
              tooltip: 'Edit School',
            ),
            IconButton(
              icon: Icon(PhosphorIcons.trash()),
              onPressed: _onDeletePressed,
              tooltip: 'Delete School',
            ),
          ],
        ),
        body: BlocBuilder<SchoolBloc, SchoolState>(
          builder: (context, state) {
            if (state is SchoolLoading) {
              return const Center(
                child: CircularProgressIndicator(),
              );
            } else if (state is SchoolDetailLoaded) {
              return _SchoolDetailView(school: state.school);
            } else if (state is SchoolError) {
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
                      'Error loading school',
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
                        context.read<SchoolBloc>().add(SchoolLoadByIdRequested(widget.schoolId));
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

class _SchoolDetailView extends StatelessWidget {
  final School school;

  const _SchoolDetailView({required this.school});

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      padding: const EdgeInsets.all(AppDimensions.paddingM),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          // Header with icon
          Center(
            child: Column(
              children: [
                CircleAvatar(
                  radius: 50,
                  backgroundColor: Theme.of(context).colorScheme.primaryContainer,
                  foregroundColor: Theme.of(context).colorScheme.onPrimaryContainer,
                  child: Icon(
                    PhosphorIcons.building(),
                    size: 32,
                  ),
                ),
                const SizedBox(height: AppDimensions.spacingM),
                Text(
                  school.name ?? 'Unnamed School',
                  style: Theme.of(context).textTheme.headlineSmall?.copyWith(
                    fontWeight: FontWeight.bold,
                  ),
                  textAlign: TextAlign.center,
                ),
                if (school.email != null && school.email!.isNotEmpty)
                  Text(
                    school.email!,
                    style: Theme.of(context).textTheme.bodyLarge?.copyWith(
                      color: Theme.of(context).colorScheme.onSurfaceVariant,
                    ),
                  ),
              ],
            ),
          ),
          const SizedBox(height: AppDimensions.spacingXL),

          // Basic Information
          _DetailSection(
            title: 'Basic Information',
            children: [
              if (school.name != null)
                _DetailRow(
                  label: 'Name',
                  value: school.name!,
                ),
              if (school.email != null)
                _DetailRow(
                  label: 'Email',
                  value: school.email!,
                ),
              if (school.website != null && school.website!.isNotEmpty)
                _DetailRow(
                  label: 'Website',
                  value: school.website!,
                ),
            ],
          ),

          // Contact Information
          _DetailSection(
            title: 'Contact Information',
            children: [
              if (school.telNumber != null && school.telNumber!.isNotEmpty)
                _DetailRow(
                  label: 'Telephone',
                  value: school.telNumber!,
                ),
              if (school.mobileNumber != null && school.mobileNumber!.isNotEmpty)
                _DetailRow(
                  label: 'Mobile',
                  value: school.mobileNumber!,
                ),
              if (school.mobileNumber2 != null && school.mobileNumber2!.isNotEmpty)
                _DetailRow(
                  label: 'Secondary Mobile',
                  value: school.mobileNumber2!,
                ),
              if (school.address != null && school.address!.isNotEmpty)
                _DetailRow(
                  label: 'Address',
                  value: school.address!,
                ),
            ],
          ),

          // Status
          _DetailSection(
            title: 'Status',
            children: [
              _DetailRow(
                label: 'Status',
                value: school.status.displayName,
              ),
              _DetailRow(
                label: 'Created',
                value: '${school.datestamp.day}/${school.datestamp.month}/${school.datestamp.year}',
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
        ...children,
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
    return Card(
      margin: const EdgeInsets.only(bottom: AppDimensions.spacingS),
      child: Padding(
        padding: const EdgeInsets.all(AppDimensions.paddingM),
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
      ),
    );
  }
}