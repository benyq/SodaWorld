import 'package:equatable/equatable.dart';
import 'package:flutter_wanandroid/net/model/article_model.dart';

class SearchState extends Equatable {

  final List<ArticleModel> searchData;
  final bool isSearching;
  final bool isEnd;

  const SearchState({required this.searchData, this.isSearching = false, this.isEnd = false});

  copyWith({List<ArticleModel>? searchData, bool? isSearching, bool? isEnd}) {
    return SearchState(
      searchData: searchData?? this.searchData,
      isSearching: isSearching?? this.isSearching,
      isEnd: isEnd?? this.isEnd,
    );
  }


  @override
  List<Object?> get props => [searchData, isSearching, isEnd];

}