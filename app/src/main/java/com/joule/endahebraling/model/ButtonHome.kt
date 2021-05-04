package com.joule.endahebraling.model

import android.graphics.drawable.Drawable

data class ButtonHome(val icon: Drawable?, val name: String?, val path: Int){
    constructor() : this(null, null,  0)
}
