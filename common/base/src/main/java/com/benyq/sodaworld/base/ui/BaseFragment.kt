package com.benyq.sodaworld.base.ui

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 *
 * @author benyq
 * @date 7/14/2023
 *
 */
abstract class BaseFragment<DB : ViewDataBinding>(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    private var _dataBind: DB? = null
    protected val dataBind: DB get() = _dataBind!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _dataBind = DataBindingUtil.bind(view)
        lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                _dataBind = null
            }
        })
        ViewCompat.setOnApplyWindowInsetsListener(dataBind.root) { view, windowInsets ->
            onApplyWindow(view, windowInsets)
            WindowInsetsCompat.CONSUMED
        }
        onFragmentCreated(savedInstanceState)
        observe()
        initData()
    }

    abstract fun onFragmentCreated(savedInstanceState: Bundle?)

    abstract fun observe()

    open fun initData(){}

    open fun onApplyWindow(view: View, windowInsets: WindowInsetsCompat){
        val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
        view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            topMargin += insets.top
        }
    }
}