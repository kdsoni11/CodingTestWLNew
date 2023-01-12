package com.codingtestwl.list.model

import androidx.lifecycle.MutableLiveData

class ResponseModel {
    var mutableList:MutableList<Model> = ArrayList()
    var status:Boolean=false
    var errorMsg:String=""
}