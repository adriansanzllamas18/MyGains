package com.example.mygains.createroutineprocess.domain.usecases.workouts

import com.example.mygains.base.response.BaseResponse
import com.example.mygains.createroutineprocess.data.models.TypeOfWorkOutModel
import com.example.mygains.createroutineprocess.data.repositoryimpl.strength.CreateRoutineRepositoryImpl
import com.example.mygains.createroutineprocess.data.repositoryimpl.workouts.TypeOfWorkoutRepositoryImpl
import javax.inject.Inject

class TypeOfWorkoutsUseCase @Inject constructor(private var typeOfWorkoutRepositoryImpl: TypeOfWorkoutRepositoryImpl) {

    suspend fun getAllWorkouts(): BaseResponse<MutableList<TypeOfWorkOutModel>> {
        return typeOfWorkoutRepositoryImpl.getAllTrainingData()
    }
}