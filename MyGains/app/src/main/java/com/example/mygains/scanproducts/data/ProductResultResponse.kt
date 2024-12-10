package com.example.mygains.scanproducts.data

data class ProductResultResponse(
    var status:String?="",
    var status_verbose:String?="",
    var productResponse: ProductResponse?=ProductResponse()
)
