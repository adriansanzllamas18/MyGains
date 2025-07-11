package com.example.mygains.scanproducts.data


import com.example.mygains.fooddetail.data.OpenFoodModel
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {

    @GET
   suspend fun getProductInfo(@Url url: String): Response<OpenFoodModel>
}