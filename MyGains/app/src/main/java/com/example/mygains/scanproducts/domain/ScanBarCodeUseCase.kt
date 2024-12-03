package com.example.mygains.scanproducts.domain

import com.example.mygains.scanproducts.data.ApiService
import com.example.mygains.scanproducts.data.ProductResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import javax.inject.Inject
import kotlin.concurrent.thread

class ScanBarCodeUseCase @Inject constructor( var retrofit: Retrofit) {

    suspend fun getProductFromcodeBar(){
         retrofit.create(ApiService::class.java).getProductInfo("product/8426617007034")
    }
}