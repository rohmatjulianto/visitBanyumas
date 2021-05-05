package com.joule.endahebraling.ui.accomodation

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.joule.endahebraling.R
import com.joule.endahebraling.databinding.FragmentAccomodationBinding
import com.joule.endahebraling.databinding.FragmentHomeBinding
import com.joule.endahebraling.listcontent.DetailContentActivity
import com.joule.endahebraling.model.DataListContent
import com.joule.endahebraling.model.HotelAccomodation
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.acos

class AccomodationFragment : Fragment() {

    private lateinit var accomodationViewModel: AccomodationViewModel
    private lateinit var binding: FragmentAccomodationBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccomodationBinding.inflate(inflater, container, false)
        val view = binding.root

        accomodationViewModel =
                ViewModelProvider(this).get(AccomodationViewModel::class.java)

        val database = Firebase.database;
        val thisRef = database.getReference("/hotel")
        thisRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var list : ArrayList<DataListContent> = ArrayList()
                for (post in snapshot.children){
                    val value = post.getValue<DataListContent>()
                    if (value != null){
                        list.add(value)
                    }
                }
                accomodationViewModel._listHotel.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        accomodationViewModel.listHotel.observe(viewLifecycleOwner, Observer {
           val recyclerView = binding.rvAccomodation
            if (it != null){
                recyclerView.adapter = HotelAdapter(it)
                recyclerView.setHasFixedSize(false)
                recyclerView.layoutManager = GridLayoutManager(context, 2)
            }
        })

        return view
    }

    private class HotelAdapter(val items : ArrayList<DataListContent>) : RecyclerView.Adapter<HotelAdapter.viewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelAdapter.viewHolder {
            val view  = LayoutInflater.from(parent.context).inflate(R.layout.item_hotel_accomodation, parent, false)
            return viewHolder(view)
        }

        override fun onBindViewHolder(holder: HotelAdapter.viewHolder, position: Int) {
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
            val imgHotel = itemView.findViewById<ImageView>(R.id.img_hotel)
            val rating = itemView.findViewById<RatingBar>(R.id.rating_hotel)
            val tvTitle = itemView.findViewById<TextView>(R.id.tv_title_hotel)
            val tvAddress = itemView.findViewById<TextView>(R.id.tv_address_hotel)
            val card = itemView.findViewById<CardView>(R.id.card_parent)

            fun bind(accomodation: DataListContent) {
                Glide.with(itemView)
                    .load(accomodation.images?.get(0)?.url)
                    .centerCrop()
                    .into(imgHotel)

                val star = accomodation.star?.toFloatOrNull()
                if (star != null) {
                    rating.numStars = accomodation.star.toInt()
                    rating.rating = star
                }

                tvTitle.text = accomodation.name
                tvAddress.text = accomodation.address
            }

        }
    }
}