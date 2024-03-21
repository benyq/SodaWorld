import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_wanandroid/ui/main_page.dart';

class SplashPage extends StatefulWidget {
  const SplashPage({super.key});

  @override
  State<StatefulWidget> createState() => _SplashPageState();
}

class _SplashPageState extends State<SplashPage> {
  @override
  void initState() {
    Future.delayed(const Duration(seconds: 1), () {
      Navigator.of(context).pushAndRemoveUntil(MaterialPageRoute(builder: (context) {
        return const MainPage();
      }), (route)=> false);
    });
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    final color = Theme.of(context).backgroundColor;
    return Scaffold(
      appBar: AppBar(
        backgroundColor: color,
        elevation: 0,
        systemOverlayStyle: SystemUiOverlayStyle(
            statusBarIconBrightness: Brightness.dark,
            statusBarColor: color),
      ),
      backgroundColor: color,
      body: Center(child: Image.asset(
        width: 150,
        "assets/images/ic_logo.png",
        fit: BoxFit.cover,
        color: Colors.white,
      ),),
    );
  }
}
