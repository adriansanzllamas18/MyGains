package com.example.mygains.base.response.errorresponse

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException


sealed class BaseAuthError: BaseResponseError() {

    object EmailAlreadyExists : BaseAuthError()
    object InvalidCredentials : BaseAuthError()
    object UserNotFound : BaseAuthError()
    object NetworkError : BaseAuthError()
    object TimeOut : BaseAuthError()
    object UnknownError : BaseAuthError()

    fun exceptionToTypeFireStoreException(exception: Exception):BaseAuthError{
        return when (exception){
            is FirebaseAuthUserCollisionException ->{EmailAlreadyExists}
            is FirebaseAuthInvalidCredentialsException ->{InvalidCredentials}
            is FirebaseAuthInvalidUserException ->{UserNotFound}
            is FirebaseNetworkException ->{NetworkError}
            else -> {UnknownError}
        }
    }

    fun mapAuthErrorToMessage(error: BaseAuthError): String {
        return when (error) {
            is EmailAlreadyExists -> "Este correo ya está registrado. Intenta con otro."
            is InvalidCredentials -> "El correo o contraseña no tienen un formato válido."
            is UserNotFound -> "El usuario no existe o ha sido eliminado."
            is NetworkError -> "Error de conexión. Verifica tu Internet e intenta nuevamente."
            is TimeOut -> "La operación excedió el tiempo límite."
            is UnknownError -> "Error interno."
        }
    }
}

