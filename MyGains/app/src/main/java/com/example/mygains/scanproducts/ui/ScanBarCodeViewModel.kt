package com.example.mygains.scanproducts.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mygains.scanproducts.data.ApiService
import com.example.mygains.scanproducts.data.ProductResponse
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
class ScanBarCodeViewModel @Inject constructor( private var retrofit: Retrofit):ViewModel() {


    private var _ProductResponse= MutableLiveData<ProductResponse> ()
    var _ProductResponseLife:MutableLiveData<ProductResponse> = _ProductResponse

    fun getProduct(codebar:String){

        viewModelScope.launch(Dispatchers.IO) {
           var result= retrofit.create(ApiService::class.java).getProductInfo("product/$codebar.json")

            if (result.isSuccessful){
                val jsonResponse = result.body()

                // Imprimir todo el JSON de respuesta para depuración
                println("Respuesta completa: ${jsonResponse?.toString()}")
                val productName = jsonResponse?.getAsJsonObject("product")?.get("product_name")?.asString
                //podemos coger el año acual restarle y añadirlo a la etiqueta , esto por si existiese una version mas actualizada
                val proteins_per100 = jsonResponse?.getAsJsonObject("product")?.getAsJsonObject("nutriments")?.get("proteins_100g")?.asDouble
                val kcl_per100 = jsonResponse?.getAsJsonObject("product")?.getAsJsonObject("nutriments")?.get("energy-kcal_100g")?.asDouble
                val carbohydrates_per100 = jsonResponse?.getAsJsonObject("product")?.getAsJsonObject("nutriments")?.get("carbohydrates_100g")?.asDouble
                val sugars_per100 = jsonResponse?.getAsJsonObject("product")?.getAsJsonObject("nutriments")?.get("sugars_100g")?.asDouble
                val fat_per100 = jsonResponse?.getAsJsonObject("product")?.getAsJsonObject("nutriments")?.get("fat_100g")?.asDouble
                val saturated_fat_per100 = jsonResponse?.getAsJsonObject("product")?.getAsJsonObject("nutriments")?.get("saturated-fat_100g")?.asDouble
                val fiber_per100 = jsonResponse?.getAsJsonObject("product")?.getAsJsonObject("nutriments")?.get("fiber_100g")?.asDouble
                val salt_per100 = jsonResponse?.getAsJsonObject("product")?.getAsJsonObject("nutriments")?.get("salt_100g")?.asDouble



                val nutriscore = jsonResponse?.getAsJsonObject("product")?.get("nutriscore_2023_tags")?.asString
                val ecoScore = jsonResponse?.getAsJsonObject("product")?.get("ecoscore_grade")?.asString

                val image = jsonResponse?.getAsJsonObject("product")?.get("image_url")?.asString
                val brand = jsonResponse?.getAsJsonObject("product")?.get("brands")?.asString
                //val category = jsonResponse?.getAsJsonObject("product")?.get("categories_hierarchy")?.asString
                val allegerns = jsonResponse?.getAsJsonObject("product")?.get("allergens")?.asString
                val novaGroup = jsonResponse?.getAsJsonObject("product")?.get("nova_group")?.asString
                var ingredients_tags_list= mutableListOf<String>()

                val ingredients_tags = jsonResponse?.getAsJsonObject("product")?.getAsJsonArray("ingredients_analysis_tags")
                    ?.forEach {
                        ingredients_tags_list.add(it.asString)
                    }
                jsonResponse?.getAsJsonObject("product")?.getAsJsonArray("allergens_tags")?.forEach {
                    ingredients_tags_list.add(it.asString)
                }

                _ProductResponse.postValue(ProductResponse("", productName = productName, brand = brand, imageUrl = image, categories = "category", calories = kcl_per100, carbohydrates = carbohydrates_per100, fatTotal = fat_per100, fiber = fiber_per100, protein = proteins_per100, salt = salt_per100, saturatedFat = saturated_fat_per100, sugar = sugars_per100, nutriScore = nutriscore, productQuantity = "", servingSize = "", allergens = allegerns, novaGroup = novaGroup, ingredients_tags = ingredients_tags_list, ingredients_from_palm_oil_tags = mutableListOf(), ingredients_that_may_be_from_palm_oil_tags = mutableListOf(), ecoScore = ecoScore))
                println(" completa: ${ProductResponse("", productName = productName, brand = brand, imageUrl = image, categories = "category", calories = kcl_per100, carbohydrates = carbohydrates_per100, fatTotal = fat_per100, fiber = fiber_per100, protein = proteins_per100, salt = salt_per100, saturatedFat = saturated_fat_per100, sugar = sugars_per100, nutriScore = nutriscore, productQuantity = "", servingSize = "", allergens = allegerns, novaGroup = novaGroup, ingredients_tags = mutableListOf(), ecoScore = ecoScore, ingredients_from_palm_oil_tags = mutableListOf(), ingredients_that_may_be_from_palm_oil_tags = mutableListOf())}")

            }

        }

    }

}