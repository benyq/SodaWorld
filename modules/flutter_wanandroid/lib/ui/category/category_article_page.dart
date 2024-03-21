import 'package:easy_refresh/easy_refresh.dart';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:flutter_wanandroid/ui/article/article_page.dart';
import 'package:flutter_wanandroid/ui/category/provider/category_provider.dart';
import 'package:flutter_wanandroid/ui/home/article_item.dart';
import 'package:flutter_wanandroid/utils/navigate_util.dart';

class CategoryArticlePage extends ConsumerStatefulWidget {
  const CategoryArticlePage(this.cid, this.title, {super.key});

  final int cid;
  final String title;

  @override
  ConsumerState<ConsumerStatefulWidget> createState() => _CategoryArticlePageState();

}


class _CategoryArticlePageState extends ConsumerState<CategoryArticlePage> {

  @override
  Widget build(BuildContext context) {
    final cid = widget.cid;
    final categoryState = ref.watch(categoryNotifierProvider.call(cid));
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
        elevation: 0,
      ),
      body: EasyRefresh(
        header: const CupertinoHeader(),
        footer: const CupertinoFooter(
            processedDuration: Duration(milliseconds: 500)),
        callLoadOverOffset: 100.h,
        refreshOnStart: true,
        onLoad: categoryState.isEnd
            ? null
            : () async {
          final res = await ref
              .read(categoryNotifierProvider.call(cid).notifier)
              .loadMore();
          return res ? IndicatorResult.success : IndicatorResult.fail;
        },
        onRefresh: () async {
          final res = await ref
              .read(categoryNotifierProvider.call(cid).notifier)
              .refresh();
          return res ? IndicatorResult.success : IndicatorResult.fail;
        },
        child: ListView.builder(
          itemCount: categoryState.articles.length,
          itemBuilder: (context, index) {
            final data = categoryState.articles[index];
            return ArticleItem(data, onTap: (model){
              navigateTo(context, ArticlePage(url: data.link, title: data.title, articleId: data.id));
            },);
          },
        ),
      ),
    );
  }
}