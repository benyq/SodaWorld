package com.benyq.sodaworld.wanandroid.mine.collect

import androidx.lifecycle.viewModelScope
import com.benyq.sodaworld.base.ui.BaseViewModel
import com.benyq.sodaworld.base.ui.DataState
import com.benyq.sodaworld.wanandroid.api.apiService
import com.benyq.sodaworld.wanandroid.api.model.ArticleListState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 *
 * @author benyq
 * @date 12/14/2023
 *
 */
class UserCollectViewModel : BaseViewModel() {

    private val _collectFlow = MutableSharedFlow<DataState<ArticleListState>>()
    val collectFlow: SharedFlow<DataState<ArticleListState>> = _collectFlow

    init {
        getCollectedArticles()
    }

    private var _pageIndex = 0

    private fun getCollectedArticles() {
        flowResponse {
            apiService.getCollectedArticles(_pageIndex)
        }.onEach {
            val mapData = it.map { pageModel ->
                val isFirst = _pageIndex == 0
                _pageIndex++
                ArticleListState(isFirst, pageModel.over, pageModel.datas)
            }
            _collectFlow.emit(mapData)
        }.launchIn(viewModelScope)
    }

    fun refresh() {
        _pageIndex = 0
        getCollectedArticles()
    }

    fun loadMore() {
        getCollectedArticles()
    }
}