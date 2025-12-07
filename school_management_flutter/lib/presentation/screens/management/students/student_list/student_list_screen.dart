import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:go_router/go_router.dart';
import 'package:phosphor_flutter/phosphor_flutter.dart';
import '../../../../../core/constants/dimensions.dart';
import '../../../../../presentation/blocs/student/student_bloc.dart';
import '../../../../../presentation/widgets/common/custom_app_bar.dart';
import '../../../../../data/models/student.dart';

class StudentListScreen extends StatefulWidget {
  const StudentListScreen({super.key});

  @override
  State<StudentListScreen> createState() => _StudentListScreenState();
}

class _StudentListScreenState extends State<StudentListScreen> {
  final TextEditingController _searchController = TextEditingController();

  @override
  void initState() {
    super.initState();
    context.read<StudentBloc>().add(const StudentLoadRequested());
  }

  @override
  void dispose() {
    _searchController.dispose();
    super.dispose();
  }

  void _onSearchChanged(String query) {
    if (query.isEmpty) {
      context.read<StudentBloc>().add(const StudentLoadRequested());
    } else {
      context.read<StudentBloc>().add(StudentLoadByFilterRequested({
        'search': query,
      }));
    }
  }

  void _onStudentTap(Student student) {
    context.go('/students/${student.id}');
  }

  void _onAddStudentPressed() {
    context.go('/students/new');
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: CustomAppBar(
        title: 'Students',
        actions: [
          IconButton(
            icon: Icon(PhosphorIcons.plus()),
            onPressed: _onAddStudentPressed,
            tooltip: 'Add Student',
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
                hintText: 'Search students...',
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

          // Student List
          Expanded(
            child: BlocBuilder<StudentBloc, StudentState>(
              builder: (context, state) {
                if (state is StudentLoading) {
                  return const Center(
                    child: CircularProgressIndicator(),
                  );
                } else if (state is StudentLoaded) {
                  final students = state.students;
                  if (students.isEmpty) {
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
                            'No students found',
                            style: Theme.of(context).textTheme.titleMedium?.copyWith(
                              color: Theme.of(context).colorScheme.outline,
                            ),
                          ),
                          const SizedBox(height: AppDimensions.spacingM),
                          ElevatedButton.icon(
                            onPressed: _onAddStudentPressed,
                            icon: Icon(PhosphorIcons.plus()),
                            label: const Text('Add First Student'),
                          ),
                        ],
                      ),
                    );
                  }

                  return ListView.builder(
                    padding: const EdgeInsets.symmetric(
                      horizontal: AppDimensions.paddingM,
                    ),
                    itemCount: students.length,
                    itemBuilder: (context, index) {
                      final student = students[index];
                      return _StudentListItem(
                        student: student,
                        onTap: () => _onStudentTap(student),
                      );
                    },
                  );
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
                          'Error loading students',
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
                            context.read<StudentBloc>().add(const StudentLoadRequested());
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

class _StudentListItem extends StatelessWidget {
  final Student student;
  final VoidCallback onTap;

  const _StudentListItem({
    required this.student,
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
            student.name.isNotEmpty ? student.name[0].toUpperCase() : '?',
            style: Theme.of(context).textTheme.titleMedium?.copyWith(
              fontWeight: FontWeight.bold,
            ),
          ),
        ),
        title: Text(
          student.fullname,
          style: Theme.of(context).textTheme.titleMedium?.copyWith(
            fontWeight: FontWeight.w500,
          ),
        ),
        subtitle: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            if (student.person?.email != null && student.person!.email!.isNotEmpty)
              Text(
                student.person!.email!,
                style: Theme.of(context).textTheme.bodyMedium,
              ),
            if (student.classroom != null)
              Text(
                'Class: ${student.classroom!.name}',
                style: Theme.of(context).textTheme.bodySmall?.copyWith(
                  color: Theme.of(context).colorScheme.onSurfaceVariant,
                ),
              ),
            if (student.dorm != null)
              Text(
                'Dorm: ${student.dorm!.name}',
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