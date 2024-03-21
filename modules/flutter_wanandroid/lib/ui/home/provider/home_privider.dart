import 'package:flutter/cupertino.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_wanandroid/app_providers/third_provider/api_provider.dart';

import 'home_state.dart';

final homeStateProvider =
    NotifierProvider<HomeStateNotifier, HomeState>(() => HomeStateNotifier());

class HomeStateNotifier extends Notifier<HomeState> {

  int pageIndex = 0;

  @override
  HomeState build() {
    return const HomeState([], []);
  }

  Future<void> loadMore() async{
    getArticles();
  }

  Future<void> refresh() async {
    pageIndex = 0;
    final api = await ref.read(apiProvider);
    return Future.wait([api.getTopArticles().then((topArticles) {
      if (topArticles.isSuccess) {
        state = state.copyWith(articles: topArticles.data!);
      }
      return topArticles.isSuccess;
    }), api.getArticles(pageIndex).then((articles) {
      if (articles.isSuccess) {
        final data = articles.data!.datas ?? [];
        state = state.copyWith(articles: state.articles + data);
      }
      return articles.isSuccess;
    })]).then((value) {
      final r1 = value[0];
      final r2 = value[1];
      debugPrint("home refresh success: ${r1 && r2}");
    }, onError: (e){
      debugPrint("home refresh error: $e");
    });
  }

  void getBanner() async {
    final api = await ref.read(apiProvider);
    api.banner().then((value){
        if (value.isSuccess) {
          state = state.copyWith(banners: value.data!);
          pageIndex += 1;
        }
    }, onError: (e) {
      debugPrint("home _getBanner error: $e");
    });
  }

  void getTopArticles() async {
    final api = await ref.read(apiProvider);
    api.getTopArticles().then((value) {
      if (value.isSuccess) {
        state = state.copyWith(articles: value.data!);
      }
    }, onError: (e) {
      debugPrint("home getTopArticles error: $e");
    });
  }

  void getArticles() async{
    try{
      final api = await ref.read(apiProvider);
      final response = await api.getArticles(pageIndex);
      if (response.isSuccess) {
        final data = response.data!.datas ?? [];
        state = state.copyWith(articles: state.articles + data);
        pageIndex += 1;
      }
    }catch(e) {
      debugPrint("home getArticles error: $e");
    }
  }

}
