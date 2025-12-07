import 'dart:typed_data';

import 'package:dartz/dartz.dart';
import 'package:school_management_flutter/core/errors/failure.dart';
import 'package:school_management_flutter/data/models/report_data.dart';
import 'package:school_management_flutter/data/models/report_filters.dart';

abstract class ReportRepository {
  Future<Either<Failure, ReportFilters>> getReportFilters();
  Future<Either<Failure, Uint8List>> generatePdfReport(
    String endpoint,
    ReportFilter filter,
  );
  Future<Either<Failure, Uint8List>> generateExcelReport(
    String endpoint,
    ReportFilter filter,
  );
}