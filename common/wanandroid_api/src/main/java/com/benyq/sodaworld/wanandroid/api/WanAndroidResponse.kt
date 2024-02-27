package com.benyq.sodaworld.wanandroid.api

import com.benyq.sodaworld.base.network.SodaResponse

/**
 *
 * @author benyq
 * @date 2/26/2024
 *
 */
data class WanAndroidResponse<T>(
    private val errorCode: Int = 0,
    private val errorMessage: String,
    private val data: T? = null
) : SodaResponse<T> {
    override fun isSuccess(): Boolean {
        return errorCode == 0
    }

    override fun getMessage(): String {
        return errorMessage
    }

    override fun getErrorCode(): Int {
        return errorCode
    }

    override fun getRealData(): T? {
        return data
    }

    companion object {
        fun <T> success(data: T): WanAndroidResponse<T> {
            return WanAndroidResponse(0, "", data)
        }

        fun <T> error(errorCode: Int, errorMsg: String): WanAndroidResponse<T> {
            return WanAndroidResponse(errorCode, errorMsg, null)
        }
    }
}

