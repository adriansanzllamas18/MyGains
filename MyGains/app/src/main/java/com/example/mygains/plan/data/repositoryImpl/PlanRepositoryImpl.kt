package com.example.mygains.plan.data.repositoryImpl

import com.example.mygains.exercisesplan.data.models.RoutineDayData
import com.example.mygains.plan.domain.repositroyInterfaces.PlanRepositoryInterface
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PlanRepositoryImpl @Inject constructor(private var firestore: FirebaseFirestore, private var firebaseAuth: FirebaseAuth ): PlanRepositoryInterface {
    override suspend fun getPlanForTheDay(date: String): MutableList<RoutineDayData> {
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