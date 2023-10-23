package com.benyq.sodaworld.base.ui.mvi.extension

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benyq.sodaworld.base.ui.mvi.ContainerLazy
import com.benyq.sodaworld.base.ui.mvi.MutableContainer
import com.benyq.sodaworld.base.ui.mvi.UiEvent
import com.benyq.sodaworld.base.ui.mvi.UiState

/**
 * 构建viewModel的Ui容器，存储Ui状态和一次性事件
 */
fun <STATE : UiState, SINGLE_EVENT : UiEvent> ViewModel.containers(
    initialState: STATE,
): Lazy<MutableContainer<STATE, SINGLE_EVENT>> {
    return ContainerLazy(initialState, viewModelScope)
}