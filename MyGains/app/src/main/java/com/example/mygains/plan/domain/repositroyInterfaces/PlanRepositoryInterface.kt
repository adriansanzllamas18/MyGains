package com.example.mygains.plan.domain.repositroyInterfaces

import com.example.mygains.exercisesplan.data.models.RoutineDayData

interface PlanRepositoryInterface {

    suspend fun getPlanForTheDay(date:String):MutableList<RoutineDayData>
}