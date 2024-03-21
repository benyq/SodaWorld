import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:flutter_wanandroid/net/model/category_model.dart';
import 'package:flutter_wanandroid/ui/category/provider/category_provider.dart';
import 'package:flutter_wanandroid/utils/navigate_util.dart';

import 'category_article_page.dart';

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

class CategoryPage extends ConsumerStatefulWidget {
  const CategoryPage({super.key});

  @override
  ConsumerState<ConsumerStatefulWidget> createState() => _CategoryPageState();
}

class _CategoryPageState extends ConsumerState<CategoryPage>
    with AutomaticKeepAliveClientMixin {
  @override
  Widget build(BuildContext context) {
    super.build(context);
    final categoryTree = ref.watch(categoryProvider);
    return categoryTree.when(
      data: (categories) => SingleChildScrollView(
        physics: const BouncingScrollPhysics(),
        child: Padding(
          padding: EdgeInsets.symmetric(vertical: 10.h),
          child: Column(
            children: [
              ...categories
                  .where((element) => element.children.isNotEmpty)
                  .map((e) {
                return _categoryItem(e);
              })
            ],
          ),
        ),
      ),
      loading: () => Center(
        child: SizedBox.square(
          dimension: 60.w,
          child: const CircularProgressIndicator(),
        ),
      ),
      error: (err, stack) => Center(
          child: TextButton(
              onPressed: () {
                ref.refresh(categoryProvider.future);
              },
              child: Text('Retry'))),
    );
  }

  @override
  bool get wantKeepAlive => true;

  Widget _categoryItem(CategoryModel model) {
    return Container(
      padding: EdgeInsets.symmetric(horizontal: 10.w),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            model.name,
            style: TextStyle(fontWeight: FontWeight.bold, fontSize: 16.sp),
          ),
          Divider(height: 0.5.h, color: Colors.grey),
          SizedBox(
            height: 10.h,
          ),
          Wrap(
            spacing: 10.w,
            runSpacing: 10.h,
            children: [
              for (var i = 0; i < model.children.length; i++)
                GestureDetector(
                  onTap: () {
                    final data = model.children[i];
                    navigateTo(context, CategoryArticlePage(data.id, data.name));
                  },
                  child: Container(
                    padding: EdgeInsets.all(10.w),
                    decoration: BoxDecoration(
                        borderRadius: BorderRadius.all(Radius.circular(10.r)),
                        color: _colors[i % _colors.length]),
                    child: Text(
                      model.children[i].name,
                      style: TextStyle(fontSize: 14.sp, color: Colors.white),
                    ),
                  ),
                ),
            ],
          ),
          SizedBox(
            height: 10.h,
          ),
        ],
      ),
    );
  }
}
