import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:go_router/go_router.dart';
import '../../../../../core/constants/dimensions.dart';
import '../../../../../data/models/student.dart';
import '../../../../../data/models/school.dart';
import '../../../../../data/models/classroom_year.dart';
import '../../../../../data/models/dorm_year.dart';
import '../../../../../presentation/blocs/student/student_bloc.dart';
import '../../../../../presentation/blocs/school/school_bloc.dart';
import '../../../../../data/models/classroom_year.dart';
import '../../../../../data/models/dorm_year.dart';
import '../../../../../presentation/widgets/common/custom_app_bar.dart';
import '../../../../../presentation/widgets/forms/student_form.dart';

class StudentFormScreen extends StatefulWidget {
  final int? studentId;

  const StudentFormScreen({
    super.key,
    this.studentId,
  });

  @override
  State<StudentFormScreen> createState() => _StudentFormScreenState();
}

class _StudentFormScreenState extends State<StudentFormScreen> {
  Student? _student;
  List<School> _schools = [];
  List<ClassroomYear> _classroomYears = [];
  List<DormYear> _dormYears = [];

  bool get _isEditing => widget.studentId != null;

  @override
  void initState() {
    super.initState();
    _loadData();
  }

  void _loadData() {
    // Load schools
    context.read<SchoolBloc>().add(const SchoolLoadRequested());

    // Load classroom years
    // Note: This would need a ClassroomYear BLoC, for now we'll use empty list

    // Load dorm years
    // Note: This would need a DormYear BLoC, for now we'll use empty list

    if (_isEditing && widget.studentId != null) {
      context.read<StudentBloc>().add(StudentLoadByIdRequested(widget.studentId!));
    }
  }

  @override
  Widget build(BuildContext context) {
    return MultiBlocListener(
      listeners: [
        BlocListener<StudentBloc, StudentState>(
          listener: (context, state) {
            if (state is StudentCreated) {
              ScaffoldMessenger.of(context).showSnackBar(
                const SnackBar(
                  content: Text('Student created successfully'),
                  backgroundColor: Colors.green,
                ),
              );
              context.go('/students');
            } else if (state is StudentUpdated) {
              ScaffoldMessenger.of(context).showSnackBar(
                const SnackBar(
                  content: Text('Student updated successfully'),
                  backgroundColor: Colors.green,
                ),
              );
              context.go('/students');
            } else if (state is StudentDetailLoaded) {
              setState(() {
                _student = state.student;
              });
            } else if (state is StudentError) {
              ScaffoldMessenger.of(context).showSnackBar(
                SnackBar(
                  content: Text(state.message),
                  backgroundColor: Theme.of(context).colorScheme.error,
                ),
              );
            }
          },
        ),
        BlocListener<SchoolBloc, SchoolState>(
          listener: (context, state) {
            if (state is SchoolLoaded) {
              setState(() {
                _schools = state.schools;
              });
            }
          },
        ),
      ],
      child: Scaffold(
        appBar: CustomAppBar(
          title: _isEditing ? 'Edit Student' : 'Add Student',
        ),
        body: BlocBuilder<StudentBloc, StudentState>(
          builder: (context, state) {
            if (state is StudentLoading && _student == null) {
              return const Center(
                child: CircularProgressIndicator(),
              );
            }

            return StudentForm(
              student: _student,
              schools: _schools,
              classroomYears: _classroomYears,
              dormYears: _dormYears,
            );
          },
        ),
      ),
    );
  }
}