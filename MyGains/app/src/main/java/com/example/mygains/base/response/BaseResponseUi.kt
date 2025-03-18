package com.example.mygains.base.response

data class BaseResponseUi(
    var show : Boolean = false,
    var response : BaseResponse<Nothing>,
    var message : String
)
