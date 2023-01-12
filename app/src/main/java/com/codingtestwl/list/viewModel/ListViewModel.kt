package com.codingtestwl.list.viewModel

import androidx.lifecycle.MutableLiveData
import com.codingtestwl.base.BaseViewModel
import com.codingtestwl.list.model.ResponseModel
import com.codingtestwl.network.Network
import kotlinx.coroutines.launch

class ListViewModel : BaseViewModel() {

    /*Get jsonList from api and observe live data into MutableLiveData*/
    fun getList(pageNo: Int): MutableLiveData<ResponseModel> {
        val liveData = MutableLiveData<ResponseModel>()
        launch {
            liveData.postValue(Network().getListFromApi(pageNo))
        }
        return liveData
    }

    fun downloadImageFromUrl(urlStr: String): MutableLiveData<Any> {
        val liveData = MutableLiveData<Any>()
        launch {
            liveData.postValue(Network().downloadImageFromUrl(urlStr))
        }

        return liveData
    }

}