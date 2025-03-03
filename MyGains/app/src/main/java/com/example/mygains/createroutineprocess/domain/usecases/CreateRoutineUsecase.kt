package com.example.mygains.createroutineprocess.domain.usecases

import com.example.mygains.base.BaseResponse
import com.example.mygains.createroutineprocess.data.models.InfoTypeOfWorkOutModel
import com.example.mygains.createroutineprocess.data.models.StrengthExerciseModel
import com.example.mygains.createroutineprocess.data.models.TypeOfWorkOutModel
import com.example.mygains.createroutineprocess.data.repositoryimpl.CreateRoutineRepositoryImpl
import com.example.mygains.exercisesplan.data.models.MuscleGroupe
import javax.inject.Inject

class CreateRoutineUsecase @Inject constructor(var createRoutineRepositoryImpl: CreateRoutineRepositoryImpl) {


    suspend fun getAllWorkouts(): BaseResponse<MutableList<TypeOfWorkOutModel>>{
        return createRoutineRepositoryImpl.getAllTrainingData()
    }

    suspend fun getAllInfoWorkOuts(workout_id: String):BaseResponse<MutableList<InfoTypeOfWorkOutModel>>{
        return createRoutineRepositoryImpl.getAllInfoWorkOuts(workout_id)
    }

    suspend fun getAllExercises(muscle_id:String):BaseResponse<MutableList<StrengthExerciseModel>>{
        return createRoutineRepositoryImpl.getAllExercises(muscle_id)
    }
}