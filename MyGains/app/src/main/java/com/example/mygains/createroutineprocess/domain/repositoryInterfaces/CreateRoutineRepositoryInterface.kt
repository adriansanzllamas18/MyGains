package com.example.mygains.createroutineprocess.domain.repositoryInterfaces

import com.example.mygains.base.BaseResponse
import com.example.mygains.createroutineprocess.data.models.TypeOfWorkOutModel

interface CreateRoutineRepositoryInterface {
    //esto tenemos que cambiarlo
    suspend fun getAllTrainingData():BaseResponse<MutableList<TypeOfWorkOutModel>>
}