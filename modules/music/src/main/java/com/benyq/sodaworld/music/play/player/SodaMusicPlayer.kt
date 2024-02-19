package com.benyq.sodaworld.music.play.player

import android.content.Context
import android.net.Uri
import com.benyq.sodaworld.music.play.controller.IPlayerController
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.upstream.DefaultDataSource


/**
 *
 * @author benyq
 * @date 10/12/2023
 *
 */
class SodaMusicPlayer: IPlayerController {

    private lateinit var exoPlayer: ExoPlayer
    private val sodaPlayerListeners = mutableSetOf<SodaPlayerListener>()

    fun initializePlayer(context: Context) {
        val playerBuilder = ExoPlayer.Builder(context)
            .setMediaSourceFactory(
                DefaultMediaSourceFactory(context)
                    .setDataSourceFactory(DefaultDataSource.Factory(context))
            )

        exoPlayer = playerBuilder.build()
        exoPlayer.addListener(object : Player.Listener {
            override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
                super.onPlayWhenReadyChanged(playWhenReady, reason)
            }

            override fun onPlayerError(error: PlaybackException) {
                sodaPlayerListeners.forEach { listener->
                    listener.onPlayerError(error)
                }
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                sodaPlayerListeners.forEach { listener->
                    listener.onIsPlayingChanged(isPlaying)
                }
            }
        })
    }

    fun registerListener(listener: SodaPlayerListener) {
        sodaPlayerListeners.add(listener)
    }

    fun unregisterListener(listener: SodaPlayerListener) {
        sodaPlayerListeners.remove(listener)
    }

    /**
     * @param [musicUrl] 需要支持https和本地
     */
    override fun play(musicUrl: String) {
        val item: MediaItem = when {
            musicUrl.contains("http:") || musicUrl.contains("ftp:") || musicUrl.contains("https:") -> {
                MediaItem.fromUri(musicUrl)
            }
            musicUrl.contains("storage") -> MediaItem.fromUri(musicUrl)
            else -> MediaItem.fromUri(Uri.parse("file:///android_asset/$musicUrl"))
        }
        exoPlayer.setMediaItem(item)
        exoPlayer.prepare()
        exoPlayer.play()
    }

    override fun resume() {
        exoPlayer.play()
    }

    override fun pause() {
        exoPlayer.pause()
    }

    override fun stop() {
        exoPlayer.seekTo(0)
        exoPlayer.pause()
    }

    override fun seekTo(position: Long) {
        exoPlayer.seekTo(position)
    }

    override fun isPlaying(): Boolean {
        return exoPlayer.isPlaying
    }

    override fun isPaused(): Boolean {
        return !exoPlayer.playWhenReady
    }

    override fun duration(): Long {
        return exoPlayer.duration
    }

    override fun currentPosition(): Long {
        return exoPlayer.currentPosition
    }

}

interface SodaPlayerListener {

    fun onIsPlayingChanged(isPlaying: Boolean)

    fun onPlayerError(error: PlaybackException) {}
}

