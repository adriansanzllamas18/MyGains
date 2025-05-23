package com.example.mygains.base.response

import com.example.mygains.base.response.errorresponse.BaseAuthError
import com.example.mygains.base.response.errorresponse.BaseFireStoreError
import com.example.mygains.base.response.errorresponse.BaseResponseError

sealed class BaseResponse < out T>() {

     data class Success<T>(val data: T) : BaseResponse<T>()
     data class Error(val error : BaseResponseError) : BaseResponse<Nothing>(){
         fun mapError():String{
            return when (error){
                is BaseAuthError -> {error.mapAuthErrorToMessage(error)}
                is BaseFireStoreError -> {error.mapFireStoreErrorToMessage(error)}
                else -> "Error inesperado."
             }
         }
     }
}