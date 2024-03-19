package com.benyq.sodaworld.wanandroid.search

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.benyq.sodaworld.base.extensions.dp
import com.benyq.sodaworld.base.extensions.visibleOrGone
import com.benyq.sodaworld.base.ui.BaseFragment
import com.benyq.sodaworld.base.ui.DataState
import com.benyq.sodaworld.base.ui.mvi.extension.collectOnLifecycle
import com.benyq.sodaworld.wanandroid.R
import com.benyq.sodaworld.wanandroid.databinding.FragmentSearchBinding
import com.chad.library.adapter4.QuickAdapterHelper
import com.chad.library.adapter4.loadState.LoadState
import com.chad.library.adapter4.loadState.trailing.TrailingLoadStateAdapter
import com.google.android.flexbox.FlexboxLayoutManager

/**
 *
 * @author benyq
 * @date 12/14/2023
 *
 */
class SearchFragment: BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    private val viewModel by viewModels<SearchViewModel>()

    private val helper by lazy {
        QuickAdapterHelper.Builder(viewModel.articleAdapter)
            // 使用默认样式的尾部"加载更多"
            .setTrailingLoadStateAdapter(object : TrailingLoadStateAdapter.OnTrailingListener {
                override fun onLoad() {
                    viewModel.loadMore()
                }

                override fun onFailRetry() {
                    viewModel.loadMore()
                }

                override fun isAllowLoading(): Boolean {
                    return true
                }
            }).build()
    }


    override fun onFragmentCreated(savedInstanceState: Bundle?) {
        val divider = object: ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.bottom = 5.dp
                outRect.right = 5.dp
            }
        }
        dataBind.rvHotKey.layoutManager = FlexboxLayoutManager(requireActivity())
        dataBind.rvHotKey.adapter = viewModel.hotKeyAdapter
        dataBind.rvHotKey.addItemDecoration(divider)

        dataBind.rvSearchHistory.layoutManager = FlexboxLayoutManager(requireActivity())
        dataBind.rvSearchHistory.addItemDecoration(divider)
        dataBind.rvSearchHistory.adapter = viewModel.searchHistoryAdapter

        dataBind.rvSearchResult.layoutManager = LinearLayoutManager(requireActivity())
        dataBind.rvSearchResult.adapter = helper.adapter

        dataBind.tvCancel.setOnClickListener {
            findNavController().navigateUp()
        }
        dataBind.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.search(dataBind.etSearch.text.toString())
            }
            true
        }
        dataBind.etSearch.doOnTextChanged { text, start, before, count ->
            dataBind.ivCancelDelete.visibleOrGone(text.isNullOrBlank().not())
            if (text.isNullOrEmpty()) {
                viewModel.exitSearchResult()
                viewModel.articleAdapter.submitList(emptyList())
            }

        }
        dataBind.ivCancelDelete.setOnClickListener {
            dataBind.etSearch.setText("")
        }
        dataBind.ivDelete.setOnClickListener {
            viewModel.clearSearchHistory()
        }
    }

    override fun observe() {
        viewModel.currentType.collectOnLifecycle(viewLifecycleOwner) {
            dataBind.llSearch.visibleOrGone(it == SearchViewModel.TYPE_HISTORY)
            dataBind.rvSearchResult.visibleOrGone(it == SearchViewModel.TYPE_RESULT)
        }
        viewModel.searchEventFlow.collectOnLifecycle(viewLifecycleOwner) {
            when(it) {
                is SearchEvent.Search -> {
                    dataBind.etSearch.setText(it.keyword)
                    viewModel.search(it.keyword)
                }
                is SearchEvent.toArticle -> {
                    findNavController().navigate(R.id.action_search_to_article, Bundle().apply {
                        putString("url", it.article.link)
                        putString("title", it.article.title)
                    })
                }
            }
        }
        viewModel.articlesFlow.collectOnLifecycle(viewLifecycleOwner) {
            when(it) {
                is DataState.Loading -> {
                }
                is DataState.Success -> {
                    val state = it.data
                    if (state.isFirst) {
                        viewModel.articleAdapter.submitList(state.articles)
                    }else {
                        viewModel.articleAdapter.addAll(state.articles)
                    }
                    helper.trailingLoadState = LoadState.NotLoading(state.isEnd)
                }
                is DataState.Error -> {
                }
            }
        }
        viewModel.searchStateFlow.collectOnLifecycle(viewLifecycleOwner) {
            dataBind.ivDelete.alpha = if (it.isSearchHistoryEmpty) 0.4f else 1f
        }
    }
}