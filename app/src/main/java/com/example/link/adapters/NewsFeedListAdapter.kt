package com.example.link.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.link.R
import com.example.link.model.main.ArticlesModelItem
import com.example.link.ui.base.CommonVH
import com.example.link.ui.details.DetailsActivity
import com.example.link.utils.loadImage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.main_news_list_item.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class NewsFeedListAdapter(
    var context: Context,
    var list: List<ArticlesModelItem>
) : RecyclerView.Adapter<CommonVH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonVH {
        return CommonVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.main_news_list_item, parent,false)
        )
    }

    override fun onBindViewHolder(holder: CommonVH, position: Int) {
        with(holder.itemView) {
            image.loadImage(list.get(position).urlToImage)
            title_txt.text  = list.get(position).title
            sponsored_txt.text = context.resources.getString(R.string.by)+" "+list.get(position).author
            if(list.get(position).publishedAt!=null) {
                date_txt.visibility = View.VISIBLE
                date_txt.text = list.get(position).publishedAt
            }else{
                date_txt.visibility = View.INVISIBLE
            }
            this.onClick {
                context.startActivity(DetailsActivity.getIntent(context,list.get(position)))
            }
        }
    }





    override fun getItemCount(): Int {
        return list.size
    }
}
