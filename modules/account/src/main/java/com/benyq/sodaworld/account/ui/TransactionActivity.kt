package com.benyq.sodaworld.account.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.benyq.sodaworld.account.R
import com.benyq.sodaworld.account.TransactionIntentExtra
import com.benyq.sodaworld.account.databinding.AccountActivityTransactionBinding
import com.benyq.sodaworld.base.extensions.statusBarColor
import com.benyq.sodaworld.base.ui.BaseActivity

/**
 * @author benyq
 * @date 2024/02/20
 * @constructor 创建[TransactionActivity]
 */
class TransactionActivity : BaseActivity<AccountActivityTransactionBinding>() {

    override fun getLayoutId() = R.layout.account_activity_transaction

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        statusBarColor(Color.WHITE)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    override fun onStart() {
        super.onStart()
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        val shortcut = intent?.getBooleanExtra(TransactionIntentExtra.shortcut, false) ?: false
        if (shortcut) {
            findNavController(R.id.transaction_fragment).navigate(
                R.id.transactionAddRecordFragment, Bundle().apply {
                    putBoolean(TransactionIntentExtra.shortcut, true)
                }, NavOptions.Builder()
                    .setLaunchSingleTop(true)
                    .build()
            )
        }
    }
}