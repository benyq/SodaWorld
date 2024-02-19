package com.benyq.sodaworld.base

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.PackageManager.GET_META_DATA
import com.benyq.sodaworld.base.extensions.appCtx

/**
 *
 * @author benyq
 * @date 8/23/2023
 *
 */
open class BaseApplication: Application() {
    private var delegates: List<ApplicationDelegate>? = null
    companion object {
        private const val MODULE_META_KEY = "ApplicationDelegate"
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        base?.let {
            delegates = findApplicationDelegate(it)
        }
        delegates?.forEach { it.attachBaseContext(this, base)}
    }

    override fun onCreate() {
        super.onCreate()
        appCtx = this
        delegates?.forEach { it.onCreate(this) }
    }

    private fun findApplicationDelegate(context: Context): List<ApplicationDelegate> {
        val delegates: MutableList<ApplicationDelegate> = ArrayList()
        try {
            val pm = context.packageManager
            val packageName = context.packageName
            val info = pm.getApplicationInfo(packageName, GET_META_DATA)
            if (info.metaData != null) {
                for (key in info.metaData.keySet()) {
                    val value = info.metaData[key]
                    if (MODULE_META_KEY == value) {
                        val delegate: ApplicationDelegate = initApplicationDelegate(key)
                        delegates.add(delegate)
                    }
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return delegates
    }

    private fun initApplicationDelegate(className: String): ApplicationDelegate {
        var clazz: Class<*>? = null
        var instance: Any? = null
        try {
            clazz = Class.forName(className)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
        try {
            instance = clazz?.newInstance()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (instance !is ApplicationDelegate) {
            throw RuntimeException("不能获取 " + ApplicationDelegate::class.java.name + " 的实例 " + instance)
        }
        return instance
    }
}

interface ApplicationDelegate {
    fun attachBaseContext(application: Application?, context: Context?)
    fun onCreate(application: Application?)
}