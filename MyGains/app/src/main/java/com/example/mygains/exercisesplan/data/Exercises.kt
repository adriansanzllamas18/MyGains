package com.example.mygains.exercisesplan.data

data class Exercises(var muscle:String="", var seriesAndReps:MutableList<SeriesAndReps> = mutableListOf(), var excerciseName:String=""){
    fun excercisesListAnyEmpty():Boolean{
        var result= true
        seriesAndReps.forEach {
            if (it.serie != "0" && it.reps != "0" && it.weght != "0") result = false
        }
        return result
    }
}
