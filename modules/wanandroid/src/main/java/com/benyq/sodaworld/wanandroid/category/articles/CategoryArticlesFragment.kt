package com.benyq.sodaworld.wanandroid.category.articles

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.benyq.sodaworld.base.ui.DataState
import com.benyq.sodaworld.base.ui.mvi.extension.collectOnLifecycle
import com.benyq.sodaworld.wanandroid.BaseListFragment
import com.benyq.sodaworld.wanandroid.article.ArticleListAdapter
import com.benyq.wanandroid.ui.category.articles.CategoryArticlesViewModel
import com.benyq.wanandroid.ui.category.articles.CategoryArticlesViewModelFactory
import com.chad.library.adapter4.loadState.LoadState
import com.benyq.sodaworld.wanandroid.R

/**
 *
 * @author benyq
 * @date 12/14/2023
 *
 */
class CategoryArticlesFragment: BaseListFragment() {

    private val viewModel by viewModels<CategoryArticlesViewModel>(ownerProducer = { this }, factoryProducer = { CategoryArticlesViewModelFactory(cid) })
    private val args: CategoryArticlesFragmentArgs by navArgs()
    private var cid: Int = 0

    private val adapter by lazy { ArticleListAdapter {
        findNavController().navigate(R.id.action_category_articles_to_article, Bundle().apply {
            putString("url", it.link)
            putString("title", it.title)
        })
    } }

    override fun onFragmentCreated(savedInstanceState: Bundle?) {
        super.onFragmentCreated(savedInstanceState)
        cid = args.cid
        dataBind.tvTitle.text = args.title
        dataBind.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun observe() {
        viewModel.articlesFlow.collectOnLifecycle(viewLifecycleOwner) {
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