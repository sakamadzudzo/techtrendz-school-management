import 'dart:convert';
import 'package:shared_preferences/shared_preferences.dart';
import '../../data/models/user.dart';

class StorageService {
  static const String _tokenKey = 'auth_token';
  static const String _refreshTokenKey = 'refresh_token';
  static const String _userKey = 'user_data';
  static const String _themeKey = 'theme_mode';
  static const String _languageKey = 'language';

  Future<SharedPreferences> get _prefs async => await SharedPreferences.getInstance();

  // Token management
  Future<void> saveToken(String token) async {
    final prefs = await _prefs;
    await prefs.setString(_tokenKey, token);
  }

  Future<String?> getToken() async {
    final prefs = await _prefs;
    return prefs.getString(_tokenKey);
  }

  Future<void> saveRefreshToken(String refreshToken) async {
    final prefs = await _prefs;
    await prefs.setString(_refreshTokenKey, refreshToken);
  }

  Future<String?> getRefreshToken() async {
    final prefs = await _prefs;
    return prefs.getString(_refreshTokenKey);
  }

  Future<void> clearToken() async {
    final prefs = await _prefs;
    await prefs.remove(_tokenKey);
    await prefs.remove(_refreshTokenKey);
  }

  // User data management
  Future<void> saveUser(User user) async {
    final prefs = await _prefs;
    final userJson = jsonEncode(user.toJson());
    await prefs.setString(_userKey, userJson);
  }

  Future<User?> getUser() async {
    final prefs = await _prefs;
    final userJson = prefs.getString(_userKey);
    if (userJson != null) {
      try {
        final userMap = jsonDecode(userJson) as Map<String, dynamic>;
        return User.fromJson(userMap);
      } catch (e) {
        // If parsing fails, clear corrupted data
        await clearUser();
        return null;
      }
    }
    return null;
  }

  Future<void> clearUser() async {
    final prefs = await _prefs;
    await prefs.remove(_userKey);
  }

  // Theme management
  Future<void> saveThemeMode(String themeMode) async {
    final prefs = await _prefs;
    await prefs.setString(_themeKey, themeMode);
  }

  Future<String?> getThemeMode() async {
    final prefs = await _prefs;
    return prefs.getString(_themeKey);
  }

  // Language management
  Future<void> saveLanguage(String language) async {
    final prefs = await _prefs;
    await prefs.setString(_languageKey, language);
  }

  Future<String?> getLanguage() async {
    final prefs = await _prefs;
    return prefs.getString(_languageKey);
  }

  // General data storage
  Future<void> saveString(String key, String value) async {
    final prefs = await _prefs;
    await prefs.setString(key, value);
  }

  Future<String?> getString(String key) async {
    final prefs = await _prefs;
    return prefs.getString(key);
  }

  Future<void> saveBool(String key, bool value) async {
    final prefs = await _prefs;
    await prefs.setBool(key, value);
  }

  Future<bool?> getBool(String key) async {
    final prefs = await _prefs;
    return prefs.getBool(key);
  }

  Future<void> saveInt(String key, int value) async {
    final prefs = await _prefs;
    await prefs.setInt(key, value);
  }

  Future<int?> getInt(String key) async {
    final prefs = await _prefs;
    return prefs.getInt(key);
  }

  Future<void> saveDouble(String key, double value) async {
    final prefs = await _prefs;
    await prefs.setDouble(key, value);
  }

  Future<double?> getDouble(String key) async {
    final prefs = await _prefs;
    return prefs.getDouble(key);
  }

  Future<void> saveStringList(String key, List<String> value) async {
    final prefs = await _prefs;
    await prefs.setStringList(key, value);
  }

  Future<List<String>?> getStringList(String key) async {
    final prefs = await _prefs;
    return prefs.getStringList(key);
  }

  // Clear all data
  Future<void> clearAll() async {
    final prefs = await _prefs;
    await prefs.clear();
  }

  // Remove specific key
  Future<void> remove(String key) async {
    final prefs = await _prefs;
    await prefs.remove(key);
  }

  // Check if key exists
  Future<bool> containsKey(String key) async {
    final prefs = await _prefs;
    return prefs.containsKey(key);
  }

  // Get all keys
  Future<Set<String>> getKeys() async {
    final prefs = await _prefs;
    return prefs.getKeys();
  }
}