package com.benyq.sodaworld.wanandroid.project

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.benyq.sodaworld.base.extensions.overScrollNever
import com.benyq.sodaworld.base.ui.BaseFragment
import com.benyq.sodaworld.base.ui.mvi.extension.collectOnLifecycle
import com.benyq.sodaworld.wanandroid.R
import com.benyq.sodaworld.wanandroid.databinding.FragmentProjectBinding
import com.google.android.material.tabs.TabLayoutMediator

/**
 *
 * @author benyq
 * @date 12/8/2023
 *
 */
class ProjectTreeFragment : BaseFragment<FragmentProjectBinding>(R.layout.fragment_project) {

    private val viewModel by viewModels<ProjectTreeViewModel>()

    override fun onFragmentCreated(savedInstanceState: Bundle?) {
        dataBind.tabs.tabCount
        dataBind.vp2.overScrollNever()
    }

    override fun observe() {
        viewModel.tree.collectOnLifecycle(viewLifecycleOwner) {
            if (it.isEmpty()) return@collectOnLifecycle
            dataBind.vp2.adapter = ProjectTreeAdapter(it, this)
            TabLayoutMediator(dataBind.tabs, dataBind.vp2) { tab, position ->
                tab.text = it[position].name
            }.attach()
        }
    }
}