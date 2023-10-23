package com.benyq.sodaworld.music.usecase

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.request.Request
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.SizeReadyCallback
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
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