import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:go_router/go_router.dart';
import '../../../../../core/constants/dimensions.dart';
import '../../../../../data/models/school.dart';
import '../../../../../presentation/blocs/school/school_bloc.dart';
import '../../../../../presentation/widgets/common/custom_app_bar.dart';
import '../../../../../presentation/widgets/forms/school_form.dart';

class SchoolFormScreen extends StatefulWidget {
  final int? schoolId;

  const SchoolFormScreen({
    super.key,
    this.schoolId,
  });

  @override
  State<SchoolFormScreen> createState() => _SchoolFormScreenState();
}

class _SchoolFormScreenState extends State<SchoolFormScreen> {
  School? _school;

  bool get _isEditing => widget.schoolId != null;

  @override
  void initState() {
    super.initState();
    if (_isEditing && widget.schoolId != null) {
      context.read<SchoolBloc>().add(SchoolLoadByIdRequested(widget.schoolId!));
    }
  }

  @override
  Widget build(BuildContext context) {
    return BlocListener<SchoolBloc, SchoolState>(
      listener: (context, state) {
        if (state is SchoolCreated) {
          ScaffoldMessenger.of(context).showSnackBar(
            const SnackBar(
              content: Text('School created successfully'),
              backgroundColor: Colors.green,
            ),
          );
          context.go('/schools');
        } else if (state is SchoolUpdated) {
          ScaffoldMessenger.of(context).showSnackBar(
            const SnackBar(
              content: Text('School updated successfully'),
              backgroundColor: Colors.green,
            ),
          );
          context.go('/schools');
        } else if (state is SchoolDetailLoaded) {
          setState(() {
            _school = state.school;
          });
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
          title: _isEditing ? 'Edit School' : 'Add School',
        ),
        body: BlocBuilder<SchoolBloc, SchoolState>(
          builder: (context, state) {
            if (state is SchoolLoading && _school == null) {
              return const Center(
                child: CircularProgressIndicator(),
              );
            }

            return SchoolForm(
              school: _school,
            );
          },
        ),
      ),
    );
  }
}