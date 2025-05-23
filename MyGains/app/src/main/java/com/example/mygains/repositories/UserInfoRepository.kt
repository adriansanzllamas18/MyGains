package com.example.mygains.repositories

import com.example.mygains.base.response.BaseResponse
import com.example.mygains.base.response.errorresponse.BaseFireStoreError
import com.example.mygains.base.response.errorresponse.BaseResponseError
import com.example.mygains.userinfo.data.models.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserInfoRepository @Inject constructor(private var firestore: FirebaseFirestore,private var firebaseAuth: FirebaseAuth) {


    suspend fun readUserInfo(): BaseResponse<UserData> {
        val currentUser = firebaseAuth.currentUser

         try {
            if (currentUser != null) {
                val result = firestore.collection("users").document(currentUser.uid).get().await()
                return BaseResponse.Success(result.toObject(UserData::class.java)!!)
            } else {
                return BaseResponse.Error(BaseFireStoreError.UnknownError)
            }

        } catch (ex: FirebaseFirestoreException) {
            return BaseResponse.Error(BaseFireStoreError.UnknownError)
        }
    }
}