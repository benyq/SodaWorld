package com.benyq.sodaworld.account.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.benyq.sodaworld.account.databinding.AccountViewConsumeBinding

/**
 *
 * @author benyq
 * @date 2022/10/14
 * @email 1520063035@qq.com
 *
 */
class ConsumeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr)  {

    private val binding: AccountViewConsumeBinding

    init {
        binding = AccountViewConsumeBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun setBGColor(@ColorRes bgColor: Int) {
        if (bgColor == 0) {
            return
        }
        binding.ivBG.setBackgroundColor(ContextCompat.getColor(context, bgColor))
    }

    fun setDrawable(@DrawableRes resId: Int) {
        binding.ivConsume.setImageResource(resId)
    }
}