package com.benyq.sodaworld.music.play.model

/**
 *
 * @author benyq
 * @date 10/13/2023
 *
 */
data class AlbumItem(
    val albumId: String, val title: String, val summary: String, val coverImg: String,
    val artist: ArtistItem, val musics: List<MusicItem>
)
