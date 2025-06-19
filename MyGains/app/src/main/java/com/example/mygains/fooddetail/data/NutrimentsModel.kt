package com.example.mygains.fooddetail.data

import com.google.gson.annotations.SerializedName

data class NutrimentsModel(
    // Energía - valores por 100g
    @SerializedName("energy-kcal_100g")
    val energyKcal100g: Double? = null,
    @SerializedName("energy-kj_100g")
    val energyKj100g: Double? = null,

    // Energía - valores por porción
    @SerializedName("energy-kcal_serving")
    val energyKcalServing: Double? = null,
    @SerializedName("energy-kj_serving")
    val energyKjServing: Double? = null,

    // Macronutrientes - valores por 100g
    @SerializedName("fat_100g")
    val fat100g: Double? = null,
    @SerializedName("saturated-fat_100g")
    val saturatedFat100g: Double? = null,
    @SerializedName("carbohydrates_100g")
    val carbohydrates100g: Double? = null,
    @SerializedName("sugars_100g")
    val sugars100g: Double? = null,
    @SerializedName("fiber_100g")
    val fiber100g: Double? = null,
    @SerializedName("proteins_100g")
    val proteins100g: Double? = null,
    @SerializedName("salt_100g")
    val salt100g: Double? = null,
    @SerializedName("sodium_100g")
    val sodium100g: Double? = null,

    // Macronutrientes - valores por porción
    @SerializedName("fat_serving")
    val fatServing: Double? = null,
    @SerializedName("saturated-fat_serving")
    val saturatedFatServing: Double? = null,
    @SerializedName("carbohydrates_serving")
    val carbohydratesServing: Double? = null,
    @SerializedName("sugars_serving")
    val sugarsServing: Double? = null,
    @SerializedName("fiber_serving")
    val fiberServing: Double? = null,
    @SerializedName("proteins_serving")
    val proteinsServing: Double? = null,
    @SerializedName("salt_serving")
    val saltServing: Double? = null,
    @SerializedName("sodium_serving")
    val sodiumServing: Double? = null,

    // Grupo Nova
    @SerializedName("nova-group")
    val novaGroup: Int? = null,
    @SerializedName("nova-group_100g")
    val novaGroup100g: Int? = null,
    @SerializedName("nova-group_serving")
    val novaGroupServing: Int? = null,

    // Vitaminas del complejo B
    @SerializedName("vitamin-b1_100g")
    val vitaminB1_100g: Double? = null,
    @SerializedName("vitamin-b2_100g")
    val vitaminB2_100g: Double? = null,
    @SerializedName("vitamin-b6_100g")
    val vitaminB6_100g: Double? = null,
    @SerializedName("vitamin-b9_100g")
    val vitaminB9_100g: Double? = null,
    @SerializedName("vitamin-b12_100g")
    val vitaminB12_100g: Double? = null,
    @SerializedName("vitamin-pp_100g")
    val vitaminPP_100g: Double? = null, // Niacina (B3)
    @SerializedName("pantothenic-acid_100g")
    val pantothenicAcid100g: Double? = null, // B5
    @SerializedName("biotin_100g")
    val biotin100g: Double? = null,

    // Vitaminas del complejo B - por porción
    @SerializedName("vitamin-b1_serving")
    val vitaminB1Serving: Double? = null,
    @SerializedName("vitamin-b2_serving")
    val vitaminB2Serving: Double? = null,
    @SerializedName("vitamin-b6_serving")
    val vitaminB6Serving: Double? = null,
    @SerializedName("vitamin-b9_serving")
    val vitaminB9Serving: Double? = null,
    @SerializedName("vitamin-b12_serving")
    val vitaminB12Serving: Double? = null,
    @SerializedName("vitamin-pp_serving")
    val vitaminPPServing: Double? = null,
    @SerializedName("pantothenic-acid_serving")
    val pantothenicAcidServing: Double? = null,
    @SerializedName("biotin_serving")
    val biotinServing: Double? = null,

    // Estimaciones de frutas y vegetales
    @SerializedName("fruits-vegetables-legumes-estimate-from-ingredients_100g")
    val fruitsVegetablesLegumes100g: Double? = null,
    @SerializedName("fruits-vegetables-nuts-estimate-from-ingredients_100g")
    val fruitsVegetablesNuts100g: Double? = null
){

    /**
     * Verifica si todos los macronutrientes principales (por 100g) son nulos
     * Incluye: proteínas, calorías, grasas, carbohidratos, grasas saturadas, azúcares, sal, sodio y fibra
     */
    fun areMainMacronutrientsNull(): Boolean {
        return listOf(
            proteins100g,           // proteínas
            energyKcal100g,        // calorías
            fat100g,               // grasas
            carbohydrates100g,     // carbohidratos
            saturatedFat100g,      // grasas saturadas
            sugars100g,            // azúcares
            salt100g,              // sal
            sodium100g,            // sodio
            fiber100g              // fibra
        ).all { it == null }
    }

    /**
     * Verifica si tiene al menos un macronutriente principal con valor
     */
    fun hasMainMacronutrients(): Boolean = !areMainMacronutrientsNull()

    /**
     * Versión alternativa para verificar por porción (serving)
     */
    fun areMainMacronutrientsServingNull(): Boolean {
        return listOf(
            proteinsServing,
            energyKcalServing,
            fatServing,
            carbohydratesServing,
            saturatedFatServing,
            sugarsServing,
            saltServing,
            sodiumServing,
            fiberServing
        ).all { it == null }
    }
}



