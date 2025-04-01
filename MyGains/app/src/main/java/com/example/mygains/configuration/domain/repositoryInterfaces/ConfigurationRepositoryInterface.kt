package com.example.mygains.configuration.domain.repositoryInterfaces

import com.example.mygains.base.response.BaseResponse


interface ConfigurationRepositoryInterface {
    fun logOut():BaseResponse<Boolean>
}