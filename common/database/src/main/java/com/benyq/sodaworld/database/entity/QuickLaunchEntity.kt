package com.benyq.sodaworld.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *
 * @author benyq
 * @date 8/28/2023
 *
 */

@Entity(tableName = "t_quick_launch_app")
data class QuickLaunchEntity(
    @PrimaryKey(autoGenerate = true) val uid: Long = 0,
    @ColumnInfo(name = "app_nane") val appName: String,
    @ColumnInfo(name = "package_nane") val packageName: String,
)
