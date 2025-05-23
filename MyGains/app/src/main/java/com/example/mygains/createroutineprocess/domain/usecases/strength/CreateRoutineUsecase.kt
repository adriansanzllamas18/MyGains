package com.example.mygains.createroutineprocess.domain.usecases.strength

import com.example.mygains.base.response.errorresponse.BaseAuthError
import com.example.mygains.base.response.BaseResponse
import com.example.mygains.createroutineprocess.data.models.DailyRoutine
import com.example.mygains.createroutineprocess.data.models.ExerciseLog
import com.example.mygains.createroutineprocess.data.models.ExerciseWithSets
import com.example.mygains.createroutineprocess.data.models.InfoTypeOfWorkOutModel
import com.example.mygains.createroutineprocess.data.models.StrengthExerciseModel
import com.example.mygains.createroutineprocess.data.models.TypeOfWorkOutModel
import com.example.mygains.createroutineprocess.data.repositoryimpl.strength.CreateRoutineRepositoryImpl
import com.example.mygains.extras.utils.firebase.FirestoreIdGenerator
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class CreateRoutineUsecase @Inject constructor(private var createRoutineRepositoryImpl: CreateRoutineRepositoryImpl, private var firebaseAuth: FirebaseAuth) {


    suspend fun getAllInfoWorkOuts(workout_id: String): BaseResponse<MutableList<InfoTypeOfWorkOutModel>> {
        return createRoutineRepositoryImpl.getAllInfoWorkOuts(workout_id)
    }

    suspend fun getAllExercises(muscle_id:String): BaseResponse<MutableList<StrengthExerciseModel>> {
        return createRoutineRepositoryImpl.getAllExercises(muscle_id)
    }

    suspend fun saveRoutine(routine:MutableList<ExerciseWithSets>,date:String): BaseResponse<String> {
        val userId = firebaseAuth.currentUser?.uid
        val list= mutableListOf<ExerciseLog>()
        return if (userId!= null){
            val routineId = FirestoreIdGenerator.generateRoutineId(userId)
            routine.forEach { exerciseWithSets ->
                list.add(
                    ExerciseLog(
                    id = FirestoreIdGenerator.generateExerciseId(userId),
                    exerciseId = exerciseWithSets.exercise.exerciseId?:"",
                    routineId = routineId,
                    name = exerciseWithSets.exercise.name?:"",
                    muscleGroup = exerciseWithSets.exercise.muscle_group_id?:"",
                    date = date,
                    sets = exerciseWithSets.sets,
                    duration = 0,
                    notes = ""
                    )
                )
            }
            createRoutineRepositoryImpl.saveRoutine(DailyRoutine(
                id = routineId,
                userId = userId,
                date = date ,
                exercises = list)
            )
        }else{
            BaseResponse.Error(BaseAuthError.UnknownError)
        }
    }
}