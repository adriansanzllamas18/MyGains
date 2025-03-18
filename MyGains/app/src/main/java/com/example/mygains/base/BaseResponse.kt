package com.example.mygains.base

 sealed class BaseResponse < out T>() {

     data class Success<T>(val data: T) : BaseResponse<T>()
     data class Error(val error : BaseResponseError) : BaseResponse<Nothing>(){
         fun mapError():String{
            return when (error){
                is BaseAuthError -> {error.mapAuthErrorToMessage(error)}
                is BaseFireStoreError -> {error.mapAuthErrorToMessage(error)}
                else -> "Error inesperado."
             }
         }
     }
}