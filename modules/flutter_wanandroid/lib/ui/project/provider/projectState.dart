import 'package:equatable/equatable.dart';
import 'package:flutter_wanandroid/net/model/article_model.dart';

class ProjectState extends Equatable {

  final List<ArticleModel> projectArticles;
  final bool isEnd;


  const ProjectState(this.projectArticles, this.isEnd);

  copyWith({
    List<ArticleModel>? projectArticles,
    bool? isEnd,
  }) {
    return ProjectState(
      projectArticles?? this.projectArticles,
      isEnd?? this.isEnd,
    );
  }


  @override
  List<Object?> get props => [projectArticles, isEnd];

}