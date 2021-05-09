package com.example.link.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.link.R
import com.example.link.model.main.NavigationItemModel
import kotlinx.android.synthetic.main.row_nav_drawer.view.*

class NavigationRVAdapter(private var items: ArrayList<NavigationItemModel>) :
    RecyclerView.Adapter<NavigationRVAdapter.NavigationItemViewHolder>() {

    private lateinit var context: Context
    private var selected_index: Int? = null

    class NavigationItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavigationItemViewHolder {
        context = parent.context
        val navItem =
            LayoutInflater.from(parent.context).inflate(R.layout.row_nav_drawer, parent, false)
        return NavigationItemViewHolder(
            navItem
        )
    }

    fun showToast(title: String) {
        Toast.makeText(context, title, Toast.LENGTH_LONG).show()
    }

    override fun getItemCount(): Int {
        return items.count()
    }


    override fun onBindViewHolder(holder: NavigationItemViewHolder, position: Int) {
        if (selected_index != null && selected_index == position) {
            holder.itemView.selected_img.visibility = View.VISIBLE
        } else {
            holder.itemView.selected_img.visibility = View.INVISIBLE
        }
        holder.itemView.navigation_title.text = items[position].title
        holder.itemView.navigation_icon.setImageResource(items[position].icon)
        holder.itemView.setOnClickListener(View.OnClickListener {
            selected_index = position
            showToast(items.get(position).title)
            notifyDataSetChanged()
        })
    }

}