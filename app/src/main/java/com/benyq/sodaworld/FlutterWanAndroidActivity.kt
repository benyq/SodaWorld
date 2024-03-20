package com.benyq.sodaworld

import android.content.Context
import io.flutter.embedding.android.FlutterActivity

/**
 *
 * @author benyq
 * @date 3/20/2024
 *
 */
class FlutterWanAndroidActivity: FlutterActivity() {

    companion object {
        fun startWithCachedEngine(context: Context) {
            context.startActivity(CachedEngineIntentBuilder(FlutterWanAndroidActivity::class.java,"wan_android").build(context))
        }
    }

}