package com.example.mygains.plan.domain

import com.example.mygains.exercisesplan.data.RoutineDayData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PlanUseCase @Inject constructor(private var firestore: FirebaseFirestore, private var firebaseAuth: FirebaseAuth){


    suspend fun getPlanForTheDay(date:String):MutableList<RoutineDayData> {
        var uid = firebaseAuth.currentUser?.uid ?: ""

        var list= mutableListOf<RoutineDayData>()
        if (uid.isNotEmpty()) {

            val result = firestore.collection("users")
                .document(uid)
                .collection("historicRoutinePlans")
                .where(Filter.equalTo("date",date))
                .orderBy("timeOfDay", Query.Direction.ASCENDING)
                .get().await()

           list= result.documents.mapNotNull {document->
               document.toObject(RoutineDayData::class.java)
            }.toMutableList()

        }

        return list
    }

}