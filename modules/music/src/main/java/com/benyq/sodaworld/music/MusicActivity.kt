package com.benyq.sodaworld.music

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.benyq.sodaworld.base.ui.BaseActivity
import com.benyq.sodaworld.music.databinding.ActivityMusicBinding
import com.benyq.sodaworld.music.play.SodaPlayManager
import com.benyq.sodaworld.music.play.widget.MusicControllerView
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MusicActivity : BaseActivity<ActivityMusicBinding>() {

    override fun getLayoutId() = R.layout.activity_music

    private val viewModel by viewModels<MusicViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        XXPermissions.with(this).permission(Permission.POST_NOTIFICATIONS).request { permissions, allGranted ->

        }

        val isPaused = SodaPlayManager.isPaused()
        dataBind.controllerView.setOnActionListener(object : MusicControllerView.OnActionListener {
            override fun onMusicClick() {

            }
            override fun onPlayStateClick() {
                SodaPlayManager.togglePlay()
            }
            override fun onPlayListClick() {
            }
        })
        dataBind.controllerView.setPlayState(isPaused)
        SodaPlayManager.playState
            .flowWithLifecycle(lifecycle)
            .onEach {
                dataBind.controllerView.setPlayState(it.isPaused)
                val progress = if (it.duration == 0L) 0 else (it.progress * 100) / it.duration
                dataBind.controllerView.setProgress(progress.toInt())
                it.music?.let { music->
                    dataBind.controllerView.setMusicInfo(music.title, music.artist.name, music.coverImg)
                }
            }
            .launchIn(lifecycleScope)
    }

    override fun initData() {
        viewModel.getTestData()
    }

    override fun observe() {
       lifecycleScope.launch{
           viewModel.playListFlow.collect {
               SodaPlayManager.setMusicAlbum(it)
               SodaPlayManager.getCurrentMusic()?.let { music->
                   dataBind.controllerView.setMusicInfo(music.title, music.artist.name, music.coverImg)
               }
           }
       }
    }
}