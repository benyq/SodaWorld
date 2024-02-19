package com.benyq.sodaworld.music.play

import com.benyq.sodaworld.music.play.model.MusicItem

/**
 *
 * @author benyq
 * @date 10/17/2023
 *
 */
data class PlayUIState(
    val nowTime: String = "00:00",
    val allTime: String = "00:00",
    val progress: Long = 0,
    val duration: Long = 0,
    val isPaused: Boolean = true,
    val repeatMode: RepeatMode = RepeatMode.LIST_CYCLE,
    val music: MusicItem? = null
)
