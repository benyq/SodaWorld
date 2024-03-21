import 'dart:io';

import 'package:cookie_jar/cookie_jar.dart';
import 'package:dio/dio.dart';
import 'package:dio_cookie_manager/dio_cookie_manager.dart';
import 'package:flutter_wanandroid/net/api_service.dart';
import 'package:path_provider/path_provider.dart';

class DioManager {
  static DioManager? _instance;

  static DioManager getInstance() {
    _instance ??= DioManager._internal();
    return _instance!;
  }

  DioManager._internal();
  final Dio _dio = Dio();
  late WanAndroidService _androidService;
  WanAndroidService get androidService => _androidService;

  late PersistCookieJar cookieJar;

  init() async {
    var logger = LogInterceptor(responseBody: true, requestBody: true);
    _dio.interceptors.add(logger);
    final Directory appDocDir = await getApplicationDocumentsDirectory();
    final String appDocPath = appDocDir.path;
    cookieJar = PersistCookieJar(
      ignoreExpires: true,
      storage: FileStorage("$appDocPath/.cookies/"),
    );
    _dio.interceptors.add(CookieManager(cookieJar));
    _androidService = WanAndroidService(_dio);
  }

  void deleteCookie() {
    cookieJar.deleteAll();
  }
}

WanAndroidService get androidService => DioManager.getInstance().androidService;
