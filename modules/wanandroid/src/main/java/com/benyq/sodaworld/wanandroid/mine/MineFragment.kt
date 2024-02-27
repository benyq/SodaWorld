package com.benyq.sodaworld.wanandroid.mine

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.benyq.sodaworld.base.ui.BaseFragment
import com.benyq.sodaworld.base.ui.mvi.extension.collectOnLifecycle
import com.benyq.sodaworld.wanandroid.R
import com.benyq.sodaworld.wanandroid.ShareViewModel
import com.benyq.sodaworld.wanandroid.databinding.FragmentMineBinding
import com.benyq.sodaworld.wanandroid.mine.language.LanguageViewModel

/**
 *
 * @author benyq
 * @date 12/8/2023
 *
 */
class MineFragment : BaseFragment<FragmentMineBinding>(R.layout.fragment_mine) {

    private val shareViewModel by viewModels<ShareViewModel>(ownerProducer = { requireActivity() })
    private val languageViewModel by viewModels<LanguageViewModel>(ownerProducer = { requireActivity() })

    override fun onFragmentCreated(savedInstanceState: Bundle?) {
        dataBind.cvUser.setOnClickListener {
            findNavController().navigate(R.id.action_mine_to_login)
        }
        dataBind.btnLogout.setOnClickListener {
            shareViewModel.logout()
        }
        dataBind.llCollect.setOnClickListener {
            findNavController().navigate(R.id.action_mine_to_user_collect)
        }
        dataBind.scLanguage.setOnClickListener {
            findNavController().navigate(R.id.action_mine_to_language)
        }
        dataBind.scLanguage.setContent(languageViewModel.currentLanguage?.name)
    }



    override fun observe() {
        shareViewModel.userInfo.collectOnLifecycle(viewLifecycleOwner) {
            dataBind.tvNickname.text = it.nickname
            dataBind.tvCollect.text = it.collectIds.size.toString()
            dataBind.tvCoin.text = it.coin
            dataBind.tvRank.text = it.rank
        }
    }
}