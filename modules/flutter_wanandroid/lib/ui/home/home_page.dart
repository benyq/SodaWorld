import 'package:cached_network_image/cached_network_image.dart';
import 'package:carousel_slider/carousel_slider.dart';
import 'package:easy_refresh/easy_refresh.dart';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:flutter_wanandroid/generated/l10n.dart';
import 'package:flutter_wanandroid/net/model/article_model.dart';
import 'package:flutter_wanandroid/ui/article/article_page.dart';
import 'package:flutter_wanandroid/ui/home/article_item.dart';
import 'package:flutter_wanandroid/ui/home/provider/home_privider.dart';
import 'package:flutter_wanandroid/utils/navigate_util.dart';


class HomePage extends ConsumerStatefulWidget {
  const HomePage({super.key});

  @override
  ConsumerState<ConsumerStatefulWidget> createState() => _HomePageState();
}

class _HomePageState extends ConsumerState<HomePage>
    with AutomaticKeepAliveClientMixin {

  late EasyRefreshController _controller;

  @override
  void initState() {
    super.initState();
    _controller = EasyRefreshController(controlFinishLoad: true, controlFinishRefresh: true);
    ref.read(homeStateProvider.notifier).getBanner();
    ref.read(homeStateProvider.notifier).refresh();
  }

  @override
  void dispose() {
    super.dispose();
    _controller.dispose();
  }

  @override
  Widget build(BuildContext context) {
    super.build(context);
    final homeState = ref.watch(homeStateProvider);
    return EasyRefresh(
      header: const CupertinoHeader(),
      footer: const CupertinoFooter(processedDuration: Duration(milliseconds: 500)),
      callLoadOverOffset: 100.h,
      controller: _controller,
      child: CustomScrollView(
        slivers: [
          SliverToBoxAdapter(
            child: CarouselSlider(
              options: CarouselOptions(
                aspectRatio: 2.0,
                enlargeCenterPage: true,
                enlargeStrategy: CenterPageEnlargeStrategy.zoom,
                enlargeFactor: 0.5,
                autoPlay: true,
              ),
              items: [
                ...homeState.banners.map((e) => GestureDetector(
                      onTap: () {
                        _navigateToArticlePage(e.title, e.url, e.id);
                      },
                      child: Container(
                        padding: EdgeInsets.symmetric(
                            vertical: 10.h, horizontal: 10.w),
                        child: ClipRRect(
                          borderRadius: BorderRadius.all(Radius.circular(10.r)),
                          child: Image(
                              image: CachedNetworkImageProvider(e.imagePath),
                              fit: BoxFit.fill),
                        ),
                      ),
                    ))
              ],
            ),
          ),
          SliverList(
            delegate: SliverChildBuilderDelegate(
              (context, index) {
                return ArticleItem(homeState.articles[index], onTap: (model){
                  navigateTo(context, ArticlePage(title: model.title, url:model.link, articleId: model.id,));
                },);
              },
              childCount: homeState.articles.length,
            ),
          ),
        ],
      ),
      onRefresh: () async{
        await ref.read(homeStateProvider.notifier).refresh();
        _controller.finishRefresh();
      },
      onLoad: () async {
        await ref.read(homeStateProvider.notifier).loadMore();
        _controller.finishLoad();
      },
    );
  }

  @override
  bool get wantKeepAlive => true;

  Widget _articleItem(BuildContext context, ArticleModel model) {

    String authorName(ArticleModel model) {
      if (model.author.isNotEmpty) {
        return '${S.of(context).author} : ${model.author}';
      }else {
        return '${S.of(context).sharer} : ${model.shareUser}';
      }
    }

    return GestureDetector(
        behavior: HitTestBehavior.opaque,
      onTap: () {
        _navigateToArticlePage(model.title, model.link, model.id);
      },
      child: Container(
        margin: EdgeInsets.only(left: 10.w, right: 10.w, bottom: 10.h),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              children: [
                Text(authorName(model)),
                const Spacer(),
                Text(model.niceDate),
              ],
            ),
            SizedBox(height: 5.h),
            Text(model.title, maxLines: 2, overflow: TextOverflow.ellipsis),
            SizedBox(height: 5.h),
            Text("${model.superChapterName}-${model.chapterName}", style: TextStyle(fontSize: 12.sp), overflow: TextOverflow.ellipsis, maxLines: 1,),
            SizedBox(height: 10.h),
            Divider(height: 1.h),
          ],
        ),
      ),
    );
  }

  void _navigateToArticlePage(String title, String url, int articleId) {
    navigateTo(context, ArticlePage(title: title, url:url, articleId: articleId,));
  }
}
