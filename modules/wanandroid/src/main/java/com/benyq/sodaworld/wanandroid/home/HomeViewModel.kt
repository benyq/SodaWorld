package com.benyq.sodaworld.wanandroid.home

import com.benyq.sodaworld.base.ui.BaseViewModel
import com.benyq.sodaworld.wanandroid.api.WanAndroidResponse
import com.benyq.sodaworld.wanandroid.api.apiService
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class HomeViewModel : BaseViewModel() {

    //first list, second isFirstLoad, third isOver
    private val _articleFlow =
        MutableSharedFlow<Triple<List<BannerArticleModel>, Boolean, Boolean>>()
    val articleFlow: SharedFlow<Triple<List<BannerArticleModel>, Boolean, Boolean>> = _articleFlow
    private var pageIndex = 0

    private val _refreshingState = MutableSharedFlow<Boolean>()
    val refreshingState: SharedFlow<Boolean> = _refreshingState

    init {
        refresh()
    }

    fun refresh() {
        pageIndex = 0
        execute {
            val banner = async { apiService.banner() }
            val article = async { apiService.articles(pageIndex) }
            val bannerResponse = banner.await()
            val articleResponse = article.await()

            if (!bannerResponse.isSuccess()) {
                throw Exception(bannerResponse.getMessage())
            }
            if (!articleResponse.isSuccess()) {
                throw Exception(articleResponse.getMessage())
            }
            WanAndroidResponse.success(listOf(BannerArticleModel(banners = bannerResponse.getRealData()))
                    + articleResponse.getRealData()!!.datas.map {
                BannerArticleModel(articleModel = it)
            })
        }.onSuccess { response ->
            _articleFlow.emit(Triple(response.getRealData()!!, true, false))
            pageIndex++
        }.onFinally {
            _refreshingState.emit(false)
        }
    }

    private fun getArticle(page: Int) {
        execute {
            apiService.articles(page)
        }.onSuccess { response ->
            if (response.isSuccess()) {
                _articleFlow.emit(Triple(response.getRealData()!!.datas.map { article ->
                    BannerArticleModel(articleModel = article)
                }, false, response.getRealData()!!.over))
                pageIndex++
            }
        }
    }

    fun loadMore() {
        getArticle(pageIndex)
    }

}