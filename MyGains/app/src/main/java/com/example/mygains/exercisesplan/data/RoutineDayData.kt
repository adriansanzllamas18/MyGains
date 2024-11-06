package com.example.mygains.exercisesplan.data

data class RoutineDayData(var date: String="", var exerciseType:String="", var exercises:Exercises = Exercises()){

    fun isEmpty(): Boolean {
        return if (exercises.muscle.isNotEmpty()&&exercises.excerciseName.isNotEmpty() && !exercises.excercisesListAnyEmpty()) false else true
    }
}

