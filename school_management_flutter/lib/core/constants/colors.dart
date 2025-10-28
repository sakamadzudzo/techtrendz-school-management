import 'package:flutter/material.dart';

class AppColors {
  // Primary Colors
  static const Color primary = Color(0xFF1976D2);
  static const Color primaryLight = Color(0xFF42A5F5);
  static const Color primaryDark = Color(0xFF1565C0);
  static const Color primaryVariant = Color(0xFF0D47A1);

  // Secondary Colors
  static const Color secondary = Color(0xFFDC004E);
  static const Color secondaryLight = Color(0xFFFF5983);
  static const Color secondaryDark = Color(0xFF9A0036);
  static const Color secondaryVariant = Color(0xFFC2185B);

  // Accent Colors
  static const Color accent = Color(0xFFFFC107);
  static const Color accentLight = Color(0xFFFFF350);
  static const Color accentDark = Color(0xFFC79100);

  // Background Colors
  static const Color background = Color(0xFFFAFAFA);
  static const Color surface = Color(0xFFFFFFFF);
  static const Color cardBackground = Color(0xFFFFFFFF);

  // Text Colors
  static const Color textPrimary = Color(0xFF212121);
  static const Color textSecondary = Color(0xFF757575);
  static const Color textHint = Color(0xFFBDBDBD);
  static const Color textDisabled = Color(0xFF9E9E9E);

  // Status Colors
  static const Color success = Color(0xFF4CAF50);
  static const Color successLight = Color(0xFF81C784);
  static const Color successDark = Color(0xFF388E3C);

  static const Color error = Color(0xFFF44336);
  static const Color errorLight = Color(0xFFEF5350);
  static const Color errorDark = Color(0xFFC62828);

  static const Color warning = Color(0xFFFF9800);
  static const Color warningLight = Color(0xFFFFB74D);
  static const Color warningDark = Color(0xFFEF6C00);

  static const Color info = Color(0xFF2196F3);
  static const Color infoLight = Color(0xFF64B5F6);
  static const Color infoDark = Color(0xFF1976D2);

  // Border Colors
  static const Color border = Color(0xFFE0E0E0);
  static const Color borderLight = Color(0xFFF5F5F5);
  static const Color borderDark = Color(0xFFBDBDBD);

  // Shadow Colors
  static const Color shadow = Color(0x1F000000);
  static const Color shadowLight = Color(0x0F000000);
  static const Color shadowDark = Color(0x3F000000);

  // Transparent Colors
  static const Color transparent = Color(0x00000000);
  static const Color overlay = Color(0x80000000);

  // Grade Colors (for academic performance)
  static const Color gradeA = Color(0xFF4CAF50); // Green
  static const Color gradeB = Color(0xFF8BC34A); // Light Green
  static const Color gradeC = Color(0xFFFFC107); // Amber
  static const Color gradeD = Color(0xFFFF9800); // Orange
  static const Color gradeF = Color(0xFFF44336); // Red

  // Role-based Colors
  static const Color adminColor = Color(0xFF9C27B0); // Purple
  static const Color teacherColor = Color(0xFF3F51B5); // Indigo
  static const Color staffColor = Color(0xFF009688); // Teal
  static const Color parentColor = Color(0xFFFF5722); // Deep Orange

  // Subject Colors
  static const Color mathColor = Color(0xFF2196F3); // Blue
  static const Color scienceColor = Color(0xFF4CAF50); // Green
  static const Color englishColor = Color(0xFFFF9800); // Orange
  static const Color historyColor = Color(0xFF9C27B0); // Purple
  static const Color artColor = Color(0xFFE91E63); // Pink

  // Activity Colors
  static const Color sportsColor = Color(0xFF4CAF50); // Green
  static const Color musicColor = Color(0xFF9C27B0); // Purple
  static const Color dramaColor = Color(0xFFFF5722); // Deep Orange
  static const Color clubColor = Color(0xFF3F51B5); // Indigo

  // Dark Theme Colors
  static const Color darkBackground = Color(0xFF121212);
  static const Color darkSurface = Color(0xFF1E1E1E);
  static const Color darkCardBackground = Color(0xFF2D2D2D);
  static const Color darkTextPrimary = Color(0xFFFFFFFF);
  static const Color darkTextSecondary = Color(0xFFB3B3B3);
  static const Color darkBorder = Color(0xFF404040);

  // Utility methods
  static Color getGradeColor(String grade) {
    switch (grade.toUpperCase()) {
      case 'A':
        return gradeA;
      case 'B':
        return gradeB;
      case 'C':
        return gradeC;
      case 'D':
        return gradeD;
      case 'F':
        return gradeF;
      default:
        return textSecondary;
    }
  }

  static Color getRoleColor(String role) {
    switch (role.toLowerCase()) {
      case 'admin':
        return adminColor;
      case 'teacher':
        return teacherColor;
      case 'staff':
        return staffColor;
      case 'parent':
        return parentColor;
      default:
        return primary;
    }
  }

  static Color getSubjectColor(String subject) {
    switch (subject.toLowerCase()) {
      case 'mathematics':
      case 'math':
        return mathColor;
      case 'science':
      case 'physics':
      case 'chemistry':
      case 'biology':
        return scienceColor;
      case 'english':
      case 'literature':
        return englishColor;
      case 'history':
      case 'social studies':
        return historyColor;
      case 'art':
      case 'music':
      case 'drama':
        return artColor;
      default:
        return primary;
    }
  }

  static Color getActivityColor(String activity) {
    if (activity.toLowerCase().contains('sport')) {
      return sportsColor;
    } else if (activity.toLowerCase().contains('music')) {
      return musicColor;
    } else if (activity.toLowerCase().contains('drama') ||
               activity.toLowerCase().contains('theater')) {
      return dramaColor;
    } else if (activity.toLowerCase().contains('club')) {
      return clubColor;
    } else {
      return secondary;
    }
  }
}