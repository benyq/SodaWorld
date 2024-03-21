import 'package:equatable/equatable.dart';
import 'package:flutter_wanandroid/net/model/collect_article_model.dart';

class CollectState extends Equatable {

  final List<CollectArticleModel> articles;
  //到底了
  final bool isEnd;

  const CollectState({required this.articles, required this.isEnd});


  copyWith({List<CollectArticleModel>? articles, bool? isEnd}) {
    return CollectState(
      articles: articles?? this.articles,
      isEnd: isEnd?? this.isEnd,
    );
  }

  @override
  List<Object?> get props => [articles, isEnd];

}

