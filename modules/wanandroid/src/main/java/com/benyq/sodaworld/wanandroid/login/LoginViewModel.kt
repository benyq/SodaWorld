package com.benyq.sodaworld.wanandroid.login

import com.benyq.sodaworld.base.ui.BaseViewModel
import com.benyq.sodaworld.base.ui.DataState
import com.benyq.sodaworld.wanandroid.api.apiService
import com.benyq.sodaworld.wanandroid.api.model.LoginModel
import kotlinx.coroutines.flow.Flow

/**
 *
 * @author benyq
 * @date 12/11/2023
 *
 */


class LoginViewModel : BaseViewModel() {

    var username: String = ""
    var password: String = ""

    fun login(): Flow<DataState<LoginModel>> {
        return flowResponse {
            apiService.login(username, password)
        }
    }


}