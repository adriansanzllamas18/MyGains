package com.example.mygains.repositories

import com.example.mygains.base.response.BaseResponse
import com.example.mygains.base.response.errorresponse.BaseFireStoreError
import com.example.mygains.userinfo.data.models.UserDataModel
import com.example.mygains.userinfo.data.models.UserHealthDataModel
import com.example.mygains.userinfo.data.models.UserNutritionDataModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserInfoRepository @Inject constructor(private var firestore: FirebaseFirestore,private var firebaseAuth: FirebaseAuth) {


    suspend fun readUserInfo(): BaseResponse<UserDataModel> {
        val currentUser = firebaseAuth.currentUser

         try {
            if (currentUser != null) {
                val result = firestore.collection("users").document(currentUser.uid).get().await()
                return BaseResponse.Success(result.toObject(UserDataModel::class.java)!!)
            } else {
                return BaseResponse.Error(BaseFireStoreError.UnknownError)
            }

        } catch (ex: FirebaseFirestoreException) {
            return BaseResponse.Error(BaseFireStoreError.UnknownError)
        }
    }

    suspend fun readUserHealthData():BaseResponse<UserHealthDataModel>{

        //TODO ORDENAR POR FECHA PILLAR EL ULTIMO REGISTRO

        return try {
            val uid = firebaseAuth.currentUser?.uid
                ?: return BaseResponse.Error(BaseFireStoreError.UnknownError)

            val result = firestore.collection("users")
                .document(uid)
                .collection("historicUserHealthData")
                .get()
                .await()

            if (result.isEmpty) {
                BaseResponse.Error(BaseFireStoreError.DocumentNotFound)
            } else {
                BaseResponse.Success(result.toObjects(UserHealthDataModel::class.java).last())
            }

        } catch (ex: Exception) {
            BaseResponse.Error(BaseFireStoreError.UnknownError)
        }
    }

    suspend fun readUserUserNutritionGoalsData():BaseResponse<UserNutritionDataModel>{

        return  try {
            val result = firebaseAuth.currentUser?.uid?.let { uid->
                firestore.collection("users")
                    .document(uid)
                    .collection("historicUserNutritionGoalsData")
                    .get()
                    .await()
            }

            if (result == null || result.isEmpty){
                BaseResponse.Error(BaseFireStoreError.DocumentNotFound)
            }else{
                BaseResponse.Success(result.toObjects(UserNutritionDataModel::class.java).last())
            }

        }catch (ex:Exception){
            BaseResponse.Error(BaseFireStoreError.UnknownError)
        }
    }
}