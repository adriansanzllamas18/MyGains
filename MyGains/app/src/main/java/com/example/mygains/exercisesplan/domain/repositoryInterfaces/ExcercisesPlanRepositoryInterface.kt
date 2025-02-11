package com.example.mygains.exercisesplan.domain.repositoryInterfaces

import com.example.mygains.exercisesplan.data.models.RoutineDayData


interface ExcercisesPlanRepositoryInterface {
    suspend fun saveDataRoutine(timeStamp:String,routineDayData : RoutineDayData):Boolean
}