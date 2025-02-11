package com.example.mygains.routinedetail.domain.repositoryinterfaces

import com.example.mygains.routinedetail.data.RoutineDetailModel

interface RoutineDetailRepositoryInterface {

    suspend fun getRoutineOfDay(currentDate:String): MutableList<RoutineDetailModel>
}