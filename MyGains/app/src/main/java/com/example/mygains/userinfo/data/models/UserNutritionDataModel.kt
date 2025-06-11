package com.example.mygains.userinfo.data.models

data class UserNutritionDataModel(
    val currentCalories: Double = 0.00,
    val currentProtein: Double = 0.00,
    val currentCarbs: Double = 0.00,
    val currentFat: Double = 0.00,
    val goalCalories: Double = 0.00,
    val goalProtein: Double = 0.00,
    val goalCarbs: Double = 0.00,
    val goalFat: Double = 0.00,
    val registerDate:String=""
)
