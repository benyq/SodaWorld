package com.benyq.sodaworld.wanandroid.project.articles

import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.benyq.sodaworld.base.extensions.dp
import com.benyq.sodaworld.base.extensions.gone
import com.benyq.sodaworld.base.ui.mvi.extension.collectOnLifecycle
import com.benyq.sodaworld.wanandroid.BaseListFragment
import com.benyq.sodaworld.wanandroid.R
import com.chad.library.adapter4.loadState.LoadState
import java.lang.Exception

/**
 *
 * @author benyq
 * @date 12/13/2023
 *
 */
class ProjectArticlesFragment : BaseListFragment() {

    companion object {
        fun newInstance(cid: Int): ProjectArticlesFragment {
            return ProjectArticlesFragment().apply {
                val bundle = Bundle()
                bundle.putInt("cid", cid)
                bundle.putString("title", "")
                arguments = bundle
            }
        }
    }

    private val args: ProjectArticlesFragmentArgs by navArgs()
    private var cid: Int = 0

    private val viewModel by viewModels<ProjectArticlesViewModel>(
        factoryProducer = { ProjectArticlesViewModelFactory(cid) })

    override fun onFragmentCreated(savedInstanceState: Bundle?) {
        cid = args.cid
        super.onFragmentCreated(savedInstanceState)

        dataBind.tvTitle.text = args.title
        dataBind.clHead.gone()

        dataBind.recyclerView.setPadding(10.dp, 0, 10.dp, 0)
        dataBind.recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                DividerItemDecoration.VERTICAL
            ).apply {
                setDrawable(ColorDrawable(Color.BLACK))
            })
        dataBind.recyclerView.addItemDecoration(object: ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                if (parent.getChildLayoutPosition(view) == 0) {
                    outRect.top = 10.dp
                }else {
                    outRect.top = 5.dp
                }
                outRect.bottom = 5.dp
            }
        })
        dataBind.swipeLayout.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    override fun refresh() {
        viewModel.refresh()
    }

    override fun loadMore() {
        viewModel.loadMore()
    }

    override fun provideAdapter() = viewModel.articleAdapter
    override fun isAllowLoading(): Boolean {
        return try {
            !dataBind.swipeLayout.isRefreshing
        }catch (e: Exception) {
            true
        }
    }

    override fun observe() {
        viewModel.state.collectOnLifecycle(viewLifecycleOwner) {
            if (it.isFirst) {
                viewModel.articleAdapter.submitList(it.articles)
            } else {
                viewModel.articleAdapter.addAll(it.articles)
            }
            helper.trailingLoadState = LoadState.NotLoading(it.isEnd)
            dataBind.swipeLayout.isRefreshing = false
        }
        viewModel.event.collectOnLifecycle(viewLifecycleOwner) {
            when (it) {
                is ProjectEvent.NavigateToArticle -> {
                    it.article?.let { article ->
                        findNavController().navigate(
                            R.id.action_project_to_article,
                            Bundle().apply {
                                putString("url", article.link)
                                putString("title", article.title)
                            })
                    }
                }
            }
        }
    }

}