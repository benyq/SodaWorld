import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_wanandroid/app_providers/third_provider/api_provider.dart';
import 'package:flutter_wanandroid/net/api_response.dart';
import 'package:flutter_wanandroid/net/model/article_model.dart';
import 'package:flutter_wanandroid/net/model/project_tree_model.dart';
import 'package:flutter_wanandroid/net/page_model.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';
import 'projectState.dart';

part 'generated/project_provider.g.dart';

@riverpod
class ProjectNotifier extends _$ProjectNotifier {
  @override
  ProjectState build(int cid) {
    return const ProjectState([], false);
  }

  int _page = 0;

  Future<bool> refresh() async {
    _page = 0;
    state = const ProjectState([], false);
    final response = await _getProjectArticles(cid);
    if (response == null) {
      return false;
    }
    if (response.isSuccess) {
      final data = response.data!;
      _page += 1;
      state = ProjectState(data.datas ?? [], data.over);
    } else {
      debugPrint(response.errorMsg);
      return false;
    }
    return true;
  }

  Future loadMore() async {
    final response = await _getProjectArticles(cid);
    if (response == null) {
      return false;
    }
    if (response.isSuccess) {
      final data = response.data!;
      state =
          ProjectState(state.projectArticles + (data.datas ?? []), data.over);
      _page += 1;
    } else {
      debugPrint(response.errorMsg);
      return false;
    }
    return true;
  }

  Future<ApiResponse<PageModel<ArticleModel>>?> _getProjectArticles(
      int cid) async {
    try {
      final api = await ref.read(apiProvider);
      return api.getProjectArticles(_page, cid);
    } catch (e) {
      debugPrint(e.toString());
      return null;
    }
  }
}

@riverpod
Future<List<ProjectTreeModel>> projectTree(ProjectTreeRef ref) async {
  final api = await ref.read(apiProvider);
  final response = await api.projectTree();
  return response.isSuccess ? response.data! : [];
}
