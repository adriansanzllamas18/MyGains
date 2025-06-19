package com.example.mygains.fooddetail.domain

import com.example.mygains.base.response.BaseResponse
import com.example.mygains.fooddetail.data.OpenFoodModel
import com.example.mygains.repositories.ScannerRepository
import javax.inject.Inject

class FoodDetailUsecase @Inject constructor(var scannerRepository: ScannerRepository) {

    suspend fun getProductByBarCode(barCode:String):BaseResponse<OpenFoodModel>{
        return scannerRepository.getProductByCodeBar(barCode)
    }
}