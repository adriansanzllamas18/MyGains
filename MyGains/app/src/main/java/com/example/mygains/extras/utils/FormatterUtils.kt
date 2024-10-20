package com.example.mygains.extras.utils

class FormatterUtils {

    fun getUserNameFirstNameScondName(string: String):MutableList<String>{

        val list:MutableList<String> = mutableListOf<String>()
        val s= string.split(" ")

        list.add(s[0])
        list.add(s[1])
        list.add(s[2])

        return list
    }
}