package com.joule.endahebraling.ui.accomodation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.joule.endahebraling.model.DataListContent
import com.joule.endahebraling.model.HotelAccomodation

class AccomodationViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    public val _listHotel = MutableLiveData<ArrayList<DataListContent>>().apply {
        value = null
    }

    val listHotel: LiveData<ArrayList<DataListContent>> = _listHotel
}