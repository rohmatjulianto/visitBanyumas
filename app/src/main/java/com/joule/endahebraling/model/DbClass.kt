package com.joule.endahebraling.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class DbClass(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "contact") val contact: String?,
    @ColumnInfo(name = "desc") val desc: String?,
    @ColumnInfo(name = "maps") val maps: String?,
    @ColumnInfo(name = "images") val images: String?
) : Parcelable
