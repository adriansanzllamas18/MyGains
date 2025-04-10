package com.example.mygains.configuration.data.repository

import com.example.mygains.base.response.BaseResponse
import com.example.mygains.base.response.errorresponse.BaseAuthError
import com.example.mygains.configuration.domain.repositoryInterfaces.ConfigurationRepositoryInterface
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import okhttp3.internal.wait
import javax.inject.Inject

class ConfigurationRepositoryImpl @Inject constructor(private var auth: FirebaseAuth): ConfigurationRepositoryInterface {
    override fun logOut(): BaseResponse<Boolean> {
        return try {
            auth.signOut()
            BaseResponse.Success(true)
        } catch (e: FirebaseAuthInvalidUserException) {
            BaseResponse.Error(BaseAuthError.UserNotFound)
        } catch (e: FirebaseNetworkException) {
            BaseResponse.Error(BaseAuthError.NetworkError)
        } catch (e: Exception) {
            BaseResponse.Error(BaseAuthError.UnknownError("Ha ocurrido un error interno."))
        }
    }
}