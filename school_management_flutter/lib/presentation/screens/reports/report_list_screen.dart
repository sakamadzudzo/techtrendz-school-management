import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:phosphor_flutter/phosphor_flutter.dart';
import 'package:school_management_flutter/core/services/report_service.dart';
import 'package:school_management_flutter/data/models/report_data.dart';
import 'package:school_management_flutter/data/models/report_filters.dart';
import 'package:school_management_flutter/presentation/blocs/report/report_bloc.dart';
import 'package:school_management_flutter/presentation/blocs/report/report_event.dart';
import 'package:school_management_flutter/presentation/blocs/report/report_state.dart';
import 'package:school_management_flutter/presentation/widgets/common/custom_app_bar.dart';
import 'package:school_management_flutter/presentation/widgets/common/custom_button.dart';

class ReportListScreen extends StatefulWidget {
  const ReportListScreen({super.key});

  @override
  State<ReportListScreen> createState() => _ReportListScreenState();
}

class _ReportListScreenState extends State<ReportListScreen> {
  @override
  void initState() {
    super.initState();
    // Load report filters when screen initializes
    context.read<ReportBloc>().add(LoadReportFilters());
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: const CustomAppBar(
        title: 'Reports',
        showBackButton: true,
      ),
      body: BlocBuilder<ReportBloc, ReportState>(
        builder: (context, state) {
          if (state is ReportLoading) {
            return const Center(
              child: CircularProgressIndicator(),
            );
          }

          if (state is ReportError) {
            return Center(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  const Icon(
                    PhosphorIconsRegular.warning,
                    size: 64,
                    color: Colors.red,
                  ),
                  const SizedBox(height: 16),
                  Text(
                    'Error loading reports',
                    style: Theme.of(context).textTheme.headlineSmall,
                  ),
                  const SizedBox(height: 8),
                  Text(
                    state.message,
                    style: Theme.of(context).textTheme.bodyMedium,
                    textAlign: TextAlign.center,
                  ),
                  const SizedBox(height: 16),
                  CustomButton(
                    text: 'Retry',
                    onPressed: () {
                      context.read<ReportBloc>().add(LoadReportFilters());
                    },
                  ),
                ],
              ),
            );
          }

          return Padding(
            padding: const EdgeInsets.all(16.0),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  'Available Reports',
                  style: Theme.of(context).textTheme.headlineMedium,
                ),
                const SizedBox(height: 8),
                Text(
                  'Select a report type to generate and view reports',
                  style: Theme.of(context).textTheme.bodyMedium,
                ),
                const SizedBox(height: 24),
                Expanded(
                  child: ListView.builder(
                    itemCount: ReportService.reportTypes.length,
                    itemBuilder: (context, index) {
                      final reportType = ReportService.reportTypes[index];
                      return Card(
                        margin: const EdgeInsets.only(bottom: 12),
                        child: ListTile(
                          leading: Icon(
                            _getReportIcon(reportType.endpoint),
                            size: 32,
                            color: Theme.of(context).primaryColor,
                          ),
                          title: Text(
                            reportType.title,
                            style: Theme.of(context).textTheme.titleMedium,
                          ),
                          subtitle: Text(
                            _getReportDescription(reportType),
                            style: Theme.of(context).textTheme.bodySmall,
                          ),
                          trailing: const Icon(PhosphorIconsRegular.caretRight),
                          onTap: () {
                            context.read<ReportBloc>().add(SelectReportType(reportType));
                            Navigator.pushNamed(
                              context,
                              '/reports/filters',
                              arguments: reportType,
                            );
                          },
                        ),
                      );
                    },
                  ),
                ),
              ],
            ),
          );
        },
      ),
    );
  }

  IconData _getReportIcon(String endpoint) {
    if (endpoint.contains('marks')) {
      return PhosphorIconsRegular.graduationCap;
    } else if (endpoint.contains('ratings')) {
      return PhosphorIconsRegular.star;
    } else if (endpoint.contains('progress')) {
      return PhosphorIconsRegular.trendUp;
    } else {
      return PhosphorIconsRegular.fileText;
    }
  }

  String _getReportDescription(ReportType reportType) {
    final filters = reportType.availableFilters;
    final filterDescriptions = <String>[];

    if (filters.students) filterDescriptions.add('Students');
    if (filters.teachers) filterDescriptions.add('Teachers');
    if (filters.classrooms) filterDescriptions.add('Classrooms');
    if (filters.subjects) filterDescriptions.add('Subjects');
    if (filters.extraCurriculars) filterDescriptions.add('Activities');
    if (filters.calendarYears) filterDescriptions.add('Years');

    final filterText = filterDescriptions.isNotEmpty
        ? 'Filter by: ${filterDescriptions.join(', ')}'
        : 'No filters required';

    return filterText;
  }
}