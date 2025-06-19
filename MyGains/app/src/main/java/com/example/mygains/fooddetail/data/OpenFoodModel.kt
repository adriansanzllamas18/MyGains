package com.example.mygains.fooddetail.data

import com.google.gson.annotations.SerializedName

data class OpenFoodModel(
    @SerializedName("status")
    val status: Int? = null,
    @SerializedName("status_verbose")
    val statusVerbose: String? = null,
    @SerializedName("product")
    val product: FoodDetailModel? = null
){

    fun isProductFound(): Boolean = status == 1
    fun isProductNotFound(): Boolean = status == 0
}
