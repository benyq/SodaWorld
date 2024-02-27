package com.benyq.sodaworld.wanandroid.mine.collect

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.benyq.sodaworld.base.ui.DataState
import com.benyq.sodaworld.base.ui.mvi.extension.collectOnLifecycle
import com.benyq.sodaworld.wanandroid.BaseListFragment
import com.benyq.sodaworld.wanandroid.R
import com.benyq.sodaworld.wanandroid.article.ArticleListAdapter
import com.chad.library.adapter4.loadState.LoadState

/**
 *
 * @author benyq
 * @date 12/14/2023
 *
 */
class UserCollectFragment: BaseListFragment() {

    private val viewModel by viewModels<UserCollectViewModel>()
    private val adapter by lazy { ArticleListAdapter {
        findNavController().navigate(R.id.action_user_collect_to_article, Bundle().apply {
            putString("url", it.link)
            putString("title", it.title)
        })
    } }

    override fun onFragmentCreated(savedInstanceState: Bundle?) {
        super.onFragmentCreated(savedInstanceState)
        dataBind.tvTitle.text = "我的收藏"
        dataBind.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun observe() {
        viewModel.collectFlow.collectOnLifecycle(viewLifecycleOwner) {
            when (it) {
                is DataState.Success -> {
                    if (it.data.isFirst) {
                        adapter.submitList(it.data.articles)
                    }else {
                        adapter.addAll(it.data.articles)
                    }
                    helper.trailingLoadState = LoadState.NotLoading(it.data.isEnd)
                    finishRefresh()
                }
                is DataState.Error -> {
                    finishRefresh()
                }
                is DataState.Loading -> {

                }
            }
        }
    }

    override fun refresh() {
        viewModel.refresh()
    }

    override fun loadMore() {
        viewModel.loadMore()
    }

    override fun provideAdapter() = adapter

    override fun isAllowLoading(): Boolean {
        return !dataBind.swipeLayout.isRefreshing
    }
}