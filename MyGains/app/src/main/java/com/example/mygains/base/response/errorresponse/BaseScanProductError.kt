package com.example.mygains.base.response.errorresponse

sealed class BaseScanProductError():BaseResponseError() {

    object ProductNotFound : BaseScanProductError()
    object UnknownError : BaseScanProductError()
    object TimeOut : BaseScanProductError()


    fun mapProductScanErrorToMessage(error:BaseScanProductError): String {
        return when (error) {
            is ProductNotFound -> "Ups, No hemos encontrado datos para este alimento."
            is UnknownError -> "Error desconocido , intentelo más tarde."
            is TimeOut  -> "La operación excedió el tiempo límite, intentelo más tarde o compruebe su conexión a internet"
        }
    }
}