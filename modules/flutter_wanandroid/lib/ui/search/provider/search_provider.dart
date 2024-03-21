import 'dart:async';
import 'dart:convert';

import 'package:flutter/cupertino.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_wanandroid/app_providers/third_provider/api_provider.dart';
import 'package:flutter_wanandroid/net/model/hot_word_model.dart';
import 'package:flutter_wanandroid/ui/search/provider/search_state.dart';
import 'package:mmkv/mmkv.dart';

const _searchHistoryKey = "searchHistory";

final searchProvider =
    NotifierProvider.autoDispose<SearchNotifier, SearchState>(
        () => SearchNotifier());
final hotKeyProvider =
    AsyncNotifierProvider.autoDispose<SearchHotKeyNotifier, List<HotWordModel>>(
        () => SearchHotKeyNotifier());
final searchHistoryProvider =
    AsyncNotifierProvider.autoDispose<SearchHistoryNotifier, List<String>>(
        () => SearchHistoryNotifier());

class SearchNotifier extends AutoDisposeNotifier<SearchState> {
  @override
  SearchState build() {
    return const SearchState(searchData: []);
  }

  int _page = 0;
  String _searchText = '';

  Future<void> search(String searchText, {bool refresh = true}) async {
    if (refresh) {
      _page = 0;
      _searchText = searchText;
      state = state.copyWith(searchData: [], isEnd: false, isSearching: true);
    }
    final api = await ref.read(apiProvider);
    final response = await api.searchArticles(_page, searchText);
    if (response.isSuccess) {
      _page++;
      final data = response.data?.datas ?? [];
      for (var element in data) {
        element.title = _removeHighLight(element.title);
      }
      state = state.copyWith(
          searchData: refresh ? data : state.searchData + data,
          isEnd: response.data!.over);
    }
    if (refresh) {
      ref.read(searchHistoryProvider.notifier).saveSearchHistory(searchText);
    }
  }

  Future loadMore() async {
    return search(_searchText, refresh: false);
  }

  void exitSearch() {
    state = state.copyWith(searchData: [], isSearching: false, isEnd: false);
  }

  String _removeHighLight(String text) {
    RegExp regex = RegExp(r"<em class='highlight'>(.*?)<\/em>");
    // 获取匹配到的内容
    Iterable<Match> matches = regex.allMatches(text);
    // 遍历匹配结果并替换标签
    String result = text;
    for (Match match in matches) {
      String highlightedText = match.group(1)!; // 获取匹配到的内容
      result = result.replaceFirst(match.group(0)!, highlightedText); // 替换标签为内容
    }
    return result;
  }

}

class SearchHotKeyNotifier
    extends AutoDisposeAsyncNotifier<List<HotWordModel>> {
  @override
  FutureOr<List<HotWordModel>> build() async {
    final api = await ref.read(apiProvider);
    final response = await api.hotKey();
    return response.data ?? [];
  }
}

class SearchHistoryNotifier extends AutoDisposeAsyncNotifier<List<String>> {
  @override
  FutureOr<List<String>> build() async {
    final mmkv = MMKV.defaultMMKV();
    final searchJson = mmkv.decodeString(_searchHistoryKey);
    if (searchJson != null) {
      dynamic jsonData = jsonDecode(searchJson);
      if (jsonData is List) {
        return jsonData.map((e) => e as String).toList();
      }
    }
    return [];
  }

  Future<void> saveSearchHistory(String searchText) async {
    final mmkv = MMKV.defaultMMKV();
    final searchHistory = state.value ?? [];
    searchHistory.remove(searchText);
    searchHistory.insert(0, searchText);
    if (searchHistory.length > 10) {
      searchHistory.removeLast();
    }
    mmkv.encodeString(_searchHistoryKey, jsonEncode(searchHistory));
    state = await AsyncValue.guard(() => Future(() {
          return searchHistory;
        }));
  }

  Future<void> cleanSearchHistory() async {
    final mmkv = MMKV.defaultMMKV();
    mmkv.removeValue(_searchHistoryKey);
    state = await AsyncValue.guard(() => Future(() {
          return [];
        }));
  }
}
