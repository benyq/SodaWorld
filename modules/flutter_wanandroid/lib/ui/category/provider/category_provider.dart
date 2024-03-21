import 'package:flutter/material.dart';
import 'package:flutter_wanandroid/app_providers/third_provider/api_provider.dart';
import 'package:flutter_wanandroid/net/api_response.dart';
import 'package:flutter_wanandroid/net/model/article_model.dart';
import 'package:flutter_wanandroid/net/model/category_model.dart';
import 'package:flutter_wanandroid/net/page_model.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';

import 'category_state.dart';

part 'generated/category_provider.g.dart';

@riverpod
Future<List<CategoryModel>> category(CategoryRef ref) async{
  final api = await ref.read(apiProvider);
  final response = await api.categoryTree();
  if (response.isSuccess){
    return response.data!;
  }
  return [];
}


@riverpod
class CategoryNotifier extends _$CategoryNotifier {
  @override
  CategoryState build(int cid) {
    return const CategoryState([], false);
  }


  int _page = 0;

  Future<bool> refresh() async {
    _page = 0;
    state = const CategoryState([], false);
    final response = await _getCategoryArticles(cid);
    if (response == null) {
      return false;
    }
    if (response.isSuccess) {
      final data = response.data!;
      _page += 1;
      state = CategoryState(data.datas ?? [], data.over);
    } else {
      debugPrint(response.errorMsg);
      return false;
    }
    return true;
  }

  Future loadMore() async {
    final response = await _getCategoryArticles(cid);
    if (response == null) {
      return false;
    }
    if (response.isSuccess) {
      final data = response.data!;
      state =
          CategoryState(state.articles + (data.datas ?? []), data.over);
      _page += 1;
    } else {
      debugPrint(response.errorMsg);
      return false;
    }
    return true;
  }

  Future<ApiResponse<PageModel<ArticleModel>>?> _getCategoryArticles(
      int cid) async {
    try {
      final api = await ref.read(apiProvider);
      return api.getArticles(_page, cid: cid);
    } catch (e) {
      debugPrint(e.toString());
      return null;
    }
  }
}