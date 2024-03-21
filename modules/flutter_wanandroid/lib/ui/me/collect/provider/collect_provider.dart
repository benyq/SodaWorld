import 'package:flutter/foundation.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_wanandroid/app_providers/third_provider/api_provider.dart';
import 'package:flutter_wanandroid/app_providers/user_provider/user_provider.dart';
import 'package:flutter_wanandroid/ui/me/collect/provider/collect_state.dart';

class CollectProvider extends AutoDisposeNotifier<CollectState> {
  CollectProvider();

  int _index = 0;

  Future<void> refresh() async {
    _index = 0;
    return getCollectList(refresh: true);
  }

  Future<void> getCollectList({bool refresh = false}) async {
    final api = await ref.read(apiProvider);
    api.getCollectArticles(_index).then((value) {
      if (value.isSuccess) {
        final data = value.data?.datas ?? [];
        state = state.copyWith(
            articles: refresh ? data : (state.articles + data),
            isEnd: value.data?.over ?? false);
        _index++;
      }
    }, onError: (e) {
      debugPrint("getCollectList page: $_index, error: $e");
    });
  }

  Future collectArticle(int id) async {
    final api = await ref.read(apiProvider);
    api.collectArticle(id).then((response) {
      if (response.errorCode == 0) {
        ref.read(userProvider.notifier).addCollect(id);
      } else {
        debugPrint(
            'collectArticle error: errorMsg: ${response.errorMsg}, errorCode: ${response.errorCode}');
      }
    });
  }

  Future deleteCollectedArticle(int id) async {
    final api = await ref.read(apiProvider);
    api.unCollectArticle(id).then((value) {
      if (value.errorCode == 0) {
        final articles =
            state.articles.where((element) => element.originId != id).toList();
        state = state.copyWith(articles: articles);
        debugPrint('deleteCollectedArticle articles: ${articles.length}');
        ref.read(userProvider.notifier).removeCollect(id);
      }
    }, onError: (e) {
      debugPrint('deleteCollectedArticle error: $e');
    });
  }

  @override
  CollectState build() {
    return const CollectState(articles: [], isEnd: false);
  }
}

final collectStateProvider =
    NotifierProvider.autoDispose<CollectProvider, CollectState>(() {
  return CollectProvider();
});
