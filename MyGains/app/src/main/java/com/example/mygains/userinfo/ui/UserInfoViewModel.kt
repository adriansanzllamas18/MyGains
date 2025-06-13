package com.example.mygains.userinfo.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mygains.base.response.BaseResponse
import com.example.mygains.base.response.errorresponse.BaseFireStoreError
import com.example.mygains.userinfo.data.models.UserNutritionDataModel
import com.example.mygains.userinfo.domain.usecases.UserInfoUseCase
import com.example.mygains.userinfo.ui.states.UserNutritionGoalsSectionUIState
import com.example.mygains.userinfo.ui.states.UserProfileHealthSectionUIState
import com.example.mygains.userinfo.ui.states.UserProfileSectionUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(private var userInfoUseCase: UserInfoUseCase):ViewModel() {



    private val _uiState = MutableStateFlow<UserProfileSectionUIState>(UserProfileSectionUIState.Loading)
    val uiState : StateFlow<UserProfileSectionUIState> = _uiState

    private val _healthSateSecton = MutableStateFlow<UserProfileHealthSectionUIState>(UserProfileHealthSectionUIState.Loading)
    val healthSateSecton : StateFlow<UserProfileHealthSectionUIState> = _healthSateSecton

    private val _nutritionSectionSate = MutableStateFlow<UserNutritionGoalsSectionUIState>(UserNutritionGoalsSectionUIState.Loading)
    val nutritionSectonSate : StateFlow<UserNutritionGoalsSectionUIState> = _nutritionSectionSate

    private val _refreshing =  MutableLiveData<Boolean> (false)
    val refreshingLife :MutableLiveData<Boolean> = _refreshing

    init {
        readUserInfo()
        readUserHealthData()
        readUserNutritionGoalsData()
        readUsercurrentNutritionData()

    }

    private fun readUserInfo(){
        viewModelScope.launch (Dispatchers.IO){
            when(val response = userInfoUseCase.getUserInfo()){
                is BaseResponse.Success->{
                    _uiState.emit(UserProfileSectionUIState.Succes(response.data))
                    _refreshing.postValue(false)
                }
                is BaseResponse.Error->{
                    _uiState.emit(UserProfileSectionUIState.Error(response.mapError()))
                    _refreshing.postValue(false)
                }
            }
        }
    }

    private fun readUserHealthData(){

        viewModelScope.launch (Dispatchers.IO){
            delay(1500)
            when(val response = userInfoUseCase.readUserHealthData()){
                is BaseResponse.Success->{
                    _healthSateSecton.emit(UserProfileHealthSectionUIState.Succes(response.data))
                    _refreshing.postValue(false)
                }
                is BaseResponse.Error->{
                    if (response.error == BaseFireStoreError.DocumentNotFound){
                        _healthSateSecton.emit(UserProfileHealthSectionUIState.NodataYet)
                    }else{
                        _healthSateSecton.emit(UserProfileHealthSectionUIState.Error(response.mapError()))
                    }
                    _refreshing.postValue(false)
                }
            }
        }
    }


    private fun readUserNutritionGoalsData(){
        viewModelScope.launch (Dispatchers.IO){
            when(val response = userInfoUseCase.readusergoal()){
                is BaseResponse.Success->{
                    _nutritionSectionSate.getAndUpdate { currentState ->
                        when (val state = currentState) {
                            is UserNutritionGoalsSectionUIState.Succes -> {
                                // Preserva los datos existentes y actualiza solo goals
                                UserNutritionGoalsSectionUIState.Succes(
                                    state.userNutritionData.copy(
                                        userNutritionGoalsDataModel = response.data
                                    )
                                )
                            }
                            else -> {
                                // Si no hay estado previo, crea uno nuevo
                                UserNutritionGoalsSectionUIState.Succes(
                                    UserNutritionDataModel(userNutritionGoalsDataModel = response.data)
                                )
                            }
                        }
                    }
                    _refreshing.postValue(false)
                }
                is BaseResponse.Error->{
                    if (response.error == BaseFireStoreError.DocumentNotFound){
                        _nutritionSectionSate.emit(UserNutritionGoalsSectionUIState.NoDataYet)
                    }else{
                        _nutritionSectionSate.emit(UserNutritionGoalsSectionUIState.Error(response.mapError()))
                    }
                    _refreshing.postValue(false)
                }
            }
        }
    }
    private fun readUsercurrentNutritionData(){
        viewModelScope.launch (Dispatchers.IO){
            when(val response = userInfoUseCase.readusercurrent()){
                is BaseResponse.Success->{
                    _nutritionSectionSate.getAndUpdate { currentState ->
                        when (val state = currentState) {
                            is UserNutritionGoalsSectionUIState.Succes -> {
                                // Preserva los datos existentes y actualiza solo goals
                                UserNutritionGoalsSectionUIState.Succes(
                                    state.userNutritionData.copy(
                                        userCurrentNutritionDataModel = response.data
                                    )
                                )
                            }
                            else -> {
                                // Si no hay estado previo, crea uno nuevo
                                UserNutritionGoalsSectionUIState.Succes(
                                    UserNutritionDataModel(userCurrentNutritionDataModel = response.data)
                                )
                            }
                        }
                    }
                    _refreshing.postValue(false)
                }
                is BaseResponse.Error->{
                    if (response.error == BaseFireStoreError.DocumentNotFound){
                        _nutritionSectionSate.emit(UserNutritionGoalsSectionUIState.NoDataYet)
                    }else{
                        _nutritionSectionSate.emit(UserNutritionGoalsSectionUIState.Error(response.mapError()))
                    }
                    _refreshing.postValue(false)
                }
            }
        }
    }

    fun refreshData(){
        _healthSateSecton.update { (UserProfileHealthSectionUIState.Loading) }
        readUserHealthData()
        readUserNutritionGoalsData()
        readUsercurrentNutritionData()
        _refreshing.postValue(true)
    }
}