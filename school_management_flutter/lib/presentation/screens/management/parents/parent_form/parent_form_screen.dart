import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:go_router/go_router.dart';
import '../../../../../core/constants/dimensions.dart';
import '../../../../../data/models/parent.dart';
import '../../../../../data/models/student.dart';
import '../../../../../presentation/blocs/parent/parent_bloc.dart';
import '../../../../../presentation/blocs/student/student_bloc.dart';
import '../../../../../presentation/widgets/common/custom_app_bar.dart';
import '../../../../../presentation/widgets/forms/parent_form.dart';

class ParentFormScreen extends StatefulWidget {
  final int? parentId;

  const ParentFormScreen({
    super.key,
    this.parentId,
  });

  @override
  State<ParentFormScreen> createState() => _ParentFormScreenState();
}

class _ParentFormScreenState extends State<ParentFormScreen> {
  Parent? _parent;
  List<Student> _students = [];

  bool get _isEditing => widget.parentId != null;

  @override
  void initState() {
    super.initState();
    _loadData();
  }

  void _loadData() {
    // Load students for selection
    context.read<StudentBloc>().add(const StudentLoadRequested());

    if (_isEditing && widget.parentId != null) {
      context.read<ParentBloc>().add(ParentLoadByIdRequested(widget.parentId!));
    }
  }

  @override
  Widget build(BuildContext context) {
    return MultiBlocListener(
      listeners: [
        BlocListener<ParentBloc, ParentState>(
          listener: (context, state) {
            if (state is ParentCreated) {
              ScaffoldMessenger.of(context).showSnackBar(
                const SnackBar(
                  content: Text('Parent created successfully'),
                  backgroundColor: Colors.green,
                ),
              );
              context.go('/parents');
            } else if (state is ParentUpdated) {
              ScaffoldMessenger.of(context).showSnackBar(
                const SnackBar(
                  content: Text('Parent updated successfully'),
                  backgroundColor: Colors.green,
                ),
              );
              context.go('/parents');
            } else if (state is ParentDetailLoaded) {
              setState(() {
                _parent = state.parent;
              });
            } else if (state is ParentError) {
              ScaffoldMessenger.of(context).showSnackBar(
                SnackBar(
                  content: Text(state.message),
                  backgroundColor: Theme.of(context).colorScheme.error,
                ),
              );
            }
          },
        ),
        BlocListener<StudentBloc, StudentState>(
          listener: (context, state) {
            if (state is StudentLoaded) {
              setState(() {
                _students = state.students;
              });
            }
          },
        ),
      ],
      child: Scaffold(
        appBar: CustomAppBar(
          title: _isEditing ? 'Edit Parent' : 'Add Parent',
        ),
        body: BlocBuilder<ParentBloc, ParentState>(
          builder: (context, state) {
            if (state is ParentLoading && _parent == null) {
              return const Center(
                child: CircularProgressIndicator(),
              );
            }

            return ParentForm(
              parent: _parent,
              students: _students,
            );
          },
        ),
      ),
    );
  }
}