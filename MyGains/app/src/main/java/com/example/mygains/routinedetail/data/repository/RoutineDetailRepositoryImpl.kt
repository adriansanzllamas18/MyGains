package com.example.mygains.routinedetail.data.repository

import com.example.mygains.routinedetail.data.RoutineDetailModel
import com.example.mygains.routinedetail.domain.repositoryinterfaces.RoutineDetailRepositoryInterface
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import okhttp3.internal.wait
import okio.IOException
import javax.inject.Inject

class RoutineDetailRepositoryImpl @Inject constructor( var firestore: FirebaseFirestore, var uid: String?):RoutineDetailRepositoryInterface{
    override suspend fun getRoutineOfDay(currentDate:String): RoutineDetailModel? {

       try {

          val result=  uid?.let {
               firestore
               .collection("users")
               .document(it)
               .collection("routineOfDay")
               .whereEqualTo("date",currentDate).get().await()
           }

          if (result != null && !result.isEmpty) {
              return result.documents.mapNotNull { document->
                  document.toObject(RoutineDetailModel::class.java)
              }.first()
          } else return null

       }catch (ex: Exception){
           return null
       }

    }

}