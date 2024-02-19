package com.benyq.sodaworld.music.netease.model

/**
 *
 * @author benyq
 * @date 10/16/2023
 *
 */
data class NeteasePlayListTrack(
    val code: Int,
    val songs: List<Song>
) {
    data class Song(
        val name: String,
        val id: String,
    )
}