package com.example.mygains.createroutineprocess.domain

import com.example.mygains.base.BaseResponse
import com.example.mygains.createroutineprocess.data.models.TypeOfWorkOutModel
import com.example.mygains.createroutineprocess.data.repositoryimpl.CreateRoutineRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class CreateRoutineUsecase @Inject constructor(var createRoutineRepositoryImpl: CreateRoutineRepositoryImpl) {


    suspend fun getAllWorkouts(): BaseResponse<MutableList<TypeOfWorkOutModel>>{
        return createRoutineRepositoryImpl.getAllTrainingData()
    }
}