import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:go_router/go_router.dart';
import '../../../../../core/constants/dimensions.dart';
import '../../../../../data/models/teacher.dart';
import '../../../../../presentation/blocs/teacher/teacher_bloc.dart';
import '../../../../../presentation/widgets/common/custom_app_bar.dart';
import '../../../../../presentation/widgets/forms/teacher_form.dart';

class TeacherFormScreen extends StatefulWidget {
  final int? teacherId;

  const TeacherFormScreen({
    super.key,
    this.teacherId,
  });

  @override
  State<TeacherFormScreen> createState() => _TeacherFormScreenState();
}

class _TeacherFormScreenState extends State<TeacherFormScreen> {
  Teacher? _teacher;

  bool get _isEditing => widget.teacherId != null;

  @override
  void initState() {
    super.initState();
    if (_isEditing && widget.teacherId != null) {
      context.read<TeacherBloc>().add(TeacherLoadByIdRequested(widget.teacherId!));
    }
  }

  @override
  Widget build(BuildContext context) {
    return BlocListener<TeacherBloc, TeacherState>(
      listener: (context, state) {
        if (state is TeacherCreated) {
          ScaffoldMessenger.of(context).showSnackBar(
            const SnackBar(
              content: Text('Teacher created successfully'),
              backgroundColor: Colors.green,
            ),
          );
          context.go('/teachers');
        } else if (state is TeacherUpdated) {
          ScaffoldMessenger.of(context).showSnackBar(
            const SnackBar(
              content: Text('Teacher updated successfully'),
              backgroundColor: Colors.green,
            ),
          );
          context.go('/teachers');
        } else if (state is TeacherDetailLoaded) {
          setState(() {
            _teacher = state.teacher;
          });
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
          title: _isEditing ? 'Edit Teacher' : 'Add Teacher',
        ),
        body: BlocBuilder<TeacherBloc, TeacherState>(
          builder: (context, state) {
            if (state is TeacherLoading && _teacher == null) {
              return const Center(
                child: CircularProgressIndicator(),
              );
            }

            return TeacherForm(
              teacher: _teacher,
            );
          },
        ),
      ),
    );
  }
}