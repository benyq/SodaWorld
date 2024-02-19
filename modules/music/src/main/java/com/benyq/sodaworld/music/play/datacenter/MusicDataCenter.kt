package com.benyq.sodaworld.music.play.datacenter

import com.benyq.sodaworld.music.play.RepeatMode
import com.benyq.sodaworld.music.play.model.AlbumItem
import com.benyq.sodaworld.music.play.model.MusicItem

/**
 *
 * @author benyq
 * @date 10/13/2023
 * 管理播放列表已经当前播放信息
 */
class MusicDataCenter {

    private var repeatMode = RepeatMode.LIST_CYCLE
    //当前播放 music index
    private var playIndex = 0
    //当前播放 music在实际列表中的index，列表选中状态
    private var inAlbumIndex = 0

    private val originalMusicPlayList = mutableListOf<MusicItem>()
    private val shuffleMusicPlayList = mutableListOf<MusicItem>()

    fun getPlayingList(): List<MusicItem> {
        return if (repeatMode == RepeatMode.RANDOM) shuffleMusicPlayList else originalMusicPlayList
    }

    fun getCurrentMusic(): MusicItem? {
        if (getPlayingList().isEmpty()) return null
        return getPlayingList()[playIndex]
    }

    fun countNextIndex() {
        if (playIndex == getPlayingList().size - 1) {
            playIndex = 0
        }else {
            playIndex ++
        }
        inAlbumIndex = originalMusicPlayList.indexOf(getCurrentMusic())
    }

    fun countBackIndex() {
        if (playIndex == 0) {
            playIndex = getPlayingList().size - 1
        }else {
            playIndex --
        }
        inAlbumIndex = originalMusicPlayList.indexOf(getCurrentMusic())
    }

    fun setMusicAlbum(album: AlbumItem) {
        originalMusicPlayList.clear()
        originalMusicPlayList.addAll(album.musics)

        shuffleMusicPlayList.clear()
        shuffleMusicPlayList.addAll(album.musics)
        shuffleMusicPlayList.shuffle()
    }

    fun getAlbumIndex(): Int {
        return inAlbumIndex
    }

    fun setAlbumIndex(albumIndex: Int) {
        inAlbumIndex = albumIndex
        playIndex = getPlayingList().indexOf(originalMusicPlayList[inAlbumIndex])
    }
}