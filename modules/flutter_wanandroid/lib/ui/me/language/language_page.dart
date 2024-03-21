import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:flutter_wanandroid/generated/l10n.dart';
import 'package:flutter_wanandroid/ui/me/language/provider/language_provider.dart';


class LanguagePage extends ConsumerStatefulWidget {
  const LanguagePage({super.key});

  @override
  ConsumerState<ConsumerStatefulWidget> createState() => _LanguagePageState();
}

class _LanguagePageState extends ConsumerState<LanguagePage> {

  int _currentIndex = 0;

  @override
  void initState() {
    super.initState();
    _currentIndex = languages.indexOf(ref.read(languageProvider));
  }

  @override
  Widget build(BuildContext context) {
    final languageState = ref.watch(languageProvider);
    return Scaffold(
      appBar: AppBar(
        title: Text(S.of(context).language),
        actions: [
          UnconstrainedBox(
            child: SizedBox(
                height: 35.h,
                child: ElevatedButton(
                  onPressed: () {
                    ref.read(languageProvider.notifier).setLanguage(languages[_currentIndex]);
                  },
                  child: Text(S.of(context).save),
                )),
          ),
          SizedBox(width: 15.w,)
        ],
      ),
      body: ListView.builder(
        itemCount: languages.length,
        itemBuilder: (context, index) {
          return ListTile(
            title: Text(languages[index].language),
            trailing: Icon(
                _currentIndex == index ? Icons.check : null),
            onTap: () {
              setState(() {
                _currentIndex = index;
              });
            },
          );
        },
      ),
    );
  }

}
