import 'package:easy_refresh/easy_refresh.dart';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:flutter_wanandroid/generated/l10n.dart';
import 'package:flutter_wanandroid/ui/article/article_page.dart';
import 'package:flutter_wanandroid/ui/home/article_item.dart';
import 'package:flutter_wanandroid/ui/search/app_bar_search.dart';
import 'package:flutter_wanandroid/ui/search/provider/search_provider.dart';
import 'package:flutter_wanandroid/utils/navigate_util.dart';

const _colors = [
  Color(0xFFF44336),
  Color(0xFFE91E63),
  Color(0xFF9C27B0),
  Color(0xFF673AB7),
  Color(0xFF3F51B5),
  Color(0xFF2196F3),
  Color(0xFF03A9F4),
  Color(0xFF00BCD4),
  Color(0xFF009688)
];

class SearchPage extends ConsumerStatefulWidget {
  const SearchPage({Key? key}) : super(key: key);

  @override
  ConsumerState<ConsumerStatefulWidget> createState() => _SearchPageState();
}

class _SearchPageState extends ConsumerState<SearchPage> {
  late EasyRefreshController _controller;
  late TextEditingController _editingController;

  @override
  void initState() {
    super.initState();
    _controller = EasyRefreshController(controlFinishLoad: true, controlFinishRefresh: true);
    _editingController = TextEditingController();
  }

  @override
  void dispose() {
    super.dispose();
    _controller.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final searchState = ref.watch(searchProvider);
    final hotKeyState = ref.watch(hotKeyProvider);
    final searchHistoryState = ref.watch(searchHistoryProvider);

    return Scaffold(
      appBar: AppBarSearch(
        controller: _editingController,
        borderRadius: 20.w,
        height: 45.h,
        hintText: S.of(context).input_hint,
        actions: [
          GestureDetector(
            onTap: () {
              Navigator.pop(context);
            },
            child: Container(
              constraints: BoxConstraints(minWidth: 40.w),
              margin: EdgeInsets.symmetric(horizontal: 6.w),
              alignment: Alignment.center,
              child:
                  Text(S.of(context).cancel, style: TextStyle(fontSize: 14.sp)),
            ),
          )
        ],
        onSearch: (value) {
          ref.read(searchProvider.notifier).search(value);
        },
        onClear: (){
          ref.read(searchProvider.notifier).exitSearch();
        },
        onChanged: (value) {
          if (value.isEmpty) {
            ref.read(searchProvider.notifier).exitSearch();
          }
        },
      ),
      body: Padding(
        padding: EdgeInsets.symmetric(horizontal: 10.w, vertical: 10.h),
        child: Stack(
          children: [
            Visibility(
              visible: !searchState.isSearching,
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  // 搜索历史
                  Container(
                    child: searchHistoryState.hasValue
                        ? Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Row(
                                children: [
                                  Text(S.of(context).search_history),
                                  const Spacer(),
                                  GestureDetector(
                                    onTap: () {
                                      ref.read(searchHistoryProvider.notifier)
                                          .cleanSearchHistory();
                                    },
                                    child: Opacity(
                                        opacity: searchHistoryState.value!.isEmpty
                                            ? 0.5
                                            : 1,
                                        child: const Icon(Icons.delete)),
                                  ),
                                ],
                              ),
                              SizedBox(
                                height: 10.h,
                              ),
                              Wrap(
                                direction: Axis.horizontal,
                                alignment: WrapAlignment.start,
                                spacing: 20.w,
                                runSpacing: 10.h,
                                children: searchHistoryState.value!
                                    .map((e) => _searchHistoryItem(e))
                                    .toList(),
                              ),
                              SizedBox(
                                height: 10.h,
                              ),
                            ],
                          )
                        : null,
                  ),
                  // 热门搜索
                  Container(
                    child: hotKeyState.hasValue
                        ? Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Text(S.of(context).search_hot),
                              SizedBox(
                                height: 10.h,
                              ),
                              Wrap(
                                direction: Axis.horizontal,
                                alignment: WrapAlignment.start,
                                spacing: 20.w,
                                runSpacing: 10.h,
                                children: hotKeyState.value!
                                    .map((e) => _searchHistoryItem(e.name))
                                    .toList(),
                              ),
                            ],
                          )
                        : null,
                  )
                ],
              ),
            ),
            Visibility(
              visible: searchState.isSearching,
              child: EasyRefresh(
                header: const CupertinoHeader(),
                footer: const CupertinoFooter(processedDuration: Duration(milliseconds: 500)),
                callLoadOverOffset: 100.h,
                controller: _controller,
                onLoad: searchState.isEnd? null: () async {
                  _controller.resetFooter();
                  await ref.read(searchProvider.notifier).loadMore();
                  _controller.finishLoad(searchState.isEnd?IndicatorResult.noMore: IndicatorResult.success);
                },
                child: ListView.builder(
                  itemCount: searchState.searchData.length,
                  itemBuilder: (context, index) {
                    return ArticleItem(searchState.searchData[index], onTap: (model){
                      navigateTo(context, ArticlePage(title: model.title, url:model.link, articleId: model.id,));
                    },);
                  },
                ),
              ),
            )
          ],
        ),
      ),
    );
  }

  Widget _searchHistoryItem(String key) {
    return GestureDetector(
      onTap: () {
        _searchHistoryItemClick(key);
      },
      child: Container(
        padding: EdgeInsets.symmetric(horizontal: 10.w, vertical: 10.h),
        decoration: BoxDecoration(
            borderRadius: BorderRadius.all(Radius.circular(12.r)),
            color: _colors[key.hashCode % _colors.length]),
        child: Text(
          key,
          style: const TextStyle(color: Colors.white),
        ),
      ),
    );
  }

  void _searchHistoryItemClick(String key) {
    _editingController.text = key;
    ref.read(searchProvider.notifier).search(key);
    //收起键盘
    FocusScope.of(context).requestFocus(FocusNode());
  }

}
