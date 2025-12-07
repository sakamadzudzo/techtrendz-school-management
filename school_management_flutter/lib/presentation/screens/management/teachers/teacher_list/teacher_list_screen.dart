import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:go_router/go_router.dart';
import 'package:phosphor_flutter/phosphor_flutter.dart';
import '../../../../../core/constants/dimensions.dart';
import '../../../../../presentation/blocs/teacher/teacher_bloc.dart';
import '../../../../../presentation/widgets/common/custom_app_bar.dart';
import '../../../../../data/models/teacher.dart';

class TeacherListScreen extends StatefulWidget {
  const TeacherListScreen({super.key});

  @override
  State<TeacherListScreen> createState() => _TeacherListScreenState();
}

class _TeacherListScreenState extends State<TeacherListScreen> {
  final TextEditingController _searchController = TextEditingController();

  @override
  void initState() {
    super.initState();
    context.read<TeacherBloc>().add(const TeacherLoadRequested());
  }

  @override
  void dispose() {
    _searchController.dispose();
    super.dispose();
  }

  void _onSearchChanged(String query) {
    if (query.isEmpty) {
      context.read<TeacherBloc>().add(const TeacherLoadRequested());
    } else {
      context.read<TeacherBloc>().add(TeacherLoadByFilterRequested({
        'search': query,
      }));
    }
  }

  void _onTeacherTap(Teacher teacher) {
    context.go('/teachers/${teacher.id}');
  }

  void _onAddTeacherPressed() {
    context.go('/teachers/new');
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: CustomAppBar(
        title: 'Teachers',
        actions: [
          IconButton(
            icon: Icon(PhosphorIcons.plus()),
            onPressed: _onAddTeacherPressed,
            tooltip: 'Add Teacher',
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
                hintText: 'Search teachers...',
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

          // Teacher List
          Expanded(
            child: BlocBuilder<TeacherBloc, TeacherState>(
              builder: (context, state) {
                if (state is TeacherLoading) {
                  return const Center(
                    child: CircularProgressIndicator(),
                  );
                } else if (state is TeacherLoaded) {
                  final teachers = state.teachers;
                  if (teachers.isEmpty) {
                    return Center(
                      child: Column(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          Icon(
                            PhosphorIcons.users(),
                            size: 64,
                            color: Theme.of(context).colorScheme.outline,
                          ),
                          const SizedBox(height: AppDimensions.spacingM),
                          Text(
                            'No teachers found',
                            style: Theme.of(context).textTheme.titleMedium?.copyWith(
                              color: Theme.of(context).colorScheme.outline,
                            ),
                          ),
                          const SizedBox(height: AppDimensions.spacingM),
                          ElevatedButton.icon(
                            onPressed: _onAddTeacherPressed,
                            icon: Icon(PhosphorIcons.plus()),
                            label: const Text('Add First Teacher'),
                          ),
                        ],
                      ),
                    );
                  }

                  return ListView.builder(
                    padding: const EdgeInsets.symmetric(
                      horizontal: AppDimensions.paddingM,
                    ),
                    itemCount: teachers.length,
                    itemBuilder: (context, index) {
                      final teacher = teachers[index];
                      return _TeacherListItem(
                        teacher: teacher,
                        onTap: () => _onTeacherTap(teacher),
                      );
                    },
                  );
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
                          'Error loading teachers',
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
                            context.read<TeacherBloc>().add(const TeacherLoadRequested());
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

class _TeacherListItem extends StatelessWidget {
  final Teacher teacher;
  final VoidCallback onTap;

  const _TeacherListItem({
    required this.teacher,
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
          child: Text(
            teacher.name.isNotEmpty ? teacher.name[0].toUpperCase() : '?',
            style: Theme.of(context).textTheme.titleMedium?.copyWith(
              fontWeight: FontWeight.bold,
            ),
          ),
        ),
        title: Text(
          teacher.fullname,
          style: Theme.of(context).textTheme.titleMedium?.copyWith(
            fontWeight: FontWeight.w500,
          ),
        ),
        subtitle: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            if (teacher.person?.email != null && teacher.person!.email!.isNotEmpty)
              Text(
                teacher.person!.email!,
                style: Theme.of(context).textTheme.bodyMedium,
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