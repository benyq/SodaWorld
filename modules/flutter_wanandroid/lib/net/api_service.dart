import 'package:dio/dio.dart';
import 'package:flutter_wanandroid/net/api_response.dart';
import 'package:flutter_wanandroid/net/model/category_model.dart';
import 'package:flutter_wanandroid/net/model/collect_article_model.dart';
import 'package:flutter_wanandroid/net/model/github_repository_release_model.dart';
import 'package:flutter_wanandroid/net/model/hot_word_model.dart';
import 'package:flutter_wanandroid/net/model/project_tree_model.dart';
import 'package:flutter_wanandroid/net/page_model.dart';
import 'package:retrofit/dio.dart';
import 'package:retrofit/http.dart';
import 'model/article_model.dart';
import 'model/banner_model.dart';
import 'model/login_model.dart';
import 'model/user_model.dart';

part 'generated/api_service.g.dart';

@RestApi(baseUrl: "https://www.wanandroid.com/")
abstract class WanAndroidService {
  factory WanAndroidService(Dio dio, {String baseUrl}) = _WanAndroidService;

  @GET("banner/json")
  Future<ApiResponse<List<BannerModel>>> banner([@CancelRequest() CancelToken? cancelToken]);

  @GET("article/top/json")
  Future<ApiResponse<List<ArticleModel>>> getTopArticles();

  @GET("article/list/{page}/json")
  Future<ApiResponse<PageModel<ArticleModel>>> getArticles(@Path() int page, {@Query('cid') int? cid});

  @POST("user/login")
  @FormUrlEncoded()
  Future<ApiResponse<LoginModel>> login(@Field("username") String username, @Field("password") String password);

  @GET('user/lg/userinfo/json')
  Future<ApiResponse<UserModel>> getUserInfo();

  @GET('lg/collect/list/{page}/json')
  Future<ApiResponse<PageModel<CollectArticleModel>>> getCollectArticles(@Path() int page);

  @POST('lg/collect/{id}/json')
  Future<ApiResponse<String>> collectArticle(@Path("id") int id);

  @POST('lg/uncollect_originId/{id}/json')
  Future<ApiResponse<String>> unCollectArticle(@Path("id") int id);

  @POST('article/query/{page}/json')
  @FormUrlEncoded()
  Future<ApiResponse<PageModel<ArticleModel>>> searchArticles(@Path() int page, @Field("k") String key);

  @GET('hotkey/json')
  Future<ApiResponse<List<HotWordModel>>> hotKey();

  @GET('project/tree/json')
  Future<ApiResponse<List<ProjectTreeModel>>> projectTree();
  
  @GET('project/list/{page}/json')
  Future<ApiResponse<PageModel<ArticleModel>>> getProjectArticles(@Path('page') int page, @Query('cid') int categoryId);

  @GET('tree/json')
  Future<ApiResponse<List<CategoryModel>>> categoryTree();

  @GET('https://api.github.com/repos/benyq/flutter_wanandroid/releases')
  Future<List<GithubRepositoryReleaseModel>> queryGithubRelease({@Query('per_page') int page = 1});

}