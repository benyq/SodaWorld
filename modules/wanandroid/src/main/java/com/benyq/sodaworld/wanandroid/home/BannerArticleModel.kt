package com.benyq.sodaworld.wanandroid.home

import com.benyq.sodaworld.wanandroid.api.model.ArticleModel
import com.benyq.sodaworld.wanandroid.api.model.BannerModel

/**
 *
 * @author benyq
 * @date 12/11/2023
 *
 */
data class BannerArticleModel(
    val articleModel: ArticleModel? = null,
    val banners: List<BannerModel>? = null
)
