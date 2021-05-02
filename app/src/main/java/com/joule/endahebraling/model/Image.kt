package com.joule.endahebraling.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(val url : String?, val by : String?) : Parcelable{
    constructor(): this(null, null)
}
