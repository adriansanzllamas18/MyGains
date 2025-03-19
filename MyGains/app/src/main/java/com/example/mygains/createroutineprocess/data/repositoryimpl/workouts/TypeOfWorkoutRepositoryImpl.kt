package com.example.mygains.createroutineprocess.data.repositoryimpl.workouts

import com.example.mygains.base.response.BaseResponse
import com.example.mygains.base.response.errorresponse.BaseAuthError
import com.example.mygains.createroutineprocess.data.models.TypeOfWorkOutModel
import com.example.mygains.createroutineprocess.domain.repositoryInterfaces.workouts.TypeOfWorkoutsInterface
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TypeOfWorkoutRepositoryImpl @Inject constructor(private var firestore: FirebaseFirestore):TypeOfWorkoutsInterface {
    override suspend fun getAllTrainingData(): BaseResponse<MutableList<TypeOfWorkOutModel>> {
        return try {
            val data =  firestore.collection("workouts").get().await()
            BaseResponse.Success(data = data.toObjects(TypeOfWorkOutModel::class.java))
        }catch (ex:Exception){
            BaseResponse.Error(BaseAuthError.UnknownError(ex.message))
        }
    }
}