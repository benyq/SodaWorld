package com.benyq.sodaworld.account

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

/**
 *
 * @author benyq
 * @date 2022/10/10
 * @email 1520063035@qq.com
 *
 */

data class ConsumeType(
    val code: Int,
    val message: String,
    val order: Int,
    @DrawableRes val resId: Int,
    @ColorRes val colorId: Int,
    var selected: Boolean = false
) {

    companion object {
        private val consumeTypes = listOf(
            ConsumeType(0, "快餐", 1, R.drawable.ic_food1, R.color.orange_500, true),
            ConsumeType(1, "聚餐", 2, R.drawable.ic_food2, R.color.deep_orange_500),
            ConsumeType(2, "生鲜", 3, R.drawable.ic_food3, R.color.lime_500),
            ConsumeType(3, "超市", 4, R.drawable.ic_food4, R.color.yellow_500),
            ConsumeType(4, "网购", 5, R.drawable.ic_shopping1, R.color.amber_500),
            ConsumeType(5, "交通", 6, R.drawable.ic_traffic1, R.color.green_500),
            ConsumeType(6, "房租", 7, R.drawable.ic_house1, R.color.teal_500),
            ConsumeType(7, "娱乐", 4, R.drawable.ic_entertainment1, R.color.cyan_500),
            ConsumeType(8, "医院", 7, R.drawable.ic_hospital1, R.color.light_blue_500)
        ).sortedBy { it.order }

        fun consumeTypes() = consumeTypes.map { it.copy() }

        val singleFood = consumeTypes[0]

        fun fromCode(code: Int): ConsumeType {
            return consumeTypes.find { it.code == code }
                ?: throw IllegalArgumentException("错误消费码, code: $code")
        }

    }

}

data class PaidType(val code: Int, val message: String, @DrawableRes val resId: Int) {
    companion object {
        val paidTypes = listOf(
            PaidType(0, "现金", R.drawable.ic_pay_cash),
            PaidType(1, "信用卡", R.drawable.ic_pay_credit_card),
            PaidType(2, "支付宝", R.drawable.ic_pay_ali),
            PaidType(3, "微信", R.drawable.ic_pay_wechat)
        )

        fun fromCode(code: Int): PaidType {
            return paidTypes.find { it.code == code }
                ?: throw IllegalArgumentException("错误支付码, code: $code")
        }

        val cash = paidTypes[0]
    }
}