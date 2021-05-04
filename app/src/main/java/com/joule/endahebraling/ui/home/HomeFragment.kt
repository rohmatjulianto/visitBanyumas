package com.joule.endahebraling.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
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
import com.jama.carouselview.CarouselViewListener
import com.joule.endahebraling.listcontent.ListContentActivity
import com.joule.endahebraling.R
import com.joule.endahebraling.databinding.FragmentHomeBinding
import com.joule.endahebraling.model.ButtonHome
import com.joule.endahebraling.model.NewsItem
import com.joule.endahebraling.model.SliderHome


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        val database = Firebase.database;
        val sliderRef = database.getReference("/homeslider")
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        homeViewModel.progress.observe(viewLifecycleOwner, {
            if (it) {
                binding.pbCarousel.visibility = View.VISIBLE
            } else {
                binding.pbCarousel.visibility = View.GONE
            }
        })
        homeViewModel.listCarousel.observe(viewLifecycleOwner, {
            if (it != null) {
                homeViewModel.setProgress(false)
                binding.carouselView.apply {
                    size = it.size
                    resource = R.layout.item_carousel
                    autoPlay = false
                    setCarouselViewListener(CarouselViewListener { view, position ->
                        val imageView = view.findViewById<ImageView>(R.id.img_carousel)
                        Glide.with(view.context)
                            .load(it.get(position).image)
                            .centerCrop()
                            .into(imageView)
                    })
                    show()
                }
            } else {
                homeViewModel.setProgress(true)
            }
        })

        homeViewModel.listButton.observe(viewLifecycleOwner, {
            binding.rvItemBtn.adapter = btnAdapter(it)
            binding.rvItemBtn.layoutManager = GridLayoutManager(context, 3)
        })

        homeViewModel.news.observe(viewLifecycleOwner, {
            val rvNews = binding.rvNews
            if (it != null){
                rvNews.adapter = NewsAdapter(it.hot)
                rvNews.setHasFixedSize(true)
                rvNews.layoutManager = LinearLayoutManager(context)
                binding.pbNews.visibility = View.GONE
            }else{
                binding.pbNews.visibility = View.VISIBLE
            }
        })

        sliderRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var sliderArr: ArrayList<SliderHome> = ArrayList()
                for (postSnap in snapshot.children) {
                    val slider = postSnap.getValue<SliderHome>()
                    if (slider != null) {
                        sliderArr.add(slider)
                    }
                }
                homeViewModel.setListCarousel(sliderArr)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("yy", "onCancelled: " + error.message)
            }
        })



        return view
    }

    private class btnAdapter(private val items: ArrayList<ButtonHome>) : RecyclerView.Adapter<btnAdapter.viewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): btnAdapter.viewHolder {
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.item_button_home,
                parent,
                false
            )
            return viewHolder(view)
        }

        override fun onBindViewHolder(holder: btnAdapter.viewHolder, position: Int) {
            holder.bind(items.get(position))
            holder.cardView.setOnClickListener(View.OnClickListener {
                val intent = Intent(it.context, ListContentActivity::class.java)
                intent.putExtra(ListContentActivity.EXTRA_TITLE, holder.tvTitle.text.toString())
                it.context.startActivity(intent)
            })
        }

        override fun getItemCount(): Int {
            return items.size
        }

        class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val cardView = itemView.findViewById<CardView>(R.id.card_parent)
            val tvTitle = itemView.findViewById<TextView>(R.id.tv_title_button)
            fun bind(buttonHome: ButtonHome) {
                tvTitle.text = buttonHome.name
                tvTitle.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(itemView.context, buttonHome.path), null, null)
            }
        }
    }

    private class NewsAdapter(private val item: ArrayList<NewsItem>) : RecyclerView.Adapter<NewsAdapter.viewHolder>(){
        class viewHolder(itemview: View) : RecyclerView.ViewHolder(itemview){
            val imgHotel = itemView.findViewById<ImageView>(R.id.img_hotel)
            val tvTitle = itemView.findViewById<TextView>(R.id.tv_title_hotel)
            val tvNoImg = itemView.findViewById<TextView>(R.id.tv_no_image)

            fun bind(get: NewsItem) {
                tvTitle.text = get.title
                Glide.with(itemView)
                    .load(get.image_url)
                    .centerCrop()
                    .into(imgHotel)
                if (get.image_url == null){
                    tvNoImg.visibility = View.VISIBLE
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.viewHolder {
            val view  = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
            return viewHolder(view)
        }

        override fun onBindViewHolder(holder: viewHolder, position: Int) {
            holder.bind(item.get(position))
        }

        override fun getItemCount(): Int {
           return item.size
        }

    }

}