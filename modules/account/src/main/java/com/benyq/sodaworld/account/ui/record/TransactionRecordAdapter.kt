package com.benyq.sodaworld.account.ui.record

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benyq.sodaworld.account.databinding.AccountItemTransactionGroupRecordBinding
import com.benyq.sodaworld.account.databinding.AccountItemTransactionRecordBinding
import com.chad.library.adapter4.BaseMultiItemAdapter

/**
 *
 * @author benyq
 * @date 2/20/2024
 *
 */
class TransactionRecordAdapter: BaseMultiItemAdapter<TransactionRecordGroupData>() {

    class HeaderVH(
        parent: ViewGroup,
        val binding: AccountItemTransactionGroupRecordBinding = AccountItemTransactionGroupRecordBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
    ) : RecyclerView.ViewHolder(binding.root)

    class VH(
        parent: ViewGroup,
        val binding: AccountItemTransactionRecordBinding = AccountItemTransactionRecordBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
    ) : RecyclerView.ViewHolder(binding.root)

    init {
        addItemType(TransactionRecordGroupData.HEADER_TYPE, object: OnMultiItemAdapterListener<TransactionRecordGroupData, HeaderVH> {
            override fun onBind(
                holder: HeaderVH,
                position: Int,
                item: TransactionRecordGroupData?,
            ) {
                item?.header?.run {
                    holder.binding.tvDate.text = date()
                    holder.binding.tvWeek.text = week()
                    holder.binding.tvTotalAmount.text = totalAmount()
                }
            }

            override fun onCreate(context: Context, parent: ViewGroup, viewType: Int): HeaderVH {
                return HeaderVH(parent)
            }

        }).addItemType(TransactionRecordGroupData.RECORD_TYPE, object: OnMultiItemAdapterListener<TransactionRecordGroupData, VH> {
            override fun onBind(holder: VH, position: Int, item: TransactionRecordGroupData?) {
                item?.record?.run {
                    holder.binding.consumeView.setDrawable(consumeType.resId)
                    holder.binding.consumeView.setBGColor(consumeType.colorId)

                    holder.binding.tvConsumeType.text = consumeType.message
                    holder.binding.tvPayAmount.text = record.amount
                    holder.binding.tvPaidType.text = paidType.message
                }
            }

            override fun onCreate(context: Context, parent: ViewGroup, viewType: Int): VH {
                return VH(parent)
            }

        }).onItemViewType { position, list -> list[position].type }
    }

}