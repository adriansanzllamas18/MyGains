package com.example.mygains.userinfo.data.models

data class UserNutritionDataModel(
    var userNutritionGoalsDataModel: UserNutritionGoalsDataModel = UserNutritionGoalsDataModel() ,
    var userCurrentNutritionDataModel: UserCurrentNutritionDataModel = UserCurrentNutritionDataModel()
)
