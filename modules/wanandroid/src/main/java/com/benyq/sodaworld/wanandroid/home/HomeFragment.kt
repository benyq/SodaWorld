package com.benyq.sodaworld.wanandroid.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.benyq.sodaworld.base.ui.BaseFragment
import com.benyq.sodaworld.base.ui.mvi.extension.collectOnLifecycle
import com.benyq.sodaworld.wanandroid.R
import com.benyq.sodaworld.wanandroid.databinding.FragmentHomeBinding
import com.chad.library.adapter4.QuickAdapterHelper
import com.chad.library.adapter4.loadState.LoadState
import com.chad.library.adapter4.loadState.trailing.TrailingLoadStateAdapter
import kotlinx.coroutines.flow.distinctUntilChanged

/**
 *
 * @author benyq
 * @date 12/8/2023
 *
 */
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel by viewModels<HomeViewModel>()
    private val articleAdapter by lazy {
        BannerArticleAdapter { article, banner->
            findNavController().navigate(R.id.action_home_to_article, Bundle().apply {
                article?.let {
                    putString("url", it.link)
                    putString("title", it.title)
                }
                banner?.let {
                    putString("url", it.url)
                    putString("title", it.title)
                }
            })
        }
    }
    private val helper = QuickAdapterHelper.Builder(articleAdapter)
        // 使用默认样式的尾部"加载更多"
        .setTrailingLoadStateAdapter(object : TrailingLoadStateAdapter.OnTrailingListener {
            override fun onLoad() {
                viewModel.loadMore()
            }

            override fun onFailRetry() {
                viewModel.loadMore()
            }

            override fun isAllowLoading(): Boolean {
                // 是否允许触发“加载更多”，通常情况下，下拉刷新的时候不允许进行加载更多
                return !dataBind.swipeLayout.isRefreshing
            }
        }).build()

    override fun onFragmentCreated(savedInstanceState: Bundle?) {
        dataBind.swipeLayout.setOnRefreshListener {
            viewModel.refresh()
        }
        dataBind.rvArticle.setViewTreeLifecycleOwner(viewLifecycleOwner)
        dataBind.rvArticle.layoutManager = LinearLayoutManager(requireActivity())
        dataBind.rvArticle.adapter = helper.adapter

        dataBind.ivSearch.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_search)
        }
    }


    override fun observe() {
        viewModel.articleFlow
            .distinctUntilChanged()
            .collectOnLifecycle(viewLifecycleOwner) { (data, isFirst, isOver) ->
                if (isFirst) {
                    articleAdapter.submitList(data)
                }else {
                    articleAdapter.addAll(data)
                }
                helper.trailingLoadState = LoadState.NotLoading(isOver)
                dataBind.swipeLayout.isRefreshing = false
            }
        viewModel.refreshingState
            .collectOnLifecycle(viewLifecycleOwner) {
            dataBind.swipeLayout.isRefreshing = it
        }
    }
}