package com.codingtestwl.list.model

import android.graphics.Bitmap
import android.widget.ImageView

data class Model(

    var id:Int = 0,
    var author:String = "",
    var width:Int = 0,
    var height:Int = 0,
    var url:String = "",
    var download_url:String = "",
    @Transient
    var bitmap: Bitmap? = null,
    @Transient
    var itemImage: ImageView? = null
    )
