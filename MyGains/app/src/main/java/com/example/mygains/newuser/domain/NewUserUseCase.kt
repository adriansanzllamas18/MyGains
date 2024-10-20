package com.example.mygains.newuser.domain

import android.util.Log
import com.example.mygains.userinfo.data.UserData
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NewUserUseCase @Inject constructor(private var  firebaseAuth: FirebaseAuth,private var db:FirebaseFirestore) {

    suspend fun createUserWithEmailPass(userData: UserData): AuthResult? {
        return firebaseAuth.createUserWithEmailAndPassword(userData.email,userData.pass).await()
    }

    suspend fun createUserWithGoogleCredentials(account: GoogleSignInAccount?): FirebaseUser? {
        // Asegúrate de que account no sea nulo y de que el idToken esté disponible
        val idToken = account?.idToken ?: throw IllegalArgumentException("El idToken es nulo. Verifica la configuración de Google Sign-In.")

        // Crear credenciales de Firebase
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        // Iniciar sesión con las credenciales
        return try {
            firebaseAuth.signInWithCredential(credential).await().user
        } catch (e: Exception) {
            Log.e("FirebaseAuth", "Error durante la autenticación: ${e.message}")
            null // Retorna null en caso de error
        }
    }


    // Guardar información del usuario en Firestore
    suspend fun saveInfoUser(userData: UserData): Boolean {
        val user = hashMapOf(
            "name" to userData.name,
            "first_name" to userData.first_name,
            "second_name" to userData.second_name,
            "email" to userData.email,
            "image" to userData.image
        )



        val uid = firebaseAuth.currentUser?.uid ?: throw IllegalStateException("UID no disponible")

        return try {
            // Esperamos a que se complete la operación de set()
            db.collection("users").document(uid).set(user,SetOptions.merge()).await()
            true // Retornamos true si la operación fue exitosa
        } catch (e: Exception) {
            false // Retornamos false si ocurrió un error
        }
    }



}