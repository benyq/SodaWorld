package com.benyq.sodaworld.music.play.model

/**
 *
 * @author benyq
 * @date 10/13/2023
 *
 */
data class MusicItem(val musicId: String, val title: String, val url: String, val coverImg: String, val artist: ArtistItem)
