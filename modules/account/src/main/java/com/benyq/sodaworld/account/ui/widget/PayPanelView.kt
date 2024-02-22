package com.benyq.sodaworld.account.ui.widget

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.benyq.sodaworld.account.PaidType
import com.benyq.sodaworld.account.databinding.AccountViewPayPanelBinding
import com.benyq.sodaworld.base.extensions.dp
import com.benyq.sodaworld.base.extensions.textTrim
import com.benyq.sodaworld.base.extensions.toNumberDefault
import java.text.SimpleDateFormat
import java.util.*

/**
 *
 * @author benyq
 * @date 2022/10/8
 * @email 1520063035@qq.com
 * 支付按键 面板
 */
class PayPanelView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var itemListener: PayPanelClickListener? = null

    private val binding: AccountViewPayPanelBinding
    private val dialAdapter: PayDialAdapter = PayDialAdapter {
        dialClick(it)
    }

    var paidType: PaidType = PaidType.cash
        set(value) {
            field = value
            binding.ivWallet.setImageResource(value.resId)
        }

    init {
        binding = AccountViewPayPanelBinding.inflate(LayoutInflater.from(context), this, true)
        initView()
    }

    fun setDefaultData(amount: String, paidCode: Int, note: String) {
        binding.tvPayAmount.text = amount
        binding.tvPayNote.setText(note)
        paidType = PaidType.fromCode(paidCode)
    }

    fun setDate(time: Long) {

        var dateText = SimpleDateFormat("MM-dd", Locale.CHINA).format(time)

        val calendarToday = Calendar.getInstance()
        calendarToday.time = Date()

        val currentCalendar = Calendar.getInstance()
        currentCalendar.timeInMillis = time

        if (currentCalendar.get(Calendar.DAY_OF_YEAR) == calendarToday.get(Calendar.DAY_OF_YEAR)) {
            dateText = "今天"
        } else if (currentCalendar.get(Calendar.DAY_OF_YEAR) == calendarToday.get(Calendar.DAY_OF_YEAR) - 1) {
            dateText = "昨天"
        }
        var dateIndex = -1
        dialAdapter.items.forEachIndexed { index, item ->
            if (item.code == "date") {
                dateIndex = index
                item.text = dateText
            }
        }
        if (dateIndex != -1) {
            binding.rvDial.adapter?.notifyItemChanged(dateIndex)
        }
    }

    private fun initView() {
        binding.rvDial.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.right = 6.dp
                outRect.left = 6.dp
                outRect.top = 10
                outRect.bottom = 10
            }
        })
        binding.rvDial.layoutManager = GridLayoutManager(context, 4)
        binding.rvDial.adapter = dialAdapter
        dialAdapter.submitList(buildModels())

        binding.ivWallet.setOnClickListener {
            itemListener?.onClickWallet()
        }
    }

    private fun dialClick(data: PanelButtonData) {
        var payAmount = binding.tvPayAmount.text.toString()

        fun calculate(text: String): String {
            var sum = 0f
            if (text.contains("+")) {
                val numbers = payAmount.split("+")
                numbers.forEachIndexed { _, s ->
                    sum += s.toNumberDefault(0f)
                }
            }else if (text.contains("-")) {
                val numbers = payAmount.split("-")
                numbers.forEachIndexed { index, s ->
                    if (index == 0) sum = s.toNumberDefault(0f)
                    else sum -= s.toNumberDefault(0f)
                }
            }else {
                return text
            }
            return sum.toString()
        }

        when (data.code) {
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" -> {
                val lastNumber = payAmount.split("[+\\-]".toRegex()).last()

                if (payAmount == "0") payAmount = data.text
                else if (lastNumber.matches(Regex("[0-9]*\\.[0-9]{2}"))){
                    //do nothing
                }
                else payAmount += data.text
            }
            "add", "minus" -> {
                val result = calculate(payAmount)
                payAmount = "$result${data.text}"
            }
            "dot" -> {
                val lastNumber = payAmount.split("[+\\-]".toRegex()).last()
                if (lastNumber.isEmpty()) payAmount += "0" + data.text
                else if (!lastNumber.contains(".")) payAmount += data.text
            }
            "delete" -> {
                payAmount = payAmount.substring(0, payAmount.length - 1)
                if (payAmount.isEmpty()) payAmount = "0"
            }
            "date" -> {
                itemListener?.onClickDate()
            }
            "done" -> {
                val note = binding.tvPayNote.textTrim()
                itemListener?.onClickDone(calculate(payAmount), paidType, note)
            }
        }
        binding.tvPayAmount.text = payAmount
    }

    interface PayPanelClickListener {
        fun onClickDate()
        fun onClickDone(amount: String, paidType: PaidType, note: String)
        fun onClickWallet()
    }
}