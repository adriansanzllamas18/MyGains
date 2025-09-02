package com.example.mygains.fooddetail.data

import com.google.gson.annotations.SerializedName

data class FoodDetailModel( // Información básica del producto
val code: String? = null,
@SerializedName("product_name")
val productName: String? = null,
val brands: String? = null,
val categories: String? = null,
@SerializedName("image_url")
val imageUrl: String? = null,
@SerializedName("image_front_url")
val imageFrontUrl: String? = null,

// Información nutricional
val nutriments: NutrimentsModel? = null,

// Puntuaciones y grados
@SerializedName("nutrition_grades")
val nutritionGrades: String? = null, // Nutri-Score (A, B, C, D, E)

@SerializedName("ecoscore_grade")
val ecoscore: String? = null, // Eco-Score (A, B, C, D, E)

@SerializedName("nova_group")
val novaScore: String? = null, // NOVA Score (1, 2, 3, 4)

// Información adicional útil
val quantity: String? = null,
@SerializedName("serving_size")
val servingSize: String? = null,
@SerializedName("serving_quantity")
val servingQuantity: String? =null,
@SerializedName("packaging_tags")
val packagingTags: List<String>? = null,
@SerializedName("ingredients_text")
val ingredientsText: String? = null,
val allergens: String? = null,
val traces: String? = null,

// Labels y certificaciones
val labels: String? = null,

// Información del fabricante
@SerializedName("manufacturing_places")
val manufacturingPlaces: String? = null,
val origins: String? = null,
val stores: String? = null
)