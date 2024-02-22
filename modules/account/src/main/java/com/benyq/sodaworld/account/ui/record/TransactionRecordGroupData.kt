package com.benyq.sodaworld.account.ui.record

import com.benyq.sodaworld.account.ConsumeType
import com.benyq.sodaworld.account.PaidType
import com.benyq.sodaworld.base.extensions.toNumberDefault
import com.benyq.sodaworld.database.entity.TransactionRecord
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 *
 * @author benyq
 * @date 2/20/2024
 *
 */
data class TransactionRecordGroupData(
    val header: TransactionRecordHeaderData? = null,
    val record: TransactionRecordData? = null,
    val type: Int,
    val tag: String = ""
) {
    companion object {

        const val HEADER_TYPE = 0
        const val RECORD_TYPE = 1

        fun headerData(header: TransactionRecordHeaderData, tag: String) = TransactionRecordGroupData(header, type = HEADER_TYPE, tag = tag)
        fun recordData(record: TransactionRecordData) = TransactionRecordGroupData(type = RECORD_TYPE, record = record)
    }
}


data class TransactionRecordHeaderData(
    val finalList: MutableList<TransactionRecordData> = mutableListOf()
) {
    private val calendar = Calendar.getInstance()

    init {
        calendar.firstDayOfWeek = Calendar.MONDAY
    }

    fun setupTime(time: Long) {
        calendar.timeInMillis = time
    }

    fun totalAmount(): String {
        var sum = 0f
        finalList.forEach {
            sum += it.record.amount.toNumberDefault(0f)
        }
        return  "-%.2f".format(sum)
    }

    fun date(): String {
        return "${calendar.get(Calendar.MONTH) + 1}月${calendar.get(Calendar.DAY_OF_MONTH)}日"
    }

    fun week(): String {
        return int2Week(calendar.get(Calendar.DAY_OF_WEEK))
    }


    private fun int2Week(number: Int): String {
        var dayOfWeek: Int = number - 1
        if (dayOfWeek == 0) {
            dayOfWeek = 7
        }
        val week = listOf("一", "二", "三", "四", "五", "六", "七")
        return "星期" + week[dayOfWeek - 1]
    }
}
data class TransactionRecordData(val record: TransactionRecord) {

    val consumeType: ConsumeType = ConsumeType.fromCode(record.consumeType)
    val paidType: PaidType = PaidType.fromCode(record.paidType)
    val date: String
        get() {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault())
            return dateFormat.format(record.createTime)
        }
}