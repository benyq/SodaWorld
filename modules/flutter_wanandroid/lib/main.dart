import 'package:flutter/material.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:flutter_smart_dialog/flutter_smart_dialog.dart';
import 'package:flutter_wanandroid/app_providers/theme_provider/themes.dart';
import 'package:flutter_wanandroid/app_providers/theme_provider/themes_provider.dart';
import 'package:flutter_wanandroid/generated/l10n.dart';
import 'package:flutter_wanandroid/ui/splash_page.dart';
import 'package:mmkv/mmkv.dart';

import 'ui/me/language/provider/language_provider.dart';

void main() async {
  final rootDir = await MMKV.initialize();
  debugPrint("rootDir: $rootDir");
  runApp(const ProviderScope(child: MyApp()));
}

class MyApp extends ConsumerWidget {
  const MyApp({
    super.key,
  });

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final themeModeState = ref.watch(themesProvider);
    final languageState = ref.watch(languageProvider);
    return ScreenUtilInit(
      designSize: const Size(360, 690),
      splitScreenMode: true,
      builder: (context, child) {
        return MaterialApp(
          localizationsDelegates: const [
            GlobalMaterialLocalizations.delegate,
            GlobalWidgetsLocalizations.delegate,
            GlobalCupertinoLocalizations.delegate,
            S.delegate,
          ],
          supportedLocales: S.delegate.supportedLocales,
          locale: Locale(languageState.tag),
          debugShowMaterialGrid: false,
          theme: Themes.lightTheme,
          darkTheme: Themes.darkTheme,
          themeMode: themeModeState.mode,
          debugShowCheckedModeBanner: false,
          home: child,
          navigatorObservers: [FlutterSmartDialog.observer],
          builder: FlutterSmartDialog.init(),
        );
      },
        child: const SplashPage()
    );
  }
}
