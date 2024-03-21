import 'package:equatable/equatable.dart';

class LoginState extends Equatable{
  bool isLoading;
  bool canLogin;
  bool obscureText;

  LoginState({this.isLoading = false, this.canLogin = false, this.obscureText = true});

  LoginState copyWith({
    bool? isLoading,
    bool? canLogin,
    bool? obscureText,
  }) {
    return LoginState(
      isLoading: isLoading?? this.isLoading,
      canLogin: canLogin?? this.canLogin,
      obscureText: obscureText?? this.obscureText,
    );
  }

  @override
  List<Object?> get props => [isLoading, canLogin, obscureText];
}