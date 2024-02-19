package com.benyq.sodaworld.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.benyq.sodaworld.base.extensions.appCtx
import com.benyq.sodaworld.database.dao.QuickLaunchAppDao
import com.benyq.sodaworld.database.entity.QuickLaunchEntity

/**
 *
 * @author benyq
 * @date 8/23/2023
 *
 */

@Database(entities = [QuickLaunchEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun quickLaunchAppDao(): QuickLaunchAppDao
}


val appDatabase by lazy { Room.databaseBuilder(appCtx, AppDatabase::class.java, "db_soda_world").build() }