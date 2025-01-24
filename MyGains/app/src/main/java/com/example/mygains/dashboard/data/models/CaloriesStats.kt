package com.example.mygains.dashboard.data.models

data class CaloriesStats(
     val title:String="",
     val subTitle:String="",
     val currentCalories: Int?=null,
     val targetCalories: Int?=null,
     val burnedCalories: Int?=null,
)
