package com.benyq.sodaworld.music

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benyq.sodaworld.base.network.RetrofitManager
import com.benyq.sodaworld.music.play.model.AlbumItem
import com.benyq.sodaworld.music.play.model.ArtistItem
import com.benyq.sodaworld.music.play.model.MusicItem
import com.benyq.sodaworld.music.netease.NeteaseMusicApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

/**
 *
 * @author benyq
 * @date 10/16/2023
 *
 */
class MusicViewModel: ViewModel() {

    private val api: NeteaseMusicApi =
        RetrofitManager.create("http://127.0.0.1:3000/", NeteaseMusicApi::class.java)

    val playListFlow = MutableSharedFlow<AlbumItem>()

    fun getTestData() {
        viewModelScope.launch {
            val artist = ArtistItem("Beyond")
            val album = AlbumItem("1", "专辑亦", "summary", "", artist, listOf(
                MusicItem("1", "music1.mp3", "music/music1.mp3", "https://p1.music.126.net/USNapWbUW2BBd_ccCHzuWw==/734473767366516.jpg", artist),
            ))
            playListFlow.emit(album)
        }
    }
}