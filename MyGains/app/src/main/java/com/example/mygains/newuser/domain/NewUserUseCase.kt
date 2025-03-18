package com.example.mygains.newuser.domain

import android.accounts.NetworkErrorException
import android.util.Log
import com.example.mygains.base.BaseAuthError
import com.example.mygains.base.BaseResponse
import com.example.mygains.userinfo.data.models.UserData
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.gson.internal.bind.util.ISO8601Utils
import kotlinx.coroutines.tasks.await
import okio.IOException
import javax.inject.Inject

class NewUserUseCase @Inject constructor(private var  firebaseAuth: FirebaseAuth,private var db:FirebaseFirestore) {

    suspend fun createUserWithEmailPass(userData: UserData): BaseResponse<String> {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(userData.email,userData.pass).await()
            BaseResponse.Success("Se ha creado con exito.")
        } catch (e: FirebaseAuthUserCollisionException) {
        BaseResponse.Error(BaseAuthError.EmailAlreadyExists)
        } catch (e: FirebaseAuthInvalidCredentialsException) {
        BaseResponse.Error(BaseAuthError.InvalidCredentials)
        } catch (e: FirebaseAuthInvalidUserException) {
        BaseResponse.Error(BaseAuthError.UserNotFound)
        } catch (e: FirebaseNetworkException) {
        BaseResponse.Error(BaseAuthError.NetworkError)
        } catch (e: Exception) {
        BaseResponse.Error(BaseAuthError.UnknownError(e.message))
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