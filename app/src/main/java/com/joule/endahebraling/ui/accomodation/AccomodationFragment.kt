package com.joule.endahebraling.ui.accomodation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.joule.endahebraling.R

class AccomodationFragment : Fragment() {

    private lateinit var accomodationViewModel: AccomodationViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        accomodationViewModel =
                ViewModelProvider(this).get(AccomodationViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_accomodation, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        accomodationViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}