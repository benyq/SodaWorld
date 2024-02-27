package com.benyq.sodaworld.wanandroid.article

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.benyq.sodaworld.base.ui.BaseFragment
import com.benyq.sodaworld.wanandroid.R
import com.benyq.sodaworld.wanandroid.databinding.FragmentArticleBinding
import com.benyq.sodaworld.wanandroid.webview.RobustWebView
import com.benyq.sodaworld.wanandroid.webview.WebViewManager


/**
 *
 * @author benyq
 * @date 12/8/2023
 *
 */
class ArticleFragment: BaseFragment<FragmentArticleBinding>(R.layout.fragment_article) {

    private val windowsUA =
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36 Edg/98.0.1108.55"
    private lateinit var webView: RobustWebView

    override fun onFragmentCreated(savedInstanceState: Bundle?) {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val webBackForwardList = webView.copyBackForwardList()
                val historyOneOriginalUrl = webBackForwardList.getItemAtIndex(0)?.originalUrl
                val curIndex = webBackForwardList.currentIndex

                // https://mp.weixin.qq.com/s/GJJHjbg9QK93kD4YzHnrAQ
                // 判断是否是缓存的WebView
                if (historyOneOriginalUrl?.contains("data:text/html;charset=utf-8") == true) {
                    //说明是缓存复用的的WebView
                    if (curIndex > 1) {
                        //内部跳转到另外的页面了，可以返回的
                        webView.goBack()
                    } else {
                        findNavController().navigateUp()
                    }
                } else {
                    //如果不是缓存复用的WebView，可以直接返回
                    if (webView.toGoBack()) {
                        findNavController().navigateUp()
                    }
                }
            }
        })
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        webView = WebViewManager.obtain(requireActivity())  //管理类获取对象
        webView.loadUrl(arguments?.getString("url")?: "")
        webView.layoutParams = params
        (dataBind.root as ViewGroup).addView(webView)

        arguments?.getString("title")?.let {
            dataBind.tvTitle.text = it
        }
        dataBind.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
        dataBind.ivShare.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.setAction(Intent.ACTION_SEND)
            sendIntent.putExtra(Intent.EXTRA_TEXT, arguments?.getString("url")?: "")
            sendIntent.setType("text/plain")
            startActivity(sendIntent)
        }
    }

    override fun observe() {
    }

    override fun onResume() {
        super.onResume()
        webView.onResume()
    }

    override fun onPause() {
        super.onPause()
        webView.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        WebViewManager.recycle(webView)
    }

}