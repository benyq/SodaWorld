package com.benyq.sodaworld.account.ui.detail

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.benyq.sodaworld.account.R
import com.benyq.sodaworld.account.TransactionIntentExtra
import com.benyq.sodaworld.account.databinding.AccountFragmentTransactionRecordDetailBinding
import com.benyq.sodaworld.account.ui.record.TransactionRecordData
import com.benyq.sodaworld.base.extensions.showToast
import com.benyq.sodaworld.base.ui.BaseFragment
import com.benyq.sodaworld.base.ui.DataState
import com.benyq.sodaworld.base.ui.mvi.extension.collectOnLifecycle
import com.benyq.sodaworld.database.entity.TransactionRecord


/**
 *
 * @author benyq
 * @date 2022/10/14
 * @email 1520063035@qq.com
 *
 */
class TransactionRecordDetailFragment :
    BaseFragment<AccountFragmentTransactionRecordDetailBinding>(R.layout.account_fragment_transaction_record_detail) {

    private val vm by viewModels<TransactionRecordDetailViewModel>()

    private lateinit var recordData: TransactionRecordData

    override fun onFragmentCreated(savedInstanceState: Bundle?) {
        val record =
            arguments?.getParcelable<TransactionRecord>(TransactionIntentExtra.transactionRecord)
                ?: return
        recordData = TransactionRecordData(record)

        setupData(recordData)

        dataBind.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
        dataBind.tvDelete.setOnClickListener {
            vm.deleteRecord(record)
        }

        dataBind.tvEdit.setOnClickListener {
            findNavController().navigate(
                R.id.detailToEdit, Bundle().apply {
                    putParcelable(TransactionIntentExtra.transactionRecord, record)
                })
        }
    }

    override fun observe() {
        vm.deleteRecordResult.collectOnLifecycle(viewLifecycleOwner) {
            when (it) {
                is DataState.Loading -> {
                }
                is DataState.Success -> {
                    if (it.data) {
                        requireActivity().showToast("删除成功")
                        findNavController().navigateUp()
                    }else {
                        requireActivity().showToast("删除失败")
                    }
                }
                is DataState.Error -> {
                    requireActivity().showToast(it.error)
                }
            }
        }
    }

    private fun setupData(data: TransactionRecordData) {
        dataBind.tvAmount.text = data.record.amount
        dataBind.tvDate.text = data.date
        dataBind.tvPayType.text = data.paidType.message
        dataBind.tvPayNote.text = data.record.note
        dataBind.tvConsume.text = data.consumeType.message
        dataBind.consumeView.setDrawable(data.consumeType.resId)
        dataBind.consumeView.setBGColor(data.consumeType.colorId)
    }
}