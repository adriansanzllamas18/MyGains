package com.example.mygains.plan.domain.usecases.usecaseInterfaces

import com.example.mygains.exercisesplan.data.models.RoutineDayData

interface PlanUsecaseInterface {
    suspend fun getPlanForTheDay(date:String):MutableList<RoutineDayData>
}