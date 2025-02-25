package com.example.mygains.createroutineprocess.data.repositoryimpl

import com.example.mygains.base.BaseAuthError
import com.example.mygains.base.BaseResponse
import com.example.mygains.createroutineprocess.data.models.InfoTypeOfWorkOutModel
import com.example.mygains.createroutineprocess.data.models.TypeOfWorkOutModel
import com.example.mygains.createroutineprocess.domain.repositoryInterfaces.CreateRoutineRepositoryInterface
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

class CreateRoutineRepositoryImpl @Inject constructor(var firestore: FirebaseFirestore, var firebaseAuth: FirebaseAuth):
    CreateRoutineRepositoryInterface {
    override suspend fun getAllTrainingData(): BaseResponse<MutableList<TypeOfWorkOutModel>> {
      return try {
          val data =  firestore.collection("workouts").get().await()
          BaseResponse.Success(data = data.toObjects(TypeOfWorkOutModel::class.java))
      }catch (ex:Exception){
          BaseResponse.Error(BaseAuthError.UnknownError(ex.message))
      }
    }

    override suspend fun getAllInfoWorkOuts(workout_id: String): BaseResponse<MutableList<InfoTypeOfWorkOutModel>> {
        try {
            val result= firestore.collection("muscular_groups").whereEqualTo("workout_id", workout_id ).get().await()
            return BaseResponse.Success(result.toObjects(InfoTypeOfWorkOutModel::class.java))

        }catch (ex: IOException){
            return BaseResponse.Error(BaseAuthError.UnknownError(ex.message))
        }
    }

}