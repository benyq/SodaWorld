import 'package:equatable/equatable.dart';
import 'package:flutter_wanandroid/net/model/article_model.dart';
import 'package:flutter_wanandroid/net/model/banner_model.dart';

class HomeState extends Equatable{
  final List<BannerModel> banners;
  final List<ArticleModel> articles;

  const HomeState(this.banners, this.articles);

  HomeState copyWith({
    List<BannerModel>? banners,
    List<ArticleModel>? articles,
  }) {
    return HomeState(
      banners?? this.banners,
      articles?? this.articles,
    );
  }

  @override
  List<Object?> get props => [banners, articles];
}