package com.benyq.sodaworld.account.ui.record

import com.benyq.sodaworld.base.R
import com.benyq.sodaworld.base.extensions.toNumberDefault
import com.benyq.sodaworld.base.ui.BaseViewModel
import com.benyq.sodaworld.base.ui.DataState
import com.benyq.sodaworld.database.appDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 *
 * @author benyq
 * @date 2/20/2024
 *
 */
class TransactionRecordViewModel : BaseViewModel() {

    private val _recordData =
        MutableStateFlow<DataState<List<TransactionRecordGroupData>>>(DataState.Loading(false))
    val recordData = _recordData.asStateFlow()

    private val _amountData = MutableStateFlow("")
    val amountData = _amountData.asStateFlow()

    fun queryRecordByTime(startTime: Long, endTime: Long) {
        execute {
            var sum = 0f
            val records = appDatabase.transactionRecordDao().getByCondition(startTime, endTime)
                .map { record ->
                    record.amount = fen2yuan(record.amount)
                    sum += record.amount.toNumberDefault(0f)
                    record
                }
            val sdf = SimpleDateFormat("yyyyMMdd", Locale.CHINA)
            val data = mutableListOf<TransactionRecordGroupData>()
            var headerIndex = -1

            records.forEachIndexed { index, record ->
                val tag = sdf.format(Date(record.createTime))
                val recordDate = TransactionRecordData(record)
                if (data.getOrNull(headerIndex)?.tag != tag) {
                    val groupData = TransactionRecordGroupData.headerData(
                        TransactionRecordHeaderData().apply {
                            setupTime(record.createTime)
                        }, tag
                    )
                    data.add(groupData)
                    headerIndex = data.lastIndex
                }

                data.getOrNull(headerIndex)?.header?.finalList?.add(recordDate)
                data.add(TransactionRecordGroupData.recordData(recordDate))
            }
            _amountData.value = "%.2f".format(sum)
            data
        }.onStart {
            _recordData.value = DataState.Loading(true)
        }.onSuccess {
            _recordData.value = DataState.Success(it)
        }.onError {
            it.printStackTrace()
            _recordData.value = DataState.Error(it.message ?: getString(R.string.error_unknown))
        }.onFinally {
            _recordData.value = DataState.Loading(false)
        }
    }

    private fun fen2yuan(amount: String): String {
        if (amount.length < 2) return amount
        val part1 = amount.substring(0, amount.length - 2)
        val part2 = amount.substring(amount.length - 2, amount.length)
        return "${part1}.${part2}"
    }
}