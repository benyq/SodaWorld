package com.benyq.sodaworld.account.ui.add

import com.benyq.sodaworld.account.R
import com.benyq.sodaworld.account.ConsumeType
import com.benyq.sodaworld.account.PaidType
import com.benyq.sodaworld.base.extensions.toNumberDefault
import com.benyq.sodaworld.base.ui.BaseViewModel
import com.benyq.sodaworld.database.appDatabase
import com.benyq.sodaworld.database.entity.TransactionRecord
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 *
 * @author benyq
 * @date 2/21/2024
 *
 */
data class AddEditRecordEvent(
    val isEdit: Boolean,
    val message: String,
    val result: Boolean = true,
)
class EditRecordViewModel: BaseViewModel() {


    private val _addRecordResult = MutableSharedFlow<AddEditRecordEvent>()
    val addRecordResult = _addRecordResult.asSharedFlow()

    fun addTransactionRecord(
        amount: String,
        consumeType: ConsumeType,
        paidType: PaidType,
        note: String,
        createTime: Long,
    ) {
        execute {
            val amountCent = yuan2fen(amount)
            appDatabase.transactionRecordDao().addTransactionRecord(TransactionRecord(amountCent, consumeType.code, paidType.code, note, createTime))
        }.onSuccess {
            _addRecordResult.emit(AddEditRecordEvent(false, "添加成功"))
        }.onError {
            _addRecordResult.emit(AddEditRecordEvent(false, it.message ?: getString(R.string.error_unknown), false))
        }

    }

    fun updateTransactionRecord(record: TransactionRecord, amount: String) {
        execute {
            record.amount = yuan2fen(amount)
            appDatabase.transactionRecordDao().update(record)
        }.onSuccess {
            record.amount = amount
            _addRecordResult.emit(AddEditRecordEvent(true, "修改成功"))
        }.onError {
            _addRecordResult.emit(AddEditRecordEvent( true, it.message ?: getString(R.string.error_unknown), false))
        }
    }


    private fun yuan2fen(amount: String): String {
        return (amount.toNumberDefault(0f) * 100).toInt().toString()
    }
}