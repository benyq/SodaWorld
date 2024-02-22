package com.benyq.sodaworld.account.ui.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.benyq.sodaworld.account.R
import com.benyq.sodaworld.base.extensions.dp

/**
 *
 * @author benyq
 * @date 2022/10/10
 * @email 1520063035@qq.com
 *
 */
class BottomTabTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var topDrawableWidth = 0
    private var topDrawableHeight = 0

    private var rightDrawableWidth = 0
    private var rightDrawableHeight = 0

    private var left: Drawable? = null
    private var top: Drawable? = null
    private var right: Drawable? = null
    private var bottom: Drawable? = null

    init {
        val array = getContext().obtainStyledAttributes(attrs, R.styleable.BottomTabTextView)
        val defaultSize = 30f.dp
        topDrawableWidth = array.getDimension(R.styleable.BottomTabTextView_top_drawable_width, defaultSize).toInt()
        topDrawableHeight = array.getDimension(R.styleable.BottomTabTextView_top_drawable_height, defaultSize).toInt()

        rightDrawableWidth = array.getDimension(R.styleable.BottomTabTextView_right_drawable_width, defaultSize).toInt()
        rightDrawableHeight = array.getDimension(R.styleable.BottomTabTextView_right_drawable_height, defaultSize).toInt()

        array.recycle()
        setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
    }

    override fun setCompoundDrawables(left: Drawable?, top: Drawable?, right: Drawable?, bottom: Drawable?) {
        top?.setBounds(0, 0, topDrawableWidth, topDrawableHeight)
        right?.setBounds(0, 0, rightDrawableWidth, rightDrawableHeight)
        super.setCompoundDrawables(left, top, right, bottom)
    }


    override fun setCompoundDrawablesWithIntrinsicBounds(
        left: Drawable?,
        top: Drawable?,
        right: Drawable?,
        bottom: Drawable?
    ) {
        this.left = left
        this.top = top
        this.right = right
        this.bottom = bottom
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
    }
}