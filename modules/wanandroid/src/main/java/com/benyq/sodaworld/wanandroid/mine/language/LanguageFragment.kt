package com.benyq.sodaworld.wanandroid.mine.language

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.benyq.sodaworld.base.extensions.invisible
import com.benyq.sodaworld.base.extensions.showToast
import com.benyq.sodaworld.base.extensions.visible
import com.benyq.sodaworld.base.ui.BaseFragment
import com.benyq.sodaworld.wanandroid.R
import com.benyq.sodaworld.wanandroid.databinding.FragmentLanguageBinding
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder

/**
 *
 * @author benyq
 * @date 12/18/2023
 *
 */
class LanguageFragment : BaseFragment<FragmentLanguageBinding>(R.layout.fragment_language) {

    private val viewModel by viewModels<LanguageViewModel>(ownerProducer = { requireActivity() })

    private var currentLanguage: LanguageModel? = null
    private val languageAdapter = object : BaseQuickAdapter<LanguageModel, QuickViewHolder>() {
        override fun onBindViewHolder(
            holder: QuickViewHolder,
            position: Int,
            item: LanguageModel?,
        ) {
            holder.itemView.setOnClickListener {
                currentLanguage = item
                notifyDataSetChanged()
            }
            if (currentLanguage == item) {
                holder.getView<ImageView>(R.id.iv_check).visible()
            } else {
                holder.getView<ImageView>(R.id.iv_check).invisible()
            }
            holder.getView<TextView>(R.id.tv_content).text = item?.name
        }

        override fun onCreateViewHolder(
            context: Context,
            parent: ViewGroup,
            viewType: Int,
        ): QuickViewHolder {
            return QuickViewHolder(R.layout.item_language, parent)
        }
    }

    override fun onFragmentCreated(savedInstanceState: Bundle?) {
        dataBind.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
        dataBind.btnApply.setOnClickListener {
            applyLanguage()
        }
        dataBind.rvLanguage.layoutManager = LinearLayoutManager(requireActivity())
        dataBind.rvLanguage.adapter = languageAdapter

        currentLanguage = viewModel.currentLanguage
        languageAdapter.submitList(viewModel.supportLanguage)
    }

    override fun observe() {

    }

    private fun applyLanguage() {
        AlertDialog.Builder(requireActivity())
            .setTitle(R.string.language_change_confirm)
            .setPositiveButton(R.string.confirm) { dialog, which ->
                currentLanguage?.let {
                    AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(it.language))
                } ?: requireActivity().showToast(getString(R.string.language_not_selected))
            }
            .setNegativeButton(R.string.cancel, null)
            .create().show()
    }

}