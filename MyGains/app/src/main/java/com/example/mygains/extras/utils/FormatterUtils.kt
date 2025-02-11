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

    fun formatNumber(input: String): String {
        // Detectar si el separador decimal es una coma o punto y limpiar el input
        val cleanedInput = if (input.contains(".") && input.contains(",")) {
            // Formato europeo: 1.234,56 -> 1234.56
            input.replace(".", "").replace(",", ".")
        } else {
            // Formato americano: 1,234.56 -> 1234.56
            input.replace(",", "")
        }

        // Convertir el valor a Double
        val number = cleanedInput.toDoubleOrNull()

        return if (number != null) {
            // Verificar si el número es un entero (sin parte decimal significativa)
            if (number % 1 == 0.0) {
                // Devolver el número como entero
                number.toInt().toString()
            } else {
                // Formatear a 2 decimales y cambiar el separador decimal a coma
                String.format("%.2f", number).replace(".", ",")
            }
        } else {
            "Número inválido"
        }
    }

    fun getImageCardByType(type:String):Int{
        return when(type){
             "P" ->R.drawable.proteina
             "H" ->R.drawable.pan
             "G" ->R.drawable.grasas_trans
             "T" ->R.drawable.target
             "E" ->R.drawable.ejercicio
             "S" ->R.drawable.sueno
             else->R.drawable.calorias
        }
    }

    fun getSliderImageByType(type:String):Int{
        return when(type){
            "Fuerza" ->R.drawable.fuerza_slider
            "Cardio" ->R.drawable.cardio_slider
            else->R.drawable.fuerza_slider
        }
    }

}