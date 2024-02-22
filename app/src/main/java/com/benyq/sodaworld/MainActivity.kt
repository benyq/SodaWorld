package com.benyq.sodaworld

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import com.benyq.sodaworld.account.ui.TransactionActivity
import com.benyq.sodaworld.base.extensions.dp
import com.benyq.sodaworld.base.extensions.fitsSystemWindows
import com.benyq.sodaworld.base.ui.BaseActivity
import com.benyq.sodaworld.databinding.ActivityMainBinding
import com.benyq.sodaworld.music.MusicActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun getLayoutId() = R.layout.activity_main

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        window.fitsSystemWindows()

        startActivity(Intent(this, TransactionActivity::class.java))
        finish()
    }

    override fun observe() {

    }

    override fun initData() {

    }

    override fun onApplyWindow(view: View, windowInsets: WindowInsetsCompat) {
        val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
        dataBind.tvHead.setPadding(0, insets.top, 0, 0)
    }
}