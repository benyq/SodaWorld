package com.benyq.sodaworld.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.benyq.sodaworld.database.entity.QuickLaunchEntity

/**
 *
 * @author benyq
 * @date 8/28/2023
 *
 */
@Dao
interface QuickLaunchAppDao {
    @Query("SELECT * FROM t_quick_launch_app")
    fun getAll(): List<QuickLaunchEntity>

    @Insert
    fun insertAll(vararg app: QuickLaunchEntity): List<Long>

    @Delete
    fun delete(app: QuickLaunchEntity)
}