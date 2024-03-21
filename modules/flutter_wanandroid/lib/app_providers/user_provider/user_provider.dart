import 'package:flutter/cupertino.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_wanandroid/app_providers/third_provider/api_provider.dart';
import 'package:flutter_wanandroid/app_providers/user_provider/user_state.dart';
import 'package:flutter_wanandroid/net/dio_manager.dart';

class UserProvider extends Notifier<UserState> {
  @override
  UserState build() {
    return const UserState();
  }

  bool isCollected(int id) {
    return state.collectIds?.contains(id)?? false;
  }

  void getUserInfo() async{
    final api = await ref.read(apiProvider);
    api.getUserInfo().then((response) {
      if (response.isSuccess) {
        var data = response.data!;
        state = state.copyWith(
            coinCount: data.coinInfo.coinCount,
            rank: data.coinInfo.rank,
            id: data.userInfo.id,
            username: data.userInfo.username,
            collectIds: data.userInfo.collectIds);
      }else {
        debugPrint('getUserInfo error: errorMsg: ${response.errorMsg}, errorCode: ${response.errorCode}');
      }
    }).catchError((e) {
      debugPrint('getUserInfo error: $e');
    });
  }

  void logout() {
    DioManager.getInstance().deleteCookie();
    state = const UserState();
  }

  void removeCollect(int id) {
    final articles = state.collectIds?.where((element) => element!= id).toList();
    state = state.copyWith(collectIds: articles);
  }

  void addCollect(int id) {
    if (state.collectIds != null) {
      if (!state.collectIds!.contains(id)) {
        state = state.copyWith(collectIds: state.collectIds!..add(id));
      }
    }else {
      state = state.copyWith(collectIds: [id]);
    }
  }
}

final userProvider =
    NotifierProvider<UserProvider, UserState>(() => UserProvider());
