package com.benyq.sodaworld.base.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benyq.sodaworld.base.coroutine.Coroutine
import com.benyq.sodaworld.base.network.SodaResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlin.coroutines.CoroutineContext

/**
 *
 * @author benyq
 * @date 7/14/2023
 *
 */

open class BaseViewModel() : ViewModel() {

    protected fun <T> execute(
        scope: CoroutineScope = viewModelScope,
        context: CoroutineContext = Dispatchers.IO,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        executeContext: CoroutineContext = Dispatchers.Main,
        block: suspend CoroutineScope.() -> T
    ): Coroutine<T> {
        return Coroutine.async(scope, context, start, executeContext, block)
    }

    protected fun <R> submit(
        scope: CoroutineScope = viewModelScope,
        context: CoroutineContext = Dispatchers.IO,
        block: suspend CoroutineScope.() -> Deferred<R>
    ): Coroutine<R> {
        return Coroutine.async(scope, context) { block().await() }
    }

    protected fun executeParallel() {
        execute {
            listOf(
                async { delayMock(1) },
                async { delayMock(1) },
                async { delayMock1(1) },
                async { delayMock(1) },
                async { delayMock(1) }
            ).awaitAll()
        }.onSuccess {

        }
    }

    suspend fun delayMock(num: Int): Int {
        delay(num * 1000L)
        return num
    }

    suspend fun delayMock1(num: Int): String {
        delay(num * 1000L)
        return num.toString()
    }

    protected fun <T> flowResponse(defaultValue: T? = null, request: suspend () -> SodaResponse<T>): Flow<DataState<T>> {
        return flow {
            val response = request()
            if (response.isSuccess() || defaultValue != null){
                emit(DataState.Success<T>(response.getRealData() ?: defaultValue!!))
            }else {
                emit(DataState.Error<T>(response.getMessage()))
            }
        }.flowOn(Dispatchers.IO)
            .onStart { emit(DataState.Loading(true)) }
            .catch { DataState.Error<T>(it.message ?: "网络错误") }
            .onCompletion {
                emit(DataState.Loading(false))
            }
    }

}