package com.benyq.sodaworld.wanandroid.api

import com.benyq.sodaworld.base.extensions.appCtx
import com.benyq.sodaworld.base.network.RetrofitManager
import com.benyq.sodaworld.wanandroid.api.model.ArticleModel
import com.benyq.sodaworld.wanandroid.api.model.BannerModel
import com.benyq.sodaworld.wanandroid.api.model.CategoryTreeModel
import com.benyq.sodaworld.wanandroid.api.model.HotKeyModel
import com.benyq.sodaworld.wanandroid.api.model.LoginModel
import com.benyq.sodaworld.wanandroid.api.model.PageModel
import com.benyq.sodaworld.wanandroid.api.model.ProjectTreeModel
import com.benyq.sodaworld.wanandroid.api.model.UserInfoModel
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface WanAndroidService {

    @GET("banner/json")
    suspend fun banner(): WanAndroidResponse<List<BannerModel>>

    @GET("article/list/{page}/json")
    suspend fun articles(@Path("page") page: Int, @Query("cid") cid: Int? = null): WanAndroidResponse<PageModel<ArticleModel>>

    @POST("user/login")
    @FormUrlEncoded
    suspend fun login(@Field("username") username: String, @Field("password") password: String): WanAndroidResponse<LoginModel>

    @GET("user/lg/userinfo/json")
    suspend fun userInfo(): WanAndroidResponse<UserInfoModel>

    @GET("hotkey/json")
    suspend fun hotKey(): WanAndroidResponse<List<HotKeyModel>>

    @GET("tree/json")
    suspend fun categoryTree(): WanAndroidResponse<List<CategoryTreeModel>>

    @GET("project/tree/json")
    suspend fun projectTree(): WanAndroidResponse<List<ProjectTreeModel>>

    @GET("project/list/{page}/json")
    suspend fun projects(@Path("page") page: Int, @Query("cid") cid: Int): WanAndroidResponse<PageModel<ArticleModel>>

    @GET("/lg/collect/list/{page}/json")
    suspend fun getCollectedArticles(@Path("page") page: Int): WanAndroidResponse<PageModel<ArticleModel>>

    @POST("article/query/{page}/json")
    @FormUrlEncoded
    suspend fun search(@Path("page") page: Int, @Field("k") k: String): WanAndroidResponse<PageModel<ArticleModel>>
}


val cookieJar by lazy {
    PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(appCtx))
}
val apiService by lazy { RetrofitManager.create("https://www.wanandroid.com/", WanAndroidService::class.java) {
    builder -> builder.cookieJar(cookieJar)
} }
