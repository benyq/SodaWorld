package com.benyq.sodaworld.music.play.usecase

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.model.GlideUrl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import java.io.File

/**
 *
 * @author benyq
 * @date 10/16/2023
 *
 */
class MusicCoverUseCase(private val scope: CoroutineScope, private val context: Context) {

    fun execute(url: String, path: String, callBack: ()->Unit) {
        scope.launch(Dispatchers.IO) {
            val requestManager = Glide.with(context)
            val glideUrl = GlideUrl(url)
            val request: RequestBuilder<File> = requestManager.downloadOnly().load(glideUrl)
            request.submit().get().renameTo(File(path))
            ensureActive()
            callBack()
        }
    }
}