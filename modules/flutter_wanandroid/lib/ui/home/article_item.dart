import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:flutter_wanandroid/generated/l10n.dart';
import 'package:flutter_wanandroid/net/model/article_model.dart';

class ArticleItem extends StatelessWidget {
  ArticleItem(this.model, {super.key, this.onTap});

  ArticleModel model;
  Function(ArticleModel)? onTap;

  @override
  Widget build(BuildContext context) {
    String authorName(ArticleModel model) {
      if (model.author.isNotEmpty) {
        return '${S.of(context).author} : ${model.author}';
      } else {
        return '${S.of(context).sharer} : ${model.shareUser}';
      }
    }

    return GestureDetector(
      behavior: HitTestBehavior.opaque,
      onTap: onTap != null ? () => onTap?.call(model) : null,
      child: Container(
        margin: EdgeInsets.only(top: 10.h, left: 10.w, right: 10.w),
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
            Text(
              "${model.superChapterName}-${model.chapterName}",
              style: TextStyle(fontSize: 12.sp),
              overflow: TextOverflow.ellipsis,
              maxLines: 1,
            ),
            SizedBox(height: 10.h),
            Divider(height: 1.h),
          ],
        ),
      ),
    );
  }
}
