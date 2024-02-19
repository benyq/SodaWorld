package com.benyq.sodaworld.music.play.widget

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import com.benyq.sodaworld.music.R
import com.benyq.sodaworld.music.databinding.ViewMusicControllerBinding
import com.bumptech.glide.Glide

/**
 *
 * @author benyq
 * @date 10/17/2023
 *
 */
class MusicControllerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: ViewMusicControllerBinding
    private var onActionListener: OnActionListener? = null

    private val rotateAnimation: ValueAnimator
    private var isPlaying = false

    init {
        binding = ViewMusicControllerBinding.inflate(LayoutInflater.from(context), this, true)
        binding.flPlay.setOnClickListener {
            onActionListener?.onPlayStateClick()
        }
        binding.ivPlayList.setOnClickListener {
            onActionListener?.onPlayListClick()
        }
        binding.root.setOnClickListener {
            onActionListener?.onMusicClick()
        }
        rotateAnimation = ObjectAnimator.ofFloat(binding.ivCover, "rotation", 0f, 360f)
            .setDuration(6000)
        rotateAnimation.repeatMode = ValueAnimator.RESTART
        rotateAnimation.repeatCount = ValueAnimator.INFINITE
        rotateAnimation.setInterpolator(LinearInterpolator())
    }

    fun setOnActionListener(listener: OnActionListener) {
        onActionListener = listener
    }

    fun setProgress(progress: Int) {
        binding.progressCircular.progress = progress
    }

    fun setMusicInfo(song: String, author: String, coverImg: String) {
        Glide.with(binding.ivCover).load(coverImg).into(binding.ivCover)
        binding.tvAuthorName.text = author
        binding.tvSongName.text = song
    }

    fun setPlayState(isPaused: Boolean) {
        binding.ivPlayState.setImageResource(if (isPaused) R.drawable.ic_controller_play_24 else R.drawable.ic_controller_pause_24)
        if (!isPlaying != isPaused) {
            if (isPaused) {
                rotateAnimation.pause()
            }else {
                if (rotateAnimation.isPaused) {
                    rotateAnimation.resume()
                }else {
                    rotateAnimation.start()
                }
            }
        }
        isPlaying = !isPaused
    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        rotateAnimation.cancel()
    }


    interface OnActionListener {
        fun onMusicClick()
        fun onPlayStateClick()
        fun onPlayListClick()
    }
}