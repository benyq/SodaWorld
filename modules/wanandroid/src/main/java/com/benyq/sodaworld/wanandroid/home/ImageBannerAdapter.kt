package com.benyq.sodaworld.wanandroid.home

import android.widget.ImageView
import com.benyq.sodaworld.wanandroid.R
import com.benyq.sodaworld.wanandroid.api.model.BannerModel
import com.bumptech.glide.Glide
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder


class ImageBannerAdapter(private val action: (BannerModel) -> Unit): BaseBannerAdapter<BannerModel>() {

    override fun bindData(
        holder: BaseViewHolder<BannerModel>?,
        data: BannerModel?,
        position: Int,
        pageSize: Int,
    ) {
        data?.let {
            holder?.findViewById<ImageView>(R.id.image)?.let {imageView->
                Glide.with(imageView).load(it.imagePath).into(imageView)
                holder.itemView.setOnClickListener {
                    action(data)
                }
            }
        }
    }

    override fun getLayoutId(viewType: Int) = R.layout.item_banner

}