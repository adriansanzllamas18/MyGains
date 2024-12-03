package com.example.mygains.extras.utils

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import com.example.mygains.R

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

    fun getNutriScoreByType(scoreW:String):Int{
        return when (scoreW){
            "a"->{ R.drawable.nutriscore_a}
            "b"->{R.drawable.nutriscore_b}
            "c"->{R.drawable.nutriscore_c}
            "d"->{R.drawable.nutriscore_d}
            "e"->{R.drawable.nutriscore_e}
            else->{R.drawable.no_nutri}
        }
    }
    fun getNovaScoreByType(scoreW:String):Int{
        return when (scoreW){
            "1"->{ R.drawable.nova_1}
            "2"->{R.drawable.nova_2}
            "3"->{R.drawable.nova_3}
            "4"->{R.drawable.nova_4}
            else->{R.drawable.none_nova}
        }
    }

    fun getEcoScoreByType(scoreW:String):Int{
        return when (scoreW){
            "a"->{ R.drawable.eco_a}
            "b"->{R.drawable.eco_b}
            "c"->{R.drawable.eco_c}
            "d"->{R.drawable.eco_d}
            "e"->{R.drawable.eco_e}
            else->{R.drawable.no_eco}
        }
    }

    fun havePalmOil(mayHave:MutableList<String>,have:MutableList<String>):Boolean{
        return mayHave.isNotEmpty()||have.isNotEmpty()
    }

    fun getMoreNutriInfo(list:MutableList<String>):MutableList<String>{
        val traducciones = mapOf(
            "en:vegan" to "Vegano",
            "en:vegetarian" to "Vegetariano",
            "en:non-vegan" to "No Vegano",
            "en:non-vegetarian" to "No Vegetariano",
            "en:palm-oil-free" to "Sin Aceite de Palma",
            "en:with-palm-oil" to "Contiene Aceite de Palma",
            "en:gluten-free" to "Sin Gluten",
            "en:lactose-free" to "Sin Lactosa",
            "en:organic" to "Orgánico",
            "en:fair-trade" to "Comercio Justo",
            "en:vegetarian-status-unknown" to "Estado Vegetariano Desconocido",
            "en:vegan-status-unknown" to "Estado Vegano Desconocido",
            "en:palm-oil-content-unknown" to "Contenido de Aceite de Palma Desconocido",
            "en:may-contain-palm-oil" to "Puede contener aceite de palma",
            "en:maybe-vegan" to "Quizá vegano",
            "en:maybe-vegetarian" to "Quizá vegetariano",
            "en:nuts" to "Contiene Frutos secos",
            "en:soy" to "Contiene Soja",
            "en:milk" to "Contiene Leche",
            "en:eggs" to "Contiene Huevos",
            "en:gluten" to "Contiene Gluten",
            "en:peanuts" to "Contiene Cacahuetes",
        )


        // Filtra y traduce los tags usando el mapa de traducción
        return list.mapNotNull { traducciones[it] }.toMutableList()
    }


    fun getTagIcon(tag:String):Int{
       return when(tag){
            "Vegano"->{R.drawable.vegan}
            "Vegetariano"->{R.drawable.vegetarian}
            "No Vegano"->{R.drawable.vegan}
            "No Vegetariano"->{R.drawable.vegetarian}
            "Sin Aceite de Palma"->{R.drawable.leaf}
            "Contiene Aceite de Palma"->{R.drawable.palm}
            "Sin Gluten"->{R.drawable.gluten_free}
            "Sin Lactosa"->{R.drawable.lactose}
            "Orgánico"->{R.drawable.bio}
            "Estado Vegetariano Desconocido"->{R.drawable.vegetarian}
            "Estado Vegano Desconocido"->{R.drawable.vegan}
            "Contenido de Aceite de Palma Desconocido"->{R.drawable.leaf}
            "Puede contener aceite de palma"->{R.drawable.leaf}
            "Quizá vegano"->{R.drawable.vegan}
            "Quizá vegetariano"->{R.drawable.vegetarian}
            "Contiene Soja" ->{R.drawable.soja}
            "Contiene Leche" ->{R.drawable.milk_bottle}
            "Contiene Huevos" ->{R.drawable.egg}
            "Contiene Gluten" ->{R.drawable.gluten}
            "Contiene Cacahuetes" ->{R.drawable.manteca_de_cacahuete}
            "Contiene Frutos secos" ->{R.drawable.frutossecos}
            else->{0}
        }
    }

    fun formatDouble(value: Double): String {
        // Convertir a String con dos decimales, pero sin redondear
        val formatted = "%f".format(value).substringBefore(".") + "." + value.toString().substringAfter(".").padEnd(2, '0').take(2)

        // Si termina en ".00" o ".0", devolver solo la parte entera
        return if (formatted.endsWith(".00") || formatted.endsWith(".0")) {
            formatted.substringBefore(".")
        } else {
            formatted
        }
    }

}