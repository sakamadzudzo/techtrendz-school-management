import 'package:equatable/equatable.dart';
import 'package:school_management_flutter/data/models/report_data.dart';

abstract class ReportEvent extends Equatable {
  const ReportEvent();

  @override
  List<Object?> get props => [];
}

class LoadReportFilters extends ReportEvent {}

class SelectReportType extends ReportEvent {
  final ReportType reportType;

  const SelectReportType(this.reportType);

  @override
  List<Object?> get props => [reportType];
}

class UpdateReportFilter extends ReportEvent {
  final String key;
  final int value;

  const UpdateReportFilter(this.key, this.value);

  @override
  List<Object?> get props => [key, value];
}

class GenerateReport extends ReportEvent {
  final ReportFilter filter;

  const GenerateReport(this.filter);

  @override
  List<Object?> get props => [filter];
}

class ClearReportData extends ReportEvent {}

class ResetReportState extends ReportEvent {}