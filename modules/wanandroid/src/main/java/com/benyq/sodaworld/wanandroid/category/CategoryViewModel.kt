package com.benyq.sodaworld.wanandroid.category

import com.benyq.sodaworld.base.ui.BaseViewModel
import com.benyq.sodaworld.wanandroid.api.apiService
import com.benyq.sodaworld.wanandroid.api.model.CategoryTreeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CategoryViewModel: BaseViewModel() {

    private val _categoryTreeFlow = MutableStateFlow<List<CategoryTreeModel>>(emptyList())
    val categoryTreeFlow: StateFlow<List<CategoryTreeModel>> = _categoryTreeFlow

    init {
        getCategoryTree()
    }

    private fun getCategoryTree() {
        execute {
            apiService.categoryTree()
        }.onSuccess {
            if (it.isSuccess()) {
                _categoryTreeFlow.emit(it.getRealData()!!)
            }
        }
    }
}