package com.benyq.sodaworld.wanandroid

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.benyq.sodaworld.base.ui.BaseFragment
import com.benyq.sodaworld.wanandroid.databinding.FragmentListBinding
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.QuickAdapterHelper
import com.chad.library.adapter4.loadState.trailing.TrailingLoadStateAdapter

/**
 *
 * @author benyq
 * @date 12/14/2023
 *
 */
abstract class BaseListFragment: BaseFragment<FragmentListBinding>(R.layout.fragment_list) {

    protected val helper by lazy {
        QuickAdapterHelper.Builder(provideAdapter())
            // 使用默认样式的尾部"加载更多"
            .setTrailingLoadStateAdapter(object : TrailingLoadStateAdapter.OnTrailingListener {
                override fun onLoad() {
                    loadMore()
                }

                override fun onFailRetry() {
                    loadMore()
                }

                override fun isAllowLoading(): Boolean {
                    // 是否允许触发“加载更多”，通常情况下，下拉刷新的时候不允许进行加载更多
                    return this@BaseListFragment.isAllowLoading()
                }
            }).build()
    }

    override fun onFragmentCreated(savedInstanceState: Bundle?) {
        dataBind.swipeLayout.setOnRefreshListener {
            refresh()
        }
        dataBind.recyclerView.adapter = helper.adapter
        //默认使用线性布局
        dataBind.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
    }

    protected fun finishRefresh() {
        dataBind.swipeLayout.isRefreshing = false
    }

    abstract fun refresh()
    abstract fun loadMore()

    abstract fun provideAdapter(): BaseQuickAdapter<*, *>

    abstract fun isAllowLoading(): Boolean

}