package com.example.mygains.configuration.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mygains.base.response.BaseResponse
import com.example.mygains.base.response.BaseResponseUi
import com.example.mygains.configuration.domain.usecases.ConfigurationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfigurationViewModel @Inject constructor(private var useCase: ConfigurationUseCase):ViewModel() {

    private var _logOutResult = MutableLiveData<BaseResponseUi> ()
    var logOutResultLife : LiveData<BaseResponseUi> = _logOutResult


    fun logOut(){
        viewModelScope.launch(Dispatchers.IO) {

            when ( val result =useCase.logOut()){
                is BaseResponse.Success -> {_logOutResult.postValue(BaseResponseUi(response = result))}
                is BaseResponse.Error -> {_logOutResult.postValue(BaseResponseUi(response = result))}
            }
        }
    }
}