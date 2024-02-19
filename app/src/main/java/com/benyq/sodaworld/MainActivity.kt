package com.benyq.sodaworld

import android.os.Bundle
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import com.benyq.sodaworld.base.extensions.dp
import com.benyq.sodaworld.base.extensions.fitsSystemWindows
import com.benyq.sodaworld.base.ui.BaseActivity
import com.benyq.sodaworld.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun getLayoutId() = R.layout.activity_main

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        window.fitsSystemWindows()
    }

    override fun observe() {

    }

    override fun onApplyWindow(view: View, windowInsets: WindowInsetsCompat) {
        val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
        dataBind.tvHead.setPadding(0, insets.top, 0, 0)
    }
}