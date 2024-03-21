import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:mmkv/mmkv.dart';


class LanguageModel {
  String language;
  String tag;

  LanguageModel(this.language, this.tag);

  LanguageModel copyWith(LanguageModel model) {
    return LanguageModel(model.language, model.tag);
  }
}

final languages = [LanguageModel("中文", "zh"), LanguageModel("English", "en")];


class LanguageNotifier extends Notifier<LanguageModel> {
  final mmkv = MMKV.defaultMMKV();

  @override
  LanguageModel build() {
    final defaultLanguage = mmkv.decodeString("language") ?? "en";
    return languages.firstWhere((element) => element.tag == defaultLanguage);
  }

  void setLanguage(LanguageModel model) {
    state = model;
    mmkv.encodeString('language', model.tag);
  }
}

final languageProvider = NotifierProvider<LanguageNotifier, LanguageModel>(
  () => LanguageNotifier(),
);