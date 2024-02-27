package com.benyq.sodaworld.base.ui

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 *
 * @author benyq
 * @date 7/14/2023
 *
 */
abstract class BaseActivity<DB : ViewDataBinding> : AppCompatActivity() {

    private var _dataBind: DB? = null
    protected val dataBind: DB get() = checkNotNull(_dataBind) { "初始化binding失败" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _dataBind = DataBindingUtil.setContentView(
            this, getLayoutId()
        )
        lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                _dataBind = null
            }
        })
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (!isTopFragmentConsumedBackPress()) {
                    onBackPressAction()
                }
            }
        })
        ViewCompat.setOnApplyWindowInsetsListener(dataBind.root) { view, windowInsets ->
            onApplyWindow(view, windowInsets)
        }
        onActivityCreated(savedInstanceState)
        observe()
        initData()
    }

    abstract fun getLayoutId(): Int

    abstract fun onActivityCreated(savedInstanceState: Bundle?)

    open fun observe() {}
    open fun initData() {}

    open fun onApplyWindow(view: View, windowInsets: WindowInsetsCompat): WindowInsetsCompat {
        val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
        view.setPadding(0, insets.top, 0, 0)
        return WindowInsetsCompat.CONSUMED
    }

    /**
     * 这个方法需要在fragment之后
     */
    open fun onBackPressAction() {
        finish()
    }

    private fun isTopFragmentConsumedBackPress(): Boolean {
        supportFragmentManager.fragments.forEach {
            if (it.isVisible && it is BackPressHandler) {
                return it.onBackPressed()
            }
        }
        return false
    }


}
