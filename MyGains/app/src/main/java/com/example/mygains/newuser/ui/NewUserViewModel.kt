package com.example.mygains.newuser.ui

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mygains.base.response.BaseResponse
import com.example.mygains.newuser.domain.NewUserUseCase
import com.example.mygains.userinfo.data.models.UserDataModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewUserViewModel @Inject constructor(private var newUserUseCase: NewUserUseCase): ViewModel() {

    private val _user= MutableLiveData<UserDataModel>(UserDataModel())
    val userLive : LiveData<UserDataModel> = _user

    private val _button= MutableLiveData<Boolean>()
    val buttonLive : LiveData<Boolean> = _button

    private val _textInfo= MutableLiveData<Boolean>()
    val textInfoLive : LiveData<Boolean> = _textInfo

    private val _result= MutableLiveData<String>()
    val resultLive : LiveData<String> = _result

    private val _isLoading= MutableLiveData<Boolean>()
    val isLoadingLive :LiveData<Boolean> = _isLoading

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResultLive: LiveData<Boolean> get() = _loginResult

    private val _isAlert= MutableLiveData<Boolean>()
    val isAlertLive :LiveData<Boolean> = _isAlert


    fun showAlert(view:Boolean){
        if (view){
            _isAlert.postValue(true)
        }else{
            _isAlert.postValue(false)
        }
    }


    fun onParamsChanged(userDataModel: UserDataModel){
        _user.value = userDataModel
        _button.value= isAllenteredEnable(userDataModel)
        _textInfo.value = isAllenteredEnable(userDataModel)
    }

    fun createUser(userDataModel: UserDataModel){
        _isLoading.value=true
        viewModelScope.launch(Dispatchers.IO) {

            try {
                when( val response = newUserUseCase.createUserWithEmailPass(userDataModel)){
                    is BaseResponse.Error ->{
                        _result.postValue(response.mapError())
                        _isAlert.postValue(true)
                        _isLoading.postValue(false)
                    }
                    is BaseResponse.Success->{_result.postValue(response.data)}
                }
            }catch (ex:Exception){
                _result.postValue("Error interno intentelo más tarde.")
            }

        }
    }

    fun isAllenteredEnable(userDataModel: UserDataModel) =
        Patterns.EMAIL_ADDRESS.matcher(userDataModel.email).matches() && userDataModel.pass.length > 6 && userDataModel.name.isNotEmpty()
                && userDataModel.first_name.isNotEmpty()  && userDataModel.second_name.isNotEmpty()

}


