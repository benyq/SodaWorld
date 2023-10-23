package com.benyq.sodaworld

import android.app.Application
import com.benyq.sodaworld.base.extensions.appCtx
import com.benyq.sodaworld.music.service.IServiceNotifier
import com.benyq.sodaworld.music.service.SodaMusicService
import com.benyq.sodaworld.music.SodaPlayManager

/**
 *
 * @author benyq
 * @date 10/9/2023
 *
 */
class SodaWorldApp: Application() {

    override fun onCreate() {
        super.onCreate()
        appCtx = this
        SodaPlayManager.init(this, object : IServiceNotifier {
            override fun notifyService(startOrStop: Boolean) {
                if (startOrStop) SodaMusicService.start(this@SodaWorldApp)
                else SodaMusicService.stop(this@SodaWorldApp)
            }
        })
    }
}