package com.example.mygains.configuration.data.model

data class ConfigurationItemModel(
    var leftIcon:Int,
    var text:String,
    var  action: () -> Unit
)
