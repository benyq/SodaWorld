package com.benyq.sodaworld

import com.benyq.sodaworld.base.BaseApplication
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor

/**
 *
 * @author benyq
 * @date 3/19/2024
 *
 */
class App: BaseApplication() {

    private val flutterEngine by lazy {
        FlutterEngine(this).apply {
            dartExecutor.executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault())
        }
    }

    override fun onCreate() {
        super.onCreate()
        FlutterEngineCache.getInstance().put("wan_android", flutterEngine)
    }

}