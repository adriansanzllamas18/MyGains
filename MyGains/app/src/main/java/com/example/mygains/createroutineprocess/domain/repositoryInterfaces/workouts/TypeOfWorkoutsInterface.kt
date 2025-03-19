package com.example.mygains.createroutineprocess.domain.repositoryInterfaces.workouts

import com.example.mygains.base.response.BaseResponse
import com.example.mygains.createroutineprocess.data.models.TypeOfWorkOutModel

interface TypeOfWorkoutsInterface {
    suspend fun getAllTrainingData(): BaseResponse<MutableList<TypeOfWorkOutModel>>
}