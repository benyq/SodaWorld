package com.benyq.sodaworld

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benyq.sodaworld.base.network.RetrofitManager
import com.benyq.sodaworld.music.model.AlbumItem
import com.benyq.sodaworld.music.model.ArtistItem
import com.benyq.sodaworld.music.model.MusicItem
import com.benyq.sodaworld.netease.NeteaseMusicApi
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
                MusicItem("2", "海阔天空.m4a", "music/海阔天空.m4a", "https://p1.music.126.net/ev2SCqkboiHtfftEP2LDcw==/18572950418214003.jpg", artist),
                MusicItem("3", "Underdog.mp3", "music/Underdog.mp3", "https://p1.music.126.net/F4QHIB0m42rrtRPbWUUDyw==/109951167465600604.jpg", artist),
                MusicItem("4", "辞九门回忆.mp3", "music/辞九门回忆.mp3", "https://p1.music.126.net/IbdETavu_XNsij9OgcSzwQ==/19172184253589771.jpg", artist),
            ))
            playListFlow.emit(album)
        }
    }
}