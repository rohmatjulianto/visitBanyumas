package com.joule.endahebraling.model

import android.os.Parcelable
import com.joule.endahebraling.model.Image
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataListContent(val name : String?,val star : String? , val address: String?, val contact: String?, val desc : String?, val maps : String?, val images: ArrayList<Image>?) : Parcelable{
    constructor(): this(null,null,null, null, null, null, null)
}
