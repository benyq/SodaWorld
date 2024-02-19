package com.benyq.sodaworld.music.play.controller

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.benyq.sodaworld.music.play.player.SodaMusicPlayer

/**
 *
 * @author benyq
 * @date 10/12/2023
 *
 */
class SodaPlayerController {

    private val player: SodaMusicPlayer = SodaMusicPlayer()
    private val mainHandler = Handler(Looper.getMainLooper())
    private var onPlayStateListener: OnPlayStateListener? = null
    private val progressAction = Runnable {
        updateProgress()
    }

    fun init(context: Context) {
        player.initializePlayer(context)
    }

    fun play(musicUrl: String) {
        player.play(musicUrl)
        afterPlay()
    }

    fun resume() {
        player.resume()
        mainHandler.post(progressAction)
    }

    fun pause() {
        player.pause()
        mainHandler.removeCallbacks(progressAction)
    }

    fun playAgain() {
        player.seekTo(0)
        player.resume()
    }

    fun isPaused(): Boolean {
        return player.isPaused()
    }

    fun isPlaying(): Boolean {
        return player.isPlaying()
    }

    fun setOnPlayStateListener(onPlayStateListener: OnPlayStateListener) {
        this.onPlayStateListener = onPlayStateListener
    }

    private fun afterPlay() {
        mainHandler.post(progressAction)
    }

    private fun updateProgress() {
        val currentPosition = player.currentPosition()
        val duration = player.duration()
        onPlayStateListener?.onPlayStateChanged(
            calculateTime(currentPosition),
            calculateTime(duration),
            currentPosition,
            duration, isPaused()
        )
        if (currentPosition == duration) {
            //播完了
            onPlayStateListener?.onPlayFinished()
        }
        mainHandler.postDelayed(progressAction, 1000)
    }

    private fun calculateTime(progress: Long): String {
        val time = progress.toInt()
        val minute: Int
        val second: Int
        return if (time >= 60) {
            minute = time / 60
            second = time % 60
            (if (minute < 10) "0$minute" else "" + minute) + if (second < 10) ":0$second" else ":$second"
        } else {
            second = time
            if (second < 10) "00:0$second" else "00:$second"
        }
    }


    interface OnPlayStateListener {

        /**
         * 当前歌曲播放完成，准备切换
         */
        fun onPlayFinished()

        fun onPlayStateChanged(
            nowTime: String = "00:00", allTime: String = "00:00", progress: Long = 0,
            duration: Long = 0, isPaused: Boolean = true,
        )
    }

}