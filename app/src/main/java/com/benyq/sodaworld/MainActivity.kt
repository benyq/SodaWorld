package com.benyq.sodaworld

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.benyq.sodaworld.base.extensions.visibleOrInvisible
import com.benyq.sodaworld.base.ui.BaseActivity
import com.benyq.sodaworld.databinding.ActivityMainBinding
import com.benyq.sodaworld.music.SodaPlayManager
import com.benyq.sodaworld.music.widget.MusicControllerView
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayoutId() = R.layout.activity_main

    private val viewModel by viewModels<MusicViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
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