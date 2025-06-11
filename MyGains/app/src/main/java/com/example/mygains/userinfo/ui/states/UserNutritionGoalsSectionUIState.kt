package com.example.mygains.userinfo.ui.states

import com.example.mygains.userinfo.data.models.UserNutritionDataModel

sealed class UserNutritionGoalsSectionUIState {

    object Loading : UserNutritionGoalsSectionUIState()
    data class Succes(val userNutritionData: UserNutritionDataModel): UserNutritionGoalsSectionUIState()
    data class Error(val error :String): UserNutritionGoalsSectionUIState()
    object NoDataYet : UserNutritionGoalsSectionUIState()

}