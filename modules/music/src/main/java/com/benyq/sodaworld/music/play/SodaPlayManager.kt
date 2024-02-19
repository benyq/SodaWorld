package com.benyq.sodaworld.music.play

import android.content.Context
import android.util.Log
import com.benyq.sodaworld.music.play.controller.SodaPlayerController
import com.benyq.sodaworld.music.play.datacenter.MusicDataCenter
import com.benyq.sodaworld.music.play.model.AlbumItem
import com.benyq.sodaworld.music.play.model.MusicItem
import com.benyq.sodaworld.music.play.service.IServiceNotifier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

/**
 *
 * @author benyq
 * @date 10/13/2023
 *
 */
object SodaPlayManager {

    private const val TAG = "SodaPlayManager"
    private var repeatMode = RepeatMode.LIST_CYCLE
    private val controller = SodaPlayerController()
    private val musicDataCenter = MusicDataCenter()
    private var noMusicPlaying = true
    val playState = MutableStateFlow(PlayUIState())

    private var iServiceNotifier: IServiceNotifier? = null

    fun init(context: Context, iServiceNotifier: IServiceNotifier) {
        SodaPlayManager.iServiceNotifier = iServiceNotifier
        controller.init(context)
        controller.setOnPlayStateListener(object : SodaPlayerController.OnPlayStateListener {
            override fun onPlayFinished() {
                if (repeatMode == RepeatMode.SINGLE_CYCLE) {
                    playAgain()
                } else {
                    playNext()
                }
            }

            override fun onPlayStateChanged(
                nowTime: String,
                allTime: String,
                progress: Long,
                duration: Long,
                isPaused: Boolean,
            ) {
                playState.update {
                    it.copy(
                        nowTime = nowTime,
                        allTime = allTime,
                        progress = progress,
                        duration = duration,
                        isPaused = isPaused
                    )
                }
            }
        })
    }

    fun setMusicAlbum(album: AlbumItem) {
        musicDataCenter.setMusicAlbum(album)
    }

    fun playAudio() {
        if (noMusicPlaying) {
            getUrlAndPlay()
        } else if (controller.isPaused()) {
            resumeAudio()
        }
    }

    fun pauseAudio() {
        controller.pause()
        iServiceNotifier?.notifyService(true)
        playState.update { it.copy(isPaused = isPaused()) }
    }

    fun resumeAudio() {
        controller.resume()
        iServiceNotifier?.notifyService(true)
        playState.update { it.copy(isPaused = isPaused()) }
    }

    fun togglePlay() {
        if (isPlaying()) {
            pauseAudio()
        } else {
            playAudio()
        }
    }

    fun playNext() {
        musicDataCenter.countNextIndex()
        getUrlAndPlay()
    }

    fun playBack() {
        musicDataCenter.countBackIndex()
        getUrlAndPlay()
    }

    fun playAgain() {
        controller.playAgain()
    }

    fun isPaused() = controller.isPaused()
    fun isPlaying() = controller.isPlaying()

    fun getCurrentMusic(): MusicItem? {
        return musicDataCenter.getCurrentMusic()
    }

    fun setRepeatMode(repeatMode: RepeatMode) {
        SodaPlayManager.repeatMode = repeatMode
        playState.update { it.copy(repeatMode = repeatMode) }
    }

    fun getRepeatMode() = repeatMode

    fun release() {
        iServiceNotifier?.notifyService(false)
    }

    private fun getUrlAndPlay() {
        val musicUrl = musicDataCenter.getCurrentMusic()?.url
        Log.d(TAG, "getUrlAndPlay: $musicUrl")
        if (musicUrl.isNullOrEmpty()) {
            pauseAudio()
        } else {
            controller.play(musicUrl)
            afterPlay()
        }
    }

    private fun afterPlay() {
        noMusicPlaying = false
        val music = musicDataCenter.getCurrentMusic()
        playState.update { it.copy(music = music) }
        iServiceNotifier?.notifyService(true)
    }

}