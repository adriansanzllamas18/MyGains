package com.example.mygains.exercisesplan.domain.usecases.interfaces

import com.example.mygains.exercisesplan.data.models.RoutineDayData

interface ExcercisesPlanUseCaseInterface {
    suspend fun saveDataRoutine(routineDayData: RoutineDayData):Boolean
}