package com.benyq.sodaworld.database.dao

import androidx.room.*
import com.benyq.sodaworld.database.entity.TransactionRecord

/**
 *
 * @author benyq
 * @date 2022/9/30
 * @email 1520063035@qq.com
 *
 */
@Dao
interface TransactionRecordDao {
    @Query("select * from t_transaction_record")
    fun getAll(): List<TransactionRecord>

    @Insert
    fun addTransactionRecord(record: TransactionRecord): Long

    @Query(
        "select * from t_transaction_record where (:consumeType is null or consumeType == :consumeType) " +
                "and (:paidType is null or paidType == :paidType)" +
                "and createTime between :startTime and :endTime order by createTime desc"
    )
    fun getByCondition(
        startTime: Long, endTime: Long, consumeType: Int? = null,
        paidType: Int? = null
    ): List<TransactionRecord>

    @Query(
        "select sum(amount) as amount, consumeType, paidType, note, createTime, id from t_transaction_record where createTime " +
                "between :startTime and :endTime group by consumeType order by createTime desc"
    )
    fun getByConsume(startTime: Long, endTime: Long): List<TransactionRecord>

    @Delete
    fun delete(record: TransactionRecord): Int

    @Update
    fun update(record: TransactionRecord): Int

}