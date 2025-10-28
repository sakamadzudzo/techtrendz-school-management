import 'package:flutter/foundation.dart';

enum Environment { development, staging, production }

class EnvironmentConfig {
  static Environment _currentEnvironment = Environment.development;

  static Environment get currentEnvironment => _currentEnvironment;

  static void setEnvironment(Environment environment) {
    _currentEnvironment = environment;
  }

  static bool get isDevelopment => _currentEnvironment == Environment.development;
  static bool get isStaging => _currentEnvironment == Environment.staging;
  static bool get isProduction => _currentEnvironment == Environment.production;

  static String get baseUrl {
    switch (_currentEnvironment) {
      case Environment.development:
        return const String.fromEnvironment(
          'DEV_BASE_URL',
          defaultValue: 'http://10.0.2.2:4002',
        );
      case Environment.staging:
        return const String.fromEnvironment(
          'STAGING_BASE_URL',
          defaultValue: 'https://api-staging.schoolmanagement.com',
        );
      case Environment.production:
        return const String.fromEnvironment(
          'PROD_BASE_URL',
          defaultValue: 'https://api.schoolmanagement.com',
        );
    }
  }

  static String get apiVersion => '';

  static String get fullBaseUrl => baseUrl;

  static bool get enableLogging => !isProduction;

  static bool get enableCrashReporting => isProduction;

  static bool get enableAnalytics => isProduction;

  static String get environmentName {
    switch (_currentEnvironment) {
      case Environment.development:
        return 'Development';
      case Environment.staging:
        return 'Staging';
      case Environment.production:
        return 'Production';
    }
  }

  static void initialize() {
    // Load environment from system properties or environment variables
    const environment = String.fromEnvironment('ENVIRONMENT', defaultValue: 'development');

    switch (environment.toLowerCase()) {
      case 'staging':
        _currentEnvironment = Environment.staging;
        break;
      case 'production':
        _currentEnvironment = Environment.production;
        break;
      case 'development':
      default:
        _currentEnvironment = Environment.development;
        break;
    }

    if (kDebugMode) {
      debugPrint('Environment initialized: $environmentName');
      debugPrint('Base URL: $baseUrl');
    }
  }
}