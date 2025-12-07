import 'dart:typed_data';

import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:school_management_flutter/core/services/report_service.dart';
import 'package:school_management_flutter/data/models/report_data.dart';
import 'package:school_management_flutter/data/models/report_filters.dart';
import 'package:school_management_flutter/data/repositories/report_repository.dart';
import 'package:school_management_flutter/presentation/blocs/report/report_event.dart';
import 'package:school_management_flutter/presentation/blocs/report/report_state.dart';

class ReportBloc extends Bloc<ReportEvent, ReportState> {
  final ReportRepository reportRepository;

  ReportBloc(this.reportRepository) : super(ReportInitial()) {
    on<LoadReportFilters>(_onLoadReportFilters);
    on<SelectReportType>(_onSelectReportType);
    on<UpdateReportFilter>(_onUpdateReportFilter);
    on<GenerateReport>(_onGenerateReport);
    on<ClearReportData>(_onClearReportData);
    on<ResetReportState>(_onResetReportState);
  }

  Future<void> _onLoadReportFilters(
    LoadReportFilters event,
    Emitter<ReportState> emit,
  ) async {
    emit(ReportLoading());

    final result = await reportRepository.getReportFilters();

    result.fold(
      (failure) => emit(ReportError(failure.message, null)),
      (filters) => emit(ReportFiltersLoaded(filters)),
    );
  }

  Future<void> _onSelectReportType(
    SelectReportType event,
    Emitter<ReportState> emit,
  ) async {
    if (state is ReportFiltersLoaded) {
      final currentState = state as ReportFiltersLoaded;
      final initialFilter = ReportFilter(
        type: 'pdf', // Default to PDF
        studentId: event.reportType.availableFilters.students ? null : null,
        teacherId: event.reportType.availableFilters.teachers ? null : null,
        courseworkId: event.reportType.availableFilters.courseworks ? null : null,
        classroomId: event.reportType.availableFilters.classrooms ? null : null,
        subjectId: event.reportType.availableFilters.subjects ? null : null,
        extraCurricularId: event.reportType.availableFilters.extraCurriculars ? null : null,
        year: event.reportType.availableFilters.calendarYears ? null : null,
      );

      emit(ReportTypeSelected(currentState.filters, event.reportType, initialFilter));
    }
  }

  Future<void> _onUpdateReportFilter(
    UpdateReportFilter event,
    Emitter<ReportState> emit,
  ) async {
    if (state is ReportTypeSelected) {
      final currentState = state as ReportTypeSelected;

      final updatedFilter = _updateFilterField(currentState.currentFilter, event.key, event.value);

      emit(ReportFilterUpdated(currentState.filters, currentState.selectedReportType, updatedFilter));
    }
  }

  Future<void> _onGenerateReport(
    GenerateReport event,
    Emitter<ReportState> emit,
  ) async {
    // Validate filter before generating report
    if (!ReportService.validateReportFilter(event.filter, ReportService.getReportTypeByIndex(ReportService.reportTypes.indexWhere((type) => type.endpoint == event.filter.type))!)) {
      emit(const ReportError('Please fill in all required filter fields', null));
      return;
    }

    emit(ReportGenerating(
      ReportService.getReportTypeByIndex(ReportService.reportTypes.indexWhere((type) => type.endpoint == event.filter.type))!,
      event.filter,
    ));

    final result = event.filter.type == 'pdf'
        ? await reportRepository.generatePdfReport(event.filter.type, event.filter)
        : await reportRepository.generateExcelReport(event.filter.type, event.filter);

    result.fold(
      (failure) => emit(ReportError(failure.message, null)),
      (reportData) {
        final filename = ReportService.generateReportFilename(
          ReportService.getReportTypeByIndex(ReportService.reportTypes.indexWhere((type) => type.endpoint == event.filter.type))!,
          event.filter,
        );
        emit(ReportGenerated(reportData, filename, event.filter.type));
      },
    );
  }

  void _onClearReportData(
    ClearReportData event,
    Emitter<ReportState> emit,
  ) {
    if (state is ReportGenerated) {
      emit(ReportInitial());
    }
  }

  void _onResetReportState(
    ResetReportState event,
    Emitter<ReportState> emit,
  ) {
    emit(ReportInitial());
  }

  ReportFilter _updateFilterField(ReportFilter filter, String key, int value) {
    switch (key) {
      case 'studentId':
        return filter.copyWith(studentId: value);
      case 'teacherId':
        return filter.copyWith(teacherId: value);
      case 'courseworkId':
        return filter.copyWith(courseworkId: value);
      case 'classroomId':
        return filter.copyWith(classroomId: value);
      case 'subjectId':
        return filter.copyWith(subjectId: value);
      case 'extraCurricularId':
        return filter.copyWith(extraCurricularId: value);
      case 'year':
        return filter.copyWith(year: value);
      default:
        return filter;
    }
  }
}