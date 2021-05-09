package com.example.link.ui.details

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.link.R
import com.example.link.model.main.ArticlesModelItem
import com.example.link.ui.base.BaseActivity
import com.example.link.utils.loadImage
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.main_news_list_item.view.*

class DetailsActivity : BaseActivity() {

    override fun getActivityView(): Int = R.layout.activity_details

    override fun afterInflation(savedInstance: Bundle?) {
        setupViews()
    }

    fun setupViews() {
        back_img.setOnClickListener(View.OnClickListener {
            finish()
        })
        open_website_btn.setOnClickListener(View.OnClickListener {
            openWebSite(articlesModelItem.url)
        })

        if (articlesModelItem.publishedAt != null) {
            date_txt.text = articlesModelItem.publishedAt
        } else {
            date_txt.visibility = View.GONE
        }

        image_details.loadImage(articlesModelItem.urlToImage)

        title_txt.text = articlesModelItem.title
        sponsored_txt.text = resources.getString(R.string.by) + " " + articlesModelItem.author
        description_txt.text = articlesModelItem.description


    }

    fun openWebSite(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    companion object {
        lateinit var articlesModelItem: ArticlesModelItem
        fun getIntent(
            context: Context,
            articlesModelItem: ArticlesModelItem
        ): Intent {
            this.articlesModelItem = articlesModelItem
            return Intent(context, DetailsActivity::class.java)
        }
    }

}