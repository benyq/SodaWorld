package com.benyq.sodaworld.wanandroid.login

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.benyq.sodaworld.base.extensions.dp
import com.benyq.sodaworld.base.extensions.hideLoading
import com.benyq.sodaworld.base.extensions.showLoading
import com.benyq.sodaworld.base.ui.BaseFragment
import com.benyq.sodaworld.base.ui.DataState
import com.benyq.sodaworld.base.ui.mvi.extension.collectOnLifecycle
import com.benyq.sodaworld.wanandroid.R
import com.benyq.sodaworld.wanandroid.ShareViewModel
import com.benyq.sodaworld.wanandroid.databinding.FragmentLoginBinding

/**
 *
 * @author benyq
 * @date 12/11/2023
 *
 */
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private val viewModel by viewModels<LoginViewModel>()
    private val shareViewModel by viewModels<ShareViewModel>(ownerProducer = { requireActivity() })

    override fun onFragmentCreated(savedInstanceState: Bundle?) {
        dataBind.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
        dataBind.btnLogin.setOnClickListener {
            viewModel.login()
                .collectOnLifecycle(viewLifecycleOwner) {
                    when(it) {
                        is DataState.Loading -> {
                            if (it.loading) {
                                requireActivity().showLoading()
                            }else {
                                requireActivity().hideLoading()
                            }
                        }
                        is DataState.Error -> {

                        }
                        is DataState.Success -> {
                            with(it.data) {
                                shareViewModel.updateUserInfo(nickname, coin, collectIds)
                                //开启一个获取用户信息的任务
                                shareViewModel.queryUserInfo()
                            }
                            findNavController().navigateUp()
                        }
                    }
                }
        }
        dataBind.etNickname.addTextChangedListener(onTextChanged = { _, _, _, _ ->
            onTextChanged()
        })
        dataBind.etPassword.addTextChangedListener(onTextChanged = { _, _, _, _ ->
            onTextChanged()
        })

    }

    override fun observe() {
    }

    override fun onApplyWindow(view: View, windowInsets: WindowInsetsCompat) {
        val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
        dataBind.flHead.updateLayoutParams<MarginLayoutParams> {
            height = 240.dp + insets.top
        }
        dataBind.ivBack.updateLayoutParams<MarginLayoutParams> {
            topMargin = 15.dp + insets.top
        }
    }

    private fun onTextChanged() {
        viewModel.username = dataBind.etNickname.text.toString().trim()
        viewModel.password = dataBind.etPassword.text.toString().trim()

        dataBind.btnLogin.isEnabled =
            viewModel.username.isNotEmpty() && viewModel.password.isNotEmpty()
    }
}