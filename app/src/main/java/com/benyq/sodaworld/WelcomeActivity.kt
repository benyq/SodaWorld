package com.benyq.sodaworld

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.benyq.sodaworld.base.extensions.isAppearanceLightStatusBars
import com.benyq.sodaworld.music.MusicActivity

/**
 * @author benyq
 * @date 12/8/2023
 * 显示完SplashScreen之后，可以展示一些业务UI，如广告
 * 但是目前没有，所以直接下个页面
 */
class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        isAppearanceLightStatusBars(false)
        splashScreen.setKeepOnScreenCondition { true }
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}