class AppConfig {
  static const String appName = 'School Management';
  static const String appVersion = '1.0.0';
  static const String appBuildNumber = '1';

  // API Configuration
  static const String baseUrl = String.fromEnvironment(
    'BASE_URL',
    defaultValue: 'http://10.0.2.2:4002',
  );

  static const String apiVersion = '';

  // App Settings
  static const int connectTimeout = 30000; // 30 seconds
  static const int receiveTimeout = 30000; // 30 seconds
  static const int sendTimeout = 30000; // 30 seconds

  // Pagination
  static const int defaultPageSize = 20;
  static const int maxPageSize = 100;

  // Cache Settings
  static const Duration cacheDuration = Duration(hours: 1);
  static const int maxCacheSize = 100;

  // File Upload
  static const int maxFileSize = 10 * 1024 * 1024; // 10MB
  static const List<String> allowedImageTypes = [
    'image/jpeg',
    'image/png',
    'image/gif',
    'image/webp'
  ];

  // Report Settings
  static const int maxReportRecords = 10000;
  static const String defaultReportFormat = 'pdf';

  // Theme Settings
  static const bool enableDarkMode = true;
  static const bool enableSystemTheme = true;

  // Security Settings
  static const int sessionTimeoutMinutes = 60;
  static const bool enableBiometrics = false;
  static const bool requirePinForSensitiveData = false;
}