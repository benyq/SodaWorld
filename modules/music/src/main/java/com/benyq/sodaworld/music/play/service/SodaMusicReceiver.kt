package com.benyq.sodaworld.music.play.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import com.benyq.sodaworld.music.play.SodaPlayManager

/**
 *
 * @author benyq
 * @date 10/16/2023
 *
 */
class SodaMusicReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.action) {
            Intent.ACTION_MEDIA_BUTTON -> {}
            SodaMusicService.NOTIFY_CLOSE -> SodaPlayManager.release()
            SodaMusicService.NOTIFY_PLAY -> SodaPlayManager.playAudio()
            SodaMusicService.NOTIFY_PAUSE, AudioManager.ACTION_AUDIO_BECOMING_NOISY -> SodaPlayManager.pauseAudio()
            SodaMusicService.NOTIFY_PREVIOUS -> SodaPlayManager.playBack()
            SodaMusicService.NOTIFY_NEXT -> SodaPlayManager.playNext()
            else -> {}
        }
    }
}