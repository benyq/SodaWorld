package com.benyq.sodaworld.wanandroid

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.benyq.sodaworld.base.ui.BaseViewModel
import com.benyq.sodaworld.wanandroid.api.apiService
import com.benyq.sodaworld.wanandroid.api.cookieJar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 *
 * @author benyq
 * @date 12/8/2023
 *
 */

data class UserInfo(
    val nickname: String= "",
    val coin: String = "",
    val rank: String = "",
    val collectIds: List<Int> = emptyList()
)

class ShareViewModel(private val savedStateHandle: SavedStateHandle) : BaseViewModel() {

    private val _userInfo = MutableStateFlow(UserInfo())
    val userInfo: StateFlow<UserInfo> = _userInfo

    fun updateUserInfo(nickname: String, coin: String, collectIds: List<Int>) {
        viewModelScope.launch {
            _userInfo.update {
                it.copy(nickname = nickname, coin = coin, collectIds = collectIds)
            }
        }
    }

    fun queryUserInfo() {
        execute {
            apiService.userInfo()
        }.onError {
            Log.d("benyq", "getUserInfo: error: $it")
        }.onSuccess { response->
            if (response.isSuccess()) {
                val data = response.getRealData()!!
                _userInfo.update {
                    it.copy(nickname = data.userInfo.nickname, coin = data.coinInfo.coin, collectIds = data.userInfo.collectIds, rank = data.coinInfo.level)
                }
            }else {
                Log.d("benyq", "getUserInfo: error: ${response.getMessage()}")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            _userInfo.emit(UserInfo())
            cookieJar.clear()
        }
    }
}