package com.benyq.sodaworld.base.network

/**
 *
 * @author benyq
 * @date 8/30/2023
 *
 */
interface SodaResponse<T> {

    fun isSuccess(): Boolean

    fun getMessage(): String

    fun getErrorCode(): Int

    fun getRealData(): T?


}