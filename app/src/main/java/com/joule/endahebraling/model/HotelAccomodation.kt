package com.joule.endahebraling.model

import android.os.Parcelable
import androidx.versionedparcelable.VersionedParcelize
import kotlinx.parcelize.Parcelize

@Parcelize
data class HotelAccomodation(
    val name: String?,
    val star: String?,
    val address: String?,
    val images: ArrayList<Image>?
) : Parcelable {
    constructor() : this(null, null, null, null)
}
