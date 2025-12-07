import 'dart:typed_data';
import 'dart:io';

import 'package:flutter/material.dart';
import 'package:path_provider/path_provider.dart';
import 'package:phosphor_flutter/phosphor_flutter.dart';
import 'package:printing/printing.dart';
import 'package:school_management_flutter/presentation/widgets/common/custom_app_bar.dart';
import 'package:school_management_flutter/presentation/widgets/common/custom_button.dart';

class ReportViewerScreen extends StatefulWidget {
  const ReportViewerScreen({super.key});

  @override
  State<ReportViewerScreen> createState() => _ReportViewerScreenState();
}

class _ReportViewerScreenState extends State<ReportViewerScreen> {
  late Uint8List reportData;
  late String filename;
  late String reportType;

  @override
  void didChangeDependencies() {
    super.didChangeDependencies();

    final arguments = ModalRoute.of(context)?.settings.arguments as Map<String, dynamic>?;
    if (arguments != null) {
      reportData = arguments['reportData'] as Uint8List;
      filename = arguments['filename'] as String;
      reportType = arguments['reportType'] as String;
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: CustomAppBar(
        title: 'Report Viewer',
        showBackButton: true,
        actions: [
          IconButton(
            icon: const Icon(PhosphorIconsRegular.share),
            onPressed: _shareReport,
          ),
          IconButton(
            icon: const Icon(PhosphorIconsRegular.download),
            onPressed: _downloadReport,
          ),
        ],
      ),
      body: Column(
        children: [
          // Report info header
          Container(
            padding: const EdgeInsets.all(16.0),
            color: Theme.of(context).primaryColor.withOpacity(0.1),
            child: Row(
              children: [
                Icon(
                  reportType == 'pdf' ? PhosphorIconsRegular.filePdf : PhosphorIconsRegular.fileXls,
                  size: 32,
                  color: Theme.of(context).primaryColor,
                ),
                const SizedBox(width: 12),
                Expanded(
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        filename,
                        style: Theme.of(context).textTheme.titleMedium,
                        maxLines: 1,
                        overflow: TextOverflow.ellipsis,
                      ),
                      Text(
                        '${reportType.toUpperCase()} Report • ${(reportData.length / 1024).round()} KB',
                        style: Theme.of(context).textTheme.bodySmall,
                      ),
                    ],
                  ),
                ),
              ],
            ),
          ),

          // Report viewer
          Expanded(
            child: reportType == 'pdf'
                ? _buildPdfViewer()
                : _buildExcelViewer(),
          ),

          // Action buttons
          Container(
            padding: const EdgeInsets.all(16.0),
            decoration: BoxDecoration(
              color: Theme.of(context).scaffoldBackgroundColor,
              border: Border(
                top: BorderSide(
                  color: Theme.of(context).dividerColor,
                  width: 1,
                ),
              ),
            ),
            child: Row(
              children: [
                Expanded(
                  child: CustomButton(
                    text: 'Print',
                    onPressed: _printReport,
                    leadingIcon: Icon(PhosphorIconsRegular.printer),
                  ),
                ),
                const SizedBox(width: 12),
                Expanded(
                  child: CustomButton(
                    text: 'Share',
                    onPressed: _shareReport,
                    leadingIcon: Icon(PhosphorIconsRegular.share),
                    variant: ButtonVariant.secondary,
                  ),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildPdfViewer() {
    return PdfPreview(
      build: (format) => reportData,
      allowPrinting: false,
      allowSharing: false,
      canChangeOrientation: false,
      canChangePageFormat: false,
      canDebug: false,
      scrollViewDecoration: const BoxDecoration(),
    );
  }

  Widget _buildExcelViewer() {
    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Icon(
            PhosphorIconsRegular.fileXls,
            size: 64,
            color: Theme.of(context).primaryColor,
          ),
          const SizedBox(height: 16),
          Text(
            'Excel Report Generated',
            style: Theme.of(context).textTheme.headlineSmall,
          ),
          const SizedBox(height: 8),
          Text(
            'The Excel file has been generated and is ready for download.',
            style: Theme.of(context).textTheme.bodyMedium,
            textAlign: TextAlign.center,
          ),
          const SizedBox(height: 24),
          CustomButton(
            text: 'Download Excel File',
            onPressed: _downloadReport,
            leadingIcon: Icon(PhosphorIconsRegular.download),
          ),
        ],
      ),
    );
  }

  Future<void> _printReport() async {
    if (reportType == 'pdf') {
      await Printing.layoutPdf(
        onLayout: (format) async => reportData,
      );
    } else {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Printing is only available for PDF reports'),
        ),
      );
    }
  }

  Future<void> _shareReport() async {
    try {
      final tempDir = await getTemporaryDirectory();
      final file = File('${tempDir.path}/$filename');
      await file.writeAsBytes(reportData);

      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Report saved to temporary directory. Sharing functionality to be implemented.'),
        ),
      );
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text('Failed to save report: $e'),
        ),
      );
    }
  }

  Future<void> _downloadReport() async {
    try {
      final tempDir = await getTemporaryDirectory();
      final file = File('${tempDir.path}/$filename');
      await file.writeAsBytes(reportData);

      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text('Report saved to: ${file.path}'),
          action: SnackBarAction(
            label: 'Open',
            onPressed: () async {
              // You could use a file opener package here
              // For now, just show the path
            },
          ),
        ),
      );
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text('Failed to download report: $e'),
        ),
      );
    }
  }
}