package com.example.mygains.exercisesplan.data.models

import com.example.mygains.routinedetail.data.RoutineDetailModel

data class RoutineDayData( var date: String="", var id: String="", var exerciseType:String="", var exercises: Exercises = Exercises(), var timeOfDay:String=""){

    fun isEmpty(): Boolean {
        return if (exercises.muscle.isNotEmpty()&&exercises.excerciseName.isNotEmpty() && !exercises.excercisesListAnyEmpty()&&timeOfDay.isNotEmpty()) false else true
    }

    fun toRoutineDetailModel():RoutineDetailModel{
        return RoutineDetailModel(
           RoutineDayData(date, setRoutineId(),exerciseType,exercises,timeOfDay),
            date = date
        )
    }

    fun setRoutineId():String{
        return date+exerciseType+exercises.muscle+timeOfDay
    }
}

