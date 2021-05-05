package com.joule.endahebraling.ui.bookmark

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.joule.endahebraling.dbroom.AppDatabase
import com.joule.endahebraling.model.DataListContent
import com.joule.endahebraling.model.Image
import org.json.JSONArray
import org.json.JSONObject

class BookmarkViewModel : ViewModel() {


    private val _text = MutableLiveData<String>().apply {
        value = "No available data bookmark"
    }
    val text: LiveData<String> = _text


    fun getListDb(db: AppDatabase) {
        val datalistContent: ArrayList<DataListContent> = ArrayList()
        val listdb = db.dbDao().getAll()
        for (list in listdb) {
            val jsonArr = JSONArray(list.images)
            val dataImages: ArrayList<Image> = ArrayList();
            for (i in 0 until jsonArr.length()) {
                val images: JSONObject = jsonArr.getJSONObject(i)
                val toImage = Image(images.getString("url"), images.getString("by"))
                dataImages.add(toImage)
            }
            val datalist =
                DataListContent(list.name, list.star, list.address,list.contact, list.desc, list.maps, dataImages)
            datalistContent.add(datalist)
        }
        _listDb.value = datalistContent

    }

    private val _listDb = MutableLiveData<ArrayList<DataListContent>>().apply {
        value = null
    }

    val dataList :LiveData<ArrayList<DataListContent>> = _listDb

}