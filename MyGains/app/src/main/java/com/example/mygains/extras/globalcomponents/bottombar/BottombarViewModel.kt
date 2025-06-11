package com.example.mygains.extras.globalcomponents.bottombar

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mygains.base.response.BaseResponse
import com.example.mygains.userinfo.data.models.UserDataModel
import com.example.mygains.userinfo.domain.usecases.UserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BottombarViewModel @Inject constructor(private var infoUseCase: UserInfoUseCase):ViewModel() {


    // esto se deberia de pillar de la cache accediendo a room

    private val _UserDataModel= MutableLiveData<UserDataModel>()
    val userDataModelLive : LiveData<UserDataModel> = _UserDataModel

    init {
        getUserData()
        Log.i("viewmodel", " se crea bottom")

    }

    fun getUserData(){
        viewModelScope.launch(Dispatchers.IO) {

            when(val response = infoUseCase.getUserInfo()){
                is BaseResponse.Success->{
                    _UserDataModel.postValue(response.data)
                }
                is BaseResponse.Error->{}
            }
        }
    }
}
