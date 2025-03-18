package com.example.mygains.base

sealed class BaseFireStoreError : BaseResponseError() {

    object DocumentNotFound : BaseFireStoreError()
    object Unavailable : BaseFireStoreError()
    object UnknownError : BaseFireStoreError()

    fun mapAuthErrorToMessage(error: BaseFireStoreError): String {
        return when (error) {
            is BaseFireStoreError.DocumentNotFound-> "Ups, datos no encontrados."
            is BaseFireStoreError.UnknownError -> "Error desconocido , intentelo más tarde."
            is BaseFireStoreError.Unavailable-> "Servicio no disponible."
        }
    }
}