package com.benyq.sodaworld.account.ui.detail

import com.benyq.sodaworld.account.R
import com.benyq.sodaworld.base.ui.BaseViewModel
import com.benyq.sodaworld.base.ui.DataState
import com.benyq.sodaworld.database.appDatabase
import com.benyq.sodaworld.database.entity.TransactionRecord
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 *
 * @author benyq
 * @date 2022/10/14
 * @email 1520063035@qq.com
 *
 */
class TransactionRecordDetailViewModel() : BaseViewModel() {

    private val _deleteRecordResult: MutableSharedFlow<DataState<Boolean>> = MutableSharedFlow()
    val deleteRecordResult = _deleteRecordResult.asSharedFlow()

    fun deleteRecord(record: TransactionRecord) {
        execute {
            appDatabase.transactionRecordDao().delete(record)
        }.onSuccess {
            _deleteRecordResult.emit(DataState.Success(it > 0))
        }.onError {
            _deleteRecordResult.emit(DataState.Error(it.message ?: getString(R.string.error_unknown)))
        }
    }
}