package com.example.mygains.scanproducts.data

import com.google.gson.annotations.SerializedName

data class ProductResponse(
 @SerializedName("code") val barcode: String?="",
 @SerializedName("product_name") val productName: String?="",
 @SerializedName("brands") val brand: String?="",
 @SerializedName("image_url") val imageUrl: String?="",
 @SerializedName("categories") val categories: String?="",

  // Información nutricional por 100g
 @SerializedName("nutriments.energy-kcal_100g") val calories: Double?=0.0,
 @SerializedName("nutriments.proteins_100g") val protein: Double?=0.0,
 @SerializedName("nutriments.carbohydrates_100g") val carbohydrates: Double?=0.0,
 @SerializedName("nutriments.sugars_100g") val sugar: Double?=0.0,
 @SerializedName("nutriments.fat_100g") val fatTotal: Double?=0.0,
 @SerializedName("nutriments.saturated-fat_100g") val saturatedFat: Double?=0.0,
 @SerializedName("nutriments.fiber_100g") val fiber: Double?=0.0,
 @SerializedName("nutriments.salt_100g") val salt: Double?=0.0,

  // Datos adicionales
 @SerializedName("nutriscore_grade") val nutriScore: String?="",
 @SerializedName("ecoscore_grade") val ecoScore: String?="",
 @SerializedName("nova_group") val novaGroup: String?="",
 @SerializedName("serving_size") val servingSize: String?="",
 @SerializedName("product_quantity") val productQuantity: String?="",
 @SerializedName("allergens") val allergens: String?="",
 @SerializedName("ingredients_tags") val ingredients_tags: MutableList<String>?= mutableListOf(),
 @SerializedName("ingredients_from_palm_oil_tags") val ingredients_from_palm_oil_tags: MutableList<String>?= mutableListOf(),
 @SerializedName("ingredients_that_may_be_from_palm_oil_tags") val ingredients_that_may_be_from_palm_oil_tags: MutableList<String>?= mutableListOf()

 )