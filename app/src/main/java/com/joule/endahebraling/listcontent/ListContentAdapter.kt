package com.joule.endahebraling.listcontent

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.joule.endahebraling.R
import com.joule.endahebraling.model.DataListContent
import org.w3c.dom.Text

class ListContentAdapter(val items: ArrayList<DataListContent>) :
    RecyclerView.Adapter<ListContentAdapter.viewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListContentAdapter.viewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_list_content,
            parent,
            false
        )
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: ListContentAdapter.viewHolder, position: Int) {
        holder.bind(items.get(position))
        holder.card.setOnClickListener(View.OnClickListener {
            val intent = Intent(it.context, DetailContentActivity::class.java)
            intent.putExtra(DetailContentActivity.EXTRA_DATA, items.get(position))
            it.context.startActivity(intent)
        })

    }

    override fun getItemCount(): Int {
        return items.size
    }

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById<ImageView>(R.id.img_list)
        val tv = itemView.findViewById<TextView>(R.id.tv_title)
        val card = itemView.findViewById<CardView>(R.id.card_parent)

        val ratingbar = itemView.findViewById<RatingBar>(R.id.rating_hotel)

        fun bind(get: DataListContent) {
            Glide.with(itemView)
                .load(get.images?.get(0)?.url)
                .centerCrop()
                .into(img)
            tv.text = get.name


            val star = get.star?.toFloatOrNull()
            if (star != null) {
                ratingbar.visibility = View.VISIBLE
                ratingbar.numStars = get.star.toInt()
                ratingbar.rating = star
            }
        }

    }


}