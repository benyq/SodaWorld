import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class Themes {
  static Color lightColor = const Color(0xffededed);
  static Color darkColor = Colors.black;

  static final lightTheme = ThemeData(
      colorScheme: ColorScheme.light(
        background: lightColor
      ),
      // For light theming
      scaffoldBackgroundColor: Colors.grey.shade100,
      iconTheme: const IconThemeData(color: Colors.black),
      listTileTheme: const ListTileThemeData(iconColor: Colors.black),
      dividerColor: darkColor,
      appBarTheme: AppBarTheme(
        backgroundColor: lightColor,
        titleTextStyle: const TextStyle(color: Colors.black, fontSize: 18),
        centerTitle: true,
        iconTheme: const IconThemeData(color: Colors.black),
        toolbarTextStyle: const TextStyle(color: Colors.black),
        systemOverlayStyle: SystemUiOverlayStyle(
            systemNavigationBarColor: Colors.black,
            systemNavigationBarIconBrightness: Brightness.light,
            statusBarIconBrightness: Brightness.dark,
            statusBarBrightness: Brightness.light,
            statusBarColor: lightColor),
      ),
      bottomNavigationBarTheme: BottomNavigationBarThemeData(
          backgroundColor: lightColor,
          selectedItemColor: Colors.blue,
          unselectedItemColor: Colors.black),
      textTheme: Typography.blackCupertino);

  static final darkTheme = ThemeData(
      colorScheme: ColorScheme.light(
          background: darkColor
      ),
      // For dark theming
      scaffoldBackgroundColor: Colors.grey.shade900,
      iconTheme: const IconThemeData(color: Colors.white),
      listTileTheme: const ListTileThemeData(iconColor: Colors.white),
      dividerColor: lightColor,
      appBarTheme: AppBarTheme(
        backgroundColor: darkColor,
        titleTextStyle: const TextStyle(color: Colors.white, fontSize: 18),
        centerTitle: true,
        iconTheme: const IconThemeData(color: Colors.white),
        toolbarTextStyle: const TextStyle(color: Colors.white),
        systemOverlayStyle: SystemUiOverlayStyle(
            systemNavigationBarColor: Colors.black,
            systemNavigationBarIconBrightness: Brightness.light,
            statusBarIconBrightness: Brightness.light,
            statusBarBrightness: Brightness.dark,
            statusBarColor: darkColor),
      ),
      bottomNavigationBarTheme: BottomNavigationBarThemeData(
          backgroundColor: darkColor,
          selectedItemColor: Colors.blue,
          unselectedItemColor: Colors.white),
  textTheme: Typography.whiteCupertino);

  static SystemUiOverlayStyle getSystemUIOverlayStyle(bool isDark) {
    if (isDark) {
      return darkTheme.appBarTheme.systemOverlayStyle!;
    } else {
      return lightTheme.appBarTheme.systemOverlayStyle!;
    }
  }

  static Color getBackgroundColor(bool isDark) {
    if (isDark) {
      return darkColor;
    } else {
      return lightColor;
    }
  }
}
