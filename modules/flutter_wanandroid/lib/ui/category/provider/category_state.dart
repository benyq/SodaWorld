import 'package:equatable/equatable.dart';
import 'package:flutter_wanandroid/net/model/article_model.dart';

class CategoryState extends Equatable {

  final List<ArticleModel> articles;
  final bool isEnd;


  const CategoryState(this.articles, this.isEnd);

  copyWith({
    List<ArticleModel>? projectArticles,
    bool? isEnd,
  }) {
    return CategoryState(
      projectArticles?? this.articles,
      isEnd?? this.isEnd,
    );
  }


  @override
  List<Object?> get props => [articles, isEnd];

}