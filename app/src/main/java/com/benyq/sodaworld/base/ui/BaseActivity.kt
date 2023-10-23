package com.benyq.sodaworld.base.ui

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
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
                onBackPressAction()
            }
        })
        onActivityCreated(savedInstanceState)
        observe()
        initData()
    }

    abstract fun getLayoutId(): Int

    abstract fun onActivityCreated(savedInstanceState: Bundle?)

    abstract fun observe()
    abstract fun initData()

    /**
     * 这个方法需要在fragment之后
     */
    open fun onBackPressAction() {
        finish()
    }
}
