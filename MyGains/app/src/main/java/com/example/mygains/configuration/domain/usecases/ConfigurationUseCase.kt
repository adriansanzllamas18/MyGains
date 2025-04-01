package com.example.mygains.configuration.domain.usecases

import com.example.mygains.base.response.BaseResponse
import com.example.mygains.configuration.data.repository.ConfigurationRepositoryImpl
import javax.inject.Inject

class ConfigurationUseCase @Inject constructor(private var configurationRepositoryImpl: ConfigurationRepositoryImpl) {

    fun logOut():BaseResponse<Boolean>{
        return configurationRepositoryImpl.logOut()
    }
}