package com.benyq.sodaworld.account.ui.widget

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benyq.sodaworld.account.databinding.AccountItemPayPanelButtonBinding
import com.benyq.sodaworld.base.extensions.throttleClick
import com.chad.library.adapter4.BaseQuickAdapter

/**
 *
 * @author benyq
 * @date 2/20/2024
 *
 */
class PayDialAdapter(private val itemClickAction: (PanelButtonData)->Unit) : BaseQuickAdapter<PanelButtonData, PayDialAdapter.VH>() {

    class VH(
        parent: ViewGroup,
        val binding: AccountItemPayPanelButtonBinding = AccountItemPayPanelButtonBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: VH, position: Int, item: PanelButtonData?) {
        item?.let {
            holder.binding.tvContent.text = it.text
            holder.binding.item.throttleClick(interval = 200) {
                itemClickAction.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH {
        return VH(parent)
    }
}