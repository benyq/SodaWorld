package com.benyq.sodaworld.account.ui.add

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benyq.sodaworld.account.ConsumeType
import com.benyq.sodaworld.account.databinding.AccountItemConsumeTypeBinding
import com.benyq.sodaworld.base.extensions.visibleOrInvisible
import com.chad.library.adapter4.BaseQuickAdapter

/**
 *
 * @author benyq
 * @date 2/20/2024
 *
 */
class ConsumeAdapter: BaseQuickAdapter<ConsumeType, ConsumeAdapter.VH>() {

    var oldPosition = 0

    class VH(parent: ViewGroup,
             val binding: AccountItemConsumeTypeBinding = AccountItemConsumeTypeBinding.inflate(
                 LayoutInflater.from(parent.context), parent, false
             ),
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: VH, position: Int, item: ConsumeType?) {
        item?.run {
            holder.binding.consumeView.setBGColor(colorId)
            holder.binding.consumeView.setDrawable(resId)
            holder.binding.ivCover.visibleOrInvisible(selected)
            holder.binding.tvContent.text = message
        }
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH {
        return VH(parent)
    }

    fun updateSelected(position: Int) {
        if (position == oldPosition) return
        getItem(oldPosition)?.selected = false
        getItem(position)?.selected = true
        notifyItemChanged(oldPosition)
        notifyItemChanged(position)
        oldPosition = position
    }
}