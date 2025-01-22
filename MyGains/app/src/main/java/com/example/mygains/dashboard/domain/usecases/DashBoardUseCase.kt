package com.example.mygains.dashboard.domain

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class DashBoardUseCase @Inject constructor(var firebaseAuth: FirebaseAuth , var firebaseFirestore: FirebaseFirestore) {

}