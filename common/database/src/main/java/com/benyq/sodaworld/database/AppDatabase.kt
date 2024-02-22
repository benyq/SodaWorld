package com.benyq.sodaworld.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.benyq.sodaworld.base.extensions.appCtx
import com.benyq.sodaworld.database.dao.QuickLaunchAppDao
import com.benyq.sodaworld.database.dao.TransactionRecordDao
import com.benyq.sodaworld.database.entity.QuickLaunchEntity
import com.benyq.sodaworld.database.entity.TransactionRecord

/**
 *
 * @author benyq
 * @date 8/23/2023
 *
 */

@Database(entities = [QuickLaunchEntity::class, TransactionRecord::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun quickLaunchAppDao(): QuickLaunchAppDao
    abstract fun transactionRecordDao(): TransactionRecordDao
}


val appDatabase by lazy { Room.databaseBuilder(appCtx, AppDatabase::class.java, "db_soda_world").build() }