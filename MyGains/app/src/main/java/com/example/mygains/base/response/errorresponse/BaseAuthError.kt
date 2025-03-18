package com.example.mygains.base.response.errorresponse


sealed class BaseAuthError: BaseResponseError() {

    object EmailAlreadyExists : BaseAuthError()
    object InvalidCredentials : BaseAuthError()
    object UserNotFound : BaseAuthError()
    object NetworkError : BaseAuthError()
    object TimeOut : BaseAuthError()
    data class UnknownError(val message: String?) : BaseAuthError()

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

