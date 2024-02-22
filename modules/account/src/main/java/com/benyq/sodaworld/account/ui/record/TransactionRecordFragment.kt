package com.benyq.sodaworld.account.ui.record

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.benyq.sodaworld.account.R
import com.benyq.sodaworld.account.TransactionIntentExtra
import com.benyq.sodaworld.account.databinding.AccountFragmentTransactionRecordBinding
import com.benyq.sodaworld.base.extensions.showToast
import com.benyq.sodaworld.base.ui.BaseFragment
import com.benyq.sodaworld.base.ui.DataState
import com.benyq.sodaworld.base.ui.mvi.extension.collectOnLifecycle
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
class TransactionRecordFragment :
    BaseFragment<AccountFragmentTransactionRecordBinding>(R.layout.account_fragment_transaction_record) {

    private val viewModel by viewModels<TransactionRecordViewModel>()
    private val recordAdapter by lazy { TransactionRecordAdapter() }

    private val currentCalendar = Calendar.getInstance().apply {
        firstDayOfWeek = Calendar.MONDAY
    }

    private val timePickerView: TimePickerView by lazy {
        val selectedDate = Calendar.getInstance()
        val startDate: Calendar = Calendar.getInstance()
        startDate.set(2021, 0, 1)
        val endDate: Calendar = Calendar.getInstance()

        TimePickerBuilder(requireActivity()) { date, v ->
            currentCalendar.time = date
            queryRecords()

            val calendar = Calendar.getInstance()
            calendar.firstDayOfWeek = Calendar.MONDAY
            val year = calendar.get(Calendar.YEAR)
            calendar.time = date
            val currentYear = calendar.get(Calendar.YEAR)
            val currentMonth = calendar.get(Calendar.MONTH)

            dataBind.tvDate.text = if (currentYear != year) "$currentYear ${currentMonth + 1}月支出" else "${currentMonth + 1}月支出"

        }.setLayoutRes(R.layout.account_pickerview_custom_time){
            it.findViewById<TextView>(R.id.ivConfirm).setOnClickListener {
                timePickerView.returnData()
                timePickerView.dismiss()
            }
        }.setRangDate(startDate, endDate)
            .setLabel("年","月","日","时","分","秒")
            .setDate(selectedDate)
            .setType(booleanArrayOf(true, true, false, false, false, false)).build()

    }

    override fun onFragmentCreated(savedInstanceState: Bundle?) {
        dataBind.rvTransactionRecord.layoutManager = LinearLayoutManager(requireActivity())
        dataBind.rvTransactionRecord.adapter = recordAdapter
        recordAdapter.setOnItemClickListener { _,_,position ->
            recordAdapter.getItem(position)?.record?.let {
                findNavController().navigate(R.id.recordToDetail, Bundle().apply {
                    putParcelable(TransactionIntentExtra.transactionRecord, it.record)
                })
            }
        }

        dataBind.tvDate.text = getString(R.string.account_transaction_date, currentCalendar.get(Calendar.MONTH) + 1)
        dataBind.tvDate.setOnClickListener {
            timePickerView.setDate(currentCalendar)
            timePickerView.show(true)
        }

        dataBind.ivAdd.setOnClickListener {
            findNavController().navigate(R.id.recordToEdit)
        }
    }

    override fun onResume() {
        super.onResume()
        queryRecords()
    }
    override fun observe() {
        viewModel.recordData.collectOnLifecycle(viewLifecycleOwner, Lifecycle.State.STARTED) {
            when(it) {
                is DataState.Loading -> {}
                is DataState.Success -> {
                    recordAdapter.submitList(it.data)
                }
                is DataState.Error -> {
                    requireActivity().showToast(it.error)
                }
            }
        }
        viewModel.amountData.collectOnLifecycle(viewLifecycleOwner, Lifecycle.State.STARTED) {
            dataBind.tvPayAmount.text = it
        }
    }


    private fun queryRecords() {
        //不知道为什么, set(Calendar.HOUR_OF_DAY, 0) 一直是 12:00:00， 百度说是 JDK问题 ?
        //currentCalendar.timeInMillis 获取是 8:00:00
        //currentCalendar.time.time 获取是 12:00:00
        // 妈的，google模拟器的锅
        currentCalendar.set(Calendar.HOUR_OF_DAY, 0)
        currentCalendar.set(Calendar.MINUTE, 0)
        currentCalendar.set(Calendar.SECOND, 0)
        currentCalendar.set(Calendar.MILLISECOND, 0)


        val first = currentCalendar.getActualMinimum(Calendar.DAY_OF_MONTH)
        currentCalendar.set(Calendar.DAY_OF_MONTH, first)
        val start = currentCalendar.time.time

        val second: Int = currentCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        currentCalendar.set(Calendar.DAY_OF_MONTH, second)
        currentCalendar.set(Calendar.HOUR_OF_DAY, 23)
        currentCalendar.set(Calendar.MINUTE, 59)
        currentCalendar.set(Calendar.SECOND, 59)
        val end = currentCalendar.time.time

        viewModel.queryRecordByTime(start, end)
        Log.d("benyq","start: $start, end: $end")
    }

}