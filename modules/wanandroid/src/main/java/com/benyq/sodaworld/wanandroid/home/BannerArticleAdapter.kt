package com.benyq.sodaworld.wanandroid.home

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.benyq.sodaworld.wanandroid.R
import com.benyq.sodaworld.wanandroid.api.model.ArticleModel
import com.benyq.sodaworld.wanandroid.api.model.BannerModel
import com.benyq.sodaworld.wanandroid.databinding.ItemBannerHolderBinding
import com.benyq.sodaworld.wanandroid.databinding.ItemHomeArticleBinding
import com.chad.library.adapter4.BaseMultiItemAdapter
import com.zhpan.bannerview.BannerViewPager

class BannerArticleAdapter(private val action: (ArticleModel?, BannerModel?) -> Unit): BaseMultiItemAdapter<BannerArticleModel>() {
   private class ArticleVH(val viewBinding: ItemHomeArticleBinding) : RecyclerView.ViewHolder(viewBinding.root)

    class BannerVH(viewBinding: ItemBannerHolderBinding) : RecyclerView.ViewHolder(viewBinding.root)


    init {
        addItemType(BANNER_TYPE, object: OnMultiItemAdapterListener<BannerArticleModel, BannerVH> {
            override fun onBind(holder: BannerVH, position: Int, item: BannerArticleModel?) {
                val bannerView: BannerViewPager<BannerModel> = holder.itemView.findViewById(R.id.banner_view)
                if (bannerView.adapter == null) {
                    bannerView.adapter = ImageBannerAdapter {
                        action(null, it)
                    }
                    bannerView.create(item?.banners)
                }
                recyclerView.findViewTreeLifecycleOwner()?.let {
                    bannerView.registerLifecycleObserver(it.lifecycle)
                }
            }

            override fun onCreate(context: Context, parent: ViewGroup, viewType: Int): BannerVH {
                val viewBinding = ItemBannerHolderBinding.inflate(LayoutInflater.from(context), parent, false)
                return BannerVH(viewBinding)
            }

        }).addItemType(ARTICLE_TYPE, object: OnMultiItemAdapterListener<BannerArticleModel, ArticleVH>{
            @SuppressLint("SetTextI18n")
            override fun onBind(holder: ArticleVH, position: Int, item: BannerArticleModel?) {
                item?.articleModel?.let {
                    holder.viewBinding.tvAuthor.text = it.author.ifEmpty { it.shareUser }
                    holder.viewBinding.tvTitle.text = it.title
                    holder.viewBinding.tvTags.text = "${it.superChapterName}/${it.chapterName}"
                    holder.viewBinding.tvDatetime.text = it.niceDate
                    holder.itemView.setOnClickListener {
                        action(item.articleModel, null)
                    }
                }
            }
            override fun onCreate(context: Context, parent: ViewGroup, viewType: Int): ArticleVH {
                val viewBinding = ItemHomeArticleBinding.inflate(LayoutInflater.from(context), parent, false)
                return ArticleVH(viewBinding)
            }
        }).onItemViewType {position, list ->
            if (list[position].articleModel != null) {
                ARTICLE_TYPE
            } else {
                BANNER_TYPE
            }
        }
    }

    companion object {
        private const val BANNER_TYPE = 0
        private const val ARTICLE_TYPE = 1
    }
}