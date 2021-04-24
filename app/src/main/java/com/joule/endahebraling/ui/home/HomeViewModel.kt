package com.joule.endahebraling.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.joule.endahebraling.model.ButtonHome
import com.joule.endahebraling.model.SliderHome

class HomeViewModel : ViewModel() {

    private val _listCarousel = MutableLiveData<ArrayList<SliderHome>>().apply {
        value = null
    }

    fun setListCarousel(list: ArrayList<SliderHome>) {
        _listCarousel.value = list
    }

    private val _listButton = MutableLiveData<ArrayList<ButtonHome>>().apply {
        var listBtn: ArrayList<ButtonHome> = ArrayList()
        val names = arrayOf("Destination", "Village", "Culture", "Art", "Culinary")
        for (i in names) {
            val newbtn = ButtonHome(null, i, null)
            listBtn.add(newbtn)
        }
        value = listBtn
    }

    private val _progress = MutableLiveData<Boolean>().apply {
        value = true;
    }

    fun setProgress(condition: Boolean) {
        _progress.value = condition
    }

    val listCarousel: LiveData<ArrayList<SliderHome>> = _listCarousel
    val listButton: LiveData<ArrayList<ButtonHome>> = _listButton
    val progress: LiveData<Boolean> = _progress
}
