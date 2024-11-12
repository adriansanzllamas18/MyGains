package com.example.mygains.extras.utils

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState

class FormatterUtils {

    fun getUserNameFirstNameScondName(string: String):MutableList<String>{

        val list:MutableList<String> = mutableListOf<String>()
        val s= string.split(" ")

        list.add(s[0])
        list.add(s[1])
        list.add(s[2])

        return list
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun formatHour(time: TimePickerState):String{

        val hour= time.hour
        val minute= time.minute
        val formattedTime = String.format("%02d:%02d", hour, minute)
        return formattedTime
    }
}