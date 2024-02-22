package com.benyq.sodaworld.account.ui.add

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benyq.sodaworld.account.PaidType
import com.benyq.sodaworld.account.databinding.AccountDialogBottomPaidTypeBinding
import com.benyq.sodaworld.account.databinding.AccountItemPaidTypeBinding
import com.benyq.sodaworld.base.extensions.linear
import com.chad.library.adapter4.BaseQuickAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog

/**
 *
 * @author benyq
 * @date 2022/10/11
 * @email 1520063035@qq.com
 *
 */
class BottomPaidTypeDialog(context: Context, val onItemClick: (item: PaidType) -> Unit) : BottomSheetDialog(context){
    private lateinit var binding: AccountDialogBottomPaidTypeBinding
    private val payTypeAdapter by lazy { PayTypeAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AccountDialogBottomPaidTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvPaidType.linear().adapter = payTypeAdapter
        payTypeAdapter.submitList(PaidType.paidTypes)
        payTypeAdapter.setOnItemClickListener { _, _, position ->
            payTypeAdapter.getItem(position)?.let {
                onItemClick(it)
                dismiss()
            }
        }
    }


}


class PayTypeAdapter: BaseQuickAdapter<PaidType, PayTypeAdapter.VH>() {
    class VH(parent: ViewGroup, val binding: AccountItemPaidTypeBinding = AccountItemPaidTypeBinding.inflate(
        LayoutInflater.from(parent.context), parent, false)): RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: VH, position: Int, item: PaidType?) {
        item?.run {
            holder.binding.ivPaidType.setImageResource(resId)
            holder.binding.tvPaidType.text = message
        }
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH {
       return VH(parent)
    }
}