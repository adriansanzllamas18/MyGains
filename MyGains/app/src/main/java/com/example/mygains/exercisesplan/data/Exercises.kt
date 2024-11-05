package com.example.mygains.exercisesplan.data

data class Exercises(var muscle:String="", var seriesAndReps:MutableList<SeriesAndReps> = mutableListOf(), var excerciseName:String="")
