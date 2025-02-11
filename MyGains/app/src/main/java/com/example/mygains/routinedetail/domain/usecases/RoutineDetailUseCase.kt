package com.example.mygains.routinedetail.domain.usecases

import com.example.mygains.routinedetail.data.RoutineDetailModel
import com.example.mygains.routinedetail.data.repository.RoutineDetailRepositoryImpl
import com.example.mygains.routinedetail.domain.usecases.usecaseinterfaces.RoutineDetailUsecaseinterface
import javax.inject.Inject

class RoutineDetailUseCase @Inject constructor(val repositoryImpl: RoutineDetailRepositoryImpl):RoutineDetailUsecaseinterface {
    override suspend fun getRoutineOfDay(currentDate: String):  MutableList<RoutineDetailModel> {
        return repositoryImpl.getRoutineOfDay(currentDate)
    }

}