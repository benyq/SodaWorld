import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:flutter_wanandroid/app_providers/theme_provider/themes.dart';
import 'package:flutter_wanandroid/app_providers/theme_provider/themes_provider.dart';
import 'package:flutter_wanandroid/app_providers/user_provider/user_provider.dart';
import 'package:flutter_wanandroid/generated/l10n.dart';
import 'package:flutter_wanandroid/ui/login/login_page.dart';
import 'package:flutter_wanandroid/ui/me/about/about_page.dart';
import 'package:flutter_wanandroid/ui/me/collect/collect_page.dart';
import 'package:flutter_wanandroid/ui/me/language/language_page.dart';
import 'package:flutter_wanandroid/ui/me/language/provider/language_provider.dart';
import 'package:flutter_wanandroid/utils/navigate_util.dart';

class MePage extends ConsumerWidget {
  const MePage({super.key});

  final Color _darkColor = const Color(0x66000000);

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final themeModeState = ref.watch(themesProvider);
    final languageState = ref.watch(languageProvider);
    SystemChrome.setSystemUIOverlayStyle(
        Themes.getSystemUIOverlayStyle(themeModeState.isDark));
    final userState = ref.watch(userProvider);
    return Container(
      color: themeModeState.isDark
          ? const Color(0x00049fb6)
          : const Color(0xffededed),
      child: SingleChildScrollView(
        physics: const BouncingScrollPhysics(),
        child: Column(
          children: [
            GestureDetector(
              onTap: () {
                _checkLogin(context, ref);
              },
              child: Container(
                margin: EdgeInsets.only(top: 50.h, left: 15.w, right: 15.w),
                padding: EdgeInsets.all(15.w),
                width: double.infinity,
                alignment: Alignment.center,
                decoration: BoxDecoration(
                  borderRadius: BorderRadius.circular(15.r),
                  color: themeModeState.isDark ? _darkColor : Colors.white,
                ),
                child: Column(
                  children: [
                    Row(
                      children: [
                        Image.asset(
                          width: 80.w,
                          height: 80.w,
                          "assets/images/ic_logo.png",
                          fit: BoxFit.cover,
                        ),
                        SizedBox(
                          width: 30.w,
                        ),
                        Text(
                          userState.username.isNotEmpty
                              ? userState.username
                              : S.of(context).please_login,
                          style: TextStyle(fontSize: 16.sp),
                        )
                      ],
                    ),
                    SizedBox(height: 10.h),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceAround,
                      children: [
                        GestureDetector(
                            onTap: () {
                              _checkLogin(context, ref, page: const CollectPage());
                            },
                            child: _userInfoItem(
                                S.of(context).collect, userState.collects)),
                        GestureDetector(
                            onTap: () => _checkLogin(context, ref),
                            child: _userInfoItem(
                                S.of(context).coin, userState.coin)),
                        GestureDetector(
                            onTap: () => _checkLogin(context, ref),
                            child: _userInfoItem(
                                S.of(context).rank, userState.rank)),
                      ],
                    )
                  ],
                ),
              ),
            ),
            Container(
              margin: EdgeInsets.only(top: 15.h, left: 15.w, right: 15.w),
              padding: EdgeInsets.symmetric(horizontal: 15.w, vertical: 5.h),
              width: double.infinity,
              alignment: Alignment.center,
              decoration: BoxDecoration(
                color: themeModeState.isDark ? _darkColor : Colors.white,
              ),
              child: Column(
                children: [
                  _settingCell(
                      Icons.mode_night_rounded, S.of(context).dark_mode,
                      selected: themeModeState.isDark, switchAction: (value) {
                    ref.read(themesProvider.notifier).changeTheme(value);
                  }),
                  Divider(height: 1.h),
                  _settingCell(Icons.language, S.of(context).language,
                      content: languageState.language, onTap: () {
                    //页面跳转
                    navigateTo(context, const LanguagePage());
                  }),
                  Divider(height: 1.h),
                  _settingCell(Icons.info_rounded, S.of(context).about, onTap: () {
                        //页面跳转
                        navigateTo(context, const AboutPage());
                      })
                ],
              ),
            ),
            SizedBox(
              height: 20.h,
            ),
            ElevatedButton(
                onPressed: () {
                  if (userState.isLoggedIn) {
                    ref.read(userProvider.notifier).logout();
                  }
                },
                child: Text(S.of(context).exit_login)),
          ],
        ),
      ),
    );
  }

  Widget _userInfoItem(String key, String value) {
    return Column(
      children: [
        Text(key,
            style: TextStyle(
              fontSize: 16.sp,
            )),
        Text(value, style: TextStyle(fontSize: 16.sp)),
      ],
    );
  }

  Widget _settingCell(IconData icon, String title,
      {bool? selected,
      ValueChanged<bool>? switchAction,
      VoidCallback? onTap,
      String? content}) {
    return InkWell(
      onTap: onTap,
      child: SizedBox(
        height: 45.h,
        child: Row(children: [
          Icon(icon, size: 25.w),
          SizedBox(
            width: 10.w,
          ),
          Expanded(
            child: Text(title),
          ),
          if (content != null) Text(content),
          SizedBox(
            width: 10.w,
          ),
          selected == null
              ? const Icon(Icons.arrow_forward_ios_rounded)
              : CupertinoSwitch(
                  value: selected,
                  onChanged: (value) {
                    switchAction?.call(value);
                  }),
        ]),
      ),
    );
  }

  _checkLogin(BuildContext context, WidgetRef ref, {Widget? page}) {
    final userState = ref.read(userProvider);
    final themeModeState = ref.read(themesProvider);
    if (!userState.isLoggedIn) {
      navigateTo(context, const LoginPage()).then((value) {
        SystemChrome.setSystemUIOverlayStyle(
            Themes.getSystemUIOverlayStyle(themeModeState.isDark));
      });
    } else {
      if (page != null) {
        _navigateTo(context, themeModeState.isDark, page);
      }
    }
  }

  _navigateTo(BuildContext context, bool isDark, Widget page) {
    navigateTo(context, page).then((value) {
      SystemChrome.setSystemUIOverlayStyle(
          Themes.getSystemUIOverlayStyle(isDark));
    });
  }
}
