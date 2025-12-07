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

class ReportFiltersScreen extends StatefulWidget {
  const ReportFiltersScreen({super.key});

  @override
  State<ReportFiltersScreen> createState() => _ReportFiltersScreenState();
}

class _ReportFiltersScreenState extends State<ReportFiltersScreen> {
  String _selectedReportType = 'pdf'; // Default to PDF

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: const CustomAppBar(
        title: 'Report Filters',
        showBackButton: true,
      ),
      body: BlocBuilder<ReportBloc, ReportState>(
        builder: (context, state) {
          if (state is ReportTypeSelected) {
            return _buildFiltersContent(state);
          }

          if (state is ReportFilterUpdated) {
            return _buildFiltersContent(state);
          }

          if (state is ReportGenerating) {
            return const Center(
              child: CircularProgressIndicator(),
            );
          }

          if (state is ReportGenerated) {
            // Navigate to viewer
            WidgetsBinding.instance.addPostFrameCallback((_) {
              Navigator.pushNamed(
                context,
                '/reports/viewer',
                arguments: {
                  'reportData': state.reportData,
                  'filename': state.filename,
                  'reportType': state.reportType,
                },
              );
            });
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
                    'Error',
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
                    text: 'Back to Reports',
                    onPressed: () {
                      Navigator.pop(context);
                    },
                  ),
                ],
              ),
            );
          }

          return const Center(
            child: Text('No report type selected'),
          );
        },
      ),
    );
  }

  Widget _buildFiltersContent(dynamic state) {
    final filters = state.filters;
    final reportType = state.selectedReportType;
    final currentFilter = state.currentFilter;

    return Padding(
      padding: const EdgeInsets.all(16.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            reportType.title,
            style: Theme.of(context).textTheme.headlineMedium,
          ),
          const SizedBox(height: 8),
          Text(
            'Configure filters for this report',
            style: Theme.of(context).textTheme.bodyMedium,
          ),
          const SizedBox(height: 24),

          // Report Type Selection
          Card(
            child: Padding(
              padding: const EdgeInsets.all(16.0),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    'Report Format',
                    style: Theme.of(context).textTheme.titleMedium,
                  ),
                  const SizedBox(height: 8),
                  Row(
                    children: [
                      Expanded(
                        child: RadioListTile<String>(
                          title: const Text('PDF'),
                          value: 'pdf',
                          groupValue: _selectedReportType,
                          onChanged: (value) {
                            setState(() {
                              _selectedReportType = value!;
                            });
                          },
                        ),
                      ),
                      Expanded(
                        child: RadioListTile<String>(
                          title: const Text('Excel'),
                          value: 'excel',
                          groupValue: _selectedReportType,
                          onChanged: (value) {
                            setState(() {
                              _selectedReportType = value!;
                            });
                          },
                        ),
                      ),
                    ],
                  ),
                ],
              ),
            ),
          ),

          const SizedBox(height: 16),

          // Filters
          Expanded(
            child: Card(
              child: Padding(
                padding: const EdgeInsets.all(16.0),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      'Filters',
                      style: Theme.of(context).textTheme.titleMedium,
                    ),
                    const SizedBox(height: 16),
                    Expanded(
                      child: SingleChildScrollView(
                        child: Column(
                          children: _buildFilterWidgets(filters, reportType, currentFilter),
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ),
          ),

          const SizedBox(height: 16),

          // Generate Button
          SizedBox(
            width: double.infinity,
            child: CustomButton(
              text: 'Generate Report',
              onPressed: () {
                final filter = currentFilter.copyWith(type: _selectedReportType);
                context.read<ReportBloc>().add(GenerateReport(filter));
              },
            ),
          ),
        ],
      ),
    );
  }

  List<Widget> _buildFilterWidgets(
    ReportFilters filters,
    ReportType reportType,
    ReportFilter currentFilter,
  ) {
    final widgets = <Widget>[];

    final availableFilters = reportType.availableFilters;

    if (availableFilters.students) {
      widgets.add(_buildDropdownFilter(
        label: 'Student',
        placeholder: 'Select student...',
        options: filters.students ?? [],
        value: currentFilter.studentId,
        onChanged: (value) {
          context.read<ReportBloc>().add(UpdateReportFilter('studentId', value));
        },
      ));
    }

    if (availableFilters.teachers) {
      widgets.add(_buildDropdownFilter(
        label: 'Teacher',
        placeholder: 'Select teacher...',
        options: filters.teachers ?? [],
        value: currentFilter.teacherId,
        onChanged: (value) {
          context.read<ReportBloc>().add(UpdateReportFilter('teacherId', value));
        },
      ));
    }

    if (availableFilters.courseworks) {
      widgets.add(_buildDropdownFilter(
        label: 'Coursework',
        placeholder: 'Select coursework...',
        options: filters.courseworks ?? [],
        value: currentFilter.courseworkId,
        onChanged: (value) {
          context.read<ReportBloc>().add(UpdateReportFilter('courseworkId', value));
        },
        displayField: 'type',
      ));
    }

    if (availableFilters.classrooms) {
      widgets.add(_buildDropdownFilter(
        label: 'Classroom',
        placeholder: 'Select classroom...',
        options: filters.classrooms ?? [],
        value: currentFilter.classroomId,
        onChanged: (value) {
          context.read<ReportBloc>().add(UpdateReportFilter('classroomId', value));
        },
      ));
    }

    if (availableFilters.subjects) {
      widgets.add(_buildDropdownFilter(
        label: 'Subject',
        placeholder: 'Select subject...',
        options: filters.subjects ?? [],
        value: currentFilter.subjectId,
        onChanged: (value) {
          context.read<ReportBloc>().add(UpdateReportFilter('subjectId', value));
        },
      ));
    }

    if (availableFilters.extraCurriculars) {
      widgets.add(_buildDropdownFilter(
        label: 'Extra Curricular',
        placeholder: 'Select activity...',
        options: filters.extraCurriculars ?? [],
        value: currentFilter.extraCurricularId,
        onChanged: (value) {
          context.read<ReportBloc>().add(UpdateReportFilter('extraCurricularId', value));
        },
      ));
    }

    if (availableFilters.calendarYears) {
      widgets.add(_buildDropdownFilter(
        label: 'Year',
        placeholder: 'Select year...',
        options: filters.calendarYears?.map((year) => YearDropdown(
          id: year,
          name: year.toString(),
          calendarYear: year,
        )).toList() ?? [],
        value: currentFilter.year,
        onChanged: (value) {
          context.read<ReportBloc>().add(UpdateReportFilter('year', value));
        },
      ));
    }

    if (widgets.isEmpty) {
      widgets.add(
        const Center(
          child: Padding(
            padding: EdgeInsets.all(32.0),
            child: Text('No filters required for this report type'),
          ),
        ),
      );
    }

    return widgets;
  }

  Widget _buildDropdownFilter({
    required String label,
    required String placeholder,
    required List<dynamic> options,
    required int? value,
    required Function(int) onChanged,
    String displayField = 'name',
  }) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 16.0),
      child: DropdownButtonFormField<int>(
        decoration: InputDecoration(
          labelText: label,
          border: const OutlineInputBorder(),
        ),
        hint: Text(placeholder),
        value: value,
        items: options.map((option) {
          return DropdownMenuItem<int>(
            value: option.id,
            child: Text(displayField == 'type' ? option.subject.name : option.name),
          );
        }).toList(),
        onChanged: (newValue) {
          if (newValue != null) {
            onChanged(newValue);
          }
        },
      ),
    );
  }
}