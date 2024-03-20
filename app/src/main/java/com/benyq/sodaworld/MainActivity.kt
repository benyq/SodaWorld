package com.benyq.sodaworld

import android.os.Bundle
import android.view.View
import androidx.core.view.WindowInsetsCompat
import com.benyq.sodaworld.base.extensions.fitsSystemWindows
import com.benyq.sodaworld.base.ui.BaseActivity
import com.benyq.sodaworld.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun getLayoutId() = R.layout.activity_wan_android

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        window.fitsSystemWindows()

        FlutterWanAndroidActivity.startWithCachedEngine(this)
        finish()
    }

    override fun observe() {

    }

    override fun initData() {

    }

    override fun onApplyWindow(view: View, windowInsets: WindowInsetsCompat): WindowInsetsCompat {
        val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
        dataBind.tvHead.setPadding(0, insets.top, 0, 0)
        return WindowInsetsCompat.CONSUMED
    }
}