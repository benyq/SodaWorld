package com.benyq.sodaworld.wanandroid.project.articles

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benyq.sodaworld.wanandroid.api.model.ArticleModel
import com.benyq.sodaworld.wanandroid.databinding.ItemProjectBinding
import com.bumptech.glide.Glide
import com.chad.library.adapter4.BaseQuickAdapter

/**
 *
 * @author benyq
 * @date 12/14/2023
 *
 */
class ProjectArticlesAdapter(private val action: (ArticleModel) -> Unit): BaseQuickAdapter<ArticleModel, ProjectArticlesAdapter.ProjectVH>() {

    class ProjectVH(val viewBinding: ItemProjectBinding): RecyclerView.ViewHolder(viewBinding.root)

    override fun onBindViewHolder(holder: ProjectVH, position: Int, item: ArticleModel?) {
        item?.let {
            holder.viewBinding.tvDatetime.text = it.niceDate
            holder.viewBinding.tvDesc.text = it.desc
            holder.viewBinding.tvTitle.text = it.title
            holder.viewBinding.tvAuthor.text = it.author.ifEmpty { it.shareUser }
            Glide.with(context).load(it.envelopePic).into(holder.viewBinding.ivCover)
            holder.itemView.setOnClickListener {
                action(item)
            }
        }
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): ProjectVH {
        return ProjectVH(ItemProjectBinding.inflate(LayoutInflater.from(context), parent, false))
    }


}