import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:go_router/go_router.dart';
import 'package:phosphor_flutter/phosphor_flutter.dart';
import '../../../../../core/constants/dimensions.dart';
import '../../../../../data/models/parent.dart';
import '../../../../../presentation/blocs/parent/parent_bloc.dart';
import '../../../../../presentation/widgets/common/custom_app_bar.dart';

class ParentDetailScreen extends StatefulWidget {
  final int parentId;

  const ParentDetailScreen({
    super.key,
    required this.parentId,
  });

  @override
  State<ParentDetailScreen> createState() => _ParentDetailScreenState();
}

class _ParentDetailScreenState extends State<ParentDetailScreen> {
  @override
  void initState() {
    super.initState();
    context.read<ParentBloc>().add(ParentLoadByIdRequested(widget.parentId));
  }

  void _onEditPressed() {
    context.go('/parents/${widget.parentId}/edit');
  }

  void _onDeletePressed() {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Delete Parent'),
        content: const Text('Are you sure you want to delete this parent? This action cannot be undone.'),
        actions: [
          TextButton(
            onPressed: () => Navigator.of(context).pop(),
            child: const Text('Cancel'),
          ),
          TextButton(
            onPressed: () {
              Navigator.of(context).pop();
              context.read<ParentBloc>().add(ParentDeleteRequested(widget.parentId));
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
    return BlocListener<ParentBloc, ParentState>(
      listener: (context, state) {
        if (state is ParentDeleted) {
          ScaffoldMessenger.of(context).showSnackBar(
            const SnackBar(
              content: Text('Parent deleted successfully'),
              backgroundColor: Colors.green,
            ),
          );
          context.go('/parents');
        } else if (state is ParentError) {
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
          title: 'Parent Details',
          actions: [
            IconButton(
              icon: Icon(PhosphorIcons.pencil()),
              onPressed: _onEditPressed,
              tooltip: 'Edit Parent',
            ),
            IconButton(
              icon: Icon(PhosphorIcons.trash()),
              onPressed: _onDeletePressed,
              tooltip: 'Delete Parent',
            ),
          ],
        ),
        body: BlocBuilder<ParentBloc, ParentState>(
          builder: (context, state) {
            if (state is ParentLoading) {
              return const Center(
                child: CircularProgressIndicator(),
              );
            } else if (state is ParentDetailLoaded) {
              return _ParentDetailView(parent: state.parent);
            } else if (state is ParentError) {
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
                      'Error loading parent',
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
                        context.read<ParentBloc>().add(ParentLoadByIdRequested(widget.parentId));
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

class _ParentDetailView extends StatelessWidget {
  final Parent parent;

  const _ParentDetailView({required this.parent});

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
                    parent.name.isNotEmpty ? parent.name[0].toUpperCase() : '?',
                    style: Theme.of(context).textTheme.headlineLarge?.copyWith(
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                ),
                const SizedBox(height: AppDimensions.spacingM),
                Text(
                  parent.fullname,
                  style: Theme.of(context).textTheme.headlineSmall?.copyWith(
                    fontWeight: FontWeight.bold,
                  ),
                  textAlign: TextAlign.center,
                ),
                if (parent.person?.email != null && parent.person!.email!.isNotEmpty)
                  Text(
                    parent.person!.email!,
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
              if (parent.person?.salutation != null)
                _DetailRow(
                  label: 'Salutation',
                  value: parent.person!.salutation!.displayName,
                ),
              if (parent.person?.firstName != null)
                _DetailRow(
                  label: 'First Name',
                  value: parent.person!.firstName!,
                ),
              if (parent.person?.lastName != null)
                _DetailRow(
                  label: 'Last Name',
                  value: parent.person!.lastName!,
                ),
              if (parent.person?.mobileNumber != null)
                _DetailRow(
                  label: 'Mobile Number',
                  value: parent.person!.mobileNumber!,
                ),
              if (parent.person?.dateOfBirth != null)
                _DetailRow(
                  label: 'Date of Birth',
                  value: '${parent.person!.dateOfBirth!.day}/${parent.person!.dateOfBirth!.month}/${parent.person!.dateOfBirth!.year}',
                ),
            ],
          ),

          // Associated Students
          _DetailSection(
            title: 'Associated Students (${parent.studentCount})',
            children: parent.students?.map((student) => Padding(
              padding: const EdgeInsets.only(bottom: AppDimensions.spacingS),
              child: Card(
                child: ListTile(
                  leading: CircleAvatar(
                    backgroundColor: Theme.of(context).colorScheme.secondaryContainer,
                    foregroundColor: Theme.of(context).colorScheme.onSecondaryContainer,
                    child: Text(
                      student.name.isNotEmpty ? student.name[0].toUpperCase() : '?',
                      style: Theme.of(context).textTheme.bodyMedium?.copyWith(
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                  ),
                  title: Text(student.fullname),
                  subtitle: Text(student.person?.email ?? ''),
                  trailing: Icon(PhosphorIcons.student()),
                ),
              ),
            )).toList() ?? [],
          ),

          // Status
          _DetailSection(
            title: 'Status',
            children: [
              _DetailRow(
                label: 'Status',
                value: parent.status.displayName,
              ),
              _DetailRow(
                label: 'Created',
                value: '${parent.datestamp.day}/${parent.datestamp.month}/${parent.datestamp.year}',
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