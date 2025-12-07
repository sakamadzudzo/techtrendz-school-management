import 'package:equatable/equatable.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import '../../../data/models/student.dart';
import '../../../data/repositories/student_repository.dart';

part 'student_event.dart';
part 'student_state.dart';

class StudentBloc extends Bloc<StudentEvent, StudentState> {
  final StudentRepository _studentRepository;

  StudentBloc(this._studentRepository) : super(const StudentInitial()) {
    on<StudentLoadRequested>(_onStudentLoadRequested);
    on<StudentLoadByFilterRequested>(_onStudentLoadByFilterRequested);
    on<StudentLoadByIdRequested>(_onStudentLoadByIdRequested);
    on<StudentCreateRequested>(_onStudentCreateRequested);
    on<StudentUpdateRequested>(_onStudentUpdateRequested);
    on<StudentDeleteRequested>(_onStudentDeleteRequested);
    on<StudentDropdownLoadRequested>(_onStudentDropdownLoadRequested);
    on<StudentDropdownForSubjectLoadRequested>(_onStudentDropdownForSubjectLoadRequested);
    on<StudentDropdownForClassroomLoadRequested>(_onStudentDropdownForClassroomLoadRequested);
  }

  Future<void> _onStudentLoadRequested(
    StudentLoadRequested event,
    Emitter<StudentState> emit,
  ) async {
    emit(const StudentLoading());
    final result = await _studentRepository.getAll();
    result.fold(
      (failure) => emit(StudentError(failure.message)),
      (students) => emit(StudentLoaded(students)),
    );
  }

  Future<void> _onStudentLoadByFilterRequested(
    StudentLoadByFilterRequested event,
    Emitter<StudentState> emit,
  ) async {
    emit(const StudentLoading());
    final result = await _studentRepository.getAllByFilter(event.filters);
    result.fold(
      (failure) => emit(StudentError(failure.message)),
      (students) => emit(StudentLoaded(students)),
    );
  }

  Future<void> _onStudentLoadByIdRequested(
    StudentLoadByIdRequested event,
    Emitter<StudentState> emit,
  ) async {
    emit(const StudentLoading());
    final result = await _studentRepository.getById(event.id);
    result.fold(
      (failure) => emit(StudentError(failure.message)),
      (student) => emit(StudentDetailLoaded(student)),
    );
  }

  Future<void> _onStudentCreateRequested(
    StudentCreateRequested event,
    Emitter<StudentState> emit,
  ) async {
    emit(const StudentLoading());
    final result = await _studentRepository.save(event.student);
    result.fold(
      (failure) => emit(StudentError(failure.message)),
      (student) => emit(StudentCreated(student)),
    );
  }

  Future<void> _onStudentUpdateRequested(
    StudentUpdateRequested event,
    Emitter<StudentState> emit,
  ) async {
    emit(const StudentLoading());
    final result = await _studentRepository.save(event.student);
    result.fold(
      (failure) => emit(StudentError(failure.message)),
      (student) => emit(StudentUpdated(student)),
    );
  }

  Future<void> _onStudentDeleteRequested(
    StudentDeleteRequested event,
    Emitter<StudentState> emit,
  ) async {
    emit(const StudentLoading());
    final result = await _studentRepository.delete(event.id);
    result.fold(
      (failure) => emit(StudentError(failure.message)),
      (_) => emit(const StudentDeleted()),
    );
  }

  Future<void> _onStudentDropdownLoadRequested(
    StudentDropdownLoadRequested event,
    Emitter<StudentState> emit,
  ) async {
    emit(const StudentLoading());
    final result = await _studentRepository.getDropdownAll();
    result.fold(
      (failure) => emit(StudentError(failure.message)),
      (dropdowns) => emit(StudentDropdownLoaded(dropdowns)),
    );
  }

  Future<void> _onStudentDropdownForSubjectLoadRequested(
    StudentDropdownForSubjectLoadRequested event,
    Emitter<StudentState> emit,
  ) async {
    emit(const StudentLoading());
    final result = await _studentRepository.getDropdownForSubject(event.subjectId);
    result.fold(
      (failure) => emit(StudentError(failure.message)),
      (dropdowns) => emit(StudentDropdownLoaded(dropdowns)),
    );
  }

  Future<void> _onStudentDropdownForClassroomLoadRequested(
    StudentDropdownForClassroomLoadRequested event,
    Emitter<StudentState> emit,
  ) async {
    emit(const StudentLoading());
    final result = await _studentRepository.getDropdownForClassroom(event.classroomId);
    result.fold(
      (failure) => emit(StudentError(failure.message)),
      (dropdowns) => emit(StudentDropdownLoaded(dropdowns)),
    );
  }
}