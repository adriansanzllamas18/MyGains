package com.example.mygains.newuser.domain

import com.example.mygains.base.response.errorresponse.BaseAuthError
import com.example.mygains.base.response.BaseResponse
import com.example.mygains.userinfo.data.models.UserDataModel
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NewUserUseCase @Inject constructor(private var  firebaseAuth: FirebaseAuth,private var db:FirebaseFirestore) {

    suspend fun createUserWithEmailPass(userDataModel: UserDataModel): BaseResponse<String> {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(userDataModel.email,userDataModel.pass).await()
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
        BaseResponse.Error(BaseAuthError.UnknownError)
        }
    }

    // Guardar información del usuario en Firestore
    suspend fun saveInfoUser(userDataModel: UserDataModel): Boolean {
        val user = hashMapOf(
            "name" to userDataModel.name,
            "first_name" to userDataModel.first_name,
            "second_name" to userDataModel.second_name,
            "email" to userDataModel.email,
            "image" to userDataModel.image
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