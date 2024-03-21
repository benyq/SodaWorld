import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_smart_dialog/flutter_smart_dialog.dart';
import 'package:flutter_wanandroid/app_providers/theme_provider/themes_provider.dart';
import 'package:flutter_wanandroid/generated/l10n.dart';
import 'package:flutter_wanandroid/net/api_response.dart';
import 'package:flutter_wanandroid/net/model/login_model.dart';
import 'package:flutter_wanandroid/ui/login/provider/login_provider.dart';

class LoginPage extends ConsumerStatefulWidget {
  const LoginPage({super.key});

  @override
  ConsumerState<ConsumerStatefulWidget> createState() => _LoginPageState();
}

class _LoginPageState extends ConsumerState<LoginPage> {
  late TextEditingController _usernameController;
  late TextEditingController _pwdController;
  bool isDark = false;

  @override
  void initState() {
    super.initState();
    SystemChrome.setSystemUIOverlayStyle(SystemUiOverlayStyle.light.copyWith(statusBarColor: Colors.blue));
    _usernameController = TextEditingController();
    _pwdController = TextEditingController();
  }

  @override
  void dispose() {
    super.dispose();
    _usernameController.dispose();
    _pwdController.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final loginState = ref.watch(authProvider);
    final themeModeState = ref.watch(themesProvider);
    ref.listen(authProvider, (previous, next) {
      if (next.isLoading) {
        _showLoading();
      } else {
        SmartDialog.dismiss();
      }
    });
    return Scaffold(
      body: ConstrainedBox(
        constraints: const BoxConstraints.expand(),
        child: Stack(
          children: [
            Positioned(
                child: Container(
              width: double.infinity,
              height: 300,
              alignment: Alignment.center,
              color: Colors.blue,
              child: Image.asset(
                width: 150,
                "assets/images/ic_logo.png",
                fit: BoxFit.cover,
                color: Colors.white,
              ),
            )),
            Positioned(
              top: 250,
              left: 20,
              right: 20,
              child: Container(
                padding: const EdgeInsets.symmetric(horizontal: 15),
                decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(5),
                    color: Colors.white,
                    boxShadow: [
                      BoxShadow(
                          color:
                              Theme.of(context).primaryColor.withOpacity(0.3),
                          blurRadius: 10.0)
                    ]),
                child: Column(
                  children: [
                    TextField(
                      controller: _usernameController,
                      decoration: InputDecoration(
                        labelText: S.of(context).username,
                        prefixIcon: const Icon(Icons.perm_identity),
                        prefixIconConstraints:
                          const BoxConstraints(minWidth: 35, maxWidth: 50),
                      ),
                      onChanged: _onTextChanged,
                      style: themeModeState.isDark? const TextStyle(color: Colors.black) : null,
                    ),
                    TextField(
                      controller: _pwdController,
                      decoration: InputDecoration(
                          labelText: S.of(context).password,
                          prefixIcon: const Icon(Icons.lock_outline, size: 28,),
                          prefixIconConstraints:
                              const BoxConstraints(minWidth: 35, maxWidth: 50),
                          suffixIcon: IconButton(icon: Icon(loginState.obscureText
                              ? Icons.visibility_off
                              : Icons.visibility, size: 24,), onPressed: _changeObscureText,),
                      suffixIconConstraints: const BoxConstraints(minWidth: 30, maxWidth: 30, maxHeight: 30)),
                      //密码模式
                      obscureText: loginState.obscureText,
                      onChanged: _onTextChanged,
                      style: themeModeState.isDark? const TextStyle(color: Colors.black) : null,
                    ),
                    const SizedBox(height: 40),
                    ElevatedButton(
                      onPressed: loginState.canLogin
                          ? () {
                              _login().then((value){
                                if(value.isSuccess){
                                  Navigator.of(context).pop();
                                }else {
                                  SmartDialog.showToast(value.errorMsg);
                                }
                              });
                            }
                          : null,
                      child: Text(S.of(context).login),
                    ),
                    const SizedBox(height: 10),
                  ],
                ),
              ),
            ),
            Positioned(
                top: 60,
                child: IconButton(
                    onPressed: () {
                      Navigator.of(context).pop();
                    },
                    icon: const Icon(
                      Icons.arrow_back,
                      color: Colors.white,
                    ))),
          ],
        ),
      ),
    );
  }

  void _showLoading() async {
    SmartDialog.showLoading();
  }

  Future<ApiResponse<LoginModel>> _login() {
    FocusScope.of(context).requestFocus(FocusNode()); // 关闭键盘
    final username = _usernameController.text;
    final password = _pwdController.text;
    return ref.read(authProvider.notifier).login(username, password);
  }

  void _onTextChanged(v) {
    final username = _usernameController.text;
    final password = _pwdController.text;
    if (username.isNotEmpty && password.isNotEmpty) {
      ref.read(authProvider.notifier).canLogin();
    }
  }

  void _changeObscureText() {
    ref.read(authProvider.notifier).changeObscureText();
  }
}
