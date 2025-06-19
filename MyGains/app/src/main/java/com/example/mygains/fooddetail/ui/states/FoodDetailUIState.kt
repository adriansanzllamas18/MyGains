package com.example.mygains.fooddetail.ui.states

import com.example.mygains.fooddetail.data.OpenFoodModel
import com.example.mygains.userinfo.data.models.UserDataModel
import com.example.mygains.userinfo.ui.states.UserProfileSectionUIState

sealed class FoodDetailUIState {

    object Loading : FoodDetailUIState()
    data class Succes(val openFoodModel: OpenFoodModel): FoodDetailUIState()
    data class Error(val error :String): FoodDetailUIState()
}