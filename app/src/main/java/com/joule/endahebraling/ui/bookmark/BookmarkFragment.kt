package com.joule.endahebraling.ui.bookmark

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.joule.endahebraling.R
import com.joule.endahebraling.databinding.FragmentBookmarkBinding
import com.joule.endahebraling.dbroom.AppDatabase
import com.joule.endahebraling.listcontent.ListContentAdapter

class BookmarkFragment : Fragment() {

    private lateinit var bookmarkViewModel: BookmarkViewModel
    private lateinit var binding: FragmentBookmarkBinding
    private lateinit var db : AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bookmarkViewModel =
            ViewModelProvider(this).get(BookmarkViewModel::class.java)
        binding = FragmentBookmarkBinding.inflate(inflater, container, false)

        val textView: TextView = binding.textNotifications
        val rvList : RecyclerView = binding.rvList

        bookmarkViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        db = context?.let {
            Room.databaseBuilder(
                it.applicationContext,
                AppDatabase::class.java, "data-list"
            ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
        }!!

        db.let { bookmarkViewModel.getListDb(it) }

        bookmarkViewModel.dataList.observe(viewLifecycleOwner, {
            if (it?.size != 0){
                textView.visibility = View.GONE
            }
            it?.apply {
                rvList.layoutManager = GridLayoutManager(context, 2)
                rvList.adapter = ListContentAdapter(this)
            }
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.textNotifications.visibility = View.VISIBLE
        db.let { bookmarkViewModel.getListDb(it) }
    }
}