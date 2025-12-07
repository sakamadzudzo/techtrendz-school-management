import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:go_router/go_router.dart';
import 'package:phosphor_flutter/phosphor_flutter.dart';
import '../../../../../core/constants/dimensions.dart';
import '../../../../../core/constants/enums.dart';
import '../../../../../presentation/blocs/classroom/classroom_bloc.dart';
import '../../../../../presentation/widgets/common/custom_app_bar.dart';
import '../../../../../data/models/classroom.dart';

class ClassroomDetailScreen extends StatefulWidget {
  final int classroomId;

  const ClassroomDetailScreen({super.key, required this.classroomId});

  @override
  State<ClassroomDetailScreen> createState() => _ClassroomDetailScreenState();
}

class _ClassroomDetailScreenState extends State<ClassroomDetailScreen> {
  @override
  void initState() {
    super.initState();
    context.read<ClassroomBloc>().add(ClassroomLoadByIdRequested(widget.classroomId));
  }

  void _onEditPressed() {
    context.go('/classrooms/${widget.classroomId}/edit');
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: CustomAppBar(
        title: 'Classroom Details',
        actions: [
          IconButton(
            icon: Icon(PhosphorIcons.pencil()),
            onPressed: _onEditPressed,
            tooltip: 'Edit Classroom',
          ),
        ],
      ),
      body: BlocBuilder<ClassroomBloc, ClassroomState>(
        builder: (context, state) {
          if (state is ClassroomLoading) {
            return const Center(
              child: CircularProgressIndicator(),
            );
          } else if (state is ClassroomDetailLoaded) {
            final classroom = state.classroom;
            return SingleChildScrollView(
              padding: const EdgeInsets.all(AppDimensions.paddingM),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  // Classroom Header
                  Card(
                    child: Padding(
                      padding: const EdgeInsets.all(AppDimensions.paddingM),
                      child: Row(
                        children: [
                          CircleAvatar(
                            radius: 32,
                            backgroundColor: Theme.of(context).colorScheme.primaryContainer,
                            foregroundColor: Theme.of(context).colorScheme.onPrimaryContainer,
                            child: Icon(PhosphorIcons.chalkboard(), size: 32),
                          ),
                          const SizedBox(width: AppDimensions.spacingM),
                          Expanded(
                            child: Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Text(
                                  classroom.name ?? 'Unnamed Classroom',
                                  style: Theme.of(context).textTheme.headlineSmall?.copyWith(
                                    fontWeight: FontWeight.bold,
                                  ),
                                ),
                                const SizedBox(height: AppDimensions.spacingXS),
                                Container(
                                  padding: const EdgeInsets.symmetric(
                                    horizontal: AppDimensions.paddingS,
                                    vertical: AppDimensions.paddingXS,
                                  ),
                                  decoration: BoxDecoration(
                                    color: classroom.status == Status.ACTIVE
                                        ? Theme.of(context).colorScheme.primaryContainer
                                        : Theme.of(context).colorScheme.errorContainer,
                                    borderRadius: BorderRadius.circular(AppDimensions.radiusS),
                                  ),
                                  child: Text(
                                    classroom.status.name.toUpperCase(),
                                    style: Theme.of(context).textTheme.labelSmall?.copyWith(
                                      color: classroom.status == Status.ACTIVE
                                          ? Theme.of(context).colorScheme.onPrimaryContainer
                                          : Theme.of(context).colorScheme.onErrorContainer,
                                      fontWeight: FontWeight.w500,
                                    ),
                                  ),
                                ),
                              ],
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),

                  const SizedBox(height: AppDimensions.spacingM),

                  // Classroom Information
                  Card(
                    child: Padding(
                      padding: const EdgeInsets.all(AppDimensions.paddingM),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text(
                            'Classroom Information',
                            style: Theme.of(context).textTheme.titleMedium?.copyWith(
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                          const SizedBox(height: AppDimensions.spacingM),

                          if (classroom.description != null && classroom.description!.isNotEmpty) ...[
                            _InfoRow(
                              label: 'Description',
                              value: classroom.description!,
                            ),
                            const SizedBox(height: AppDimensions.spacingM),
                          ],

                          _InfoRow(
                            label: 'Status',
                            value: classroom.status.name.toUpperCase(),
                          ),
                          const SizedBox(height: AppDimensions.spacingM),

                          _InfoRow(
                            label: 'Created',
                            value: classroom.datestamp.toString().split(' ')[0],
                          ),
                        ],
                      ),
                    ),
                  ),
                ],
              ),
            );
          } else if (state is ClassroomError) {
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
                    'Error loading classroom',
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
                      context.read<ClassroomBloc>().add(ClassroomLoadByIdRequested(widget.classroomId));
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
    );
  }
}

class _InfoRow extends StatelessWidget {
  final String label;
  final String value;

  const _InfoRow({
    required this.label,
    required this.value,
  });

  @override
  Widget build(BuildContext context) {
    return Row(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        SizedBox(
          width: 100,
          child: Text(
            label,
            style: Theme.of(context).textTheme.bodyMedium?.copyWith(
              color: Theme.of(context).colorScheme.onSurfaceVariant,
              fontWeight: FontWeight.w500,
            ),
          ),
        ),
        const SizedBox(width: AppDimensions.spacingM),
        Expanded(
          child: Text(
            value,
            style: Theme.of(context).textTheme.bodyMedium,
          ),
        ),
      ],
    );
  }
}