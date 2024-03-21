import 'dart:ffi';

import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:flutter_smart_dialog/flutter_smart_dialog.dart';
import 'package:flutter_wanandroid/app_providers/third_provider/api_provider.dart';
import 'package:flutter_wanandroid/generated/l10n.dart';
import 'package:package_info_plus/package_info_plus.dart';
import 'package:url_launcher/url_launcher.dart';

class AboutPage extends ConsumerStatefulWidget {
  const AboutPage({super.key});

  @override
  ConsumerState<ConsumerStatefulWidget> createState() => _AboutPageState();
}

class _AboutPageState extends ConsumerState<AboutPage> {
  PackageInfo _packageInfo = PackageInfo(
    appName: '',
    packageName: 'Unknown',
    version: '',
    buildNumber: 'Unknown',
    buildSignature: 'Unknown',
    installerStore: 'Unknown',
  );

  final _appDescription =
      'flutter版本的的玩Android App，使用Flutter开发，使用了Riverpod进行状态管理和主题管理，使用了ScreenUtil进行屏幕适配，使用了Dio+Retrofit进行网络请求，使用了mmkv进行本地缓存管理。';

  @override
  void initState() {
    super.initState();
    _initPackageInfo();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(S.of(context).about),
      ),
      body: Column(
        children: [
          SizedBox(
            width: double.infinity,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                SizedBox(height: 10.h),
                Center(
                    child: Text(
                  '玩 Android',
                  style: TextStyle(fontSize: 20.sp),
                )),
                SizedBox(height: 10.h),
                Container(
                  padding: EdgeInsets.symmetric(horizontal: 10.w),
                  child: Text(_appDescription),
                ),
              ],
            ),
          ),
          SizedBox(height: 10.h),
          _settingCell(S.of(context).check_version,
              content: _packageInfo.version, onTap: () {
            _checkUpdate(S.of(context).check_version_loading);
          }),
          Padding(
            padding: EdgeInsets.symmetric(horizontal: 10.w),
            child: Divider(height: 1.h),
          ),
          _settingCell(S.of(context).project, content: '', onTap: () {
            //https://github.com/benyq/flutter_wanandroid.git
            launchUrl(Uri.parse('https://github.com/benyq/flutter_wanandroid'));
          })
        ],
      ),
    );
  }

  Future<void> _initPackageInfo() async {
    final info = await PackageInfo.fromPlatform();
    debugPrint("$info");
    setState(() {
      _packageInfo = info;
    });
  }

  Widget _settingCell(String title, {VoidCallback? onTap, String? content}) {
    return InkWell(
      onTap: onTap,
      child: SizedBox(
        height: 45.h,
        child: Row(children: [
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
          const Icon(Icons.arrow_forward_ios_rounded)
        ]),
      ),
    );
  }

  void _checkUpdate(String msg) async {
    final api = await ref.read(apiProvider);
    SmartDialog.showLoading();
    api.queryGithubRelease().then((value) {
      final model = value[0];
      var version = model.tagName.replaceAll('v', '');
      SmartDialog.dismiss();
      if (_checkVersion(version, _packageInfo.version)) {
        debugPrint('新版本: $version');
        const tag = 'confirmUpdateTag';
        SmartDialog.show(
            clickMaskDismiss: false,
            tag: tag,
            builder: (context) {
              return Container(
                height: 230,
                width: 260,
                decoration: BoxDecoration(
                  color: Colors.white,
                  borderRadius: BorderRadius.circular(10),
                ),
                alignment: Alignment.center,
                child: Column(
                  children: [
                    SizedBox(height: 10.h),
                    Text('Tips'),
                    Expanded(child: Center(child: Text(model.body))),
                    Container(
                      width: double.infinity,
                      child: Wrap(
                        alignment: WrapAlignment.spaceAround,
                        children: [
                          ElevatedButton(
                              onPressed: () {
                                SmartDialog.dismiss(tag: tag);
                              },
                              child: Text(S.of(context).close)),
                          ElevatedButton(
                              onPressed: () {
                                SmartDialog.dismiss(tag: tag);
                                launchUrl(Uri.parse(
                                    'https://github.com/benyq/flutter_wanandroid/releases'));
                              },
                              child: Text(S.of(context).update_app)),
                        ],
                      ),
                    ),
                    SizedBox(height: 10.h),
                  ],
                ),
              );
            });
      } else {
        debugPrint('无新版本');
        SmartDialog.showToast(S.of(context).update_app_fail_version);
      }
    }, onError: (e) {
      SmartDialog.dismiss();
      SmartDialog.showToast('$e');
      debugPrint('_checkUpdate error: $e');
    });
  }

  bool _checkVersion(String version1, String version2) {
    var v1 = int.parse(version1.replaceAll('.', ''));
    var v2 = int.parse(version2.replaceAll('.', ''));
    return v1 > v2;
  }
}
