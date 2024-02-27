package com.benyq.sodaworld.wanandroid.article

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benyq.sodaworld.wanandroid.api.model.ArticleModel
import com.benyq.sodaworld.wanandroid.databinding.ItemHomeArticleBinding
import com.chad.library.adapter4.BaseQuickAdapter

/**
 *
 * @author benyq
 * @date 12/14/2023
 *
 */
class ArticleListAdapter(private val action: (ArticleModel)->Unit) : BaseQuickAdapter<ArticleModel, ArticleListAdapter.ArticleVH>() {

    class ArticleVH(val viewBinding: ItemHomeArticleBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    override fun onBindViewHolder(holder: ArticleVH, position: Int, item: ArticleModel?) {
        item?.let {
            holder.viewBinding.tvAuthor.text = it.author.ifEmpty { it.shareUser }
            holder.viewBinding.tvTitle.text = it.title
            holder.viewBinding.tvTags.text = if (it.superChapterName.isNullOrEmpty()) it.chapterName else "${it.superChapterName}/${it.chapterName}"
            holder.viewBinding.tvDatetime.text = it.niceDate
            holder.itemView.setOnClickListener {
                action(item)
            }
        }
    }

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int,
    ): ArticleVH {
        return ArticleVH(ItemHomeArticleBinding.inflate(LayoutInflater.from(context), parent, false))
    }
}