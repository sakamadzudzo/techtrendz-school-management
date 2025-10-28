class Validators {
  static String? validateEmail(String? value) {
    if (value == null || value.isEmpty) {
      return 'Email is required';
    }

    final emailRegex = RegExp(r'^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$');
    if (!emailRegex.hasMatch(value)) {
      return 'Please enter a valid email address';
    }

    return null;
  }

  static String? validateIsPresent(String? value, String? field) {
    if (value == null || value.isEmpty) {
      return '$field is required';
    }

    return null;
  }

  static String? validatePassword(String? value) {
    if (value == null || value.isEmpty) {
      return 'Password is required';
    }

    if (value.length < 8) {
      return 'Password must be at least 8 characters long';
    }

    // Check for at least one uppercase letter
    if (!RegExp(r'[A-Z]').hasMatch(value)) {
      return 'Password must contain at least one uppercase letter';
    }

    // Check for at least one lowercase letter
    if (!RegExp(r'[a-z]').hasMatch(value)) {
      return 'Password must contain at least one lowercase letter';
    }

    // Check for at least one digit
    if (!RegExp(r'[0-9]').hasMatch(value)) {
      return 'Password must contain at least one number';
    }

    return null;
  }

  static String? validateConfirmPassword(String? value, String? originalPassword) {
    if (value == null || value.isEmpty) {
      return 'Please confirm your password';
    }

    if (value != originalPassword) {
      return 'Passwords do not match';
    }

    return null;
  }

  static String? validateRequired(String? value, String fieldName) {
    if (value == null || value.trim().isEmpty) {
      return '$fieldName is required';
    }
    return null;
  }

  static String? validatePhone(String? value) {
    if (value == null || value.isEmpty) {
      return 'Phone number is required';
    }

    // Basic phone number validation (allows various formats)
    final phoneRegex = RegExp(r'^\+?[\d\s\-\(\)]{10,}$');
    if (!phoneRegex.hasMatch(value.replaceAll(RegExp(r'\s+'), ''))) {
      return 'Please enter a valid phone number';
    }

    return null;
  }

  static String? validateName(String? value, String fieldName) {
    if (value == null || value.trim().isEmpty) {
      return '$fieldName is required';
    }

    if (value.trim().length < 2) {
      return '$fieldName must be at least 2 characters long';
    }

    // Check for valid characters (letters, spaces, hyphens, apostrophes)
    final nameRegex = RegExp(r"^[a-zA-Z\s\-']+$");
    if (!nameRegex.hasMatch(value)) {
      return '$fieldName can only contain letters, spaces, hyphens, and apostrophes';
    }

    return null;
  }

  static String? validateNumeric(String? value, String fieldName) {
    if (value == null || value.isEmpty) {
      return '$fieldName is required';
    }

    final number = num.tryParse(value);
    if (number == null) {
      return '$fieldName must be a valid number';
    }

    return null;
  }

  static String? validateRange(String? value, String fieldName, num min, num max) {
    final numericValidation = validateNumeric(value, fieldName);
    if (numericValidation != null) {
      return numericValidation;
    }

    final number = num.parse(value!);
    if (number < min || number > max) {
      return '$fieldName must be between $min and $max';
    }

    return null;
  }

  static String? validateMinLength(String? value, String fieldName, int minLength) {
    if (value == null || value.length < minLength) {
      return '$fieldName must be at least $minLength characters long';
    }
    return null;
  }

  static String? validateMaxLength(String? value, String fieldName, int maxLength) {
    if (value != null && value.length > maxLength) {
      return '$fieldName must not exceed $maxLength characters';
    }
    return null;
  }

  static String? validateDate(String? value, String fieldName) {
    if (value == null || value.isEmpty) {
      return '$fieldName is required';
    }

    try {
      DateTime.parse(value);
    } catch (e) {
      return 'Please enter a valid date for $fieldName';
    }

    return null;
  }

  static String? validateFutureDate(String? value, String fieldName) {
    final dateValidation = validateDate(value, fieldName);
    if (dateValidation != null) {
      return dateValidation;
    }

    final date = DateTime.parse(value!);
    final now = DateTime.now();

    if (date.isBefore(DateTime(now.year, now.month, now.day))) {
      return '$fieldName must be today or in the future';
    }

    return null;
  }

  static String? validatePastDate(String? value, String fieldName) {
    final dateValidation = validateDate(value, fieldName);
    if (dateValidation != null) {
      return dateValidation;
    }

    final date = DateTime.parse(value!);
    final now = DateTime.now();

    if (date.isAfter(DateTime(now.year, now.month, now.day))) {
      return '$fieldName must be today or in the past';
    }

    return null;
  }
}