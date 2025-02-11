package com.example.mygains.exercisesplan.data.models

data class RoutineDayData(var date: String="", var exerciseType:String="", var exercises: Exercises = Exercises(), var timeOfDay:String=""){

    fun isEmpty(): Boolean {
        return if (exercises.muscle.isNotEmpty()&&exercises.excerciseName.isNotEmpty() && !exercises.excercisesListAnyEmpty()&&timeOfDay.isNotEmpty()) false else true
    }
}

