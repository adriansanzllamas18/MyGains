package com.example.mygains.repositories

import com.example.mygains.base.response.BaseResponse
import com.example.mygains.base.response.errorresponse.BaseAuthError
import com.example.mygains.base.response.errorresponse.BaseResponseError
import com.example.mygains.base.response.errorresponse.BaseScanProductError
import com.example.mygains.fooddetail.data.OpenFoodModel
import com.example.mygains.scanproducts.data.ApiService
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeout
import retrofit2.Retrofit
import javax.inject.Inject

class ScannerRepository @Inject constructor( var retrofit: Retrofit){

    suspend fun getProductByCodeBar(barCode:String):BaseResponse<OpenFoodModel>{
        return try {
            withTimeout(5000L){
                val result =  retrofit.create(ApiService::class.java).getProductInfo("product/$barCode.json")
                if (result.isSuccessful && result.body()!= null){
                    if (result.body()!!.isProductFound()){
                        BaseResponse.Success(result.body()!!)
                    }else{
                        BaseResponse.Error(BaseScanProductError.ProductNotFound)
                    }
                }else{
                    BaseResponse.Error(BaseScanProductError.ProductNotFound)
                }
            }
        }catch (e: TimeoutCancellationException) {
            BaseResponse.Error(BaseScanProductError.TimeOut)
        } catch (ex:Exception){
            BaseResponse.Error(BaseAuthError.NetworkError)
        }
    }
}