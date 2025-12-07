import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:go_router/go_router.dart';
import 'package:phosphor_flutter/phosphor_flutter.dart';
import '../../../../../core/constants/dimensions.dart';
import '../../../../../presentation/blocs/school/school_bloc.dart';
import '../../../../../presentation/widgets/common/custom_app_bar.dart';
import '../../../../../data/models/school.dart';

class SchoolListScreen extends StatefulWidget {
  const SchoolListScreen({super.key});

  @override
  State<SchoolListScreen> createState() => _SchoolListScreenState();
}

class _SchoolListScreenState extends State<SchoolListScreen> {
  final TextEditingController _searchController = TextEditingController();

  @override
  void initState() {
    super.initState();
    context.read<SchoolBloc>().add(const SchoolLoadRequested());
  }

  @override
  void dispose() {
    _searchController.dispose();
    super.dispose();
  }

  void _onSearchChanged(String query) {
    if (query.isEmpty) {
      context.read<SchoolBloc>().add(const SchoolLoadRequested());
    } else {
      context.read<SchoolBloc>().add(SchoolLoadByFilterRequested({
        'search': query,
      }));
    }
  }

  void _onSchoolTap(School school) {
    context.go('/schools/${school.id}');
  }

  void _onAddSchoolPressed() {
    context.go('/schools/new');
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: CustomAppBar(
        title: 'Schools',
        actions: [
          IconButton(
            icon: Icon(PhosphorIcons.plus()),
            onPressed: _onAddSchoolPressed,
            tooltip: 'Add School',
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
                hintText: 'Search schools...',
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

          // School List
          Expanded(
            child: BlocBuilder<SchoolBloc, SchoolState>(
              builder: (context, state) {
                if (state is SchoolLoading) {
                  return const Center(
                    child: CircularProgressIndicator(),
                  );
                } else if (state is SchoolLoaded) {
                  final schools = state.schools;
                  if (schools.isEmpty) {
                    return Center(
                      child: Column(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          Icon(
                            PhosphorIcons.building(),
                            size: 64,
                            color: Theme.of(context).colorScheme.outline,
                          ),
                          const SizedBox(height: AppDimensions.spacingM),
                          Text(
                            'No schools found',
                            style: Theme.of(context).textTheme.titleMedium?.copyWith(
                              color: Theme.of(context).colorScheme.outline,
                            ),
                          ),
                          const SizedBox(height: AppDimensions.spacingM),
                          ElevatedButton.icon(
                            onPressed: _onAddSchoolPressed,
                            icon: Icon(PhosphorIcons.plus()),
                            label: const Text('Add First School'),
                          ),
                        ],
                      ),
                    );
                  }

                  return ListView.builder(
                    padding: const EdgeInsets.symmetric(
                      horizontal: AppDimensions.paddingM,
                    ),
                    itemCount: schools.length,
                    itemBuilder: (context, index) {
                      final school = schools[index];
                      return _SchoolListItem(
                        school: school,
                        onTap: () => _onSchoolTap(school),
                      );
                    },
                  );
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
                          'Error loading schools',
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
                            context.read<SchoolBloc>().add(const SchoolLoadRequested());
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

class _SchoolListItem extends StatelessWidget {
  final School school;
  final VoidCallback onTap;

  const _SchoolListItem({
    required this.school,
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
          child: Icon(PhosphorIcons.building()),
        ),
        title: Text(
          school.name ?? 'Unnamed School',
          style: Theme.of(context).textTheme.titleMedium?.copyWith(
            fontWeight: FontWeight.w500,
          ),
        ),
        subtitle: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            if (school.email != null && school.email!.isNotEmpty)
              Text(
                school.email!,
                style: Theme.of(context).textTheme.bodyMedium,
              ),
            if (school.address != null && school.address!.isNotEmpty)
              Text(
                school.address!,
                style: Theme.of(context).textTheme.bodySmall?.copyWith(
                  color: Theme.of(context).colorScheme.onSurfaceVariant,
                ),
                maxLines: 2,
                overflow: TextOverflow.ellipsis,
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