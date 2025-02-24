package com.example.mygains.createroutineprocess.data.repositoryimpl

import android.util.Log
import com.example.mygains.base.BaseAuthError
import com.example.mygains.base.BaseResponse
import com.example.mygains.createroutineprocess.data.models.TypeOfWorkOutModel
import com.example.mygains.createroutineprocess.domain.repositoryInterfaces.CreateRoutineRepositoryInterface
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CreateRoutineRepositoryImpl @Inject constructor(var firestore: FirebaseFirestore, var firebaseAuth: FirebaseAuth):
    CreateRoutineRepositoryInterface {
    override suspend fun getAllTrainingData(): BaseResponse<MutableList<TypeOfWorkOutModel>> {
      return try {
          val uid =  firebaseAuth.currentUser?.uid
          if (uid!= null){
             val data =  firestore.collection("workouts").get().await()
              BaseResponse.Success(data = data.toObjects(TypeOfWorkOutModel::class.java))
          }else{
              BaseResponse.Error(BaseAuthError.UnknownError("Error interno"))
          }

      }catch (ex:Exception){
          BaseResponse.Error(BaseAuthError.UnknownError(ex.message))
      }
    }

}