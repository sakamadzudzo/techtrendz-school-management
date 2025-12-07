import 'package:dartz/dartz.dart';
import '../../core/errors/failure.dart';
import '../datasources/remote/extra_curricular_remote_data_source.dart';
import '../models/extra_curricular.dart';
import 'base_repository.dart';

class ExtraCurricularRepository implements BaseRepository<ExtraCurricular> {
  final ExtraCurricularRemoteDataSource _remoteDataSource;

  ExtraCurricularRepository(this._remoteDataSource);

  @override
  Future<Either<Failure, List<ExtraCurricular>>> getAll() async {
    return await _remoteDataSource.getAll();
  }

  @override
  Future<Either<Failure, List<ExtraCurricular>>> getAllByFilter(Map<String, dynamic> filters) async {
    return await _remoteDataSource.getAllByFilter(filters);
  }

  @override
  Future<Either<Failure, ExtraCurricular>> getById(int id) async {
    return await _remoteDataSource.getById(id);
  }

  @override
  Future<Either<Failure, ExtraCurricular>> save(ExtraCurricular extraCurricular) async {
    return await _remoteDataSource.save(extraCurricular);
  }

  @override
  Future<Either<Failure, List<ExtraCurricular>>> saveAll(List<ExtraCurricular> extraCurriculars) async {
    return await _remoteDataSource.saveAll(extraCurriculars);
  }

  @override
  Future<Either<Failure, void>> delete(int id) async {
    return await _remoteDataSource.delete(id);
  }

  Future<Either<Failure, List<Map<String, dynamic>>>> getAllList() async {
    return await _remoteDataSource.getAllList();
  }
}