package com.example.mygains.userinfo.data.repositoryImpl

import com.example.mygains.userinfo.data.models.UserDataModel
import com.example.mygains.userinfo.data.models.WeightRegister
import com.example.mygains.userinfo.domain.repositoryInterfaces.UserInfoInterface
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import javax.inject.Inject

class UserInfoImpl @Inject constructor(private var firestore: FirebaseFirestore, private var firebaseAuth: FirebaseAuth):UserInfoInterface{
    override suspend fun readUserInfo(): UserDataModel? {
        val result =
            firebaseAuth.currentUser?.let{ firestore.collection("users").document(it.uid).get().await() }

        return if (result != null) {
            if (result.exists()){
                return result.toObject(UserDataModel::class.java)
            }else
                return UserDataModel()
        }else  UserDataModel()
    }


    override suspend fun saveWeight(weightRegister: WeightRegister): WeightRegister {
        var weightRegisterData = hashMapOf(
            "date" to weightRegister.date,
            "weight" to weightRegister.weight
        )
        val uid = firebaseAuth.currentUser?.uid ?: throw IllegalStateException("UID no disponible")

        return   try {
            var result= firestore.collection("users").document(uid).collection("historicWeight")
                .document(LocalDateTime.now().toString()).set(weightRegisterData)
                .await()

            return weightRegister

        }catch (e:Exception){

            return WeightRegister()
        }

    }

    override suspend fun saveWeightInInfoUser(weightRegister: WeightRegister) {
        var weightRegisterData = hashMapOf(
            "weight" to weightRegister.weight,
            "lastUpdateWeight" to weightRegister.date
        )
        val uid = firebaseAuth.currentUser?.uid ?: throw IllegalStateException("UID no disponible")
        //el setoptions.merge hace que solo se actualice el dato y si no existe lo crea
        firestore.collection("users").document(uid).set(weightRegisterData, SetOptions.merge()).await()
    }

    override suspend fun getAllWeightsByTime(startDate: String, endDate: String): List<WeightRegister> {
        val uid = firebaseAuth.currentUser?.uid ?: throw IllegalStateException("UID no disponible")

        val result = firestore.collection("users")
            .document(uid)
            .collection("historicWeight")
            .whereGreaterThanOrEqualTo("date", startDate)
            .whereLessThanOrEqualTo("date", endDate)
            .get().await()

        return result.documents.mapNotNull { document ->
            val weight = document.getString("weight")
            val date = document.getString("date")

            if (weight != null && date != null) {
                WeightRegister(weight = weight, date = date)
            } else null
        }
    }

}