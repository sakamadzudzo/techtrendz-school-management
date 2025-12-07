import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:go_router/go_router.dart';
import 'package:phosphor_flutter/phosphor_flutter.dart';
import '../../../../../core/constants/dimensions.dart';
import '../../../../../presentation/blocs/parent/parent_bloc.dart';
import '../../../../../presentation/widgets/common/custom_app_bar.dart';
import '../../../../../data/models/parent.dart';

class ParentListScreen extends StatefulWidget {
  const ParentListScreen({super.key});

  @override
  State<ParentListScreen> createState() => _ParentListScreenState();
}

class _ParentListScreenState extends State<ParentListScreen> {
  final TextEditingController _searchController = TextEditingController();

  @override
  void initState() {
    super.initState();
    context.read<ParentBloc>().add(const ParentLoadRequested());
  }

  @override
  void dispose() {
    _searchController.dispose();
    super.dispose();
  }

  void _onSearchChanged(String query) {
    if (query.isEmpty) {
      context.read<ParentBloc>().add(const ParentLoadRequested());
    } else {
      context.read<ParentBloc>().add(ParentLoadByFilterRequested({
        'search': query,
      }));
    }
  }

  void _onParentTap(Parent parent) {
    context.go('/parents/${parent.id}');
  }

  void _onAddParentPressed() {
    context.go('/parents/new');
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: CustomAppBar(
        title: 'Parents',
        actions: [
          IconButton(
            icon: Icon(PhosphorIcons.plus()),
            onPressed: _onAddParentPressed,
            tooltip: 'Add Parent',
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
                hintText: 'Search parents...',
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

          // Parent List
          Expanded(
            child: BlocBuilder<ParentBloc, ParentState>(
              builder: (context, state) {
                if (state is ParentLoading) {
                  return const Center(
                    child: CircularProgressIndicator(),
                  );
                } else if (state is ParentLoaded) {
                  final parents = state.parents;
                  if (parents.isEmpty) {
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
                            'No parents found',
                            style: Theme.of(context).textTheme.titleMedium?.copyWith(
                              color: Theme.of(context).colorScheme.outline,
                            ),
                          ),
                          const SizedBox(height: AppDimensions.spacingM),
                          ElevatedButton.icon(
                            onPressed: _onAddParentPressed,
                            icon: Icon(PhosphorIcons.plus()),
                            label: const Text('Add First Parent'),
                          ),
                        ],
                      ),
                    );
                  }

                  return ListView.builder(
                    padding: const EdgeInsets.symmetric(
                      horizontal: AppDimensions.paddingM,
                    ),
                    itemCount: parents.length,
                    itemBuilder: (context, index) {
                      final parent = parents[index];
                      return _ParentListItem(
                        parent: parent,
                        onTap: () => _onParentTap(parent),
                      );
                    },
                  );
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
                          'Error loading parents',
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
                            context.read<ParentBloc>().add(const ParentLoadRequested());
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

class _ParentListItem extends StatelessWidget {
  final Parent parent;
  final VoidCallback onTap;

  const _ParentListItem({
    required this.parent,
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
            parent.name.isNotEmpty ? parent.name[0].toUpperCase() : '?',
            style: Theme.of(context).textTheme.titleMedium?.copyWith(
              fontWeight: FontWeight.bold,
            ),
          ),
        ),
        title: Text(
          parent.fullname,
          style: Theme.of(context).textTheme.titleMedium?.copyWith(
            fontWeight: FontWeight.w500,
          ),
        ),
        subtitle: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            if (parent.person?.email != null && parent.person!.email!.isNotEmpty)
              Text(
                parent.person!.email!,
                style: Theme.of(context).textTheme.bodyMedium,
              ),
            Text(
              '${parent.studentCount} student${parent.studentCount == 1 ? '' : 's'}',
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