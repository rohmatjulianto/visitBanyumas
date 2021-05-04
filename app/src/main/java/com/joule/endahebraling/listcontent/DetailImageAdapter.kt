package com.joule.endahebraling.listcontent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.joule.endahebraling.R
import com.joule.endahebraling.model.Image

class DetailImageAdapter(val items : ArrayList<Image>) : RecyclerView.Adapter<DetailImageAdapter.viewHolder>() {
    class viewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val img : ImageView = itemView.findViewById(R.id.img_list)
        val txt : TextView = itemView.findViewById(R.id.tv_by)
        fun bind(get: Image) {
            Glide.with(itemView)
                .load(get.url)
                .centerCrop()
                .into(img)
            txt.text = "by : ${get.by}"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailImageAdapter.viewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_image,
            parent,
            false
        )
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailImageAdapter.viewHolder, position: Int) {
        holder.bind(items.get(position))
    }

    override fun getItemCount(): Int {
        return items.size
    }
}