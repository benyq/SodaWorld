package com.benyq.sodaworld.music.play.service

import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.view.View
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.benyq.sodaworld.music.MusicActivity
import com.benyq.sodaworld.music.R
import com.benyq.sodaworld.music.play.usecase.MusicCoverUseCase
import com.benyq.sodaworld.music.play.SodaPlayManager
import com.benyq.sodaworld.music.play.model.MusicItem
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import java.io.File

/**
 * @author benyq
 * @date 10/12/2023
 */
class SodaMusicService : Service() {

    companion object {
        private const val MUSIC_GROUP_ID = "group_001"
        private const val MUSIC_CHANNEL_ID = "channel_001"
        private const val MUSIC_NOTIFY_ID = 1

        const val NOTIFY_PREVIOUS = "soda_world.benyq.previous"
        const val NOTIFY_CLOSE = "soda_world.benyq.close"
        const val NOTIFY_PAUSE = "soda_world.benyq.pause"
        const val NOTIFY_PLAY = "soda_world.benyq.play"
        const val NOTIFY_NEXT = "soda_world.benyq.next"

        fun start(context: Context) {
            context.startService(Intent(context, SodaMusicService::class.java))
        }

        fun stop(context: Context) {
            context.stopService(Intent(context, SodaMusicService::class.java))
        }
    }

    private val mainScope = MainScope()

    override fun onBind(intent: Intent) = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val currentMusic = SodaPlayManager.getCurrentMusic()
        if (currentMusic == null) {
            stopSelf()
            return START_NOT_STICKY
        }
        createNotification(currentMusic)
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }

    private fun createNotification(music: MusicItem) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val playGroup = NotificationChannelGroup(
                MUSIC_GROUP_ID,
                getString(R.string.play)
            )
            notificationManager.createNotificationChannelGroup(playGroup)
            val playChannel = NotificationChannel(
                MUSIC_CHANNEL_ID,
                getString(R.string.notify_of_play), NotificationManager.IMPORTANCE_DEFAULT
            )
            playChannel.group = MUSIC_GROUP_ID
            notificationManager.createNotificationChannel(playChannel)
        }

        val smallRemoteView =
            RemoteViews(applicationContext.packageName, R.layout.notify_player_small)
        val expandedRemoteView =
            RemoteViews(applicationContext.packageName, R.layout.notify_player_big)

        val intent = Intent(applicationContext, MusicActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.FLAG_MUTABLE else 0
        )

        setListener(smallRemoteView)
        setListener(expandedRemoteView)

        val isPaused = SodaPlayManager.isPaused()
        smallRemoteView.setViewVisibility(R.id.player_play, if (isPaused) View.VISIBLE else View.GONE)
        smallRemoteView.setViewVisibility(R.id.player_pause, if (!isPaused) View.VISIBLE else View.GONE)
        expandedRemoteView.setViewVisibility(R.id.player_play, if (isPaused) View.VISIBLE else View.GONE)
        expandedRemoteView.setViewVisibility(R.id.player_pause, if (!isPaused) View.VISIBLE else View.GONE)

        smallRemoteView.setTextViewText(R.id.player_song_name, music.title)
        smallRemoteView.setTextViewText(R.id.player_author_name, music.artist.name)
        expandedRemoteView.setTextViewText(R.id.player_song_name, music.title)
        expandedRemoteView.setTextViewText(R.id.player_author_name, music.artist.name)

        val notification = NotificationCompat.Builder(applicationContext, MUSIC_CHANNEL_ID)
            .setOnlyAlertOnce(true)
            .setSmallIcon(R.drawable.ic_player)
            .setContentIntent(pendingIntent)
            .setOnlyAlertOnce(true)
            .setCustomContentView(smallRemoteView)
            .setCustomBigContentView(expandedRemoteView)
            .setContentTitle("title").build()

        val coverImgPath = getExternalFilesDir("music_cover")?.absolutePath + File.separator + music.musicId + ".jpg"
        val bitmap = getMusicCover(coverImgPath)

        bitmap?.let {
            smallRemoteView.setImageViewBitmap(R.id.player_album_art, it)
            expandedRemoteView.setImageViewBitmap(R.id.player_album_art, it)
        } ?: let {
            val downloadCase = MusicCoverUseCase(mainScope, this)
            downloadCase.execute(music.coverImg, coverImgPath) {
                startService(Intent(applicationContext, SodaMusicService::class.java))
            }
            smallRemoteView.setImageViewResource(R.id.player_album_art, R.drawable.bg_album_default)
            expandedRemoteView.setImageViewResource(R.id.player_album_art, R.drawable.bg_album_default)
        }
        startForeground(MUSIC_NOTIFY_ID, notification)
    }

    private fun setListener(view: RemoteViews) {
        val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        else PendingIntent.FLAG_UPDATE_CURRENT
        var pendingIntent = PendingIntent.getBroadcast(
            applicationContext, 0,
            Intent(NOTIFY_PREVIOUS).setPackage(packageName), flags)
        view.setOnClickPendingIntent(R.id.player_previous, pendingIntent)

        pendingIntent = PendingIntent.getBroadcast(
            applicationContext, 0,
            Intent(NOTIFY_NEXT).setPackage(packageName), flags)
        view.setOnClickPendingIntent(R.id.player_next, pendingIntent)

        pendingIntent = PendingIntent.getBroadcast(
            applicationContext, 0,
            Intent(NOTIFY_PLAY).setPackage(packageName), flags)
        view.setOnClickPendingIntent(R.id.player_play, pendingIntent)

        pendingIntent = PendingIntent.getBroadcast(
            applicationContext, 0,
            Intent(NOTIFY_PAUSE).setPackage(packageName), flags)
        view.setOnClickPendingIntent(R.id.player_pause, pendingIntent)

        pendingIntent = PendingIntent.getBroadcast(
            applicationContext, 0,
            Intent(NOTIFY_CLOSE).setPackage(packageName), flags)
        view.setOnClickPendingIntent(R.id.player_close, pendingIntent)
    }

    private fun getMusicCover(path: String): Bitmap? {
        return kotlin.runCatching { BitmapFactory.decodeFile(path) }.getOrNull()
    }
}