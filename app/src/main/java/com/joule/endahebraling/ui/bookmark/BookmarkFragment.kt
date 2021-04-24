package com.joule.endahebraling.ui.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.joule.endahebraling.R

class BookmarkFragment : Fragment() {

    private lateinit var bookmarkViewModel: BookmarkViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        bookmarkViewModel =
                ViewModelProvider(this).get(BookmarkViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_bookmark, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        bookmarkViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}