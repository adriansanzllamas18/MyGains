package com.example.mygains.dashboard.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mygains.base.response.BaseResponse
import com.example.mygains.dashboard.data.models.HomeScreenModel
import com.example.mygains.dashboard.domain.usecases.DashBoardUseCase
import com.example.mygains.userinfo.domain.usecases.UserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(private var infoUseCase: UserInfoUseCase, private var dashBoardUseCase: DashBoardUseCase):ViewModel() {


    private val _uiState = MutableStateFlow<DashBoardUIState> (DashBoardUIState.Loading)
    val uiState :StateFlow<DashBoardUIState> = _uiState


    init {
        getUserData()
    }

    fun getUserData(){
         viewModelScope.launch(Dispatchers.IO) {
             when( val response = dashBoardUseCase.readInfoUser()){
                 is BaseResponse.Error->{
                     _uiState.emit(DashBoardUIState.Error(response.mapError()))
                 }
                 is BaseResponse.Success->{
                     _uiState.emit(DashBoardUIState.Succes(HomeScreenModel( userDataModel = response.data)))
                 }
             }

         }
    }

    fun getMotivationText():String{
        val motive = mutableListOf<String>("Que no decaiga.","Cada paso cuenta.","¡Sigue adelante, el esfuerzo siempre da frutos!","No importa lo difícil, ¡tú eres más fuerte que el reto!")
        return motive.random()
    }

}