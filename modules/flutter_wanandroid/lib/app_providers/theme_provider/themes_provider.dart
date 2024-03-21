import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_wanandroid/app_providers/theme_provider/theme_state.dart';
import 'package:mmkv/mmkv.dart';

final themesProvider = NotifierProvider<ThemesProvider, ThemeState>(() {
  return ThemesProvider();
});

const _darkModeKey = "darkMode";

class ThemesProvider extends Notifier<ThemeState> {

  void changeTheme(bool isDark) {
    final mode = isDark ? ThemeMode.dark : ThemeMode.light;
    state = state.copyWith(mode: mode);
    MMKV.defaultMMKV().encodeBool(_darkModeKey, isDark);
  }

  @override
  ThemeState build() {
    final isDark = MMKV.defaultMMKV().decodeBool(_darkModeKey, defaultValue: false);
    return ThemeState(mode: isDark? ThemeMode.dark : ThemeMode.light);
  }
}