package com.benyq.sodaworld.wanandroid

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.annotation.IdRes
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.benyq.sodaworld.base.extensions.fitsSystemWindows
import com.benyq.sodaworld.base.extensions.isAppearanceLightStatusBars
import com.benyq.sodaworld.base.extensions.visibleOrGone
import com.benyq.sodaworld.base.ui.BaseActivity
import com.benyq.sodaworld.wanandroid.databinding.ActivityWanAndroidBinding

class WanAndroidActivity : BaseActivity<ActivityWanAndroidBinding>() {

    private val viewModel by viewModels<ShareViewModel>()

    private val bottomNavigationItems = listOf(
        R.id.home_fragment,
        R.id.project_fragment,
        R.id.mine_fragment,
        R.id.category_fragment
    )

    override fun getLayoutId() = R.layout.activity_wan_android
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        window.fitsSystemWindows()
        isAppearanceLightStatusBars(true)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(dataBind.bottomNavigationView, navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            dataBind.bottomNavigationView.visibleOrGone(showBottomNavigation(destination.id))
        }

        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // app 退到后台，但不关闭
                moveTaskToBack(false)
            }
        })

        viewModel.queryUserInfo()
    }

    private fun showBottomNavigation(@IdRes id: Int): Boolean {
        return bottomNavigationItems.contains(id)
    }

    override fun onApplyWindow(view: View, windowInsets: WindowInsetsCompat): WindowInsetsCompat {
        return windowInsets
    }

}