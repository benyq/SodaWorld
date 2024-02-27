package com.benyq.sodaworld.wanandroid.category

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.benyq.sodaworld.base.ui.BaseFragment
import com.benyq.sodaworld.base.ui.mvi.extension.collectOnLifecycle
import com.benyq.sodaworld.wanandroid.R
import com.benyq.sodaworld.wanandroid.databinding.FragmentCategoryBinding

/**
 *
 * @author benyq
 * @date 12/8/2023
 *
 */
class CategoryFragment : BaseFragment<FragmentCategoryBinding>(R.layout.fragment_category) {
    private val viewModel by viewModels<CategoryViewModel>()
    private val categoryTreeAdapter by lazy {
        CategoryTreeAdapter {
            val action = CategoryFragmentDirections.actionCategoryToArticles(it.id, it.name)
            findNavController().navigate(action)
        }
    }

    override fun onFragmentCreated(savedInstanceState: Bundle?) {
        dataBind.rvCategoryTree.adapter = categoryTreeAdapter
        dataBind.rvCategoryTree.layoutManager = LinearLayoutManager(requireActivity())
    }

    override fun observe() {
        viewModel.categoryTreeFlow.collectOnLifecycle(viewLifecycleOwner) {
            categoryTreeAdapter.submitList(it)
        }
    }
}