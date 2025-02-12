package com.example.mygains.base

 sealed class BaseResponse< out T> {

     data class Success<T>(val data: T) : BaseResponse<T>()
     data class Error(val error:BaseAuthError) : BaseResponse<Nothing>()
}