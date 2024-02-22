package com.benyq.sodaworld.account

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.core.net.toUri
import com.benyq.sodaworld.account.ui.TransactionActivity
import com.benyq.sodaworld.base.ApplicationDelegate
import com.benyq.sodaworld.base.extensions.fromN_MR1

/**
 *
 * @author benyq
 * @date 2/20/2024
 *
 */
class AccountApplicationDelegate: ApplicationDelegate {
    override fun attachBaseContext(application: Application, context: Context?) {

    }

    override fun onCreate(application: Application) {
        if (fromN_MR1()) {
            val shortScan = ShortcutInfoCompat.Builder(application, "transaction")//唯一标识id
                .setShortLabel("记账")//短标签
                .setIcon(IconCompat.createWithResource(application, R.drawable.ic_transaction))//图标
                //跳转的目标，定义Activity
                .setIntent(Intent(Intent.ACTION_MAIN, null, application, TransactionActivity::class.java).apply {
                    putExtra(TransactionIntentExtra.shortcut, true)
                })
//                .setIntent(Intent("android.intent.action.addRecord", "www.benyq.account.com".toUri(), application, TransactionActivity::class.java))
                .build()
            //执行添加操作
            ShortcutManagerCompat.addDynamicShortcuts(application, mutableListOf(shortScan))
        }
    }
}