package com.example.mygains.routinedetail.domain.usecases.usecaseinterfaces

import com.example.mygains.routinedetail.data.RoutineDetailModel

interface RoutineDetailUsecaseinterface {

    suspend fun getRoutineOfDay(currentDate:String): RoutineDetailModel?
}