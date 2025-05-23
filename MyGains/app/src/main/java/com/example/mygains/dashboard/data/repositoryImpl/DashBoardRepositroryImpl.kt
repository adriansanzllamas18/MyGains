package com.example.mygains.dashboard.data.repositoryImpl

import com.example.mygains.base.response.BaseResponse
import com.example.mygains.dashboard.domain.repositoryInterfaces.DashBoardRepositroyInterface
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class DashBoardRepositroryImpl @Inject constructor(var firebaseFirestore: FirebaseFirestore) :
    DashBoardRepositroyInterface {
    override suspend fun getRoutineForToday(): BaseResponse<Boolean> {
        return BaseResponse.Success(true)
    }

}