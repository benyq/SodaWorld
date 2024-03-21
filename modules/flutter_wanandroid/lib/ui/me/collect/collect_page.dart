import 'package:easy_refresh/easy_refresh.dart';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:flutter_wanandroid/generated/l10n.dart';
import 'package:flutter_wanandroid/net/model/collect_article_model.dart';
import 'package:flutter_wanandroid/ui/article/article_page.dart';
import 'package:flutter_wanandroid/utils/navigate_util.dart';

import 'provider/collect_provider.dart';

class CollectPage extends ConsumerStatefulWidget {
  const CollectPage({super.key});

  @override
  ConsumerState<ConsumerStatefulWidget> createState() => _CollectPageState();
}

class _CollectPageState extends ConsumerState<CollectPage> {
  late EasyRefreshController _controller;

  @override
  void initState() {
    super.initState();
    _controller = EasyRefreshController(
        controlFinishLoad: true, controlFinishRefresh: true);
    ref.read(collectStateProvider.notifier).refresh();
  }

  @override
  void dispose() {
    super.dispose();
    _controller.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final collectState = ref.watch(collectStateProvider);
    return Scaffold(
      appBar: AppBar(
        title: Text(S.of(context).collect),
      ),
      body: EasyRefresh(
        controller: _controller,
        header: const CupertinoHeader(),
        footer: const CupertinoFooter(processedDuration: Duration(milliseconds: 500)),
        callLoadOverOffset: 100.h,
        onRefresh: () async {
          await ref.read(collectStateProvider.notifier).refresh();
          _controller.finishRefresh();
        },
        onLoad: collectState.isEnd
            ? null
            : () async {
                await ref.read(collectStateProvider.notifier).getCollectList();
                _controller.finishLoad();
              },
        child: Padding(
          padding: EdgeInsets.symmetric(vertical: 15.h),
          child: ListView.builder(
              itemBuilder: (context, index) =>
                  _articleItem(context, collectState.articles[index]),
              itemCount: collectState.articles.length),
        ),
      ),
    );
  }

  Widget _articleItem(BuildContext context, CollectArticleModel model) {
    return GestureDetector(
      behavior: HitTestBehavior.opaque,
      onTap: () {
        navigateTo(context, ArticlePage(title: model.title, url: model.link, articleId: model.originId,));
      },
      onLongPress: (){
        showDialog(context: context, builder: (context){
          return AlertDialog(
            title: Text(S.of(context).tips),
            content: Text(S.of(context).delete_article),
            actions: [
              TextButton(
                child: Text(S.of(context).cancel),
                onPressed: () => Navigator.of(context).pop(),
              ),
              TextButton(
                child: Text(S.of(context).confirm),
                onPressed: () {
                  Navigator.of(context).pop();
                  ref.read(collectStateProvider.notifier).deleteCollectedArticle(model.originId);
                },
              ),
            ],
          );
        });
      },
      child: Container(
        margin: EdgeInsets.only(left: 10.w, right: 10.w, bottom: 10.h),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              children: [
                Text('${S.of(context).author} : ${model.author}'),
                const Spacer(),
                Text(model.niceDate),
              ],
            ),
            SizedBox(height: 5.h),
            Text(model.title, maxLines: 2, overflow: TextOverflow.ellipsis),
            SizedBox(height: 5.h),
            Row(
              children: [
                Expanded(child: Text(model.chapterName, style: TextStyle(fontSize: 12.sp), overflow: TextOverflow.ellipsis, maxLines: 1,)),
                const Icon(Icons.favorite)
              ],
            ),
            SizedBox(height: 10.h),
            Divider(height: 1.h),
          ],
        ),
      ),
    );
  }
}
