import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_wanandroid/app_providers/third_provider/api_provider.dart';
import 'package:flutter_wanandroid/app_providers/user_provider/user_provider.dart';
import 'package:flutter_wanandroid/net/api_response.dart';
import 'package:flutter_wanandroid/net/model/login_model.dart';

import 'login_state.dart';

class LoginProvider extends AutoDisposeNotifier<LoginState> {

  Future<ApiResponse<LoginModel>> login(String username, String password) async {
    state = state.copyWith(isLoading: true);
    final api = await ref.read(apiProvider);
    final response = await api.login(username, password);
    if (response.isSuccess) {
      ref.read(userProvider.notifier).getUserInfo();
    }
    state = state.copyWith(isLoading: false);
    return response;
  }

  void canLogin() {
    state = state.copyWith(canLogin: true);
  }

  void changeObscureText() {
    state = state.copyWith(obscureText:!state.obscureText);
  }

  @override
  build() {
    return LoginState();
  }
}


final authProvider = NotifierProvider.autoDispose<LoginProvider, LoginState>(() => LoginProvider());

