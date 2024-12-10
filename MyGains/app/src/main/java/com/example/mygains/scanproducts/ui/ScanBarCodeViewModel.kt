package com.example.mygains.scanproducts.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mygains.scanproducts.data.ApiService
import com.example.mygains.scanproducts.data.ProductResponse
import com.example.mygains.scanproducts.data.ProductResultResponse
import com.example.mygains.scanproducts.domain.ScanBarCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import okhttp3.Response
import okhttp3.internal.wait
import retrofit2.Retrofit
import javax.inject.Inject
import kotlin.math.log


@HiltViewModel
class ScanBarCodeViewModel @Inject constructor( private var useCase: ScanBarCodeUseCase):ViewModel() {


    private var _ProductResponse= MutableLiveData<ProductResultResponse> ()
    var _ProductResponseLife:MutableLiveData<ProductResultResponse> = _ProductResponse

    private var _ShowProduct= MutableLiveData<Boolean> ()
    var _ShowProductLife:MutableLiveData<Boolean> = _ShowProduct

    private var _ShowScanIcon= MutableLiveData<Boolean> ()
    var _ShowScanIconLife:MutableLiveData<Boolean> = _ShowScanIcon

    fun getProduct(codebar:String){

        viewModelScope.launch(Dispatchers.IO) {
            var result=useCase.getProductFromcodeBar(codebar)
            _ProductResponse.postValue(result)
            _ShowProduct.postValue(true)
        }

    }

    fun showProduct(show:Boolean){
        this._ShowProduct.postValue(show)
    }

    fun showScanIcon(show:Boolean){
        this._ShowScanIcon.postValue(show)
    }

}