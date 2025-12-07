import 'dart:typed_data';

import 'package:dartz/dartz.dart';
import 'package:school_management_flutter/core/errors/failure.dart';
import 'package:school_management_flutter/data/datasources/remote/report_remote_data_source.dart';
import 'package:school_management_flutter/data/models/report_data.dart';
import 'package:school_management_flutter/data/models/report_filters.dart';
import 'package:school_management_flutter/data/repositories/report_repository.dart';

class ReportRepositoryImpl implements ReportRepository {
  final ReportRemoteDataSource remoteDataSource;

  ReportRepositoryImpl(this.remoteDataSource);

  @override
  Future<Either<Failure, ReportFilters>> getReportFilters() async {
    return await remoteDataSource.getReportFilters();
  }

  @override
  Future<Either<Failure, Uint8List>> generatePdfReport(
    String endpoint,
    ReportFilter filter,
  ) async {
    return await remoteDataSource.generatePdfReport(endpoint, filter);
  }

  @override
  Future<Either<Failure, Uint8List>> generateExcelReport(
    String endpoint,
    ReportFilter filter,
  ) async {
    return await remoteDataSource.generateExcelReport(endpoint, filter);
  }
}