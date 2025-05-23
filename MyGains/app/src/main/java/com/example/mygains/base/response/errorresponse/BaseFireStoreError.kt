package com.example.mygains.base.response.errorresponse

sealed class BaseFireStoreError : BaseResponseError() {

    object DocumentNotFound : BaseFireStoreError()
    object Unavailable : BaseFireStoreError()
    object UnknownError : BaseFireStoreError()
    object TimeOut : BaseFireStoreError()


    fun mapFireStoreErrorToMessage(error: BaseFireStoreError): String {
        return when (error) {
            is DocumentNotFound -> "Ups, datos no encontrados."
            is UnknownError -> "Error desconocido , intentelo más tarde."
            is TimeOut -> "La operación excedió el tiempo límite. Los datos se guardarán automáticamente cuando la conexión a Internet esté disponible."
            is Unavailable -> "Servicio no disponible."
        }
    }
}