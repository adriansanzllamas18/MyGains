package com.example.mygains.createroutineprocess.domain.usecases

import com.example.mygains.base.BaseAuthError
import com.example.mygains.base.BaseResponse
import com.example.mygains.createroutineprocess.data.models.DailyRoutine
import com.example.mygains.createroutineprocess.data.models.ExerciseLog
import com.example.mygains.createroutineprocess.data.models.ExerciseSet
import com.example.mygains.createroutineprocess.data.models.InfoTypeOfWorkOutModel
import com.example.mygains.createroutineprocess.data.models.StrengthExerciseModel
import com.example.mygains.createroutineprocess.data.models.TypeOfWorkOutModel
import com.example.mygains.createroutineprocess.data.repositoryimpl.CreateRoutineRepositoryImpl
import com.example.mygains.exercisesplan.data.models.MuscleGroupe
import com.example.mygains.extras.utils.firebase.FirestoreIdGenerator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class CreateRoutineUsecase @Inject constructor(private var createRoutineRepositoryImpl: CreateRoutineRepositoryImpl, private var firebaseAuth: FirebaseAuth) {


    suspend fun getAllWorkouts(): BaseResponse<MutableList<TypeOfWorkOutModel>>{
        return createRoutineRepositoryImpl.getAllTrainingData()
    }

    suspend fun getAllInfoWorkOuts(workout_id: String):BaseResponse<MutableList<InfoTypeOfWorkOutModel>>{
        return createRoutineRepositoryImpl.getAllInfoWorkOuts(workout_id)
    }

    suspend fun getAllExercises(muscle_id:String):BaseResponse<MutableList<StrengthExerciseModel>>{
        return createRoutineRepositoryImpl.getAllExercises(muscle_id)
    }

    suspend fun saveRoutine(routine:MutableList<StrengthExerciseModel>, date:String,sets:MutableList<ExerciseSet>):BaseResponse<String>{
        val userId = firebaseAuth.currentUser?.uid
        val list= mutableListOf<ExerciseLog>()
        if (userId!= null){
            routine.forEach {model->
                list.add(model.toExerciseLogModel(userId = userId, date = date, sets = sets))
            }

            return createRoutineRepositoryImpl.saveRoutine(DailyRoutine(
                id = FirestoreIdGenerator.generateRoutineId(userId),
                userId = userId,
                date = date ,
                exercises = list)
            )
        }else{
            return BaseResponse.Error(BaseAuthError.UnknownError("Error inesperado"))
        }
    }
}