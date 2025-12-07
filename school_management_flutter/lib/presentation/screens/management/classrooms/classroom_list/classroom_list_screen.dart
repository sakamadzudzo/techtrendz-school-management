import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:go_router/go_router.dart';
import 'package:phosphor_flutter/phosphor_flutter.dart';
import '../../../../../core/constants/dimensions.dart';
import '../../../../../presentation/blocs/classroom/classroom_bloc.dart';
import '../../../../../presentation/widgets/common/custom_app_bar.dart';
import '../../../../../data/models/classroom.dart';

class ClassroomListScreen extends StatefulWidget {
  const ClassroomListScreen({super.key});

  @override
  State<ClassroomListScreen> createState() => _ClassroomListScreenState();
}

class _ClassroomListScreenState extends State<ClassroomListScreen> {
  final TextEditingController _searchController = TextEditingController();

  @override
  void initState() {
    super.initState();
    context.read<ClassroomBloc>().add(const ClassroomLoadRequested());
  }

  @override
  void dispose() {
    _searchController.dispose();
    super.dispose();
  }

  void _onSearchChanged(String query) {
    if (query.isEmpty) {
      context.read<ClassroomBloc>().add(const ClassroomLoadRequested());
    } else {
      context.read<ClassroomBloc>().add(ClassroomLoadByFilterRequested({
        'search': query,
      }));
    }
  }

  void _onClassroomTap(Classroom classroom) {
    context.go('/classrooms/${classroom.id}');
  }

  void _onAddClassroomPressed() {
    context.go('/classrooms/new');
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: CustomAppBar(
        title: 'Classrooms',
        actions: [
          IconButton(
            icon: Icon(PhosphorIcons.plus()),
            onPressed: _onAddClassroomPressed,
            tooltip: 'Add Classroom',
          ),
        ],
      ),
      body: Column(
        children: [
          // Search Bar
          Padding(
            padding: const EdgeInsets.all(AppDimensions.paddingM),
            child: TextField(
              controller: _searchController,
              decoration: InputDecoration(
                hintText: 'Search classrooms...',
                prefixIcon: Icon(PhosphorIcons.magnifyingGlass()),
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(AppDimensions.radiusM),
                ),
                contentPadding: const EdgeInsets.symmetric(
                  horizontal: AppDimensions.paddingM,
                  vertical: AppDimensions.paddingS,
                ),
              ),
              onChanged: _onSearchChanged,
            ),
          ),

          // Classroom List
          Expanded(
            child: BlocBuilder<ClassroomBloc, ClassroomState>(
              builder: (context, state) {
                if (state is ClassroomLoading) {
                  return const Center(
                    child: CircularProgressIndicator(),
                  );
                } else if (state is ClassroomLoaded) {
                  final classrooms = state.classrooms;
                  if (classrooms.isEmpty) {
                    return Center(
                      child: Column(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          Icon(
                            PhosphorIcons.chalkboard(),
                            size: 64,
                            color: Theme.of(context).colorScheme.outline,
                          ),
                          const SizedBox(height: AppDimensions.spacingM),
                          Text(
                            'No classrooms found',
                            style: Theme.of(context).textTheme.titleMedium?.copyWith(
                              color: Theme.of(context).colorScheme.outline,
                            ),
                          ),
                          const SizedBox(height: AppDimensions.spacingM),
                          ElevatedButton.icon(
                            onPressed: _onAddClassroomPressed,
                            icon: Icon(PhosphorIcons.plus()),
                            label: const Text('Add First Classroom'),
                          ),
                        ],
                      ),
                    );
                  }

                  return ListView.builder(
                    padding: const EdgeInsets.symmetric(
                      horizontal: AppDimensions.paddingM,
                    ),
                    itemCount: classrooms.length,
                    itemBuilder: (context, index) {
                      final classroom = classrooms[index];
                      return _ClassroomListItem(
                        classroom: classroom,
                        onTap: () => _onClassroomTap(classroom),
                      );
                    },
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
                          'Error loading classrooms',
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
                            context.read<ClassroomBloc>().add(const ClassroomLoadRequested());
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
        ],
      ),
    );
  }
}

class _ClassroomListItem extends StatelessWidget {
  final Classroom classroom;
  final VoidCallback onTap;

  const _ClassroomListItem({
    required this.classroom,
    required this.onTap,
  });

  @override
  Widget build(BuildContext context) {
    return Card(
      margin: const EdgeInsets.only(bottom: AppDimensions.spacingS),
      child: ListTile(
        leading: CircleAvatar(
          backgroundColor: Theme.of(context).colorScheme.primaryContainer,
          foregroundColor: Theme.of(context).colorScheme.onPrimaryContainer,
          child: Icon(PhosphorIcons.chalkboard()),
        ),
        title: Text(
          classroom.name ?? 'Unnamed Classroom',
          style: Theme.of(context).textTheme.titleMedium?.copyWith(
            fontWeight: FontWeight.w500,
          ),
        ),
        subtitle: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            if (classroom.description != null && classroom.description!.isNotEmpty)
              Text(
                classroom.description!,
                style: Theme.of(context).textTheme.bodyMedium,
              ),
            Text(
              'Status: ${classroom.status.name.toUpperCase()}',
              style: Theme.of(context).textTheme.bodySmall?.copyWith(
                color: Theme.of(context).colorScheme.onSurfaceVariant,
              ),
            ),
          ],
        ),
        trailing: Icon(
          PhosphorIcons.caretRight(),
          color: Theme.of(context).colorScheme.onSurfaceVariant,
        ),
        onTap: onTap,
      ),
    );
  }
}