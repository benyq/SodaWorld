package com.benyq.sodaworld.music.controller

/**
 *
 * @author benyq
 * @date 10/16/2023
 *
 */
interface IPlayerController {

    fun play(musicUrl: String)

    fun resume()

    fun pause()

    fun stop()

    fun seekTo(position: Long)

    fun isPlaying(): Boolean

    fun isPaused(): Boolean

    fun duration(): Long

    fun currentPosition(): Long
}