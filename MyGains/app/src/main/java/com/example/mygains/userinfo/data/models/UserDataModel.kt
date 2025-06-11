package com.example.mygains.userinfo.data.models

data class UserDataModel(
    var name:String="" ,
    var first_name:String= "",
    var second_name:String= "",
    var email:String= "" ,
    var weight:String="",
    var lastUpdateWeight:String="",
    var image:String?="",
    var pass:String="" //TODO cambiar este parametro
)