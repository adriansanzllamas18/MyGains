package com.example.mygains.scanproducts.domain

import com.example.mygains.scanproducts.data.ApiService
import com.example.mygains.scanproducts.data.ProductResponse
import com.example.mygains.scanproducts.data.ProductResultResponse
import retrofit2.Retrofit
import javax.inject.Inject

class ScanBarCodeUseCase @Inject constructor( var retrofit: Retrofit) {

    suspend fun getProductFromcodeBar(codebar:String):ProductResultResponse{

        var productResultResponse= ProductResultResponse()
        try {

            var result= retrofit.create(ApiService::class.java).getProductInfo("product/$codebar.json")

            /*if (result.isSuccessful){
                val jsonResponse = result.body()

                var status= jsonResponse?.get("status")?.asInt
                var verbose= jsonResponse?.get("status_verbose")?.asString



                // Imprimir todo el JSON de respuesta para depuración
                println(status)
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
                if (status == 1){
                    productResultResponse=ProductResultResponse(status = status.toString(), status_verbose = verbose, productResponse = ProductResponse( productName = productName, brand = brand, imageUrl = image, categories = "category", calories = kcl_per100, carbohydrates = carbohydrates_per100, fatTotal = fat_per100, fiber = fiber_per100, protein = proteins_per100, salt = salt_per100, saturatedFat = saturated_fat_per100, sugar = sugars_per100, nutriScore = nutriscore, productQuantity = "", servingSize = "", allergens = allegerns, novaGroup = novaGroup, ingredients_tags = ingredients_tags_list, ingredients_from_palm_oil_tags = mutableListOf(), ingredients_that_may_be_from_palm_oil_tags = mutableListOf(), ecoScore = ecoScore))
                }else if (status ==0){
                    productResultResponse= ProductResultResponse(status = "0", status_verbose = "Producto no encontrado o inválido")
                }else{
                    productResultResponse= ProductResultResponse(status = "500", status_verbose = "Error, compruebe su conexión o intentelo más tarde")
                }

            }*/
        }catch (ex:Exception){
            productResultResponse= ProductResultResponse(status = "500", status_verbose = "Error, compruebe su conexión o intentelo más tarde")
        }


        return productResultResponse
    }
}