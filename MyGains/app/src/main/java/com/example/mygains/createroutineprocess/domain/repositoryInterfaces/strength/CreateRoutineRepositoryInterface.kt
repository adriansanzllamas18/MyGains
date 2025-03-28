package com.example.mygains.createroutineprocess.domain.repositoryInterfaces.strength

import com.example.mygains.base.response.BaseResponse
import com.example.mygains.createroutineprocess.data.models.DailyRoutine
import com.example.mygains.createroutineprocess.data.models.InfoTypeOfWorkOutModel
import com.example.mygains.createroutineprocess.data.models.StrengthExerciseModel
import com.example.mygains.createroutineprocess.data.models.TypeOfWorkOutModel

interface CreateRoutineRepositoryInterface {

    suspend fun getAllInfoWorkOuts(workout_id:String): BaseResponse<MutableList<InfoTypeOfWorkOutModel>>
    suspend fun getAllExercises(muscle_id:String): BaseResponse<MutableList<StrengthExerciseModel>>
    suspend fun saveRoutine(routine: DailyRoutine): BaseResponse<String>

}