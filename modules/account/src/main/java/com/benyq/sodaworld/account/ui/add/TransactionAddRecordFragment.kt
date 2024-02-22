package com.benyq.sodaworld.account.ui.add

import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.benyq.sodaworld.account.ConsumeType
import com.benyq.sodaworld.account.ConsumeType.Companion.consumeTypes
import com.benyq.sodaworld.account.PaidType
import com.benyq.sodaworld.account.R
import com.benyq.sodaworld.account.TransactionIntentExtra
import com.benyq.sodaworld.account.databinding.AccountFragmentTransactionAddRecordBinding
import com.benyq.sodaworld.account.ui.widget.PayPanelView
import com.benyq.sodaworld.base.extensions.showToast
import com.benyq.sodaworld.base.ui.BaseFragment
import com.benyq.sodaworld.database.entity.TransactionRecord
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.view.TimePickerView
import kotlinx.coroutines.launch
import java.util.Calendar

/**
 *
 * @author benyq
 * @date 2/20/2024
 *
 */
class TransactionAddRecordFragment :
    BaseFragment<AccountFragmentTransactionAddRecordBinding>(R.layout.account_fragment_transaction_add_record) {

    private val vm: EditRecordViewModel by viewModels<EditRecordViewModel>()
    private val consumeAdapter by lazy { ConsumeAdapter() }
    private lateinit var paidTypeDialog: BottomPaidTypeDialog

    private val currentCalendar = Calendar.getInstance().apply {
        firstDayOfWeek = Calendar.MONDAY
    }

    private var record: TransactionRecord? = null
    private var currentConsumeType = ConsumeType.singleFood


    private val timePickerView: TimePickerView by lazy {
        val selectedDate = Calendar.getInstance()
        val startDate: Calendar = Calendar.getInstance()
        startDate.set(startDate.get(Calendar.YEAR), 0, 1)
        val endDate: Calendar = Calendar.getInstance()

        TimePickerBuilder(requireActivity()) { date, v ->
            currentCalendar.time = date
            dataBind.payPanelView.setDate(currentCalendar.timeInMillis)
        }.setLayoutRes(R.layout.account_pickerview_custom_time) {
            it.findViewById<TextView>(R.id.ivConfirm).setOnClickListener {
                timePickerView.returnData()
                timePickerView.dismiss()
            }
        }.setRangDate(startDate, endDate)
            .setLabel("年", "月", "日", "时", "分", "秒")
            .setDate(selectedDate)
            .setType(booleanArrayOf(false, true, true, false, false, false)).build()
    }

    override fun onFragmentCreated(savedInstanceState: Bundle?) {
        val consumeTypes = consumeTypes()
        record = arguments?.getParcelable(TransactionIntentExtra.transactionRecord)
        record?.let {
            dataBind.payPanelView.setDefaultData(it.amount, it.paidType, it.note)
            dataBind.payPanelView.setDate(it.createTime)
            currentCalendar.timeInMillis = it.createTime
            currentConsumeType = ConsumeType.fromCode(it.consumeType)
            consumeTypes.forEach { consume ->
                consume.selected = consume.code == it.consumeType
            }
        }

        dataBind.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        dataBind.rvConsume.adapter = consumeAdapter
        dataBind.rvConsume.layoutManager = GridLayoutManager(requireActivity(), 4)
        consumeAdapter.submitList(consumeTypes)
        consumeAdapter.setOnItemClickListener { _, _, position ->
            consumeAdapter.updateSelected(position)
            currentConsumeType = consumeTypes[position]
        }

        dataBind.payPanelView.itemListener = object : PayPanelView.PayPanelClickListener {
            override fun onClickDate() {
                timePickerView.show()
            }

            override fun onClickDone(amount: String, paidType: PaidType, note: String) {
                if (record == null) {
                    vm.addTransactionRecord(
                        amount,
                        currentConsumeType,
                        paidType,
                        note,
                        currentCalendar.timeInMillis
                    )
                } else {
                    record?.let {
                        it.paidType = paidType.code
                        it.consumeType = currentConsumeType.code
                        it.note = note
                        it.createTime = currentCalendar.timeInMillis
                    }
                    vm.updateTransactionRecord(record!!, amount)
                }
            }

            override fun onClickWallet() {
                paidTypeDialog.show()
            }
        }
        paidTypeDialog = BottomPaidTypeDialog(requireActivity()) {
            dataBind.payPanelView.paidType = it
        }
        paidTypeDialog.create()
    }

    override fun observe() {
        lifecycleScope.launch {
            vm.addRecordResult.collect { data->
                requireActivity().showToast(data.message)
                if (data.result) {
                    if (data.isEdit) {
                        findNavController().popBackStack(R.id.transactionFragment, false)
                    }else {

                        /** 从Launch页面快捷方式跳转 */
                        val shortCutLaunch = arguments?.getBoolean(TransactionIntentExtra.shortcut, false)?: false
                        if (shortCutLaunch) {
                            requireActivity().finish()
                        }else {
                            findNavController().navigateUp()
                        }
                    }
                }

            }
        }
    }
}