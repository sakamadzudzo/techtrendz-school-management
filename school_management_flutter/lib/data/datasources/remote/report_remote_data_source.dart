import 'dart:typed_data';

import 'package:dartz/dartz.dart';
import 'package:dio/dio.dart';
import 'package:school_management_flutter/core/errors/failure.dart';
import 'package:school_management_flutter/core/network/dio_client.dart';
import 'package:school_management_flutter/data/models/report_data.dart';
import 'package:school_management_flutter/data/models/report_filters.dart';

abstract class ReportRemoteDataSource {
  Future<Either<Failure, ReportFilters>> getReportFilters();
  Future<Either<Failure, Uint8List>> generatePdfReport(String endpoint, ReportFilter filter);
  Future<Either<Failure, Uint8List>> generateExcelReport(String endpoint, ReportFilter filter);
}

class ReportRemoteDataSourceImpl implements ReportRemoteDataSource {
  final Dio _dio = DioClient.instance;

  @override
  Future<Either<Failure, ReportFilters>> getReportFilters() async {
    try {
      final response = await _dio.get('/reports/dropdowns/all');
      final reportFilters = ReportFilters.fromJson(response.data);
      return Right(reportFilters);
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  @override
  Future<Either<Failure, Uint8List>> generatePdfReport(String endpoint, ReportFilter filter) async {
    try {
      final queryParams = _buildQueryParams(filter);
      final response = await _dio.post(
        '/reports/pdf/$endpoint',
        data: queryParams,
        options: Options(responseType: ResponseType.bytes),
      );
      return Right(Uint8List.fromList(response.data));
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  @override
  Future<Either<Failure, Uint8List>> generateExcelReport(String endpoint, ReportFilter filter) async {
    try {
      final queryParams = _buildQueryParams(filter);
      final response = await _dio.post(
        '/reports/excel/$endpoint',
        data: queryParams,
        options: Options(responseType: ResponseType.bytes),
      );
      return Right(Uint8List.fromList(response.data));
    } on DioException catch (e) {
      return Left(ServerFailure(e.message ?? 'Server error'));
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  Map<String, dynamic> _buildQueryParams(ReportFilter filter) {
    final params = <String, dynamic>{};

    if (filter.studentId != null) params['studentId'] = filter.studentId;
    if (filter.teacherId != null) params['teacherId'] = filter.teacherId;
    if (filter.courseworkId != null) params['courseworkId'] = filter.courseworkId;
    if (filter.classroomId != null) params['classroomId'] = filter.classroomId;
    if (filter.subjectId != null) params['subjectId'] = filter.subjectId;
    if (filter.extraCurricularId != null) params['activityId'] = filter.extraCurricularId;
    if (filter.year != null) params['year'] = filter.year;

    return params;
  }
}