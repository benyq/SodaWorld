package com.benyq.sodaworld.base.ui.mvi

interface MutableContainer<STATE : UiState, SINGLE_EVENT : UiEvent> :
    Container<STATE, SINGLE_EVENT> {

    fun updateState(action: STATE.() -> STATE)

    fun sendEvent(event: SINGLE_EVENT)

}