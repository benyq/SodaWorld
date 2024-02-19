package com.benyq.sodaworld.base

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import androidx.core.app.NotificationCompat
import com.benyq.sodaworld.base.extensions.appCtx
import com.benyq.sodaworld.base.extensions.fromO

/**
 *
 * @author benyq
 * @date 8/25/2023
 *
 */
object NotificationHelper {

    private val mNotificationManager = appCtx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private const val KEEP_ALIVE_CHANNEL_ID = "keepAliveId"
    private const val KEEP_ALIVE_CHANNEL_NAME = "保活通知"
    private const val KEEP_ALIVE_NOTIFY_ID = 1

    fun showKeepAliveNotification(context: Context? = null) {
        if (fromO()) {
            val notificationChannel =
                NotificationChannel(
                    KEEP_ALIVE_CHANNEL_ID,
                    KEEP_ALIVE_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
                )
            //如果这里用IMPORTANCE_NOENE就需要在系统的设置里面开启渠道，
            //通知才能正常弹出
            mNotificationManager.createNotificationChannel(notificationChannel)
        }

        val notification: Notification = NotificationCompat.Builder(appCtx, KEEP_ALIVE_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher) // the status icon
            .setWhen(System.currentTimeMillis()) // the time stamp
            .setContentText("I'm still alive!")
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .build()
        if (context is Service) {
            context.startForeground(KEEP_ALIVE_NOTIFY_ID, notification)
        }else {
            mNotificationManager.notify(KEEP_ALIVE_NOTIFY_ID, notification)
        }
    }
}