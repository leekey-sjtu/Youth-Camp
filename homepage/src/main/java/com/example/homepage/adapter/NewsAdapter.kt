package com.example.homepage.adapter

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.common.base.utils.MyApplication
import com.example.homepage.R
import com.example.homepage.bean.NewsResponse

class NewsAdapter(private val newsList: MutableList<NewsResponse.Info>, ): RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tv_title)
        val image: ImageView = itemView.findViewById(R.id.iv_image)
        val author: TextView = itemView.findViewById(R.id.tv_author)
        val time: TextView = itemView.findViewById(R.id.tv_passtime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {  //绑定数据
        holder.title.text = newsList[position].title  //标题
        holder.author.text = newsList[position].src  //作者
        holder.time.text = newsList[position].time  //发布时间
        Glide.with(MyApplication.context)  //新闻封面
            .load(newsList[position].pic)
            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
            .placeholder(R.drawable.ic_loading_gif) // TODO
            .error(R.drawable.ic_loading_error)
            .into(holder.image)
        holder.itemView.setOnClickListener {
//            skipDetailActivity(newsList[position].url, newsList[position].title)  //新闻网页的url和title // TODO
            holder.title.setTextColor(Color.parseColor("#999999"))  //title标为已读
        }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    private fun skipDetailActivity(newsUrl: String, newsTitle: String) {
//        val intent = Intent(mContext, NewsDetailActivity::class.java)
//        intent.putExtra("newsUrl", newsUrl)
//        intent.putExtra("newsTitle", newsTitle)
//        ContextCompat.startActivity(mContext, intent, null)
    }

}