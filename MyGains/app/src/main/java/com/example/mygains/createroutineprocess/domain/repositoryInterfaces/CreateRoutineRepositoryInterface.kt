package com.example.mygains.createroutineprocess.domain.repositoryInterfaces

import com.example.mygains.base.BaseResponse
import com.example.mygains.createroutineprocess.data.models.InfoTypeOfWorkOutModel
import com.example.mygains.createroutineprocess.data.models.StrengthExerciseModel
import com.example.mygains.createroutineprocess.data.models.TypeOfWorkOutModel

interface CreateRoutineRepositoryInterface {

    suspend fun getAllTrainingData():BaseResponse<MutableList<TypeOfWorkOutModel>>
    suspend fun getAllInfoWorkOuts(workout_id:String):BaseResponse<MutableList<InfoTypeOfWorkOutModel>>
    suspend fun getAllExercises(muscle_id:String):BaseResponse<MutableList<StrengthExerciseModel>>

}