package com.benyq.sodaworld.music

import android.app.Application
import android.content.Context
import com.benyq.sodaworld.base.ApplicationDelegate
import com.benyq.sodaworld.music.play.SodaPlayManager
import com.benyq.sodaworld.music.play.service.IServiceNotifier
import com.benyq.sodaworld.music.play.service.SodaMusicService

/**
 * @author benyq
 * @date 2024/02/19
 * @constructor 反射创建[MusicApplicationDelegate]
 */
class MusicApplicationDelegate: ApplicationDelegate {
    override fun attachBaseContext(application: Application, context: Context?) {

    }

    override fun onCreate(application: Application) {
        SodaPlayManager.init(application, object : IServiceNotifier {
            override fun notifyService(startOrStop: Boolean) {
                if (startOrStop) SodaMusicService.start(application)
                else SodaMusicService.stop(application)
            }
        })
    }
}