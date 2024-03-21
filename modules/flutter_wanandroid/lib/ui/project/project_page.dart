import 'package:cached_network_image/cached_network_image.dart';
import 'package:easy_refresh/easy_refresh.dart';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:flutter_wanandroid/app_providers/theme_provider/themes.dart';
import 'package:flutter_wanandroid/app_providers/theme_provider/themes_provider.dart';
import 'package:flutter_wanandroid/net/model/article_model.dart';
import 'package:flutter_wanandroid/ui/article/article_page.dart';
import 'package:flutter_wanandroid/ui/project/provider/project_provider.dart';
import 'package:flutter_wanandroid/utils/navigate_util.dart';

class ProjectPage extends ConsumerStatefulWidget {
  const ProjectPage(this.cid, {Key? key}) : super(key: key);

  final int cid;

  @override
  ConsumerState<ConsumerStatefulWidget> createState() => _ProjectPageState();
}

class _ProjectPageState extends ConsumerState<ProjectPage>
    with AutomaticKeepAliveClientMixin {
  @override
  Widget build(BuildContext context) {
    super.build(context);
    final cid = widget.cid;
    final projects = ref.watch(projectNotifierProvider.call(cid));
    final theme = ref.watch(themesProvider);
    return Scaffold(
      body: EasyRefresh(
        header: const CupertinoHeader(),
        footer: const CupertinoFooter(
            processedDuration: Duration(milliseconds: 500)),
        callLoadOverOffset: 100.h,
        refreshOnStart: true,
        onLoad: projects.isEnd
            ? null
            : () async {
                final res = await ref
                    .read(projectNotifierProvider.call(cid).notifier)
                    .loadMore();
                return res ? IndicatorResult.success : IndicatorResult.fail;
              },
        onRefresh: () async {
          final res = await ref
              .read(projectNotifierProvider.call(cid).notifier)
              .refresh();
          return res ? IndicatorResult.success : IndicatorResult.fail;
        },
        child: ListView.builder(
          itemCount: projects.projectArticles.length,
          itemBuilder: (context, index) {
            final data = projects.projectArticles[index];
            return SizedBox(height: 160.h, child: _projectItem(data, theme.isDark));
          },
        ),
      ),
    );
  }

  @override
  bool get wantKeepAlive => true;

  Widget _projectItem(ArticleModel model, bool isDark) {
    return GestureDetector(
      onTap: () {
        navigateTo(context, ArticlePage(url: model.link, title: model.title, articleId: model.id));
      },
      child: Container(
        padding: EdgeInsets.all(10.r),
        margin: EdgeInsets.symmetric(horizontal: 10.w, vertical: 5.h),
        decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(10.r),
          color: Themes.getBackgroundColor(isDark)
        ),
        child: Row(
          children: [
            Image(image: CachedNetworkImageProvider(model.envelopePic), width: 90.w, fit: BoxFit.fill,),
            SizedBox(width: 10.w,),
            Expanded(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    model.title,
                    maxLines: 1,
                    overflow: TextOverflow.ellipsis,
                    style: TextStyle(fontSize: 16.sp),
                  ),
                  SizedBox(height: 10.h,),
                  Expanded(child: Text(model.desc, maxLines: 4,
                    overflow: TextOverflow.ellipsis,)),
                  Text(
                    model.author.isNotEmpty ? model.author : model.shareUser,
                    style: TextStyle(fontSize: 13.sp),
                  ),
                  Text(
                    model.niceDate,
                    style: TextStyle(fontSize: 12.sp),
                  ),
                ],
              ),
            )
          ],
        ),
      ),
    );
  }
}
