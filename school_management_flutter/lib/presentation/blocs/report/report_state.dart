import 'dart:typed_data';

import 'package:equatable/equatable.dart';
import 'package:school_management_flutter/data/models/report_data.dart';
import 'package:school_management_flutter/data/models/report_filters.dart';

abstract class ReportState extends Equatable {
  const ReportState();

  @override
  List<Object?> get props => [];
}

class ReportInitial extends ReportState {}

class ReportLoading extends ReportState {}

class ReportFiltersLoaded extends ReportState {
  final ReportFilters filters;

  const ReportFiltersLoaded(this.filters);

  @override
  List<Object?> get props => [filters];
}

class ReportTypeSelected extends ReportState {
  final ReportFilters filters;
  final ReportType selectedReportType;
  final ReportFilter currentFilter;

  const ReportTypeSelected(this.filters, this.selectedReportType, this.currentFilter);

  @override
  List<Object?> get props => [filters, selectedReportType, currentFilter];
}

class ReportGenerating extends ReportState {
  final ReportType reportType;
  final ReportFilter filter;

  const ReportGenerating(this.reportType, this.filter);

  @override
  List<Object?> get props => [reportType, filter];
}

class ReportGenerated extends ReportState {
  final Uint8List reportData;
  final String filename;
  final String reportType;

  const ReportGenerated(this.reportData, this.filename, this.reportType);

  @override
  List<Object?> get props => [reportData, filename, reportType];
}

class ReportError extends ReportState {
  final String message;
  final int? statusCode;

  const ReportError(this.message, this.statusCode);

  @override
  List<Object?> get props => [message, statusCode];
}

class ReportFilterUpdated extends ReportState {
  final ReportFilters filters;
  final ReportType selectedReportType;
  final ReportFilter currentFilter;

  const ReportFilterUpdated(this.filters, this.selectedReportType, this.currentFilter);

  @override
  List<Object?> get props => [filters, selectedReportType, currentFilter];
}