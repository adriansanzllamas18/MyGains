package com.example.mygains.base


sealed class BaseAuthError: BaseResponseError() {

    object EmailAlreadyExists : BaseAuthError()
    object InvalidCredentials : BaseAuthError()
    object UserNotFound : BaseAuthError()
    object NetworkError : BaseAuthError()
    data class UnknownError(val message: String?) : BaseAuthError()

    fun mapAuthErrorToMessage(error: BaseAuthError): String {
        return when (error) {
            is BaseAuthError.EmailAlreadyExists -> "Este correo ya está registrado. Intenta con otro."
            is BaseAuthError.InvalidCredentials -> "El correo o contraseña no tienen un formato válido."
            is BaseAuthError.UserNotFound -> "El usuario no existe o ha sido eliminado."
            is BaseAuthError.NetworkError -> "Error de conexión. Verifica tu Internet e intenta nuevamente."
            is BaseAuthError.UnknownError -> "Error interno."
        }
    }
}

